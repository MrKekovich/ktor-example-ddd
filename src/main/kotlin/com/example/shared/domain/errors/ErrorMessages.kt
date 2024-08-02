package com.example.shared.domain.errors

import java.util.*

object ErrorMessages {
    fun postNotFound(id: UUID): String = "Post with id '$id' not found"
}