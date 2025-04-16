package com.example.lesson_1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson_1.dto.Post
import com.example.lesson_1.repository.PostRepository
import com.example.lesson_1.repository.PostRepositoryFilesImpl
import com.example.lesson_1.repository.PostRepositoryInMemoryImpl
import com.example.lesson_1.repository.PostRepositorySharedPrefsImpl
import java.lang.Appendable


private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likeByMe = false,
    published = "",
    likesCounter = 0L,
    shareCounter = 0L,
    viewsCounter = 0L,
    youtubeUrl = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryFilesImpl(application)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContentAndSave(text: String) {
        edited.value?.let {
            if (it.content != text && text != "cancel_edit") {
                repository.save(it.copy(content = text))
            }
            edited.value = empty
        }
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
}