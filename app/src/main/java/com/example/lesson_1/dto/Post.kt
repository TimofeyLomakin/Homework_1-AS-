package com.example.lesson_1.dto

import com.google.gson.annotations.SerializedName


data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    @SerializedName("likedByMe")
    val likeByMe: Boolean,
    @SerializedName("likes")
    val likesCounter: Long,
    val shareCounter: Long,
    val viewsCounter: Long
)
