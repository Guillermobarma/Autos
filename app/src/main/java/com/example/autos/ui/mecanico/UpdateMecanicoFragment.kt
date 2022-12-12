package com.example.autos.ui.mecanico

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
import com.example.autos.databinding.FragmentUpdateMecanicoBinding
import com.example.autos.model.Mecanico
import com.example.autos.viewmodel.MecanicoViewModel

class UpdateMecanicoFragment : Fragment() {
    private var _binding: FragmentUpdateMecanicoBinding? = null
    private val binding get() = _binding!!
    private lateinit var mecanicoViewModel: MecanicoViewModel

    //defino un objeto para obtener los argumentos
    private val args by navArgs<UpdateMecanicoFragmentArgs>()

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mecanicoViewModel = ViewModelProvider(this).get(MecanicoViewModel::class.java)
        _binding = FragmentUpdateMecanicoBinding.inflate(inflater, container, false)

        binding.etDescripcion.setText(args.mecanico.descripcion)
        binding.etCelular.setText(args.mecanico.celular)

        binding.btUpdateMecanico.setOnClickListener { updateMecanico() }
        binding.btDeleteMecanico.setOnClickListener { deleteMecanico() }

        return binding.root
    }

    private fun verWeb() {
        TODO("Not yet implemented")
    }


    private fun deleteMecanico() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.bt_delete_mecanico))
        builder.setMessage(getString(R.string.msg_seguro_borrado) + " ${args.mecanico.descripcion}? ")
        builder.setNegativeButton(getString(R.string.msg_no)) { _, _ -> }//significa ninguna instruccion
        builder.setPositiveButton(getString(R.string.msg_si)) { _, _ ->
            mecanicoViewModel.deleteMecanico(args.mecanico)
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_mecanico_deleted),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateMecanicoFragment_to_nav_mecanico)
        }

        builder.create().show()
    }

    private fun updateMecanico() {
        val descripcion = binding.etDescripcion.text.toString()
        if (descripcion.isNotEmpty()) {  //Se puede agregar un mecanico...
            val celular=binding.etCelular.text.toString()

            val mecanico = Mecanico(
                args.mecanico.id,
                descripcion,
                celular
            )
            mecanicoViewModel.saveMecanico(mecanico)
            Toast.makeText(
                requireContext(), getString(R.string.msg_mecanico_updated),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateMecanicoFragment_to_nav_mecanico)
        } else {  //No se puede agregar el mecanico...
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
