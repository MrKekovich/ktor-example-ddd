package com.example.post.repository

import com.example.post.PostEntity
import com.example.shared.repository.IdEntityRepository
import java.util.*

interface PostRepository : IdEntityRepository<UUID, PostEntity> {
    suspend fun findByAuthorId(authorId: UUID): Collection<PostEntity>
    suspend fun existsByIdAndAuthor(id: UUID, authorId: UUID): Boolean
}
