package com.example.post.repository

import com.example.post.PostEntity
import com.example.post.schema.PostDao
import com.example.post.schema.PostTable
import com.example.utils.exists
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PostDaoRepository : PostRepository {
    override suspend fun save(entity: PostEntity): PostEntity = transaction {
        PostDao.new(entity.id) {
            title = entity.title
            content = entity.content
            createdAt = entity.createdAt.toJavaInstant()
            updatedAt = entity.updatedAt.toJavaInstant()
        }

        entity
    }

    override suspend fun findById(id: UUID): PostEntity? = transaction {
        PostDao.findById(id)?.toEntity()
    }

    override suspend fun findAll(): Collection<PostEntity> = transaction {
        PostDao.all().map { it.toEntity() }
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
    title = title,
    content = content,
    createdAt = createdAt.toKotlinInstant(),
    updatedAt = updatedAt.toKotlinInstant(),
)
