package com.tanimi.kmptestapp

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.tanimi.kmptestapp.data.AppDatabase
import com.tanimi.kmptestapp.service.ocr.OCRService
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual object AppContext {
    private var value: WeakReference<Context?>? = null
    fun set(context: Context) {
        value = WeakReference(context)
    }

    internal fun get(): Context {
        return value?.get() ?: throw RuntimeException("Context Error")
    }
}

actual fun getRoomDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val appContext = AppContext.get().applicationContext
    val dbFile = appContext.getDatabasePath("app_room.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}

@OptIn(ExperimentalUuidApi::class)
actual fun getUuid(): String {
    return Uuid.toString()
}

actual fun dateTimeStringNow(): String {
    val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}

actual class OCRServiceImpl: OCRService {
    override suspend fun executeOCR(image: ByteArray): String {
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        val image = InputImage.fromBitmap(bitmap, 0)
        return suspendCoroutine { cont ->
            TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                .process(image)
                .addOnSuccessListener { cont.resume(it.text) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }
    }
}

