package com.example.examen2b.dao;

interface GeneralDao<T, PK> {

    fun create(entity: T)
    fun read(code: PK, onSuccess: (T) -> Unit)
    fun update(entity: T)
    fun delete(code: PK, onSuccess: (Unit) -> Unit)
    fun getRandomCode(onSuccess: (PK) -> Unit)
}
