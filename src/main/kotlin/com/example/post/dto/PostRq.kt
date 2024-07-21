package com.example.post.dto

import Violation
import com.example.shared.dto.Validatable
import validators.validateAll

data class PostRq(
    val title: String,
    val content: String,
) : Validatable {
    override fun validate(): List<Violation> = validateAll {
        ::title.notBlank().length(3..255)
        ::content.notBlank().minLength(10)
    }
}