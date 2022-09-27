package com.stamford.pos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_show_product.*
import kotlinx.android.synthetic.main.layout.*
import android.content.SharedPreferences
import kotlinx.android.synthetic.main.contacts_list_item.view.*
import kotlinx.android.synthetic.main.contacts_list_view.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ShowProductFragment(): Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_show_product, container, false)

        arguments?.getInt("macaron_id_int")?. let {
            macaron_id = it

        }


        val macarons = Macaron.createMacaronsList()
        val macaron_tv = rootView.findViewById<TextView>(R.id.textView_productName)
        macaron_tv.text = macarons[macaron_id - 1000].name

//        val cart_lsreal = rootView.findViewById<ListView>(R.id.cartlist)
//        var List = ArrayList<String>()
//        var price= macarons[macaron_id - 1000].price.toString()
//        List.add(price)
//        cart_lsreal.setAdapter(MacaronAdapter)


        //val cart_ls = rootView.findViewById<TextView>(R.id.textView_example)
        //cart_ls.text = macarons[macaron_id - 1000].price.toString()

        ///
        val macaron_img = rootView.findViewById<ImageView>(R.id.imageView_product)

        when (macaron_id) {
            1000 -> macaron_img.setImageResource(R.drawable.macaron_black)
            1001 -> macaron_img.setImageResource(R.drawable.macaron_blue)
            1002 -> macaron_img.setImageResource(R.drawable.macaron_green)
            1003 -> macaron_img.setImageResource(R.drawable.macaron_navy)
            1004 -> macaron_img.setImageResource(R.drawable.macaron_pink)
            1005 -> macaron_img.setImageResource(R.drawable.macaron_red)
            1006 -> macaron_img.setImageResource(R.drawable.macaron_yellow)
            else -> {
                macaron_img.setImageResource(R.drawable.macaron_black)
            }
        }
        return rootView
    }








    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


