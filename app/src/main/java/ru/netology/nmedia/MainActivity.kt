package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
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
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,post.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent,"chooser_share_post")
                startActivity(shareIntent)
            }
            override fun playVideoContent(post:Post){
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    Intent(action, Uri.parse(post.urlVideo))
                }
                val shareIntent = Intent.createChooser(intent,"choose Youtube")
                startActivity(shareIntent)

            }
            override fun remove(post: Post) {
                viewModel.removeById(post.id)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        val activityLauncher = registerForActivityResult(NewPostActivity.Contract){text ->
            text?: return@registerForActivityResult
            viewModel.changeContentAndSave(text)

        }

        viewModel.edited.observe(this){post->
            if (post.id ==0L){
                return@observe
                }


            //binding.groupEditor.visibility = View.VISIBLE
            activityLauncher.launch(post.content)
        }


//        binding.buttonCancel.setOnClickListener{
//            binding.groupEditor.visibility = View.GONE
//            with(binding.content){
//                setText("")
//                viewModel.canelEdit()
//                AndroidUtils.hideKeyboard(this)
//            }
//        }

        binding.add.setOnClickListener{
            activityLauncher.launch(null)

        }
    }
}
