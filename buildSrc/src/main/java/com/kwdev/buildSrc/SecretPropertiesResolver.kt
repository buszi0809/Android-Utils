package com.kwdev.buildSrc

import org.gradle.api.Project
import java.util.*

fun Project.getSecret(name: String, filePath: String = "secret.properties"): String {
    val file = rootProject.file(filePath)
    return if (file.exists()) {
        // Local env, load from properties file
        val properties = Properties()
        file.inputStream().use { fis ->
            properties.load(fis)
        }
        properties.getProperty(name) ?: throw SecretNotPresent(name)
    } else {
        // CI env, load from env values
        System.getenv(name) ?: throw SecretNotPresent(name)
    }
}

private class SecretNotPresent(name: String) : IllegalStateException(
    """
        Property not present
        name - $name
        If building locally:
            - check if properties file is present
            - check if properties file is up to date
        If building on the CI pipeline:
            - check if secret value is present in environment values
    """.trimIndent()
)
