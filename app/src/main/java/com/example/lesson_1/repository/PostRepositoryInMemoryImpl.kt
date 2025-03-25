package com.example.lesson_1.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lesson_1.R
import com.example.lesson_1.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        1,
        "Нетология. Университет интернет-профессий будущего",
        "21 мая в 18:36",
        "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        false,
        999,
        1_999_999,
        19_999
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        if (post.likeByMe) {
            post = post.copy(likeByMe = !post.likeByMe, likesCounter = --post.likesCounter)
        } else {
            post = post.copy(likeByMe = !post.likeByMe, likesCounter = ++post.likesCounter)
        }
        data.value = post
    }
    override fun shareCount() {
        post = post.copy(shareCounter = ++post.shareCounter)
        data.value = post
    }
}