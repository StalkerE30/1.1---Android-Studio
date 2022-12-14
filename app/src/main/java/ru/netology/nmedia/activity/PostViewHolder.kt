package ru.netology.nmedia.activity

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.PopupMenu
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.dto.Post


fun consumClicks(countClicks: Int): String {
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

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener:OnInteractionListener,
):RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) favorite.isChecked=true else favorite.isChecked =false

            favorite.text = consumClicks(post.likes)
            share.text = consumClicks(post.share)

            favorite.setOnClickListener {
                listener.like(post)
            }
            share.setOnClickListener{
                listener.share(post)
            }
            urlVideo.text = post.urlVideo

            videoPoster.setOnClickListener(){
                listener.playVideoContent(post)}

            play.setOnClickListener(){
                listener.playVideoContent(post) }

            if (post.urlVideo.isNullOrBlank()){
                groupVideo.visibility=View.GONE
            } else{
                groupVideo.visibility=View.VISIBLE
            }

            content.setOnClickListener{
                listener.viewPost(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context,it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.remove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.edit(post)
                                true
                                }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}
