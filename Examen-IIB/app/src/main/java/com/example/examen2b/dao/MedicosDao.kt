package com.example.examen2b

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MedicosDao: ViewModel() {
    private var db = Firebase.firestore
    private val medicos = "medico"

    val createLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val updateLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val getListLiveData: MutableLiveData<List<Medico>> by lazy {
        MutableLiveData<List<Medico>>()
    }

    val deleteLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun create(medico: Medico) {
        val docRef = db.collection(medicos)
        docRef.add(medico.toMap()).addOnSuccessListener {
            createLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("create", it.localizedMessage!!)
            createLiveData.postValue(false)
        }
    }

    fun update(medico: Medico) {
        val docRef = db.collection(medicos)
        docRef.document(medico.id!!).update(medico.toMap()).addOnSuccessListener {
            updateLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("update", it.localizedMessage!!)
            updateLiveData.postValue(false)
        }
    }

    fun delete(id: String) {
        val docRef = db.collection(medicos)
        docRef.document(id).delete().addOnSuccessListener {
            deleteLiveData.postValue(true)
        }.addOnFailureListener {
            Log.d("delete", it.localizedMessage!!)
            deleteLiveData.postValue(false)
        }
    }

    fun getList() {
        val docRef = db.collection(medicos)
        docRef.get().addOnSuccessListener {
            val medicos = ArrayList<Medico>()
            for (item in it.documents) {
                val medico = Medico()
                medico.id = item.id
                medico.nombre = item.data!!["nombre"] as String?
                medico.salario = item.data!!["salario"] as Double?
                medico.esEspecialista = item.data!!["esEspecialista"] as Boolean?
                medico.aniosExp = item.data!!["aniosExp"] as Int?
                medico.image = item.data!!["image"] as Int?
                medico.create_date = item.data!!["create_date"] as Timestamp?
                medico.update_date = item.data!!["update_date"] as Timestamp?
                medicos.add(medico)
            }

            getListLiveData.postValue(medicos)
        }.addOnFailureListener {
            Log.d("get", it.localizedMessage!!)
            getListLiveData.postValue(null)
        }
    }
    
}