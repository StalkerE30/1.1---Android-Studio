package ru.netology.nmedia.activity

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
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
    private val onClickListener:(Post) -> Unit,
    private val onShareListener:(Post) -> Unit,
):RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            favorite.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_baseline_favorite_border_24
            )
            textFavorite.text = consumClicks(post.likes)
            textShare.text = consumClicks(post.share)

            favorite.setOnClickListener {
                onClickListener(post)
            }
            share.setOnClickListener{
                onShareListener(post)
            }
        }
    }
}
