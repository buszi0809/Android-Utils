package com.kwdev.buildSrc

object AndroidVersion {
    const val compileSdk = 33
    const val buildTools = "30.0.3"
    const val minSdk = 26
    const val targetSdk = 33
}

object Libs {
    object AndroidX {
        const val core = "androidx.core:core-ktx:1.9.0"
        const val compat = "androidx.appcompat:appcompat:1.5.1"
        const val fragment = "androidx.fragment:fragment:1.5.4"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime:2.5.1"

        object DataStore {
            private const val version = "1.0.0"

            const val lib = "androidx.datastore:datastore-preferences:$version"
        }
    }

    object Kotlin {
        object Coroutines {
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
        }

        object Serialization {
            const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"
        }
    }

    object Networking {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val okhttp = "com.squareup.okhttp3:okhttp:4.10.0"
    }

    object Utils {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
    }
}
