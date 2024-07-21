package com.example.utils

import java.util.*

object ErrorMessages {
    fun postNotFound(id: UUID): String = "Post with id '$id' not found"
}