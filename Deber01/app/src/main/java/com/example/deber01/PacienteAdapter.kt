package com.example.deber01

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PacienteAdapter(
    private val mContext: Context,
    private val listaPacientes: List<Paciente>,
) : ArrayAdapter<Paciente>(mContext, 0, listaPacientes){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layout = LayoutInflater.from(mContext).inflate(R.layout.item_paciente, parent, false)

        val paciente = listaPacientes[position]

        val idPaciente = layout.findViewById<TextView>(R.id.tv_idPaciente)
        idPaciente.text = "${paciente.idPaciente}"
        val nombrePaciente = layout.findViewById<TextView>(R.id.tv_nombrePaciente)
        nombrePaciente.text = paciente.nombre
        val pesoPaciente = layout.findViewById<TextView>(R.id.tv_pesoPaciente)
        pesoPaciente.text = "${paciente.peso} kg"
        val tieneSeguroPaciente = layout.findViewById<TextView>(R.id.tv_pacienteSeguro)
        tieneSeguroPaciente.text = "${paciente.tieneSeguro}"

        val imageUri = ImageController.getImageUri(mContext, paciente.idPaciente.toLong())

        layout.findViewById<ImageView>(R.id.iv_fotoPaciente).setImageURI(imageUri)

        return layout
    }
}