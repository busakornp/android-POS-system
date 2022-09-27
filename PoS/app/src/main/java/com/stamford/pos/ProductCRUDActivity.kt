package com.stamford.pos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.stamford.pos.databinding.ActivityproductcrudactivitybindingBinding
import com.stamford.pos.databinding.ProductcrudactivityBinding

class ProductCRUDActivity: AppCompatActivity() {
    private val TAG = "ProductCRUDActivity"

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ProductcrudactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ProductcrudactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<ProductFragment>(R.id.product_list_fragmentContainerView)
        }
        val view: FragmentContainerView? = binding.root
            .findViewById<FragmentContainerView>(R.id.show_product_fragmentContainerView2)
        if (view != null)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ShowProductFragment>(R.id.show_product_fragmentContainerView2)
            }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_product_crudactivity)
//        return navController.navigateUp(appBarConfiguration)||
//        return super.onSupportNavigateUp()
//    }


}