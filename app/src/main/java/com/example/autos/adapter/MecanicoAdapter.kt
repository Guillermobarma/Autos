package com.example.autos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.autos.databinding.MecanicoFilaBinding
import com.example.autos.model.Mecanico
import com.example.autos.ui.mecanico.MecanicoFragmentDirections

class MecanicoAdapter : RecyclerView.Adapter<MecanicoAdapter.MecanicoViewHolder>(){

    // lista para presentar la informacion de los Mecanicos
    private var listaMecanicos = emptyList<Mecanico>()

    inner class MecanicoViewHolder(private val itemBinding: MecanicoFilaBinding):
        RecyclerView.ViewHolder(itemBinding.root){
        fun bind(mecanico: Mecanico){
            itemBinding.tvDescripcion.text = mecanico.descripcion
            itemBinding.tvCelular.text = mecanico.celular

            itemBinding.vistaFila.setOnClickListener{
                val accion = MecanicoFragmentDirections
                    .actionNavMecanicoToUpdateMecanicoFragment(mecanico)
                itemView.findNavController().navigate(accion)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MecanicoViewHolder {
        //se crea elemento en memoria de una cajita vista_fila
        val itemBinding = MecanicoFilaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MecanicoViewHolder(itemBinding) //lo que devuelve es vista de una cajita
    }

    override fun onBindViewHolder(holder: MecanicoViewHolder, position: Int) {
        //obtiene el objeto que "dibujará" en la fila del recyclerView que "voy"
        val mecanicoActual = listaMecanicos[position]
        holder.bind(mecanicoActual) //llama a la funcion que efectivamente "pinta" la info
    }

    override fun getItemCount(): Int {
        return listaMecanicos.size
    }

    fun setData(mecanicos: List<Mecanico>){
        this.listaMecanicos=mecanicos
        notifyDataSetChanged()
    }
}
