package com.example.post.infrastructure.persistence

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.timestamp
import com.example.shared.utils.types.JavaInstant

object PostTable : UUIDTable("posts") {
    val authorId = uuid("author_id")
    val title = varchar("title", 255)
    val content = text("content")
    val createdAt: Column<JavaInstant> = timestamp("createdAt")
    val updatedAt: Column<JavaInstant> = timestamp("updatedAt")
}
