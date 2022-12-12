package com.example.autos.ui.servicio

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.autos.R
import com.example.autos.databinding.FragmentUpdateServicioBinding
import com.example.autos.model.Servicio
import com.example.autos.viewmodel.ServicioViewModel

class UpdateServicioFragment : Fragment() {
    private var _binding: FragmentUpdateServicioBinding? = null
    private val binding get() = _binding!!
    private lateinit var servicioViewModel: ServicioViewModel

    //defino un objeto para obtener los argumentos
    private val args by navArgs<UpdateServicioFragmentArgs>()

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        servicioViewModel = ViewModelProvider(this).get(ServicioViewModel::class.java)
        _binding = FragmentUpdateServicioBinding.inflate(inflater, container, false)

        binding.etFecha.setText(args.servicio.fecha)
        binding.etDescripcion.setText(args.servicio.descripcion)
        binding.etKilometraje.setText(args.servicio.kilometraje)

        binding.btUpdateServicio.setOnClickListener { updateServicio() }
        binding.btDeleteServicio.setOnClickListener { deleteServicio() }

        return binding.root
    }

    private fun verWeb() {
        TODO("Not yet implemented")
    }


        private fun deleteServicio() {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.bt_delete_servicio))
            builder.setMessage(getString(R.string.msg_seguro_borrado) + " ${args.servicio.descripcion}? ")
            builder.setNegativeButton(getString(R.string.msg_no)) { _, _ -> }//significa ninguna instruccion
            builder.setPositiveButton(getString(R.string.msg_si)) { _, _ ->
                servicioViewModel.deleteServicio(args.servicio)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.msg_servicio_deleted),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_updateServicioFragment_to_nav_servicio)
            }

            builder.create().show()
        }

        private fun updateServicio() {
            val descripcion = binding.etDescripcion.text.toString()
            if (descripcion.isNotEmpty()) {  //Se puede agregar un servicio...
                val fecha=binding.etFecha.text.toString()
                val kilometraje= binding.etKilometraje.text.toString()

                val servicio = Servicio(
                    args.servicio.id,
                    fecha,
                    descripcion,
                    kilometraje
                )
                servicioViewModel.saveServicio(servicio)
                Toast.makeText(
                    requireContext(), getString(R.string.msg_servicio_updated),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_updateServicioFragment_to_nav_servicio)
            } else {  //No se puede agregar el servicio...
                Toast.makeText(
                    requireContext(), getString(R.string.msg_data),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
