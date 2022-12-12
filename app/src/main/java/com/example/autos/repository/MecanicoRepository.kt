package com.example.autos.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.autos.data.MecanicoDao
import com.example.autos.model.Mecanico

class MecanicoRepository(private val mecanicoDao: MecanicoDao) {

    fun saveMecanico(mecanico: Mecanico) {
        mecanicoDao.saveMecanico(mecanico)
    }

    fun deleteMecanico(mecanico: Mecanico) {
        mecanicoDao.deleteMecanico(mecanico)
    }

    val getMecanicos : MutableLiveData<List<Mecanico>> = mecanicoDao.getMecanicos()
}