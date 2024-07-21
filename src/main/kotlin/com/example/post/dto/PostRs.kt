package com.example.post.dto

import com.example.utils.KotlinInstant
import com.example.utils.SerializedUUID
import kotlinx.serialization.Serializable

@Serializable
data class PostRs(
    val id: SerializedUUID,
    val authorId: SerializedUUID,
    val title: String,
    val content: String,
    val createdAt: KotlinInstant,
    val updatedAt: KotlinInstant,
)
