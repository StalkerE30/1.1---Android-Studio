package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        likes = 999,
        share = 999999,
        likedByMe = false
    )
    private val data = MutableLiveData(post)

    override fun consumClicks(countClicks: Int):String {
        val res: String
        when {
            (countClicks / 1_000_000).toString().length >= 2 -> res =
                (countClicks / 1_000_000).toString() + "M"
            (countClicks / 1_000_000) != 0 && (countClicks / 1_000_000).toString().length == 1 -> res =
                (countClicks / 1_000_000.0).toString().subSequence(0, 3).toString() + "M"
            (countClicks / 1_000).toString().length >= 2 -> res =
                (countClicks / 1_000).toString() + "K"
            (countClicks / 1_000) != 0 && (countClicks / 1_000).toString().length == 1 -> res =
                (countClicks / 1_000.0).toString().subSequence(0, 3).toString() + "K"
            else -> res = countClicks.toString()
        }
        return res
    }

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        if (post.likedByMe) post = post.copy(likes = ++post.likes) else post = post.copy(likes = --post.likes)
        data.value = post
    }
    override fun clickShare(){
        post = post.copy(share = ++post.share)
        data.value = post
    }
}