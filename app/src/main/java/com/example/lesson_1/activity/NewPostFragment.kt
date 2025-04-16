package com.example.lesson_1.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lesson_1.databinding.FragmentNewPostBinding
import com.example.lesson_1.util.StringArg
import com.example.lesson_1.viewmodel.PostViewModel

class NewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        arguments?.textArg?.let {
            binding.content.setText(it)
        }

        binding.content.requestFocus()

        binding.ok.setOnClickListener {
            val content = binding.content.text.toString()
            if (content.isNotBlank()) {
                viewModel.changeContentAndSave(content)
            }
            findNavController().navigateUp()
        }
        return binding.root
    }

    companion object {
        var Bundle.textArg by StringArg
    }
}