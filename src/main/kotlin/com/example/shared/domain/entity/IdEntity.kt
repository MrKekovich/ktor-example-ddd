package com.example.shared.domain.entity

interface IdEntity<ID : Comparable<ID>> {
    val id: ID
}