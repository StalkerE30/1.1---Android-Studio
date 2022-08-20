package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import android.util.Log

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 999,
    var share: Int = 999999,
    var likedByMe: Boolean = false
)
private fun consumClicks(countClicks: Int):String{
    val res:String
    when {
        (countClicks/1_000_000).toString().length>=2 -> res = (countClicks/1_000_000).toString() + "M"
        (countClicks/1_000_000)!=0 && (countClicks/1_000_000).toString().length==1 -> res = (countClicks/1_000_000.0).toString().subSequence(0,3).toString()+"M"
        (countClicks/1_000).toString().length>=2 -> res = (countClicks/1_000).toString() + "K"
        (countClicks/1_000)!=0 && (countClicks/1_000).toString().length==1 -> res = (countClicks/1_000.0).toString().subSequence(0,3).toString()+"K"
        else -> res=countClicks.toString()
        }
    return res
}
class MainActivity : AppCompatActivity() {

    private lateinit var post: Post
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initViews()
        setupListeners()
    }
    private fun initData(){
        post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
        )
    }
    private fun initViews(){
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe)
                favorite?.setImageResource(R.drawable.ic_liked_24)
            else
                favorite?.setImageResource(R.drawable.ic_baseline_favorite_border_24)

            textFavorite?.text = consumClicks(post.likes)
            textShare?.text = consumClicks(post.share)
        }
    }

    private fun setupListeners(){
        binding.apply {
            favorite.setOnClickListener {
                post.likedByMe = !post.likedByMe
                favorite.setImageResource(
                    if(post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_baseline_favorite_border_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                textFavorite.text = consumClicks(post.likes)
            }
            share.setOnClickListener {
                post.share++
                textShare.text = consumClicks(post.share)
            }
        }
    }
}

