package com.example.autos.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.autos.databinding.AutoFilaBinding
import com.example.autos.model.Auto
import com.example.autos.ui.auto.AutoFragmentDirections

class AutoAdapter : RecyclerView.Adapter<AutoAdapter.AutoViewHolder>(){

    // lista para presentar la informacion de los Autos
    private var listaAutos = emptyList<Auto>()

    inner class AutoViewHolder(private val itemBinding: AutoFilaBinding):
        RecyclerView.ViewHolder(itemBinding.root){
        fun bind(auto: Auto){
            itemBinding.tvDescripcion.text = auto.nombre
            //itemBinding.tvFecha.text = auto.marca


//            itemBinding.vistaFila.setOnClickListener{
//                val accion = AutoFragmentDirections
//                    .actionNavAutoToUpdateAutoFragment(auto)
//                itemView.findNavController().navigate(accion)
//            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoViewHolder {
        //se crea elemento en memoria de una cajita vista_fila
        val itemBinding = AutoFilaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AutoViewHolder(itemBinding) //lo que devuelve es vista de una cajita
    }

    override fun onBindViewHolder(holder: AutoViewHolder, position: Int) {
        //obtiene el objeto que "dibujará" en la fila del recyclerView que "voy"
        val autoActual = listaAutos[position]
        holder.bind(autoActual) //llama a la funcion que efectivamente "pinta" la info
    }

    override fun getItemCount(): Int {
        return listaAutos.size
    }

    fun setData(autos: List<Auto>){
        this.listaAutos=autos
        notifyDataSetChanged()
    }
}
