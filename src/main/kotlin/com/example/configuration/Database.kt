package com.example.configuration

import com.example.post.infrastructure.persistence.PostTable
import com.example.shared.infrastructure.config.Environment
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun configureDatabase() {
    val host: String = Environment["POSTGRES_HOST"]
    val port: String = Environment["POSTGRES_PORT"]
    val user: String = Environment["POSTGRES_USER"]
    val password: String = Environment["POSTGRES_PASSWORD"]
    val database: String = Environment["POSTGRES_DATABASE"]
    val driver = "org.postgresql.Driver"

    val db = Database.connect(
        driver = driver,
        url = "jdbc:postgresql://$host:$port/$database",
        user = user,
        password = password
    )

    transaction(db) {
        SchemaUtils.createMissingTablesAndColumns(
            PostTable
        )
    }
}
