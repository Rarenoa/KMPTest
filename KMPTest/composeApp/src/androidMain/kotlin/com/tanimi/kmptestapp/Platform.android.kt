package com.tanimi.kmptestapp

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tanimi.kmptestapp.data.AppDatabase
import java.lang.ref.WeakReference

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