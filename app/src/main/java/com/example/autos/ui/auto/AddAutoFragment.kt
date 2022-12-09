package com.example.autos.ui.auto

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
import com.example.autos.databinding.FragmentAddAutoBinding
import com.example.autos.model.Auto
import com.example.autos.utiles.AudioUtiles
import com.example.autos.utiles.ImagenUtiles
import com.example.autos.viewmodel.AutoViewModel

class AddAutoFragment : Fragment() {
    private var _binding: FragmentAddAutoBinding? = null
    private val binding get() = _binding!!
    private lateinit var autoViewModel: AutoViewModel

    //Para capturar la imagen del lugar
    private lateinit var tomarFotoActivity: ActivityResultLauncher<Intent>
    private lateinit var imagenUtiles: ImagenUtiles

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        autoViewModel = ViewModelProvider(this).get(AutoViewModel::class.java)
        _binding = FragmentAddAutoBinding.inflate(inflater, container, false)

        binding.btAdd.setOnClickListener {
            binding.progressBar.visibility = ProgressBar.VISIBLE
            binding.msgMensaje.text = getString(R.string.msg_subiendo_imagen)
            }

        //inicializa objeto de activity para tomar la foto
        tomarFotoActivity= registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                imagenUtiles.actualizaFoto()
            }
        }

        //se inicializa el objeto para gestionar foto del lugar
        imagenUtiles = ImagenUtiles(
            requireContext(),
            binding.btPhoto,
            binding.btRotaL,
            binding.btRotaR,
            binding.imagen,
            tomarFotoActivity)

        return binding.root
    }

    private fun subeImagen(rutaPublicaAudio: String) {
        binding.msgMensaje.text = getString(R.string.msg_subiendo_imagen)
        val imagenFile = imagenUtiles.imagenFile
        if(imagenFile.exists() && imagenFile.isFile && imagenFile.canRead()){
            val rutaLocal = Uri.fromFile(imagenFile)
            val rutaNube = "AutosApp/${Firebase.auth.currentUser?.email}/imagenes/${imagenFile.name}"
            //esta de abajo basado en el string de arriba genera una referencia a firebase
            val referencia : StorageReference = Firebase.storage.reference.child(rutaNube)
            referencia.putFile(rutaLocal)//esto genera subir el archivo a la nube
                .addOnSuccessListener {
                    referencia.downloadUrl.addOnSuccessListener {
                        val rutaPublicaImagen = it.toString()
                        subeAuto(rutaPublicaAudio, rutaPublicaImagen)
                    }
                }//si pasa elgún problema entonces...sube url vacia con ""
                .addOnCanceledListener {
                    subeAuto(rutaPublicaAudio,"")
                }
        } else{
            subeAuto(rutaPublicaAudio,"")
        }
    }

    private fun subeAuto(rutaPublicaAudio: String, rutaPublicaImagen: String) {
        val nombre=binding.etNombre.text.toString()
        if (nombre.isNotEmpty()) {  //Se puede agregar un auto...
            val marca=binding.etFecha.text.toString()

            val auto = Auto("",nombre,marca,"")
            autoViewModel.saveAuto(auto)
            Toast.makeText(requireContext(),getString(R.string.msg_auto_added),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addAutoFragment_to_nav_auto)
        } else {  //No se puede agregar el auto...
            Toast.makeText(requireContext(),getString(R.string.msg_data),
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}