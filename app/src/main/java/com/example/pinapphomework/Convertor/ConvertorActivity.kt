package com.example.pinapphomework.Convertor

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.ItemTouchHelper.START
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.pinapphomework.R

class ConvertorActivity : AppCompatActivity(), OnButtonClickListener {

    lateinit var adapterRecyclerView: CurrencyAdapter
    lateinit var managerRecycleView: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)
        setSupportActionBar(findViewById(R.id.toolbar))

        val countryMoneyList = mutableListOf<MainI>(
            Currency(R.drawable.kz, getString(R.string.tenge)),
            Currency(R.drawable.eu, getString(R.string.euro)),
            Currency(R.drawable.tk, getString(R.string.lyra)),
            ButtonItem()
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        managerRecycleView = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterRecyclerView = CurrencyAdapter(this)
        adapterRecyclerView.setItems(countryMoneyList)

        recyclerView.apply {
            adapter = adapterRecyclerView
            layoutManager = managerRecycleView
        }

        val itemToucheHelper by lazy {
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(UP or DOWN, START or END) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder,
                ): Boolean {
                    val adapter = recyclerView.adapter as CurrencyAdapter
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition
                    adapter.moveItem(from, to)
                    adapter.notifyItemMoved(from, to)

                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    adapterRecyclerView.deleteItem(position)
                    adapterRecyclerView.notifyItemRemoved(position)
                }
            })
        }

        itemToucheHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val infalter: MenuInflater = menuInflater
        infalter.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sort_by_alphabet -> {
                adapterRecyclerView.sortByAlphabet()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onButtonClick(size: Int) {
        val smoothScroller = object : LinearSmoothScroller(this) {
            override fun getVerticalSnapPreference(): Int = LinearSmoothScroller.SNAP_TO_START
        }

        val position = size-1
        smoothScroller.targetPosition = position
        managerRecycleView?.startSmoothScroll(smoothScroller) // плавная прокрутка


        adapterRecyclerView.setItemToPosition(
            Currency(
                R.drawable.usa,
                getString(R.string.dollars)
            ),
            position+1
        )
    }


}

interface OnButtonClickListener {
    fun onButtonClick(listSize: Int)
}