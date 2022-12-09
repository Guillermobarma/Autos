package com.example.autos.ui.servicio

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.example.autos.R
import com.example.autos.databinding.FragmentAddServicioBinding
import com.example.autos.model.Servicio
import com.example.autos.utiles.AudioUtiles
import com.example.autos.utiles.ImagenUtiles
import com.example.autos.viewmodel.ServicioViewModel

class AddServicioFragment : Fragment() {
    private var _binding: FragmentAddServicioBinding? = null
    private val binding get() = _binding!!
    private lateinit var servicioViewModel: ServicioViewModel


    private fun subeServicio(rutaPublicaAudio: String, rutaPublicaImagen: String) {
        val descripcion=binding.etNombre.text.toString()
        if (descripcion.isNotEmpty()) {  //Se puede agregar un servicio...
            val fecha=binding.etFecha.text.toString()
            val kilometraje= binding.etKilometraje.text.toString()

            val servicio = Servicio("",fecha, descripcion, kilometraje)
            servicioViewModel.saveServicio(servicio)
            Toast.makeText(requireContext(),getString(R.string.msg_servicio_added),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addServicioFragment_to_nav_servicio)
        } else {  //No se puede agregar el servicio...
            Toast.makeText(requireContext(),getString(R.string.msg_data),
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}