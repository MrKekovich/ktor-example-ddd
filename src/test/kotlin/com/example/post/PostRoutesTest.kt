package com.example.post

import com.example.configuration.generateTestToken
import com.example.post.application.dto.PostRq
import com.example.post.application.dto.PostRs
import com.example.post.domain.entity.PostEntity
import com.example.post.repository.PostInMemoryRepository
import com.example.testModule
import com.example.utils.createClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import kotlinx.datetime.Clock
import org.junit.Before
import org.junit.Test
import com.example.shared.utils.types.KotlinInstant
import java.util.*
import kotlin.test.assertEquals

class PostRoutesTest {
    @Before
    fun setup() {
        PostInMemoryRepository.clear()
    }

    @Test
    fun `should return all posts`() = testApplication {
        application { testModule() }
        val client = createClient()

        val actualPosts = PostInMemoryRepository.populate(10)

        val response = client.get("/posts") {
            bearerAuth(generateTestToken())
            accept(ContentType.Application.Json)
        }
        val posts = response.body<List<PostRs>>().map { it.toEntity() }

        assertEquals(
            response.status,
            HttpStatusCode.OK,
            "Response status check failed: ${response.status} != ${HttpStatusCode.OK}"
        )
        assertEquals(posts.size, actualPosts.size, "Size check failed: ${posts.size} != ${actualPosts.size}")

        for ((idx, actualPost) in actualPosts.withIndex()) {
            assertEquals(actualPost, posts[idx], "Entity check failed: $actualPost != ${posts[idx]}")
        }
    }

    @Test
    fun `should filter posts by user`() = testApplication {
        application { testModule() }
        val client = createClient()


        PostInMemoryRepository.populate(5) // Dummy posts

        val userId = UUID.randomUUID()
        val actualPosts = PostInMemoryRepository.populate(5, authorId = userId)

        val response = client.get("/posts?userId=$userId") {
            bearerAuth(generateTestToken())
            accept(ContentType.Application.Json)
        }
        val posts = response.body<List<PostRs>>().map { it.toEntity() }

        assertEquals(
            response.status,
            HttpStatusCode.OK,
            "Response status check failed: ${response.status} != ${HttpStatusCode.OK}"
        )
        assertEquals(posts.size, actualPosts.size, "Size check failed: ${posts.size} != ${actualPosts.size}")

        for ((idx, actualPost) in actualPosts.withIndex()) {
            assertEquals(actualPost, posts[idx], "Entity check failed: $actualPost != ${posts[idx]}")
        }
    }

    @Test
    fun `should return post by id`() = testApplication {
        application { testModule() }
        val client = createClient()


        val post = PostInMemoryRepository.populate(1).first()

        val response = client.get("/posts/${post.id}") {
            bearerAuth(generateTestToken())
            accept(ContentType.Application.Json)
        }
        val actualPost = response.body<PostRs>().toEntity()

        assertEquals(
            response.status,
            HttpStatusCode.OK,
            "Response status check failed: ${response.status} != ${HttpStatusCode.OK}"
        )
        assertEquals(
            actualPost,
            post,
            "Entity check failed: $actualPost != $post"
        )
    }

    @Test
    fun `should create post`() = testApplication {
        application { testModule() }
        val client = createClient()

        val rq = PostRq(
            title = "title",
            content = "contentcontentcontent",
        )

        val rs = client.post("/posts") {
            contentType(ContentType.Application.Json)
            bearerAuth(generateTestToken())
            accept(ContentType.Application.Json)
            setBody(rq)
        }
        val post = rs.body<PostRs>().toEntity()

        assertEquals(
            rs.status,
            HttpStatusCode.OK,
            "Valid response status check failed: ${rs.status} != ${HttpStatusCode.OK}"
        )

        assertEquals(
            post.title,
            rq.title,
            "Post title check failed: ${post.title} != ${rq.title}"
        )
        assertEquals(
            post.content,
            rq.content,
            "Post content check failed: ${post.content} != ${rq.content}"
        )
    }

    @Test
    fun `should update post`() = testApplication {
        application { testModule() }
        val client = createClient()


        val post = PostInMemoryRepository.populate(1).first()

        val rq = PostRq(
            title = "${post.title} updated",
            content = "${post.content} updated",
        )

        val rs = client.put("/posts/${post.id}") {
            contentType(ContentType.Application.Json)
            bearerAuth(generateTestToken(id = post.authorId))
            accept(ContentType.Application.Json)
            setBody(rq)
        }
        val updatedPost = rs.body<PostRs>().toEntity()


        assertEquals(
            rs.status,
            HttpStatusCode.OK,
            "Valid response status check failed: ${rs.status} != ${HttpStatusCode.OK}"
        )
        assertEquals(
            updatedPost,
            post.copy(title = rq.title, content = rq.content, updatedAt = updatedPost.updatedAt),
            "Post check failed: $updatedPost != $post"
        )
    }

    @Test
    fun `should delete post`() = testApplication {
        application { testModule() }
        val client = createClient()


        val post = PostInMemoryRepository.populate(1).first()

        val rs = client.delete("/posts/${post.id}") {
            bearerAuth(generateTestToken(id = post.authorId))
            accept(ContentType.Application.Json)
        }

        assertEquals(
            rs.status,
            HttpStatusCode.OK,
            "Valid response status check failed: ${rs.status} != ${HttpStatusCode.OK}"
        )
        assertEquals(PostInMemoryRepository.findAll().size, 0, "Size check failed: ${PostInMemoryRepository.findAll().size} != 0")
    }

    @Test
    fun `should fail on validation`() = testApplication {
        application { testModule() }
        val client = createClient()

        val invalidRq = PostRq(
            title = "",
            content = "",
        )

        val rs = client.post("/posts") {
            contentType(ContentType.Application.Json)
            bearerAuth(generateTestToken())
            accept(ContentType.Application.Json)
            setBody(invalidRq)
        }

        assertEquals(
            rs.status,
            HttpStatusCode.BadRequest,
            "Invalid response status check failed: ${rs.status} != ${HttpStatusCode.BadRequest}"
        )
    }
}

private suspend fun PostInMemoryRepository.populate(
    amount: Int,
    authorId: UUID = UUID.randomUUID(),
    title: String? = null,
    content: String? = null,
    createdAt: KotlinInstant = Clock.System.now(),
    updatedAt: KotlinInstant = Clock.System.now(),
): List<PostEntity> = buildList {
    repeat(amount) {
        val entity = save(
            PostEntity(
                id = UUID.randomUUID(),
                authorId = authorId,
                title = title ?: "title for post $it",
                content = content ?: "content for post $it",
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        )
        add(entity)
    }
}

private fun PostRs.toEntity(): PostEntity = PostEntity(
    id = id,
    authorId = authorId,
    title = title,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
)