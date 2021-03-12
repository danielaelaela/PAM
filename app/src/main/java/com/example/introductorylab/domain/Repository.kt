package com.example.introductorylab.domain

interface Repository<T: Entity> {
    fun findById(id: String): T?
    fun findByOrder(order: Int): T?
    fun findAll(): List<T>
    fun countAll(): Int
    fun save(obj: T): T
    fun deleteById(id: String)
}