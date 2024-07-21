package com.example.shared.repository

import com.example.shared.IdEntity

interface IdEntityRepository<ID : Comparable<ID>, E : IdEntity<ID>> : EntityRepository<ID, E>
