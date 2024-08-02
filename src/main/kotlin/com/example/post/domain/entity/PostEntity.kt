package com.example.post.domain.entity

import com.example.shared.domain.entity.IdEntity
import com.example.shared.utils.types.KotlinInstant
import java.util.UUID

data class PostEntity(
    override val id: UUID,
    val authorId: UUID,
    val title: String,
    val content: String,
    val createdAt: KotlinInstant,
    val updatedAt: KotlinInstant,
) : IdEntity<UUID>
