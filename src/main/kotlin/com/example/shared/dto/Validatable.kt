package com.example.shared.dto

import Violation

interface Validatable {
    fun validate(): List<Violation>
}