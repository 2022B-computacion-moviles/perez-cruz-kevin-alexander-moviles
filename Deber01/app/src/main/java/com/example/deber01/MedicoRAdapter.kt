package com.example.deber01

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicoRAdapter(
    private val listaMedicos: List<Medico>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<MedicoRAdapter.MyViewHolder>(){

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nombreTextView: TextView
        val salarioTextView: TextView
        val esEspecialistaTextView: TextView
        val idTextView: TextView
        val fotoImgView: ImageView
        init{
            nombreTextView = view.findViewById(R.id.tv_nombreMedico)
            salarioTextView = view.findViewById(R.id.tv_salarioMedico)
            esEspecialistaTextView = view.findViewById(R.id.tv_medicoEsEspecialista)
            idTextView = view.findViewById(R.id.tv_idMedico)
            fotoImgView = view.findViewById(R.id.iv_fotoMedico)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicoRAdapter.MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_medico,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MedicoRAdapter.MyViewHolder, position: Int) {
        val medicoActual = this.listaMedicos[position]
        holder.nombreTextView.text = medicoActual.nombre
        holder.salarioTextView.text = "$${medicoActual.salario}"
        holder.idTextView.text = "${medicoActual.id}"
        holder.esEspecialistaTextView.text = "${medicoActual.esEspecialista}"
        holder.fotoImgView.setImageResource(medicoActual.image)
    }

    override fun getItemCount(): Int {
        return this.listaMedicos.size
    }



}