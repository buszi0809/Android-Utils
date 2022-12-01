package com.kwdev.datastore.crypto

import com.kwdev.datastore.crypto.SettingsCryptoSpecs.TRANSFORMATION
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

internal class SettingsCipherProvider {

    private val keyProvider = SettingsKeyProvider()

    fun getEncryptionCipher(): Cipher =
        Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, keyProvider.getKey())
        }

    fun getDecryptionCipher(iv: ByteArray): Cipher =
        Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, keyProvider.getKey(), IvParameterSpec(iv))
        }
}
