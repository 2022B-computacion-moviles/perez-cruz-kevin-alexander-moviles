package com.example.examen2b

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2b.dao.ConsultorioDao
import com.example.examen2b.entidades.Paciente

class CRUDPaciente : AppCompatActivity() {

    private var selectedPacienteId: Int? = null
    private var selectedMedicoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crudpaciente)

        selectedMedicoId = intent.getIntExtra("selectedMedicoId", 0)

        val componentRecyclerView = findViewById<RecyclerView>(R.id.rv_pacientes)
        val medicoIdTextView = findViewById<TextView>(R.id.tv_idMedicoPaciente)
        val medicoNombreTextView = findViewById<TextView>(R.id.tv_nombreMedicoPaciente)
        val btnCrearPaciente = findViewById<Button>(R.id.btn_crearPaciente)

        // Setting component device code when it is ready
        ConsultorioDao.consultorio.getMedicoDao().read(
            selectedMedicoId!!,
            onSuccess = { device ->
                medicoIdTextView.text = device.id.toString()
                medicoNombreTextView.text = device.nombre
            }
        )

        // Setting the Recycler View when the data is ready
        ConsultorioDao.consultorio.getPacienteDao().getPacientesPorMedico(
            selectedMedicoId!!,
            onSuccess = { components ->
                initializeRecyclerView(components, componentRecyclerView)
                registerForContextMenu(componentRecyclerView)
            }
        )

        btnCrearPaciente.setOnClickListener {
            val intent = Intent(this, CreatePaciente::class.java)
            intent.putExtra("selectedMedicoId", selectedMedicoId)
            startActivity(intent)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mm_editarPaciente -> {
                val intent = Intent(this, PacienteActivity::class.java)
                intent.putExtra("selectedPacienteId", selectedPacienteId)
                startActivity(intent)
                return true
            }
            R.id.mm_eliminarPaciente -> {
                openDeleteDialog()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun setSelectedComponentCode(pacienteId: Int) {
        selectedPacienteId = pacienteId
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar")
        builder.setMessage("Estas seguro de querer eliminar este registro de Paciente?")

        builder.setPositiveButton("Sí") { _, _ ->
            ConsultorioDao.consultorio.getPacienteDao().delete(
                selectedPacienteId!!,
                onSuccess = {
                    ConsultorioDao.consultorio.getPacienteDao().getPacientesPorMedico(
                        selectedMedicoId!!,
                        onSuccess = { components ->
                            initializeRecyclerView(components, findViewById(R.id.rv_pacientes))
                        }
                    )
                }
            )
            Toast.makeText(this, "Paciente Eliminado", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No") { _, _ -> }

        val dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeRecyclerView(
        list: ArrayList<Paciente>,
        recyclerView: RecyclerView
    ) {
        val adapter = PacienteAdapter(this, list)

        recyclerView.adapter = adapter
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
    }
}