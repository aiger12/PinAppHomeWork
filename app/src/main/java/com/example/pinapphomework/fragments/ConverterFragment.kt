package com.example.pinapphomework.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.ItemTouchHelper.START
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pinapphomework.Convertor.ButtonItem
import com.example.pinapphomework.Convertor.Currency
import com.example.pinapphomework.Convertor.CurrencyAdapter
import com.example.pinapphomework.Convertor.MainI
import com.example.pinapphomework.Convertor.OnButtonClickListener
import com.example.pinapphomework.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputEditText

class ConverterFragment : Fragment(), OnButtonClickListener {

    lateinit var adapterRecyclerView: CurrencyAdapter
    lateinit var managerRecycleView: LinearLayoutManager
    private var isDeleteMode = false
    lateinit var currencyFlags: Map<String, Int>
    lateinit var conversionRates: Map<String, Double>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_converter, container, false)
        adapterRecyclerView = CurrencyAdapter(object : OnButtonClickListener {
            override fun onButtonClick(listSize: Int) {}
        })
        // Initialize your RecyclerView, BottomSheet, and other components here
        setupCurrencyData()
        setupRecyclerView(view)
        setupAddCurrencyButton(view)
        setupBottomSheet(view)


        return view
    }





    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        managerRecycleView = LinearLayoutManager(context)


        val countryMoneyList = mutableListOf<MainI>(

            Currency(R.drawable.kz, getString(R.string.tenge), 1, 1.0),
            Currency(R.drawable.eu, getString(R.string.euro), 1, conversionRates[getString(R.string.euro)] ?: 1.0),
            Currency(R.drawable.tk, getString(R.string.lyra), 1, conversionRates[getString(R.string.lyra)] ?: 1.0),
            ButtonItem()
        )

        adapterRecyclerView.setItems(countryMoneyList)

        recyclerView.layoutManager = managerRecycleView
        recyclerView.adapter = adapterRecyclerView

        // Setup ItemTouchHelper if needed
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(UP or DOWN, START or END) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                adapterRecyclerView.moveItem(from, to)
                adapterRecyclerView.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                adapterRecyclerView.deleteItem(position)
                adapterRecyclerView.notifyItemRemoved(position)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupCurrencyData() {
        // Initialize currency flags
        currencyFlags = mapOf(
            getString(R.string.dollars) to R.drawable.usa,
            getString(R.string.euro) to R.drawable.eu,
            getString(R.string.lyra) to R.drawable.tk,
            getString(R.string.tenge) to R.drawable.kz
        )

        // Initialize conversion rates
        conversionRates = mapOf(
            getString(R.string.dollars) to 420.0, // 1 USD = 420 Tenge
            getString(R.string.euro) to 505.0, // 1 EUR = 505 Tenge
            getString(R.string.lyra) to 15.17, // 1 Lyra = 15.17 Tenge
            getString(R.string.tenge) to 1.0 // 1 Tenge = 1 Tenge
        )

        // Initialize your currency list
        val countryMoneyList = mutableListOf<MainI>(
            Currency(R.drawable.kz, getString(R.string.tenge), 1000, 1.0),
            Currency(R.drawable.eu, getString(R.string.euro), 500, conversionRates[getString(R.string.euro)] ?: 1.0),
            Currency(R.drawable.tk, getString(R.string.lyra), 300, conversionRates[getString(R.string.lyra)] ?: 1.0),
            ButtonItem()
        )

        // Set the items to the RecyclerView adapter
        adapterRecyclerView.setItems(countryMoneyList)
    }
    private fun setupAddCurrencyButton(view: View) {
        val addCurrencyButton = view.findViewById<Button>(R.id.choose_add_bottom_btn)
        addCurrencyButton.setOnClickListener {
            val currencyName = view.findViewById<TextInputEditText>(R.id.name_new_currency).text.toString()
            val flagIcon = determineFlagIcon(currencyName)
            val amount = view.findViewById<TextInputEditText>(R.id.name_new_intg).text.toString().toIntOrNull()

            if (currencyName.isNotBlank() && amount != null) {
                val flagIcon = determineFlagIcon(currencyName)
                val conversionRate = conversionRates[currencyName] ?: 1.0
                val newCurrency = Currency(flagIcon, currencyName, amount, conversionRate)
                adapterRecyclerView.setItem(newCurrency)
            }  else {

            }
        }
    }

    private fun determineFlagIcon(currencyName: String): Int {
        return currencyFlags[currencyName] ?: R.drawable.kz
    }

    private fun setupBottomSheet(view: View) {
        val bottomSheet = view.findViewById<FrameLayout>(R.id.sheet)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        bottomSheetBehavior.apply {
            peekHeight = 100
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }

    override fun onButtonClick(listSize: Int) {
        TODO("Not yet implemented")
    }
}

