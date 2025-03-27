package com.example.lesson_1.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likeByMe: Boolean,
    val likesCounter: Long,
    val shareCounter: Long,
    val viewsCounter: Long
)
