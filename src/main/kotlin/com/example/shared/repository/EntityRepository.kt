package com.example.shared.repository

interface EntityRepository<E : Any> {
    suspend fun save(entity: E): E

    suspend fun findAll(): Collection<E>

    suspend fun update(entity: E): E?
}