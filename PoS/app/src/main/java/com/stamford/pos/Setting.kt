package com.stamford.pos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider.getUriForFile
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import java.util.*

class Setting : AppCompatActivity(){
    private val TAG = "Setting"
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)

//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action",
//                Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        val takePic = registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { isSuccess ->
            if (isSuccess) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Alert Title")
                builder.setMessage("registerForActivityResult () successful.")

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    Toast.makeText(
                        applicationContext,
                        R.string.yes, Toast.LENGTH_SHORT
                    ).show()

                    val linearLayout = findViewById<LinearLayout>(
                        R.id.setting_left_panel_11
                    )
                    val factor: Float =
                        linearLayout.context.resources.displayMetrics.density
                    val width = (linearLayout.width * factor * 0.5)
                    val height = (linearLayout.height * factor * 0.3)

                    val imageView = ImageView(this)
                    imageView.layoutParams = LinearLayout.LayoutParams(
                        width.toInt(), height.toInt()
                    )
                    imageView.setImageURI(imageUri)
                    linearLayout?.addView(imageView)
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(
                        applicationContext,
                        R.string.no, Toast.LENGTH_SHORT
                    ).show()
                }

                builder.setNeutralButton("Maybe") { dialog, which ->
                    Toast.makeText(
                        applicationContext,
                        R.string.maybe, Toast.LENGTH_SHORT
                    ).show()
                }
                builder.show()

            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Alert Title")
                builder.setMessage("registerForActivityResult() failed.")

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    Toast.makeText(
                        applicationContext,
                        R.string.yes, Toast.LENGTH_SHORT
                    ).show()
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(
                        applicationContext,
                        R.string.no, Toast.LENGTH_SHORT
                    ).show()
                }
                builder.setNeutralButton("Maybe") { dialog, which ->
                    Toast.makeText(
                        applicationContext,
                        R.string.maybe, Toast.LENGTH_SHORT
                    ).show()
                }
                builder.show()
            }
        }

        val uploadDailyReportBtn = findViewById<Button>(R.id.button10)

        uploadDailyReportBtn.setOnClickListener {
            val imagePath: File = File(getExternalFilesDir(null), "my_images")
            imagePath.mkdirs()
            val newFile = File(imagePath, "img_" + System.currentTimeMillis() + ".jpg")
            val imgUri: Uri = getUriForFile(this@Setting, "com.stamford.pos.fileprovider", newFile)
            this.imageUri = imgUri
            takePic.launch(imgUri)
        }

        val retrieveOrdersFromRemoteServerBtn =
            findViewById<Button>(R.id.retrieve_orders_from_remote_server_button)
        retrieveOrdersFromRemoteServerBtn.setOnClickListener {
            Log.i(TAG, "Retrieve orders from remote server")

            GlobalScope.launch {
                val url = "http://10.0.2.2/pos/pos_api/public/orders"

                val jsonRequest = JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    { response ->
                        Log.i(TAG, "Response: %s".format(response.toString()))
                        for (i in 0 until response.length()) {
                            val order = response.getJSONObject(i)

                            Log.i(
                                TAG,
                                "Order $i = ${order.get("id")},branch ID is = ${order.get("branch_id")}")
                        }
                    },
                    {
                        Log.i(TAG, "That didnt work, Error is $it")
                    }
                )

                jsonRequest.retryPolicy = DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 1f
                )
                VolleySingleton.getInstance(applicationContext).addToRequestQueue(jsonRequest)
            }
        }


        val SubmitOrderToRemoteServerBtn =
            findViewById<Button>(R.id.submit_orders_to_remote_server_button)
        SubmitOrderToRemoteServerBtn.setOnClickListener {
            Log.i(TAG, "Submit orders to remote server")

            GlobalScope.launch {
                val localOrders = getOrdersFromLocalDBAsync()
                Log.i(TAG, "Orders:")
                for (order in localOrders.await()) {
                    Log.i(
                        TAG,
                        "Order ID = ${order.uid}" + "Branch ID = ${order.branchID}" + "Staff ID ${order.staffID}"
                    )
                    val url = "http://10.0.2.2/pos/pos_api/public/orders"

                    val params = JSONObject()
                    params.put("order_local_id", "${order.uid}")
                    params.put("branch_id", "${order.branchID}")
                    params.put("staff_id", "${order.staffID}")

                    val jsonRequest = JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        params,
                        { response ->
                            Log.i(TAG, "Response: $response")
                        },
                        {
                            Log.i(TAG, "That didnt work, Error is $it")
                        }
                    )

                    VolleySingleton.getInstance(applicationContext).addToRequestQueue(jsonRequest)
                }
            }
        }




        val button = findViewById<Button>(R.id.button11)

        button.setOnClickListener{
            val myIntent = Intent(this@Setting, SearchContacts::class.java)
            startActivity(myIntent)
        }


    }

    private fun getOrdersFromLocalDBAsync() = GlobalScope.async {
        val db = POSAppDatabase.getInstance(applicationContext)
        db.orderDao().getAll()
    }
}