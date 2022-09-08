package com.example.locationreminder.ui.reminder

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locationreminder.R
import com.example.locationreminder.databinding.FragmentReminderListBinding
import com.example.locationreminder.utils.GeoFenceBroadcastReceiver
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ReminderListFragment : Fragment() {

    private val viewModel by sharedViewModel<ReminderListViewModel>()
    private lateinit var binding: FragmentReminderListBinding
    private lateinit var viewModelAdapter: ReminderListAdapter
    lateinit var geofencingClient: GeofencingClient

    private val directions = ReminderListFragmentDirections.actionReminderListFragmentToReminderMapsFragment()
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(requireContext(), GeoFenceBroadcastReceiver::class.java)
//        intent.action = ACTION_GEOFENCE_EVENT
        // Use FLAG_UPDATE_CURRENT so that you get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

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
        geofencingClient = LocationServices.getGeofencingClient(requireContext())

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
                return when (menuItem.itemId) {
                    R.id.favorite -> {
                        AuthUI.getInstance().signOut(requireContext())
                        findNavController().navigate(ReminderListFragmentDirections.actionReminderListFragmentToAuthFragment())
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.getAllReminders.observe(viewLifecycleOwner) {
            it?.let {
                viewModelAdapter.submitList(it)
                viewModel.onSwipeEnd()
            }
        }

        viewModel.statusOfAddFab.observe(viewLifecycleOwner, Observer {
            if (it) {
                val transitionName by lazy { getString(R.string.fab_transition) }
                val extra = FragmentNavigatorExtras(binding.floatingActionButton2 to transitionName)
                findNavController().navigate(directions, extra)
                Timber.e("clicked")
                exitTransition = MaterialElevationScale(false).apply {
                    duration = 500
                }
                reenterTransition = MaterialElevationScale(true).apply {
                    duration = 500
                }
                viewModel.addFabStatusChangeOnNavigated()
            }
        })
    }

}