package com.example.introductorylab.domain

interface Repository<T: Entity> {
    suspend fun findById(id: String): T?
    suspend fun findByOrder(order: Int): T?
    suspend fun findAll(): List<T>
    suspend fun countAll(): Int
    suspend fun save(obj: T): T
    suspend fun deleteById(id: String)
}