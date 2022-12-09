package com.example.autos.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.autos.data.AutoDao
import com.example.autos.model.Auto

class AutoRepository(private val autoDao: AutoDao) {

    fun saveAuto(auto: Auto) {
        autoDao.saveAuto(auto)
    }

    fun deleteAuto(auto: Auto) {
        autoDao.deleteAuto(auto)
    }

    val getAutos : MutableLiveData<List<Auto>> = autoDao.getAutos()
}