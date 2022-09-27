package com.stamford.pos

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class OrderActivity : AppCompatActivity() {

    private val TAG = "OrderActivity"
    private var totalAmount : Int = 0

    private val model : OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orderactivity)
        val total_price_tv = findViewById<TextView>(R.id.total_price_textView)
        total_price_tv.text = getString(R.string.total_amount_str,model.totalAmount.value)

        val amountObserver = Observer<Int> { newAmount ->
            total_price_tv.text = getString(R.string.total_amount_str, newAmount)
        }
//////////////////////LIVEDATA//////////////////////////////
        model.getTotalAmount().observe(this,amountObserver)
        //total_price_tv.text = getString(R.string.total_amount_str,model.totalAmount)
        val intent = intent
        val catID = intent.getIntExtra("CatID",totalAmount)

        when (catID){
            MACARON_CAT_ID ->{
            val rvMacarons = findViewById<View>(R.id.rvProductsList) as RecyclerView

                val macarons = Macaron.createMacaronsList()

                val adapter = MacaronAdapter (macarons) { macaron ->
                    Log.d(TAG, "A row is clicked, macaron price is ${macaron.price}")

                    totalAmount += macaron.price
                    //model.totalAmount = totalAmount

                    model.totalAmount.value = model.totalAmount.value?.plus(macaron.price)
                    total_price_tv.text = getString(R.string.total_amount_str, model.totalAmount.value)
                }
//////////////////////////////////////////////////////
            rvMacarons.adapter = adapter
            rvMacarons.layoutManager = LinearLayoutManager(this)

                Toast.makeText(this,"Macaron Category!", Toast.LENGTH_SHORT).show()
            }
            DRINK_CAT_ID -> {
                Toast.makeText(this,"Drink Category!", Toast.LENGTH_SHORT).show()
            }
            else ->{
                Toast.makeText(this,"Unknown Category!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun onclick_submit_order_btn (view: View){
        Log.i(TAG, "Submit order button clicked. Order is going to be stored.")

        GlobalScope.launch{
            val order1 = Order(null, 5001, 2001)

            val db = POSAppDatabase.getInstance(applicationContext)
            val orderID : Long = db.orderDao().insert(order1)

            val orderLine1 = OrderLine(null, orderID, 1001, 50, 3)
            val orderLine2 = OrderLine(null, orderID, 1005, 70, 2)
            db.orderLineDao().insertAll(orderLine1, orderLine2)
        }
    }

    fun onclick_retrieve_orders_btn (view: View){
        Log.i(TAG,"Retrieve Orders button clicked.")

        GlobalScope.launch{
            val db = POSAppDatabase.getInstance(applicationContext)
            val orders = db.orderDao().getAll()

            Log.i(TAG, "Orders")
            for (order in orders){
                Log.i(TAG, "Order ID = $(order.uid), " + "Branch ID =  $(order.branchID)" + "Staff ID = $(order.staffID)")}
                Log.i(TAG, "orderLines:")
                val orderLines = db.orderLineDao().getAll()
                for (orderLine in orderLines){
                    Log.i(TAG, "orderLine ID = $(orderLine.uid), " + "Order ID =  $(orderLine.orderID)" + "Product ID = $(orderLine.productID)")
            }
        }
    }



}