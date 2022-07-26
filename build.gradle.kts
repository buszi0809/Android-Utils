plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.7.21" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.21" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
