package com.example.post.repository

import com.example.post.PostEntity
import com.example.shared.repository.IdEntityRepository
import java.util.*

interface PostRepository : IdEntityRepository<UUID, PostEntity>
