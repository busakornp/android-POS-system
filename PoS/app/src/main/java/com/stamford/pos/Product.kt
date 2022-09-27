package com.stamford.pos
import androidx.room.*
abstract class Product {
    open val name : String = "No Name"
    open val price : Int = 0
    open val id : Long = 0
}
@Entity (tableName = "productTbl")
data class ProductDB (
    @PrimaryKey(autoGenerate = true) var uid: Long?,
    @ColumnInfo(name = "name" ) var name: String,
    @ColumnInfo(name = "price") var price: Long,

    )