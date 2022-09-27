package com.stamford.pos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondactivity)
    }
    fun onclickMacaronsBtn (view: View){
        val intent = Intent(this, ProductCRUDActivity::class.java)
        intent.putExtra("catID",MACARON_CAT_ID)
        startActivity(intent)
    }
    fun onclickDrinksBtn(view: View){
        val intent = Intent(this, ProductCRUDActivity::class.java)
        intent.putExtra("catID",DRINK_CAT_ID)
        startActivity(intent)
    }
}