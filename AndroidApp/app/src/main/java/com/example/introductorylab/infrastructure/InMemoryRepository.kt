package com.example.introductorylab.infrastructure

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.introductorylab.domain.Entity
import com.example.introductorylab.domain.Repository

abstract class InMemoryRepository<T: Entity> : Repository<T> {

    private val objects: MutableList<T> = ArrayList()

    override suspend fun findById(id: String): T? {
        return objects.find { obj -> obj.id == id }
    }

    override suspend fun findByOrder(order: Int): T? =
        if (order >= 0 && order < objects.size) objects[order] else null

    override suspend fun findAll(): List<T> {
        return objects
    }

    override suspend fun countAll(): Int = objects.size

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun save(obj: T): T {
        val existingObject = findById(obj.id)
        if (existingObject == null) {
            if (obj.id.isEmpty()) {
                obj.id = nextId()
            }
            objects.add(obj)
        } else {
            objects.replaceAll { o -> if (o == obj) obj else o }
        }
        return obj
    }

    // Find next unused Id
    private fun nextId(): String =
        ((objects.map { obj -> Integer.parseInt(obj.id) }.max() ?: 0) + 1).toString()

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun deleteById(id: String) {
        objects.removeIf { obj -> obj.id == id }
    }
}