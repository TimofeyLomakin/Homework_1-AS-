package com.example.lesson_1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.IOException
import com.example.lesson_1.dto.Post
import com.example.lesson_1.model.FeedModel
import com.example.lesson_1.repository.PostRepository
import com.example.lesson_1.repository.PostRepositoryRoomImpl
import kotlin.concurrent.thread
import com.example.lesson_1.util.SingleLiveEvent

private val empty = Post(
    id = 0,
    content = "",
    author = "Me",
    likeByMe = false,
    published = "now",
    likesCounter = 0L,
    shareCounter = 0L,
    viewsCounter = 0L,
    youtubeUrl = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryRoomImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)

    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

    fun loadPosts() {
        thread {
            // Начинаем загрузку
            _data.postValue(FeedModel(loading = true))
            try {
                // Данные успешно получены
                val posts = repository.getAll()
                FeedModel(posts = posts, empty = posts.isEmpty())
            } catch (e: IOException) {
                // Получена ошибка
                FeedModel(error = true)
            }.also(_data::postValue)
        }
    }

    fun save() {
        edited.value?.let {
            thread {
                repository.save(it)
                _postCreated.postValue(Unit)
            }
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) {
        thread {
            try {
                val oldPosts = _data.value?.posts.orEmpty()
                val oldPost = oldPosts.find { it.id == id } ?: return@thread
                val newPost = repository.likeById(id, oldPost.likeByMe)

                val newPosts = oldPosts.map { if (it.id == id) newPost else it }
                _data.postValue(_data.value?.copy(posts = newPosts as List<Post>))
            } catch (e: IOException) {
                loadPosts()
            }
        }
    }

    fun removeById(id: Long) {
        thread {
            // Оптимистичная модель
            val old = _data.value?.posts.orEmpty()
            _data.postValue(
                _data.value?.copy(posts = _data.value?.posts.orEmpty()
                    .filter { it.id != id }
                )
            )
            try {
                repository.removeById(id)
            } catch (e: IOException) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        }
    }
    fun shareById(id: Long) = repository.shareById(id)
}







