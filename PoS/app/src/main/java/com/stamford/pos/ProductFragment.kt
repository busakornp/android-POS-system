package com.stamford.pos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProductFragment : Fragment() {
    private val TAG = "ProductFragment"

    private var param1:String? = null
    private var param2:String? = null

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
    ): View {
        val layout = inflater.inflate(R.layout.fragment_product, container, false)

        val rvMacarons = layout.findViewById<View>(R.id.rvProductsListInFragment) as RecyclerView

        val macarons = Macaron.createMacaronsList()

        val adapter = MacaronAdapter(macarons) { macaron ->
            Log.d(TAG, "onClick Listener: ${macaron.name}")

            val transaction = parentFragmentManager.beginTransaction()
            val showProductFrag = ShowProductFragment()

            showProductFrag.arguments = Bundle(1).apply {
                putInt("macaron_id_int", macaron.id.toInt())
            }


            val view: FragmentContainerView? =
                activity?.findViewById<FragmentContainerView>(R.id.show_product_fragmentContainerView2)
            if (view!=null){
            transaction.replace(R.id.show_product_fragmentContainerView2, showProductFrag)
        }
            else{
            transaction.replace(R.id.product_list_fragmentContainerView, showProductFrag)
        }
            transaction.addToBackStack(null)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                transaction.commit()
        }

        rvMacarons.adapter = adapter
        rvMacarons.layoutManager = LinearLayoutManager(layout.context)
        return layout
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