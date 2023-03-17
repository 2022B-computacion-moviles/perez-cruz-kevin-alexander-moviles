package com.example.examen2b

import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2b.entidades.Medico

class MedicoAdapter(
    private val mContext: CRUDMedico,
    private val listaMedicos: ArrayList<Medico>,
) : RecyclerView.Adapter<MedicoAdapter.MyViewHolder>(){

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

        val idTextView: TextView
        val nombreTextView: TextView
        val salarioTextView: TextView
        val esEspecialistaTextView: TextView
        val aniosExpTextView: TextView
        val fechaNacimientoTextView: TextView

        init {
            idTextView = view.findViewById(R.id.tv_idMedico)
            nombreTextView = view.findViewById(R.id.tv_nombreMedico)
            salarioTextView = view.findViewById(R.id.tv_salarioMedico)
            esEspecialistaTextView = view.findViewById(R.id.tv_medicoEsEspecialista)
            aniosExpTextView = view.findViewById(R.id.tv_aniosExp)
            fechaNacimientoTextView = view.findViewById(R.id.tv_fechaNacimientoDr)

            view.setOnCreateContextMenuListener(this)

            // Setting the view selection mode
            itemView.isClickable = true
            itemView.isLongClickable = true

            // Setting the style
            val typedValue = TypedValue()
            itemView.context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
            itemView.setBackgroundResource(typedValue.resourceId)
        }
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            if (menu != null) {
                val inflater = MenuInflater(view?.context)
                inflater.inflate(R.menu.menumedico, menu)

                mContext.setSelectedMedicoId(listaMedicos[adapterPosition].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_medico,
                parent,
                false
            )

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.listaMedicos.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val medico = this.listaMedicos[position]

        holder.idTextView.text = medico.id.toString()
        holder.nombreTextView.text = medico.nombre
        holder.salarioTextView.text = "$${ medico.salario.toString() }"
        holder.esEspecialistaTextView.text = medico.esEspecialista.toString()
        holder.aniosExpTextView.text = "${medico.aniosExp.toString()} años"
        holder.fechaNacimientoTextView.text = medico.fechaNacimiento.toString()
    }
}