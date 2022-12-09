package com.example.autos.ui.servicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.autos.databinding.FragmentServicioBinding
import com.example.autos.viewmodel.ServicioViewModel

class ServicioFragment : Fragment() {

    private var _binding: FragmentServicioBinding? = null
    private val binding get() = _binding!!
    private lateinit var servicioViewModel: ServicioViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        servicioViewModel =
            ViewModelProvider(this).get(ServicioViewModel::class.java)

        _binding = FragmentServicioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        servicioViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}