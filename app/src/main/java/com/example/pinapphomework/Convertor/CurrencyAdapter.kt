package com.example.pinapphomework.Convertor

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pinapphomework.R
import com.google.android.material.textfield.TextInputEditText

class CurrencyAdapter(private val currencyList: ArrayList<Currency>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CURRENCY = 1
        private const val VIEW_TYPE_ADD_BUTTON = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CURRENCY -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_currency, parent, false)
                ViewHolderCurrency(itemView)
            }
            VIEW_TYPE_ADD_BUTTON -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.button_add_currency, parent, false)
                ViewHolderAddButton(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderCurrency -> {
                val currentItem = currencyList[position]
                val amountText = currentItem.amount

                holder.flagImage.setImageResource(currentItem.flagImage)
                holder.amount.text = Editable.Factory.getInstance().newEditable(amountText)
                holder.currencyName.text = currentItem.currencyName
            }
            is ViewHolderAddButton -> {
                holder.btnAdd.setOnClickListener {
                    //clicked, what else?
                }
            }
        }
    }

    override fun getItemCount(): Int {
        // Return the total number of items in the list, including the "Add" button
        return currencyList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < currencyList.size) {
            VIEW_TYPE_CURRENCY
        } else {
            VIEW_TYPE_ADD_BUTTON
        }
    }

    class ViewHolderCurrency(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flagImage: ImageView = itemView.findViewById(R.id.fl_kz)
        val amount: TextInputEditText = itemView.findViewById(R.id.amount)
        val currencyName: TextView = itemView.findViewById(R.id.currencyName)
    }

    class ViewHolderAddButton(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnAdd: Button = itemView.findViewById(R.id.btn_add_currency)
    }
}
