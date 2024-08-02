package com.example.post.application.dto

import Violation
import kotlinx.serialization.Serializable
import com.example.shared.utils.validation.Validatable
import validators.validateAll

@Serializable
data class PostRq(
    val title: String,
    val content: String,
) : Validatable {
    override fun validate(): List<Violation> = validateAll {
        ::title.notBlank().length(3..255)
        ::content.notBlank().minLength(10)
    }
}
