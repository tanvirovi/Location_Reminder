package com.example.locationreminder.ui.reminder

import android.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.locationreminder.R
import com.example.locationreminder.databinding.FragmentAuthBinding
import com.example.locationreminder.databinding.FragmentReminderListBinding
import com.example.locationreminder.model.Reminder
import com.example.locationreminder.ui.auth.AuthViewModel
import com.firebase.ui.auth.AuthUI

class ReminderListFragment : Fragment() {

    private val viewModel: ReminderListViewModel by activityViewModels()
    private lateinit var binding: FragmentReminderListBinding
    private var viewModelAdapter: ReminderListAdapter? = null

    private val reminder = listOf<Reminder>(Reminder(1, "test", "test", "test"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_reminder_list,
            container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        viewModelAdapter = ReminderListAdapter()

        binding.locationRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.top_app_bar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    R.id.favorite -> {
                        AuthUI.getInstance().signOut(requireContext())
                        findNavController().navigate(ReminderListFragmentDirections.actionReminderListFragmentToAuthFragment())
                        true
                    }else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModelAdapter?.submitList(reminder)
    }
}