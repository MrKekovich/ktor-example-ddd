package com.example.utils

import com.example.exceptions.EnvironmentException

object Environment {
    inline operator fun <reified T> get(key: String): T = try {
        val envVar: String = System.getenv(key)

        when (T::class) {
            String::class -> envVar as T
            Boolean::class -> envVar.toBooleanStrict() as T
            Int::class -> envVar.toInt() as T
            else -> throw EnvironmentException("Unsupported type: ${T::class.simpleName}")
        }
    } catch (nullPtrException: NullPointerException) {
        throw EnvironmentException("Missing environment variable: $key")
    } catch (illegalArgException: IllegalArgumentException) {
        throw EnvironmentException("Invalid environment variable type: $key != ${T::class.simpleName}")
    } catch (numberFormatException: NumberFormatException) {
        throw EnvironmentException("Invalid environment variable type: $key != ${T::class.simpleName}")
    }

    operator fun get(key: String): String = get<String>(key)
}
