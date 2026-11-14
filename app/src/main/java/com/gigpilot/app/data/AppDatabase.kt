package com.gigpilot.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SupportFactory

/**
 * Local-first database, encrypted at rest with SQLCipher.
 * The passphrase is generated on first run and protected by the Android Keystore.
 * Nothing here ever leaves the device; there are no accounts in v1.
 */
@Database(entities = [Shift::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shiftDao(): ShiftDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val passphrase = DatabaseKeyManager.getPassphrase(context)
                val factory = SupportFactory(passphrase)
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gigpilot.db"
                )
                    .openHelperFactory(factory)
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
