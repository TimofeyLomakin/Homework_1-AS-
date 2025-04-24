package com.example.lesson_1.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lesson_1.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean,
    val likesCounter: Long,
    val shareCounter: Long,
    val viewsCounter: Long,
    val youtubeUrl: String,
) {
    fun toDto() = Post(
        id,
        author,
        published,
        content,
        likedByMe,
        likesCounter,
        shareCounter,
        viewsCounter,
        youtubeUrl
    )

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                dto.id,
                dto.author,
                dto.published,
                dto.content,
                dto.likeByMe,
                dto.likesCounter,
                dto.shareCounter,
                dto.viewsCounter,
                dto.youtubeUrl
            )

    }
}