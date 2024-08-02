package com.example.shared.utils.serialization

import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.*

typealias SerializedUUID = @Serializable(UUIDSerializer::class) UUID
typealias SerializedBigDecimal = @Serializable(BigDecimalSerializer::class) BigDecimal
