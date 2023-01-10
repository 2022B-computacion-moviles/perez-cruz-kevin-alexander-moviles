package com.example.movcompkapc

class BEntrenador(
    var nombre:String,
    var descripcion:String
) {

    override fun toString(): String {
        return "${nombre} - ${descripcion}"
    }
}