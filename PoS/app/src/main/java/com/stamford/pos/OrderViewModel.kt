package com.stamford.pos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {

    val totalAmount:MutableLiveData<Int> by lazy{
         MutableLiveData<Int>(0)
}
fun getTotalAmount(): LiveData<Int> {
    return totalAmount
}
}