package ru.netology.nmedia

import android.os.Bundle
import androidx.navigation.NavController
import ru.netology.nmedia.NewPostFragment.Companion.textArg

class RouterImpl(
    val navController: NavController

):Router {
    override fun toNewPostScreen(text: String) {
        navController.navigate(
            R.id.action_feedFragment_to_newPostFragment,
            Bundle().apply {
                textArg = text
            }
        )
    }
 }