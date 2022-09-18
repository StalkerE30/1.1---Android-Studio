package ru.netology.nmedia

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.util.StringArg

class NewPostFragment : Fragment(){
    companion object {
        var Bundle.textArg: String? by StringArg
    }
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )

        arguments?.textArg
            ?.let(binding.content::setText)

//        val postContent = arguments?.getString("texArg")
//            if(postContent !=null) {
//            with(binding) {
//                content.setText(postContent)
//            }
//        }

        binding.ok.setOnClickListener{
            viewModel.changeContentAndSave(binding.content.text.toString())
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()

        }
        return binding.root
    }
}




