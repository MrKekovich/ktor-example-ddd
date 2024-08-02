package com.example.post.repository

import com.example.post.domain.entity.PostEntity
import com.example.post.domain.repository.PostRepository
import com.example.shared.repository.InMemoryRepository
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Clock
import java.util.*

object PostInMemoryRepository : InMemoryRepository<UUID, PostEntity>(), PostRepository {
    override suspend fun findByAuthorId(authorId: UUID): Collection<PostEntity> = mutex.withLock {
        storage.filter { it.authorId == authorId }
    }

    override suspend fun existsByIdAndAuthor(id: UUID, authorId: UUID): Boolean = mutex.withLock {
        storage.find { it.id == id && it.authorId == authorId } != null
    }

    override suspend fun update(entity: PostEntity): PostEntity? = mutex.withLock {
        val foundEntity = storage.find { it.id == entity.id } ?: return@withLock null
        val updatedEntity = foundEntity.copy(
            authorId = entity.authorId,
            title = entity.title,
            content = entity.content,
            updatedAt = Clock.System.now(),
        )

        storage.remove(foundEntity)
        storage.add(updatedEntity)

        updatedEntity
    }
}