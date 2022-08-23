package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.viewmodel.PostViewModel
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                favorite.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_baseline_favorite_border_24
                )
                textFavorite.text = viewModel.consumClicks(post.likes)
                textShare.text = viewModel.consumClicks(post.share)
            }
        }

        binding.favorite.setOnClickListener {
            viewModel.like()
            viewModel.data.observe(this) { post ->
                with(binding) {
                    textFavorite.text = viewModel.consumClicks(post.likes)
                }
            }
        }

        binding.share.setOnClickListener() {
            viewModel.clickShare()
            viewModel.data.observe(this) { post ->
                with(binding) {
                    textShare.text = viewModel.consumClicks(post.share)
                }
            }
        }
    }
}
