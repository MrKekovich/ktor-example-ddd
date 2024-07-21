package com.example.post

import com.example.shared.IdEntity
import com.example.utils.KotlinInstant
import java.util.UUID

data class PostEntity(
    override val id: UUID,
    val title: String,
    val content: String,
    val createdAt: KotlinInstant,
    val updatedAt: KotlinInstant,
) : IdEntity<UUID>
