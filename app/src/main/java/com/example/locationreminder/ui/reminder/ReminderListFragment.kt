package com.example.locationreminder.ui.reminder

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locationreminder.R
import com.example.locationreminder.databinding.FragmentReminderListBinding
import com.example.locationreminder.model.Reminder
import com.firebase.ui.auth.AuthUI
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ReminderListFragment : Fragment() {

    private val viewModel by viewModel<ReminderListViewModel>()
    private lateinit var binding: FragmentReminderListBinding
    private lateinit var viewModelAdapter: ReminderListAdapter

//    private val reminder = listOf<Reminder>(Reminder(1, "test", "test", "test"))

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
        binding.viewModel = viewModel
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

        viewModel.getAllReminders.observe(viewLifecycleOwner) {
            it?.let {
                viewModelAdapter.submitList(it)
            }
        }

        viewModel.statusOfAddFab.observe(viewLifecycleOwner, Observer {
            if (it){
                findNavController().navigate(ReminderListFragmentDirections.actionReminderListFragmentToReminderListAddItem())
                Timber.e("clicked")
                viewModel.fabStatusChangeOnNavigated()
            }
        })
    }
}