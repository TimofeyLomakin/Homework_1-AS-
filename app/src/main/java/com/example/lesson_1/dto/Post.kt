package com.example.lesson_1.dto

data class Post(
    val id : Long,
    val author : String,
    val published: String,
    val content : String,
    var likeByMe : Boolean,
    var likesCounter : Long,
    var shareCounter : Long,
    var viewsCounter : Long
)
