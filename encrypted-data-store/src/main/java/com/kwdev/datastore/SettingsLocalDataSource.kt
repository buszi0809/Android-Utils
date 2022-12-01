package com.kwdev.datastore

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import com.kwdev.datastore.store.SettingsStoreSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import timber.log.Timber

class SettingsLocalDataSource(context: Context) {

    private val dataStore = DataStoreFactory.create(
        serializer = SettingsStoreSerializer(),
        produceFile = { context.dataStoreFile("file") },
        corruptionHandler = ReplaceFileCorruptionHandler { exception ->
            Timber.e(exception, "Settings file corrupted")
            Settings()
        },
    )

    val settingsFlow: Flow<Settings>
        get() = dataStore.data

    suspend fun getSettings(): Settings = dataStore.data.first()

    suspend fun setSettings(settings: Settings) {
        dataStore.updateData {
            settings
        }
    }
}
