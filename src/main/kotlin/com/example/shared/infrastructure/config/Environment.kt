package com.example.shared.infrastructure.config

object Environment {
    inline operator fun <reified T> get(key: String): T =
        runCatching { System.getenv(key) as T }
            .getOrElse {
                val message = when (it) {
                    is NullPointerException -> "Environment variable '$key' not found"
                    is ClassCastException -> "Environment variable '$key' has wrong type"
                    else -> throw it
                }

                throw RuntimeException(message)
            }

    operator fun get(key: String): String = get<String>(key)
}
