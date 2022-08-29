package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getALL()
    fun likeById(id: Long) = repository.likeById(id)
    fun clickShareById(id: Long) = repository.clickShareById(id)
    //fun consumClicks(countClicks: Int) = repository.consumClicks(countClicks)

}