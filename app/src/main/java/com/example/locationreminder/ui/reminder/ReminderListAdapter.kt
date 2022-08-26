package com.example.locationreminder.ui.reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.locationreminder.databinding.ReminderListItemBinding
import com.example.locationreminder.model.Reminder

class ReminderListAdapter : ListAdapter<Reminder, ReminderListAdapter.ViewHolder>(ReminderDiffCallback()) {

    class ViewHolder private constructor(val viewDataBinding: ReminderListItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun onBind(item: Reminder) {
            viewDataBinding.reminder = item
            println(item)
            viewDataBinding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val withDataBinding = ReminderListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(withDataBinding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

class ReminderDiffCallback : DiffUtil.ItemCallback<Reminder>() {
    override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem == newItem
    }

}