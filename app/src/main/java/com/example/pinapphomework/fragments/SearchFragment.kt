package com.example.pinapphomework.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pinapphomework.Convertor.ButtonItem
import com.example.pinapphomework.Convertor.Currency
import com.example.pinapphomework.Convertor.CurrencyAdapter
import com.example.pinapphomework.Convertor.MainI
import com.example.pinapphomework.Convertor.OnButtonClickListener
import com.example.pinapphomework.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    lateinit var adapterRecyclerView: CurrencyAdapter
    lateinit var managerRecycleView: LinearLayoutManager
    lateinit var conversionRates: Map<String, Double>
    lateinit var countryMoneyList: MutableList<MainI>




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        initializeData()
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        adapterRecyclerView = CurrencyAdapter(object : OnButtonClickListener {
            override fun onButtonClick(listSize: Int) {}
        })

        setupRecyclerView(view)

        return view

    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        managerRecycleView = LinearLayoutManager(context)

        adapterRecyclerView.setItems(countryMoneyList)

        recyclerView.layoutManager = managerRecycleView
        recyclerView.adapter = adapterRecyclerView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipGroup = view.findViewById<ChipGroup>(R.id.chipGroup)
        countryMoneyList.filterIsInstance<Currency>().forEach { currency ->
            (layoutInflater.inflate(R.layout.chip_choice, chipGroup, false) as? Chip)?.let { chip ->
                chip.id = View.generateViewId()
                chip.text = currency.currencyName
                chip.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        Log.d("chip click", "chip id: ${chip.id}, text: ${currency.currencyName}") //Клик на чип
                    }
                }
                chipGroup.addView(chip)
            }
        }

        // Assuming you want to check the first chip by default
        val firstChip = chipGroup.getChildAt(0) as? Chip
        firstChip?.let {
            chipGroup.check(it.id)
        }
    }
    private fun initializeData(){
        conversionRates = mapOf(
            getString(R.string.dollars) to 420.0, // 1 USD = 420 Tenge
            getString(R.string.euro) to 505.0, // 1 EUR = 505 Tenge
            getString(R.string.lyra) to 15.17, // 1 Lyra = 15.17 Tenge
            getString(R.string.tenge) to 1.0 // 1 Tenge = 1 Tenge
        )

        countryMoneyList = mutableListOf<MainI>(
            Currency(R.drawable.kz, getString(R.string.tenge), 1000, 1.0),
            Currency(R.drawable.eu, getString(R.string.euro), 500, conversionRates[getString(R.string.euro)] ?: 1.0),
            Currency(R.drawable.tk, getString(R.string.lyra), 300, conversionRates[getString(R.string.lyra)] ?: 1.0),
            ButtonItem()
        )
    }


}