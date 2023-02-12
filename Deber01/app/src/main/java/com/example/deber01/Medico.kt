package com.example.deber01

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medico")
class Medico(
    var nombre: String?,
    var salario: Double,
    var esEspecialista: Boolean,
    var image: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return "{$id} - {$nombre} - {$salario} - {$esEspecialista}"
    }
    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeInt(id)
        p0.writeString(nombre)
        p0.writeDouble(salario)
        p0.writeByte(if (esEspecialista) 1 else 0)
        p0.writeInt(image)
    }

    companion object CREATOR : Parcelable.Creator<Medico> {
        override fun createFromParcel(parcel: Parcel): Medico {
            return Medico(parcel)
        }

        override fun newArray(size: Int): Array<Medico?> {
            return arrayOfNulls(size)
        }
    }

}