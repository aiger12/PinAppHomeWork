package com.example.pinapphomework.Convertor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pinapphomework.R

class ConvertorActivity:AppCompatActivity (){

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Currency>
    lateinit var flagImage : Array<Int>
    lateinit var amount : Array<String>
    lateinit var currentName : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        flagImage = arrayOf(
            R.drawable.kz,
            R.drawable.eu,
            R.drawable.tk,
            R.drawable.kz,
            R.drawable.usa,
            R.drawable.usa,
            )

        amount = arrayOf(
            "1 500 000", "2 500", "3 500", "2 500 000", "6 000","6 000",
        )

        currentName = arrayOf(
            "Теңге, Казахстан","Евро, ЕС","Лира, Турция", "Теңге, Казахстан", "Доллары, США","Доллары, США",
        )

        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Currency>()
        getCurrencyData()
    }

    private fun getCurrencyData() {
        for(i in flagImage.indices){
            val currency = Currency(flagImage[i],amount[i], currentName[i])
            newArrayList.add(currency)
        }

        newRecyclerView.adapter = CurrencyAdapter(newArrayList)
    }
}