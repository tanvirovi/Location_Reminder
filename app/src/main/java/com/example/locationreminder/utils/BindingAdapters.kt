package com.example.locationreminder.utils

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.locationreminder.model.Reminder


@BindingAdapter("android:fadeVisible")
fun setFadeVisible(view: View, items: LiveData<List<Reminder>>?) {
    items?.value?.let {
        if (it.isEmpty()) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }
}

@BindingAdapter("setRefresh")
fun setRefresh(swipeRefreshLayout: SwipeRefreshLayout, refresh: LiveData<Boolean>) {
    swipeRefreshLayout.setOnRefreshListener {
        Log.e("refresh", refresh.value.toString())
        swipeRefreshLayout.isRefreshing = refresh.value!!
    }
}