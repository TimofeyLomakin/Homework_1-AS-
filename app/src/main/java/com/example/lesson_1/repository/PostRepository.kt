package com.example.lesson_1.repository

import com.example.lesson_1.dto.Post

interface PostRepository {
    fun shareById(id: Long)

    fun likeByIdAsync(id: Long, likeByMe: Boolean, callback : LikeByIdCallback)
    interface LikeByIdCallback {
        fun onSuccess(post:Post)
        fun onError(e: Exception)
    }

    fun saveAsync (post:Post, callback : SaveCallback)
    interface SaveCallback {
        fun onSuccess()
        fun onError(e: Exception)
    }

    fun removeByIdAsync(id: Long, callback : RemoveCallback)
    interface RemoveCallback {
        fun onSuccess()
        fun onError(e: Exception)
    }

    fun getAllAsync(callback : GetAllCallback)
    interface GetAllCallback {
        fun onSuccess(posts: List<Post>)
        fun onError(e: Exception)
    }
}