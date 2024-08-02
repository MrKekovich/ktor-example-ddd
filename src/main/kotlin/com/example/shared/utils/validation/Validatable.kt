package com.example.shared.utils.validation

import Violation

interface Validatable {
    fun validate(): List<Violation>
}
