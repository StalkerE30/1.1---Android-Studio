package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentViewPostBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.LongArg
import ru.netology.nmedia.viewmodel.PostViewModel

class FragmentViewPost : Fragment(){
    companion object {
        var Bundle.idArg: Long by LongArg
    }
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewPostBinding.inflate(
            inflater,
            container,
            false
        )
        val postId = arguments?.getLong("idArg") ?: -1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: return@observe
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content + " \n" + (post.urlVideo ?: "")
                favorite.text = post.likes.toString()
                favorite.isChecked = post.likedByMe
                favorite.setOnClickListener{
                    viewModel.likeById(post.id)
                }
                share.setOnClickListener{
                    viewModel.clickShareById(post.id)
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content + " " + (post.urlVideo ?: ""))
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(intent, "chooser_share_post")
                    startActivity(shareIntent)

                }
                share.text = post.share.toString()
                menu.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.options_post)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.remove -> {
                                    viewModel.removeById(postId)
                                    AndroidUtils.hideKeyboard(requireView())
                                    findNavController().navigate(R.id.action_viewPostFragment_to_feedFragment)
                                    true
                                }
                                R.id.edit -> {
                                    viewModel.edit(post)
                                    findNavController().navigate(R.id.action_viewPostFragment_to_newPostFragment,Bundle().apply {textArg = post.content})
                                    true
                                }
                                else -> false
                            }
                        }
                    }.show()


                }
            }
        }

        return binding.root
    }

}