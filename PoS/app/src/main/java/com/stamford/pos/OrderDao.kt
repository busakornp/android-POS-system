package com.stamford.pos
import androidx.room.*

@Dao //mark an interface as a Data access object
interface OrderDao {
    @Query("SELECT * FROM orderTbl")
    fun getAll(): List<Order>

    @Query("SELECT * FROM orderTbl WHERE uid LIKE :id LIMIT 1")
    fun findByID(id:Long):Order
    @Insert
    fun insert(order: Order): Long
    @Delete
    fun delete(order: Order)
}