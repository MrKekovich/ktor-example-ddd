package com.example.post.infrastructure.repository

import com.example.post.domain.entity.PostEntity
import com.example.post.domain.repository.PostRepository
import com.example.post.infrastructure.persistence.PostDao
import com.example.post.infrastructure.persistence.PostTable
import com.example.shared.infrastructure.persistence.exists
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.intLiteral
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PostDaoRepository : PostRepository {
    override suspend fun save(entity: PostEntity): PostEntity = transaction {
        PostDao.new(entity.id) {
            authorId = entity.authorId
            title = entity.title
            content = entity.content
            createdAt = entity.createdAt.toJavaInstant()
            updatedAt = entity.updatedAt.toJavaInstant()
        }

        entity
    }

    override suspend fun findByAuthorId(authorId: UUID): Collection<PostEntity> = transaction {
        PostDao
            .find { PostTable.authorId eq authorId }
            .map { it.toEntity() }
    }

    override suspend fun existsByIdAndAuthor(id: UUID, authorId: UUID): Boolean = transaction {
        PostTable
            .select(intLiteral(1))
            .where {
                (PostTable.id eq id) and
                        (PostTable.authorId eq authorId)
            }
            .empty().not()
    }

    override suspend fun findById(id: UUID): PostEntity? = transaction {
        PostDao.findById(id)?.toEntity()
    }

    override suspend fun findAll(): Collection<PostEntity> = transaction {
        PostDao.all().map { it.toEntity() }
    }

    override suspend fun update(entity: PostEntity): PostEntity? = transaction {
        val postDao = PostDao.findById(entity.id)
            ?: return@transaction null

        postDao.apply {
            authorId = entity.authorId
            title = entity.title
            content = entity.content
            updatedAt = entity.updatedAt.toJavaInstant()
        }.toEntity()
    }

    override suspend fun delete(id: UUID): Boolean = transaction {
        PostDao.findById(id)?.let {
            it.delete()
            true
        } ?: false
    }

    override suspend fun exists(id: UUID): Boolean = transaction { exists(id, PostTable) }
}

private fun PostDao.toEntity(): PostEntity = PostEntity(
    id = id.value,
    authorId = authorId,
    title = title,
    content = content,
    createdAt = createdAt.toKotlinInstant(),
    updatedAt = updatedAt.toKotlinInstant(),
)
