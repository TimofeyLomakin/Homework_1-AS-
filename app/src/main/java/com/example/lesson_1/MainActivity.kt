package com.example.lesson_1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson_1.databinding.ActivityMainBinding
import com.example.lesson_1.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            1,
            "Нетология. Университет интернет-профессий будущего",
            "21 мая в 18:36",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            false,
            999,
            1_999_999,
            19_999
        )

        fun translate(count: Long): String {
            if ((count / 1_000_000).toInt() != 0) {
                return ((count / 100_000).toDouble() / 10).toString() + "M"
            } else if ((count / 1_000).toInt() != 0) {
                return ((count / 100).toDouble() / 10).toString() + "K"
            }
            return count.toString()
        }

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            val textViewLikes = findViewById<TextView>(R.id.likes_count)
            val textViewShare = findViewById<TextView>(R.id.share_count)
            val textViewViews = findViewById<TextView>(R.id.views_count)
            textViewLikes.text = translate(post.likesCounter)
            textViewShare.text = translate(post.shareCounter)
            textViewViews.text = translate(post.viewsCounter)

            likes.setImageResource(R.drawable.ic_like_24)
            likes.setOnClickListener {
                post.likeByMe = !post.likeByMe

                likes.setImageResource(
                    if (post.likeByMe) {
                        textViewLikes.text = translate(post.likesCounter++)
                        R.drawable.ic_like_24
                    } else {
                        textViewLikes.text = translate(post.likesCounter--)
                        R.drawable.ic_liked_24
                    }
                )
            }

            share.setOnClickListener {
                textViewShare.text = translate(post.shareCounter++)
            }
        }

    }
}