package com.example.post.schema

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.timestamp

object PostTable : UUIDTable("posts") {
    val title = varchar("title", 255)
    val content = text("content")
    val createdAt = timestamp("createdAt")
    val updatedAt = timestamp("createdAt")
}