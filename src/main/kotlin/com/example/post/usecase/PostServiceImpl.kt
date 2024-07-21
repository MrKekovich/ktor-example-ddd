package com.example.post.usecase

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
    override suspend fun save(postRq: PostRq): PostRs {
        val postEntity: PostEntity = postRq.toEntity(UUID.randomUUID())

        return postRepository.save(postEntity).toRs()
    }

    override suspend fun findAll(): List<PostRs> = postRepository.findAll().map { it.toRs() }

    override suspend fun findById(id: UUID): PostRs = postRepository.findById(id)?.toRs()
        ?: throw NotFoundException(ErrorMessages.postNotFound(id))

    override suspend fun update(id: UUID, postRq: PostRq) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: UUID) {
        TODO("Not yet implemented")
    }
}

private fun PostRq.toEntity(id: UUID): PostEntity = PostEntity(
    id = id,
    title = title,
    content = content,
    createdAt = Clock.System.now(),
    updatedAt = Clock.System.now(),
)

private fun PostEntity.toRs(): PostRs = PostRs(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
