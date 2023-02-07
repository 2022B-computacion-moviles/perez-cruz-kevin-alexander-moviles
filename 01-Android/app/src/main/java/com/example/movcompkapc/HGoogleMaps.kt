package com.example.movcompkapc

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap

class HGoogleMaps : AppCompatActivity() {

    private lateinit var mapa: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hgoogle_maps)
    }

    fun solicitarPermisos() {
        val contexto = this.applicationContext
        var permisosFineLocation = ContextCompat
        .checkSelfPermission(
            contexto,
            android.Manifest.permission.ACCESS_FINE_LOCATION // Permiso que van
        )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if (tienePermisos) {
            permisos = true
        } else {
            ActivityCompat.requestPermissions(
                this, // Contexto
                arrayOf( // Arreglo Permisos
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1 // Código de Petición de Permisos
            )
        }
    }
}