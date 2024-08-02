package com.example.shared.infrastructure.repository

interface EntityRepository<E : Any> {
    suspend fun findAll(): Collection<E>
    suspend fun save(entity: E): E
    suspend fun update(entity: E): E?
    suspend fun delete(entity: E): Boolean
    suspend fun exists(entity: E): Boolean
}