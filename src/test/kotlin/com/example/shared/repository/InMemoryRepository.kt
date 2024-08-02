package com.example.shared.repository

import com.example.shared.domain.entity.IdEntity
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import com.example.shared.infrastructure.repository.IdEntityRepository

abstract class InMemoryRepository<ID : Comparable<ID>, E : IdEntity<ID>> : IdEntityRepository<ID, E> {
    protected val mutex = Mutex()
    protected val storage = mutableListOf<E>()

    override suspend fun findById(id: ID): E? = mutex.withLock {
        storage.find { it.id == id }
    }

    override suspend fun delete(id: ID): Boolean = mutex.withLock {
        storage.removeIf { it.id == id }
    }

    override suspend fun exists(id: ID): Boolean = mutex.withLock {
        storage.any { it.id == id }
    }

    override suspend fun save(entity: E): E = mutex.withLock {
        storage.add(entity)
        entity
    }

    override suspend fun findAll(): Collection<E> = mutex.withLock {
        storage
    }

    fun clear() = storage.clear()
}