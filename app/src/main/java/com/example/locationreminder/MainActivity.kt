package com.example.locationreminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.locationreminder.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_LocationReminder)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setSupportActionBar(binding.topAppBar)

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.favorite -> {
                    true
                }
                R.id.saved_location -> {
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }
    }
}