package com.example.locationreminder.ui.reminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.locationreminder.R
import com.example.locationreminder.databinding.FragmentReminderListAddItemBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReminderListAddItemFragment : Fragment() {

    private val viewModel by viewModel<ReminderListViewModel>()
    private lateinit var binding: FragmentReminderListAddItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_reminder_list_add_item,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.statusOfSaveFab.observe(viewLifecycleOwner, Observer {
            if (it){
                findNavController().navigate(ReminderListAddItemFragmentDirections.actionReminderListAddItemToReminderListFragment2())
                viewModel.saveFabStatusChangeOnNavigated()
            }
        })

        viewModel.statusMessage.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { message ->
                print(message)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        })

        binding.textView2.setOnClickListener{
            findNavController().navigate(ReminderListAddItemFragmentDirections.actionReminderListAddItemToReminderMapsFragment())
        }
    }
}