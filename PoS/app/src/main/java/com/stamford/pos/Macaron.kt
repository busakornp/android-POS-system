package com.stamford.pos

class Macaron (Name: String, Price: Int, ID: Long, Color: String) :Product (){
        override val name : String = Name
        override val price : Int = Price
        override val id : Long = ID
    val color : String = Color

    companion object{
        fun createMacaronsList(): ArrayList<Macaron>{
            val macarons = ArrayList<Macaron>()

            macarons.add(Macaron("Black",25,1000, "Black"))
            macarons.add(Macaron("Blue",32,1001, "Blue"))
            macarons.add(Macaron("Green",23,1002, "Green"))
            macarons.add(Macaron("Navy",43,1003, "Navy"))
            macarons.add(Macaron("Pink",45,1004, "Pink"))
            macarons.add(Macaron("Red",24,1005, "Red"))
            macarons.add(Macaron("Yellow",55,1006, "Yellow"))

            return macarons
        }
    }
}