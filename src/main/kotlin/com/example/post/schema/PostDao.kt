package com.example.post.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class PostDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<PostDao>(PostTable)

    var title by PostTable.title
    var content by PostTable.content
    var createdAt by PostTable.createdAt
    var updatedAt by PostTable.updatedAt
}