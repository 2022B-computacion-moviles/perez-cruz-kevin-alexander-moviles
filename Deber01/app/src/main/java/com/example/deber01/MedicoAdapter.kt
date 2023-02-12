package com.example.deber01

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicoAdapter(
    private val mContext: Context,
    private val listaMedicos: List<Medico>,
) : ArrayAdapter<Medico>(mContext, 0, listaMedicos){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var layout = LayoutInflater.from(mContext).inflate(R.layout.item_medico, parent, false)

        val medico = listaMedicos[position]

        val idMedico = layout.findViewById<TextView>(R.id.tv_idMedico)
        idMedico.text = "${medico.id}"
        val nombreMedico = layout.findViewById<TextView>(R.id.tv_nombreMedico)
        nombreMedico.text = medico.nombre
        val salarioMedico = layout.findViewById<TextView>(R.id.tv_salarioMedico)
        salarioMedico.text = "$${medico.salario}"
        val esEspecialistaMedico = layout.findViewById<TextView>(R.id.tv_medicoEsEspecialista)
        esEspecialistaMedico.text = "${medico.esEspecialista}"

        val imageUri = ImageController.getImageUri(mContext, medico.id.toLong())

        layout.findViewById<ImageView>(R.id.iv_fotoMedico).setImageURI(imageUri)

        return layout
    }
}