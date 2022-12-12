package com.example.autos.ui.auto

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
import com.example.autos.databinding.FragmentUpdateAutoBinding
import com.example.autos.model.Auto
import com.example.autos.viewmodel.AutoViewModel

class UpdateAutoFragment : Fragment() {
    private var _binding: FragmentUpdateAutoBinding? = null
    private val binding get() = _binding!!
    private lateinit var autoViewModel: AutoViewModel

    //defino un objeto para obtener los argumentos
    private val args by navArgs<UpdateAutoFragmentArgs>()

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        autoViewModel = ViewModelProvider(this).get(AutoViewModel::class.java)
        _binding = FragmentUpdateAutoBinding.inflate(inflater, container, false)

        binding.etDescripcion.setText(args.auto.nombre)
        //binding.etMarca.setText(args.auto.marca)

        binding.btUpdateAuto.setOnClickListener { updateAuto() }
        binding.btDeleteAuto.setOnClickListener { deleteAuto() }

        return binding.root
    }

    private fun verWeb() {
        TODO("Not yet implemented")
    }


        private fun deleteAuto() {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.bt_delete_auto))
            builder.setMessage(getString(R.string.msg_seguro_borrado) + " ${args.auto.nombre}? ")
            builder.setNegativeButton(getString(R.string.msg_no)) { _, _ -> }//significa ninguna instruccion
            builder.setPositiveButton(getString(R.string.msg_si)) { _, _ ->
                autoViewModel.deleteAuto(args.auto)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.msg_auto_deleted),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_updateAutoFragment_to_nav_auto)
            }

            builder.create().show()
        }

        private fun updateAuto() {
            val nombre = binding.etNombre.text.toString()
            if (nombre.isNotEmpty()) {  //Se puede agregar un auto...
                val marca=binding.etMarca.text.toString()
                val auto = Auto(
                    args.auto.id,
                    nombre,
                    marca
                )
                autoViewModel.saveAuto(auto)
                Toast.makeText(
                    requireContext(), getString(R.string.msg_auto_updated),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_updateAutoFragment_to_nav_auto)
            } else {  //No se puede agregar el auto...
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
