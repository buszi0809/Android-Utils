package com.kwdev.datastore.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.kwdev.datastore.crypto.SettingsCryptoSpecs.ALGORITHM
import com.kwdev.datastore.crypto.SettingsCryptoSpecs.BLOCK_MODE
import com.kwdev.datastore.crypto.SettingsCryptoSpecs.PADDING
import timber.log.Timber
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

internal class SettingsKeyProvider {

    fun getKey(): SecretKey = getKeyFromKeystore() ?: generateNewKey()

    private fun getKeyFromKeystore(): SecretKey? =
        try {
            val entry = getKeystore().getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
            entry?.secretKey
        } catch (e: Throwable) {
            Timber.w(e, "Failed to get key entry")
            null
        }

    private fun generateNewKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM)

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(BLOCK_MODE)
            .setEncryptionPaddings(PADDING)
            .setUserAuthenticationRequired(false)
            .setRandomizedEncryptionRequired(true)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        return keyGenerator.generateKey()
    }

    private fun getKeystore(): KeyStore =
        KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    private companion object {
        private const val KEY_ALIAS = "SettingsSecretKey"
    }
}
