package com.example.utils

import kotlinx.serialization.Serializable
import java.util.*

typealias SerializedUUID = @Serializable(UUIDSerializer::class) UUID

typealias KotlinInstant = kotlinx.datetime.Instant
typealias JavaInstant = java.time.Instant
