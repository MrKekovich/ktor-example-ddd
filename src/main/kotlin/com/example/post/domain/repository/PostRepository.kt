package com.example.post.domain.repository

import com.example.post.domain.entity.PostEntity
import com.example.shared.infrastructure.repository.IdEntityRepository
import java.util.*

interface PostRepository : IdEntityRepository<UUID, PostEntity> {
    suspend fun findByAuthorId(authorId: UUID): Collection<PostEntity>
    suspend fun existsByIdAndAuthor(id: UUID, authorId: UUID): Boolean
}
