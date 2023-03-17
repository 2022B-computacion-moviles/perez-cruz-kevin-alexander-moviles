package com.example.examen2b

import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2b.entidades.Paciente

class PacienteAdapter(
    private val mContext: CRUDPaciente,
    private val listaPacientes: ArrayList<Paciente>,
) : RecyclerView.Adapter<PacienteAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener{

        val idTextView: TextView

        val nombreTextView: TextView
        val pesoTextView: TextView
        val tieneSeguroTextView: TextView
        val diasDietaTextView: TextView
        val fechaNacimientoTextView: TextView

        init {
            idTextView = view.findViewById(R.id.tv_idPaciente)

            nombreTextView = view.findViewById(R.id.tv_nombrePaciente)
            pesoTextView = view.findViewById(R.id.tv_pesoPaciente)
            tieneSeguroTextView = view.findViewById(R.id.tv_pacienteSeguro)
            diasDietaTextView = view.findViewById(R.id.tv_diasDieta)
            fechaNacimientoTextView = view.findViewById(R.id.tv_fechaNacimiento)

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
                inflater.inflate(R.menu.menupaciente, menu)

                mContext.setSelectedComponentCode(listaPacientes[adapterPosition].id)
            }
        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PacienteAdapter.MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_paciente,
                parent,
                false
            )

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PacienteAdapter.MyViewHolder, position: Int) {
        val paciente = this.listaPacientes[position]

        holder.idTextView.text = paciente.id.toString()

        holder.nombreTextView.text = paciente.nombre
        holder.pesoTextView.text = "${paciente.peso.toString()} kg"
        holder.tieneSeguroTextView.text = paciente.tieneSeguro.toString()
        holder.diasDietaTextView.text = "${paciente.diasDieta.toString()} días"
        holder.fechaNacimientoTextView.text = paciente.fechaNacimiento.toString()
    }

    override fun getItemCount(): Int {
        return this.listaPacientes.size
    }
}