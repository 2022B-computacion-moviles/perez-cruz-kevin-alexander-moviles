package com.example.examen2b

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Medico::class, Paciente::class], version = 11)
abstract class AppDatabase : RoomDatabase(){

    abstract fun medicos(): MedicosDao
    abstract fun pacientes(): PacientesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration().build()

                INSTANCE = instance

                return instance
            }
        }
    }
}