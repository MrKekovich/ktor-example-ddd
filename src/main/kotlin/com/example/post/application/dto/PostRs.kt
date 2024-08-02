package com.example.post.application.dto

import kotlinx.serialization.Serializable
import com.example.shared.utils.serialization.SerializedUUID
import com.example.shared.utils.types.KotlinInstant

@Serializable
data class PostRs(
    val id: SerializedUUID,
    val authorId: SerializedUUID,
    val title: String,
    val content: String,
    val createdAt: KotlinInstant,
    val updatedAt: KotlinInstant,
)
