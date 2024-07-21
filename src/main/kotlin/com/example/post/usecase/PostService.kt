package com.example.post.usecase

import com.example.post.dto.PostRq
import com.example.post.dto.PostRs
import java.util.*

interface PostService {
    suspend fun save(postRq: PostRq, userId: UUID): PostRs

    suspend fun findAll(): List<PostRs>

    suspend fun findById(id: UUID): PostRs

    suspend fun findByUser(userId: UUID): List<PostRs>

    suspend fun update(id: UUID, postRq: PostRq, userId: UUID): PostRs

    suspend fun delete(id: UUID, userId: UUID)
}