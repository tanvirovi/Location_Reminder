package com.example.locationreminder.ui.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.locationreminder.R
import com.example.locationreminder.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private val viewModel : WelcomeViewModel by viewModels()
    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_welcome,
            container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}