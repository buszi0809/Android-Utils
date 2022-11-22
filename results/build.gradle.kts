import com.kwdev.buildSrc.AndroidVersion
import com.kwdev.buildSrc.Libs

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.kwdev.results"
    compileSdk = AndroidVersion.compileSdk
    buildToolsVersion = AndroidVersion.buildTools

    defaultConfig {
        minSdk = AndroidVersion.minSdk
        targetSdk = AndroidVersion.targetSdk

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Libs.Kotlin.Coroutines.core)
    implementation(Libs.Kotlin.Serialization.json)
    implementation(Libs.Networking.retrofit)
    implementation(Libs.Utils.timber)
}
