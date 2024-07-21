package com.example.shared

interface IdEntity<ID : Comparable<ID>> {
    val id: ID
}
