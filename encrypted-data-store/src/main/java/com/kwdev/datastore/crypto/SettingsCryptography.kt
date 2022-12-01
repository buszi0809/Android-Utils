package com.kwdev.datastore.crypto

import androidx.datastore.core.CorruptionException
import com.kwdev.datastore.Settings
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

internal class SettingsCryptography {

    private val cipherProvider = SettingsCipherProvider()
    private val json = Json

    fun encrypt(settings: Settings, outputStream: OutputStream) {
        val serialized = json.encodeToString(settings)
        val input = serialized.encodeToByteArray()
        val cipher = cipherProvider.getEncryptionCipher()
        val encrypted = cipher.doFinal(input)

        outputStream.use {
            it.write(cipher.iv.size)
            it.write(cipher.iv)
            it.write(encrypted)
        }
    }

    fun decrypt(inputStream: InputStream): Settings =
        inputStream.use {
            val iv = ByteArray(it.read())
            it.read(iv)
            val encrypted = it.readBytes()

            val cipher = cipherProvider.getDecryptionCipher(iv)
            val decrypted = try {
                cipher.doFinal(encrypted)
            } catch (e: Throwable) {
                throw CorruptionException("Failed to decrypt settings", e)
            }

            try {
                json.decodeFromString(decrypted.decodeToString())
            } catch (e: Throwable) {
                throw CorruptionException("Failed to deserialize settings", e)
            }
        }
}
