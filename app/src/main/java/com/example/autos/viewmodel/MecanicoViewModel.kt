package com.example.autos.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.autos.data.MecanicoDao
import com.example.autos.model.Mecanico
import com.example.autos.repository.MecanicoRepository
import kotlinx.coroutines.launch

class MecanicoViewModel(application: Application) : AndroidViewModel(application) {
    val getMecanicos : MutableLiveData<List<Mecanico>>

    private val repository: MecanicoRepository = MecanicoRepository(MecanicoDao())

    init {
        getMecanicos = repository.getMecanicos
    }

    fun saveMecanico(mecanico: Mecanico) {
        viewModelScope.launch { repository.saveMecanico(mecanico) }
    }

    fun deleteMecanico(mecanico: Mecanico) {
        viewModelScope.launch { repository.deleteMecanico(mecanico)}
    }
}