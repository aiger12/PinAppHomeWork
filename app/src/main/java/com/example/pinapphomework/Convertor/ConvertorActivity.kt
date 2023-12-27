package com.example.pinapphomework.Convertor

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.ItemTouchHelper.START
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.pinapphomework.R
import com.example.pinapphomework.fragments.DeleteConfirmationDialogFragment

class ConvertorActivity : AppCompatActivity(), OnButtonClickListener {

    lateinit var adapterRecyclerView: CurrencyAdapter
    lateinit var managerRecycleView: LinearLayoutManager
    private var isDeleteMode = false

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

                override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                    super.onSelectedChanged(viewHolder, actionState)
                    val position = viewHolder?.adapterPosition
                    if (position != null) {
                        showDeleteConfirmationDialog(position)
                    }
                }

                override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                    super.clearView(recyclerView, viewHolder)
                    if (isDeleteMode) {
                        resetToolBar()
                        isDeleteMode = false
                    }
                }


            })
        }

        itemToucheHelper.attachToRecyclerView(recyclerView)
    }
    private fun showDeleteConfirmationDialog(position: Int) {
        changeToolBarToDeleteState()
        val deleteConfirmationDialog = DeleteConfirmationDialogFragment.newInstance(position)
        deleteConfirmationDialog.show(supportFragmentManager, "deleteConfirmationDialog")
    }

    private fun changeToolBarToDeleteState() {
        supportActionBar?.apply {
            val spannableTitle = SpannableString("Item Selected")
            spannableTitle.setSpan(ForegroundColorSpan(Color.BLACK), 0, spannableTitle.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            title = spannableTitle
            setBackgroundDrawable(ContextCompat.getDrawable(this@ConvertorActivity, R.color.primary_text))
            setHomeAsUpIndicator(R.drawable.ic_menu_delete)
            setDisplayHomeAsUpEnabled(true)
        }
    }
    private fun resetToolBar() {
        supportActionBar?.apply {
            title = "Converter"
            setBackgroundDrawable(ContextCompat.getDrawable(this@ConvertorActivity, R.color.primary_text))
            setDisplayHomeAsUpEnabled(false)
        }
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
            android.R.id.home -> {
                if (isDeleteMode) {
                    // Handle back button press in delete mode
                    resetToolBar()
                    isDeleteMode = false
                    return true
                }
            }
            R.id.delete_currency -> {
                // Show your delete confirmation dialog here
                val deleteConfirmationDialog = DeleteConfirmationDialogFragment.newInstance(-1)
                deleteConfirmationDialog.show(supportFragmentManager, "deleteConfirmationDialog")
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