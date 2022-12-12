package com.example.autos.ui.mecanico


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.autos.databinding.FragmentMecanicoBinding
import com.example.autos.viewmodel.MecanicoViewModel

class MecanicoFragment : Fragment() {

    private var _binding: FragmentMecanicoBinding? = null
    private val binding get() = _binding!!
    private lateinit var mecanicoViewModel: MecanicoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mecanicoViewModel =
            ViewModelProvider(this).get(MecanicoViewModel::class.java)

        _binding = FragmentMecanicoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        mecanicoViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}