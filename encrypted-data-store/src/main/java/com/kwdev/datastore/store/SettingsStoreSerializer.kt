package com.kwdev.datastore.store

import androidx.datastore.core.Serializer
import com.kwdev.datastore.Settings
import com.kwdev.datastore.crypto.SettingsCryptography
import java.io.InputStream
import java.io.OutputStream

internal class SettingsStoreSerializer : Serializer<Settings> {

    private val crypto = SettingsCryptography()

    override val defaultValue: Settings
        get() = Settings()

    override suspend fun readFrom(input: InputStream): Settings =
        crypto.decrypt(input)

    override suspend fun writeTo(t: Settings, output: OutputStream) {
        crypto.encrypt(t, output)
    }
}
