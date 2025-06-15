package com.example.lesson_1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lesson_1.dto.Post
import com.example.lesson_1.model.FeedModel
import com.example.lesson_1.repository.PostRepository
import com.example.lesson_1.repository.PostRepositoryRoomImpl
import com.example.lesson_1.util.SingleLiveEvent

private val empty = Post(
    id = 0,
    content = "",
    author = "Me",
    likeByMe = false,
    published = "",
    likesCounter = 0L,
    shareCounter = 0L,
    viewsCounter = 0L
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
        _data.postValue(FeedModel(loading = true))
        repository.getAllAsync(object : PostRepository.GetAllCallback {
            override fun onSuccess(posts: List<Post>) {
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }

        })
    }

    fun save() {
        edited.value?.let { post ->
            edited.value = empty
            repository.saveAsync(post, object : PostRepository.SaveCallback {
                override fun onSuccess() {
                    loadPosts()
                    _postCreated.postValue(Unit)
                }

                override fun onError(e: Exception) {
                    edited.value = post
                }
            })
        }
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

        val oldPosts = _data.value?.posts.orEmpty()
        val oldPost = oldPosts.find { it.id == id } ?: return


        val newPost = oldPost.copy(
            likeByMe = !oldPost.likeByMe,
            likesCounter = if (oldPost.likeByMe) oldPost.likesCounter - 1 else oldPost.likesCounter + 1
        )
        val newPosts = oldPosts.map { if (it.id == id) newPost else it }
        _data.value = _data.value?.copy(posts = newPosts)

        repository.likeByIdAsync(id, oldPost.likeByMe, object : PostRepository.LikeByIdCallback {
            override fun onSuccess(post: Post) {
                val updatedPosts = oldPosts.map { if (it.id == id) post else it }
                _data.postValue(_data.value?.copy(posts = updatedPosts))
            }

            override fun onError(e: Exception) {
                _data.postValue(_data.value?.copy(posts = oldPosts))
            }

        })
    }

    fun removeById(id: Long) {
        // Оптимистичная модель
        val old = _data.value?.posts.orEmpty()
        _data.value = _data.value?.copy(posts = old.filter { it.id != id })

        repository.removeByIdAsync(id, object : PostRepository.RemoveCallback {
            override fun onSuccess() {
            }

            override fun onError(e: Exception) {
                _data.postValue(_data.value?.copy(posts = old))
            }

        })
    }

    fun shareById(id: Long) = repository.shareById(id)
}







