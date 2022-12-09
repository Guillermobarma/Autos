package com.example.autos.ui.auto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.autos.R
import com.example.autos.adapter.AutoAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.autos.databinding.FragmentAutoBinding
import com.example.autos.viewmodel.AutoViewModel

class AutoFragment : Fragment() {

    private var _binding: FragmentAutoBinding? = null
    private val binding get() = _binding!!
    private lateinit var autoViewModel: AutoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        autoViewModel =
            ViewModelProvider(this).get(AutoViewModel::class.java)

        _binding = FragmentAutoBinding.inflate(inflater, container, false)

        binding.btAddServicioFab.setOnClickListener {
            findNavController().navigate(R.id.action_nav_auto_to_addAutoFragment)
        }

        //activacion del ReciclerView
        val autoAdapter= AutoAdapter() //objeto del adaptador desarrolado para dibujar los ligares en las cajitas
        val reciclador = binding.reciclador // recupera el reciclerview de la vista

        reciclador.adapter= autoAdapter // se asocia el adaptador programado al rec
        reciclador.layoutManager = LinearLayoutManager(requireContext())

        autoViewModel.getAutos.observe(viewLifecycleOwner){ autos ->
            autoAdapter.setData(autos)//este es el que llena todas las filas del reclacler view
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}