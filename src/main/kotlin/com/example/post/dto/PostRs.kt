package com.example.post.dto

import com.example.utils.KotlinInstant
import com.example.utils.SerializedUUID

data class PostRs(
    val id: SerializedUUID,
    val title: String,
    val content: String,
    val createdAt: KotlinInstant,
    val updatedAt: KotlinInstant,
)
