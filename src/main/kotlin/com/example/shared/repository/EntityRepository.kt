package com.example.shared.repository

interface EntityRepository<ID : Any, E : Any> {
    suspend fun save(entity: E): E
    suspend fun findById(id: ID): E?
    suspend fun findAll(): Collection<E>
    suspend fun delete(id: ID): Boolean
    suspend fun exists(id: ID): Boolean
}