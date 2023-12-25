package com.example.pinapphomework.Convertor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pinapphomework.R
import com.google.android.material.textfield.TextInputLayout

class CurrencyAdapter(
    private val onButtonClickListener: OnButtonClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CURRENCY = 1
        private const val VIEW_TYPE_ADD_BUTTON = 2
    }
    private val data = mutableListOf<MainI>()

    fun setItems(list: List<MainI>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun setItem(money:Currency){
        data.add(money)
        notifyItemChanged(data.lastIndex)
    }

    fun setItemToPosition(money:Currency, position: Int){
        data.add(position,money)
        notifyItemChanged(position)
    }

    fun sortByAlphabet() {
        val currencyList = data.filterIsInstance<Currency>()
        val buttonItem = data.firstOrNull { it is ButtonItem }

        if (currencyList.isNotEmpty()) {
            data.removeAll(currencyList)
            data.addAll(currencyList.sortedBy { it.currencyName })
        }

        if (buttonItem != null) {
            data.remove(buttonItem)
            data.add(buttonItem)
        }

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_CURRENCY -> {
                ViewHolderCurrency(inflater,parent)
            }

            VIEW_TYPE_ADD_BUTTON -> {
                ViewHolderAddButton(inflater,parent)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int = when(data[position]){
        is Currency -> VIEW_TYPE_CURRENCY
        is ButtonItem -> VIEW_TYPE_ADD_BUTTON
        else -> VIEW_TYPE_CURRENCY
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder){
            is ViewHolderCurrency ->{
                holder.bind(data[position])
            }
            is ViewHolderAddButton ->{
                holder.bind(onButtonClickListener, position)
            }
        }
    }
    fun moveItem(from: Int, to: Int) {
        val item = data.removeAt(from)
        data.add(to,item)
    }

    fun deleteItem(from: Int){
        data.removeAt(from)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolderCurrency(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_currency, parent, false)) {
        val flagImage: ImageView = itemView.findViewById(R.id.fl_kz)
        val amount = itemView.findViewById<TextInputLayout>(R.id.amount)
        val currencyName: TextView = itemView.findViewById(R.id.currencyName)

        fun bind(item: MainI ) {
            item as Currency
            currencyName.text = item.currencyName
            amount.hint=item.currencyName
            flagImage.setImageResource(item.flagImage)
        }
    }

    class ViewHolderAddButton(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.button_add_currency, parent, false)) {
        val btnAdd: Button = itemView.findViewById<Button>(R.id.btn_add_currency)
        fun bind(onButtonClickListener: OnButtonClickListener, position: Int) {
            btnAdd.setOnClickListener {
                onButtonClickListener.onButtonClick(position)
            }
        }
    }

}
