package com.stamford.pos
import androidx.room.*

@Dao //mark an interface as a Data access object
interface OrderLineDao {
    @Query("SELECT * FROM orderLineTbl")
    fun getAll(): List<OrderLine>

    @Query("SELECT * FROM orderLineTbl WHERE uid LIKE :id LIMIT 1")
    fun findByID(id:Long):OrderLine
    @Insert
    fun insertAll(vararg orderLine: OrderLine)
    @Delete
    fun delete(orderLine: OrderLine)
}