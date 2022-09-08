package com.example.locationreminder.di

import androidx.room.Room
import com.example.locationreminder.database.RemindersDAO
import com.example.locationreminder.database.RemindersDatabase
import com.example.locationreminder.repository.GeoFenceRepository
import com.example.locationreminder.repository.RemindersRepository
import com.example.locationreminder.repository.RemindersRepositoryImpl
import com.example.locationreminder.ui.reminder.ReminderListViewModel
import com.example.locationreminder.utils.Constants.REMINDERS_DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            RemindersDatabase::class.java,
            REMINDERS_DATABASE_NAME
        ).build()
    }
    single<RemindersRepository> {
        RemindersRepositoryImpl(get())
    }
    single {
        GeoFenceRepository(get())
    }
    viewModel {
        ReminderListViewModel(get(),get())
    }
    single<RemindersDAO> {
        val database = get<RemindersDatabase>()
        database.getRemindersDao()
    }
}