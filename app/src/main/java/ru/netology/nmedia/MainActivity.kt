package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.activity.OnInteractionListener
import ru.netology.nmedia.activity.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(object : OnInteractionListener{
            override fun edit(post: Post) {
                viewModel.edit(post)
            }
            override fun like(post: Post) {
                viewModel.likeById(post.id)
            }
            override fun share(post: Post) {
                viewModel.clickShareById(post.id)
            }
            override fun remove(post: Post) {
                viewModel.removeById(post.id)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this){
            if (it.id ==0L){
                return@observe
            }
            binding.groupEditor.visibility = View.VISIBLE
            with(binding.content){
                setText(it.content)
                requestFocus()
            }
        }

        binding.buttonCancel.setOnClickListener{
            binding.groupEditor.visibility = View.GONE
            with(binding.content){
                setText("")
                AndroidUtils.hideKeyboard(this)
            }
        }

        binding.buttonSend.setOnClickListener{
            with(binding.content){
                val text = text?.toString()
                if(text.isNullOrBlank()){
                    Toast.makeText(context,R.string.empty_post_error,Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(text)
                viewModel.save()
                setText("")
                clearFocus()
                binding.groupEditor.visibility = View.GONE
                AndroidUtils.hideKeyboard(this)
            }
        }
    }
}
