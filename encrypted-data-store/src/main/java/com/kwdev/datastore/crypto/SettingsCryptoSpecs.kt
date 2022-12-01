package com.kwdev.datastore.crypto

import android.security.keystore.KeyProperties

internal object SettingsCryptoSpecs {
    internal const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
    internal const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
    internal const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
    internal const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
}
