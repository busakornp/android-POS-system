package com.stamford.pos

class Cake (Name: String, Price: Int, ID: Long, Favour: String)
    :Product (){
    override val name : String = "Product Name"
    override val price : Int = 0
    override val id : Long = 0
    val favour : String = Favour
}