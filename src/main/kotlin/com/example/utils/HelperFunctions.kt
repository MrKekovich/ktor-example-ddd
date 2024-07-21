package com.example.utils

import Violation
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.util.pipeline.PipelineContext
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.intLiteral
import java.util.*

/**
 * Converts a list of [Violation] to a [ValidationResult].
 *
 * @return if the list is empty, returns [ValidationResult.Valid]. Otherwise, returns [ValidationResult.Invalid].
 */
fun MutableList<Violation>.toValidationResult(): ValidationResult = when {
    isEmpty() -> ValidationResult.Valid
    else -> ValidationResult.Invalid(map { it.message })
}

/**
 * Gets the [String] from the path parameter and tries to convert it to [UUID].
 *
 * @param pathParameterName the parameter name to search for.
 * @return If found, returns [UUID] wrapped in [Result]. If not found, returns [Result.success] with `null`.
 */
fun PipelineContext<Unit, ApplicationCall>.getUuid(pathParameterName: String): Result<UUID?> {
    val pathParameter = call.parameters[pathParameterName]
        ?: return Result.success(null)

    val uuid: UUID = runCatching { UUID.fromString(pathParameter) }.getOrElse {
        return Result.failure(BadRequestException("Invalid $pathParameterName: '$pathParameter' Must be a valid UUID."))
    }

    return Result.success(uuid)
}

/**
 * Tries to convert a string to [UUID]. If fails, returns `null`.
 *
 * @return the [UUID] or `null`
 */
fun String.toUuidOrNull(): UUID? = runCatching { UUID.fromString(this) }.getOrNull()

/**
 * ### !Requires `transaction` context!
 * Checks if an [id] exists in a [table].
 * This operation is more optimized than `DAO.findById(id) != null`.
 *
 * Generated SQL query
 * ```sql
 * SELECT 1 FROM table WHERE id = ? LIMIT 1
 * ```
 *
 * @param ID type of the id. Must be Comparable.
 * @param T type of the [IdTable] that takes [ID] as generic parameter.
 * @param id the id to check
 * @param table the [IdTable] to check in.
 * @return `true` if the [id] exists in the [table], `false` otherwise.
 */
fun <ID : Comparable<ID>, T : IdTable<ID>> exists(id: ID, table: T): Boolean =
    table.select(intLiteral(1)).where { table.id eq id }.empty().not()
