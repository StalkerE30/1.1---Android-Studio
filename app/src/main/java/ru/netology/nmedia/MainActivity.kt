package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import android.util.Log
import ru.netology.nmedia.activity.PostAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(onClickListener = {
            viewModel.likeById(it.id)
            }, onShareListener = {
            viewModel.clickShareById(it.id)
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

    }
}
