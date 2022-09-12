package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl
import ru.netology.nmedia.dto.Post

private val empty = Post(
    id = 0L,
    author = "",
    content= "",
    published= "",
    likes = 0,
    share = 0,
    likedByMe = false,
    urlVideo = "https://www.youtube.com/watch?v=WhWc3b3KhnY" // временно. чтобы при добавлении нового поста вставлялось видео
)

class PostViewModel : ViewModel() {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getALL()
    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun clickShareById(id: Long) = repository.clickShareById(id)
    fun removeById(postId: Long) = repository.removeById(postId)

    fun edit(post:Post){
        edited.value = post
    }

    fun changeContentAndSave(content:String){
        if (content == edited.value?.content){
            return
        }
        edited.value?.let {
            repository.save(it.copy(content = content))
            edited.value = empty
        }

    }

    fun canelEdit(){
        edited.value = empty
    }
}