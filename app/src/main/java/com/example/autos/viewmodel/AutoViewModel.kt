package com.example.autos.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.autos.data.AutoDao
import com.example.autos.model.Auto
import com.example.autos.repository.AutoRepository
import kotlinx.coroutines.launch

class AutoViewModel(application: Application) : AndroidViewModel(application) {
    val getAutos : MutableLiveData<List<Auto>>

    private val repository: AutoRepository = AutoRepository(AutoDao())

    init {
        getAutos = repository.getAutos
    }

    fun saveAuto(auto: Auto) {
        viewModelScope.launch { repository.saveAuto(auto) }
    }

    fun deleteAuto(auto: Auto) {
        viewModelScope.launch { repository.deleteAuto(auto)}
    }
}