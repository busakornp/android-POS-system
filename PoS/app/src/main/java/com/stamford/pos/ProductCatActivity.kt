package com.stamford.pos

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ProductCatActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondactivity)
    }
    fun onclickMacaronsBtn (view: View){
        val intent = Intent(this, OrderActivity::class.java)
        intent.putExtra("catID",MACARON_CAT_ID)
        startActivity(intent)
    }
    fun onclickDrinksBtn(view: View){
        val intent = Intent(this, OrderActivity::class.java)
        intent.putExtra("catID",DRINK_CAT_ID)
        startActivity(intent)
    }
}