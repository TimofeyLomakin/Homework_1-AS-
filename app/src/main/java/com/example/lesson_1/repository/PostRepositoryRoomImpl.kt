package com.example.lesson_1.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lesson_1.dao.PostDao
import com.example.lesson_1.dto.Post
import com.example.lesson_1.entity.PostEntity

class PostRepositoryRoomImpl(
    private val dao: PostDao
) : PostRepository {
    override fun getAll(): LiveData<List<Post>> =
        dao.getAll().map { list -> list.map(PostEntity::toDto) }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }
}