package com.example.autos.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.autos.data.ServicioDao
import com.example.autos.model.Servicio

class ServicioRepository(private val servicioDao: ServicioDao) {

    fun saveServicio(servicio: Servicio) {
        servicioDao.saveServicio(servicio)
    }

    fun deleteServicio(servicio: Servicio) {
        servicioDao.deleteServicio(servicio)
    }

    val getServicios : MutableLiveData<List<Servicio>> = servicioDao.getServicios()
}