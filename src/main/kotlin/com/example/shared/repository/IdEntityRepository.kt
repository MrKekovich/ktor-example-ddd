package com.example.shared.repository

import com.example.shared.IdEntity

interface IdEntityRepository<ID : Comparable<ID>, E : IdEntity<ID>> : EntityRepository<E> {
    suspend fun findById(id: ID): E?

    suspend fun delete(id: ID): Boolean

    suspend fun exists(id: ID): Boolean
}
