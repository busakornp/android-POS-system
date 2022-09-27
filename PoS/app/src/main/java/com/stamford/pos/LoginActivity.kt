package com.stamford.pos

import android.content.ContentValues.TAG
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.content.Intent
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log

import android.view.View
import android.widget.TextView
import com.stamford.pos.databinding.LoginBinding
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.view.*
import java.nio.charset.Charset
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec



class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    var isRemembered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)

        val username = sharedPreferences.getString("USERNAME","")
        usernameEditTextTextPersonName2.setText(username)
        val password = sharedPreferences.getString("PASSWORD","")
        passwordEditTextTextPassword.setText(password)

        if (isRemembered) {

//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
            }

        button.setOnClickListener {

            val username: String = usernameEditTextTextPersonName2.text.toString()
            val password: String = passwordEditTextTextPassword.text.toString()
            val checked: Boolean = checkBox.isChecked
//new value is stored in the default preference file.
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("USERNAME", username)
            editor.putString("PASSWORD", password)
            editor.putBoolean("CHECKBOX",checked)
            editor.apply()

            //Toast.makeText(this, "Information NOT Saved!", Toast.LENGTH_LONG).show()

            val intent = Intent(this, CommandCenter::class.java)
            startActivity(intent)
        }

//                sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//                isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)

//                button.setOnClickListener {
//                    Toast.makeText(this, "Information not saved", Toast.LENGTH_SHORT).show()
//                    val myIntent = Intent(this@LoginActivity, CommandCenter::class.java)
//                    startActivity(myIntent)
//                }
//            }
//        }


        //Log.i(TAG, "Remember me is checked, username is = $username, password = $password")
//        val button = findViewById<Button>(R.id.button)
//
//        button.setOnClickListener{
//            Toast.makeText(this, "Busakorn has clicked the login button", Toast.LENGTH_SHORT).show()
//            val myIntent = Intent(this@LoginActivity, CommandCenter::class.java)
//            startActivity(myIntent)
//        }

        val txt = findViewById<View>(R.id.textView4)
        txt.setOnLongClickListener { // TODO Auto-generated method stub
            val intent = Intent(this@LoginActivity, Setting::class.java)
            Log.d(TAG, "Launching Setting Activity")
            startActivity(intent)
            return@setOnLongClickListener true
        }

    }
}




        //Log.d(TAG, "Login process successful, going to start the AddProductActivity")





