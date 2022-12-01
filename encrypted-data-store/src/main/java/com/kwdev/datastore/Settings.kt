package com.kwdev.datastore

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val userId: String? = null,
)
