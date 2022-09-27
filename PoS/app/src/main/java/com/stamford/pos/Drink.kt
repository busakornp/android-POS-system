package com.stamford.pos

class Drink (Name: String, Price: Int, ID: Long, Alcoholic: Boolean)
    :Product (){
    override val name : String = "Product Name"
    override val price : Int = 0
    override val id : Long = 0
    val alcoholic : Boolean = Alcoholic
}