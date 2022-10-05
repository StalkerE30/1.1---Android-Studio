package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likeById(id: Long)
    fun removeById(id: Long)
    fun clickShareById(id:Long)
}
