package com.example.autos.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.autos.databinding.ServicioFilaBinding
import com.example.autos.model.Servicio
import com.example.autos.ui.servicio.ServicioFragmentDirections

class ServicioAdapter : RecyclerView.Adapter<ServicioAdapter.ServicioViewHolder>(){

    // lista para presentar la informacion de los Servicios
    private var listaServicios = emptyList<Servicio>()

    inner class ServicioViewHolder(private val itemBinding: ServicioFilaBinding):
        RecyclerView.ViewHolder(itemBinding.root){
        fun bind(servicio: Servicio){
            itemBinding.tvNombre.text = servicio.descripcion
            itemBinding.tvFecha.text = servicio.fecha
            itemBinding.tvKilometraje.text = servicio.kilometraje

//            itemBinding.vistaFila.setOnClickListener{
//                val accion = ServicioFragmentDirections
//                    .actionNavServicioToUpdateServicioFragment(servicio)
//                itemView.findNavController().navigate(accion)
//            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioViewHolder {
        //se crea elemento en memoria de una cajita vista_fila
        val itemBinding = ServicioFilaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServicioViewHolder(itemBinding) //lo que devuelve es vista de una cajita
    }

    override fun onBindViewHolder(holder: ServicioViewHolder, position: Int) {
        //obtiene el objeto que "dibujará" en la fila del recyclerView que "voy"
        val servicioActual = listaServicios[position]
        holder.bind(servicioActual) //llama a la funcion que efectivamente "pinta" la info
    }

    override fun getItemCount(): Int {
        return listaServicios.size
    }

    fun setData(servicios: List<Servicio>){
        this.listaServicios=servicios
        notifyDataSetChanged()
    }
}
