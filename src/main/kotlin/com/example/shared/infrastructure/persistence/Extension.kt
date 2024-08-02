package com.example.shared.infrastructure.persistence

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.intLiteral

/**
 * ### !Requires `transaction` context!
 * Checks if an [id] exists in a [table].
 * This operation is more optimized than `DAO.findById(id)`.
 *
 * Generated SQL query
 * ```sql
 * SELECT 1 FROM ArticleTable WHERE id = ? LIMIT 1
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

/**
 * ### !Requires `transaction` context!
 * Deletes an [id] from a [table].
 * This operation is more optimized than `DAO.deleteById(id)`.
 *
 * Generated SQL query
 * ```sql
 * DELETE FROM ArticleTable WHERE id = ?
 * ```
 *
 * @param ID type of the id. Must be Comparable.
 * @param T type of the [IdTable] that takes [ID] as generic parameter.
 * @param id the id to delete
 * @param table the [IdTable] to delete in.
 * @return the number of deleted rows
 */
fun <ID : Comparable<ID>, T : IdTable<ID>> delete(id: ID, table: T): Int =
    table.deleteWhere { table.id eq id }
