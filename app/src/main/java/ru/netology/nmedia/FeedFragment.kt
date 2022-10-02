package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.FragmentViewPost.Companion.idArg
import ru.netology.nmedia.NewPostFragment.Companion.textArg
import ru.netology.nmedia.activity.OnInteractionListener
import ru.netology.nmedia.activity.PostAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = PostAdapter(object : OnInteractionListener {
            override fun edit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(
                    R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { textArg = post.content }
                )
            }

            override fun like(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun share(post: Post) {
                viewModel.clickShareById(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content + " " + (post.urlVideo ?: ""))
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, "chooser_share_post")
                startActivity(shareIntent)
            }

            override fun playVideoContent(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.urlVideo))
                val youtubeIntent = Intent.createChooser(intent, "choose Youtube")
                startActivity(youtubeIntent)
            }

            override fun remove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun viewPost(post: Post) {
                findNavController().navigate(R.id.action_feedFragment_to_fragmentViewPost,
                    Bundle().apply { idArg = post.id }
                )
            }

        })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)

        }

        return binding.root
    }
}
