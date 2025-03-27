package com.example.lesson_1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lesson_1.repository.PostRepository
import com.example.lesson_1.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    fun likeById(id : Long) = repository.likeById(id)
    fun shareById(id : Long) = repository.shareById(id)
}