package com.example.movcompkapc

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class BListView : AppCompatActivity() {
    val arreglo: ArrayList<BEntrenador> = BBaseDatosMemoria.arregloBEntrenador
    var idItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val ListView = findViewById<ListView>(R.id.lv_list_view)
        val adapatador = ArrayAdapter(
            this, // contexto
            android.R.layout.simple_list_item_1, // Como se va a ver (XML)
            arreglo
        )
        ListView.adapter = adapatador
        adapatador.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_list_view)
        botonAnadirListView
            .setOnClickListener{
                anadirEntrenador(adapatador)
            }
        registerForContextMenu(ListView)
    } // Fín de OnCreate

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                "${idItemSeleccionado}"
                return true
            }
            R.id.mi_eliminar -> {
                abrirDialogo()
                "${idItemSeleccionado}"
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(){
    val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog,
                                              which ->
                // Al Aceptar eliminar el registro
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val opciones = resources.getStringArray(
            R.array.string_array_opciones_dialogo
        )
        val seleccionPrevia = booleanArrayOf(
            true,
            false,
            false
        )
        builder.setMultiChoiceItems(
            opciones,
            seleccionPrevia,
            { dialog,
              which,
              isChecked ->
                "Dio click en el ítem ${which}"
            }
        )
        val dialogo = builder.create()
        dialogo.show()
    }

    fun anadirEntrenador(adapter: ArrayAdapter<BEntrenador>){
        arreglo.add(
            BEntrenador(
                1, "Ejemplo", "a@a.com"
            )
        )
        adapter.notifyDataSetChanged()
    }

}