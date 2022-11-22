import com.kwdev.buildSrc.AndroidVersion
import com.kwdev.buildSrc.Libs

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":view-scoped-delegates"))

    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.compat)
}
