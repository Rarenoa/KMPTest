package com.tanimi.kmptestapp

import androidx.room.Room
import androidx.room.RoomDatabase
import com.tanimi.kmptestapp.common.Constant
import com.tanimi.kmptestapp.data.AppDatabase
import com.tanimi.kmptestapp.service.ocr.OCRService
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSData
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.create
import platform.UIKit.UIDevice
import platform.Foundation.NSFileManager
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUUID
import platform.Foundation.NSUserDomainMask

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual object AppContext

actual fun getRoomDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val path = documentDirectory() + Constant.kDatabasePath
    return Room.databaseBuilder<AppDatabase>(
        name = path
    )
}

actual fun getUuid(): String {
    return NSUUID.UUID().UUIDString().lowercase()
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}

actual fun dateTimeStringNow(): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = "yyyy/MM/dd HH:mm:ss"
    return dateFormatter.stringFromDate(NSDate())
}

actual class OCRServiceImpl: OCRService {
    @OptIn(ExperimentalForeignApi::class)
    override suspend fun executeOCR(image: ByteArray): String {
        return suspendCancellableCoroutine { cont ->
            val nsData = NSData.create(bytes = image.refTo(0), length = image.size.toULong())
            OCRExecutor.shared.recognizeText(from = nsData) { result, error ->
                if (error != null) {
                    cont.resume("")
                } else {
                    cont.resume(result ?: "")
                }
            }
        }
    }
}