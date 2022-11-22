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
    }
}