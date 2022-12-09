package com.example.autos.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.autos.data.ServicioDao
import com.example.autos.model.Servicio
import com.example.autos.repository.ServicioRepository
import kotlinx.coroutines.launch

class ServicioViewModel(application: Application) : AndroidViewModel(application) {
    val getServicios : MutableLiveData<List<Servicio>>

    private val repository: ServicioRepository = ServicioRepository(ServicioDao())

    init {
        getServicios = repository.getServicios
    }

    fun saveServicio(servicio: Servicio) {
        viewModelScope.launch { repository.saveServicio(servicio) }
    }

    fun deleteServicio(servicio: Servicio) {
        viewModelScope.launch { repository.deleteServicio(servicio)}
    }
}