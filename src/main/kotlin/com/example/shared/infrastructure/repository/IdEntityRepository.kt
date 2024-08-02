package com.example.shared.infrastructure.repository

import com.example.shared.domain.entity.IdEntity

interface IdEntityRepository<ID : Comparable<ID>, E : IdEntity<ID>> : EntityRepository<E> {
    suspend fun findById(id: ID): E?
    suspend fun delete(id: ID): Boolean
    suspend fun exists(id: ID): Boolean
    override suspend fun delete(entity: E): Boolean = delete(entity.id)
    override suspend fun exists(entity: E): Boolean = exists(entity.id)
}