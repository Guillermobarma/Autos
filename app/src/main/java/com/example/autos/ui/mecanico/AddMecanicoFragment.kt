package com.example.autos.ui.mecanico

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
import com.example.autos.databinding.FragmentAddMecanicoBinding
import com.example.autos.model.Mecanico
import com.example.autos.utiles.AudioUtiles
import com.example.autos.utiles.ImagenUtiles
import com.example.autos.viewmodel.MecanicoViewModel

class AddMecanicoFragment : Fragment() {
    private var _binding: FragmentAddMecanicoBinding? = null
    private val binding get() = _binding!!
    private lateinit var mecanicoViewModel: MecanicoViewModel


    private fun subeMecanico(rutaPublicaAudio: String, rutaPublicaImagen: String) {
        val descripcion=binding.etDescripcion.text.toString()
        if (descripcion.isNotEmpty()) {  //Se puede agregar un mecanico...
            val celular= binding.etCelular.text.toString()

            val mecanico = Mecanico("", descripcion, celular, 0.0,0.0,0.0)
            mecanicoViewModel.saveMecanico(mecanico)
            Toast.makeText(requireContext(),getString(R.string.msg_mecanico_added),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addMecanicoFragment_to_nav_mecanico)
        } else {  //No se puede agregar el mecanico...
            Toast.makeText(requireContext(),getString(R.string.msg_data),
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}