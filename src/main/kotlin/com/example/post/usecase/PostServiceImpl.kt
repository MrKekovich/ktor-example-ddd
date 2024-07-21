package com.example.post.usecase

import com.example.exceptions.ForbiddenException
import com.example.post.PostEntity
import com.example.post.dto.PostRq
import com.example.post.dto.PostRs
import com.example.post.repository.PostRepository
import com.example.utils.ErrorMessages
import io.ktor.server.plugins.NotFoundException
import kotlinx.datetime.Clock
import java.util.*

class PostServiceImpl(
    private val postRepository: PostRepository,
) : PostService {
    override suspend fun save(postRq: PostRq, userId: UUID): PostRs {
        val postEntity: PostEntity = postRq.toEntity(UUID.randomUUID(), userId)

        return postRepository.save(postEntity).toRs()
    }

    override suspend fun findAll(): List<PostRs> = postRepository.findAll().map { it.toRs() }

    override suspend fun findById(id: UUID): PostRs = postRepository.findById(id)?.toRs()
        ?: throw NotFoundException(ErrorMessages.postNotFound(id))

    override suspend fun findByUser(userId: UUID): List<PostRs> =
        postRepository.findByAuthorId(userId).map { it.toRs() }

    override suspend fun update(id: UUID, postRq: PostRq, userId: UUID): PostRs {
        ensureUserHasAccess(userId, id)

        val postEntity = postRq.toEntity(id, userId)

        return postRepository.update(postEntity)?.toRs()
            ?: throw NotFoundException(ErrorMessages.postNotFound(id))
    }

    override suspend fun delete(id: UUID, userId: UUID) {
        ensureUserHasAccess(userId, id)

        postRepository.delete(id).let {
            if (!it) throw NotFoundException(ErrorMessages.postNotFound(id))
        }
    }

    private suspend fun ensureUserHasAccess(userId: UUID, postId: UUID) {
        val exists = postRepository.existsByIdAndAuthor(id = postId, authorId = userId)

        if (!exists) throw ForbiddenException("You don't have access to this resource")
    }
}

private fun PostRq.toEntity(id: UUID, userId: UUID): PostEntity = PostEntity(
    id = id,
    authorId = userId,
    title = title,
    content = content,
    createdAt = Clock.System.now(),
    updatedAt = Clock.System.now(),
)

private fun PostEntity.toRs(): PostRs = PostRs(
    id = id,
    authorId = authorId,
    title = title,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
