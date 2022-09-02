package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getALL(): LiveData<List<Post>>
    fun likeById(id:Long)
    fun clickShareById(id:Long)
    fun removeById(id:Long)
    fun save(post:Post)
}