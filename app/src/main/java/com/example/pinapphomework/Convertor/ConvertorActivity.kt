package com.example.pinapphomework.Convertor

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pinapphomework.fragments.AccountFragment
import com.example.pinapphomework.fragments.FavoriteFragment
import com.example.pinapphomework.R
import com.example.pinapphomework.fragments.SearchFragment
import com.example.pinapphomework.fragments.TranslateFragment
import com.example.pinapphomework.fragments.ConverterFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ConvertorActivity : AppCompatActivity(), OnButtonClickListener {

    lateinit var adapterRecyclerView: CurrencyAdapter
    lateinit var bottomNav:BottomNavigationView
//    lateinit var managerRecycleView: LinearLayoutManager
//    private var isDeleteMode = false
//    lateinit var currencyFlags: Map<String, Int>
    lateinit var conversionRates: Map<String, Double>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter_page)
        setSupportActionBar(findViewById(R.id.toolbar))

        bottomNav = findViewById(R.id.bottomBar)
        bottomNav.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.first_fg ->{
                    replaceFragment(TranslateFragment())
                    true
                }
                R.id.second_fg ->{
                    replaceFragment(FavoriteFragment())
                    true
                }
                R.id.third_fg ->{
                    replaceFragment(ConverterFragment())
                    true
                }
                R.id.fourth_fg ->{
                    replaceFragment(SearchFragment())
                    true
                }R.id.fifth_fg ->{
                replaceFragment(AccountFragment())
                true
                }
                else -> false
            }
        }
//        currencyFlags = mapOf(
//            getString(R.string.dollars) to R.drawable.usa,
//            getString(R.string.euro) to R.drawable.eu,
//            getString(R.string.lyra) to R.drawable.tk,
//            getString(R.string.tenge) to R.drawable.kz
//        )
         conversionRates = mapOf(
             getString(R.string.dollars) to 420.0, // 1 USD = 420 Tenge
             getString(R.string.euro) to 505.0, // 1 EUR = 505 Tenge)
             getString(R.string.lyra) to 15.17,
             getString(R.string.tenge) to 1.0
         )
//
        val countryMoneyList = mutableListOf<MainI>(
            Currency(R.drawable.kz, getString(R.string.tenge), 1, 1.0),
            Currency(R.drawable.eu, getString(R.string.euro), 1, conversionRates[getString(R.string.euro)] ?: 1.0),
            Currency(R.drawable.tk, getString(R.string.lyra),1, conversionRates[getString(R.string.lyra)] ?: 1.0),
            ButtonItem()
        )
//        val sheet = findViewById<FrameLayout>(R.id.sheet)
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        managerRecycleView = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterRecyclerView = CurrencyAdapter(this)
        adapterRecyclerView.setItems(countryMoneyList)
//
//        recyclerView.apply {
//            adapter = adapterRecyclerView
//            layoutManager = managerRecycleView
//        }
//
//        BottomSheetBehavior.from(sheet).apply {
//            peekHeight=100
//            this.state=BottomSheetBehavior.STATE_COLLAPSED
//        }
//        val itemToucheHelper by lazy {
//            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(UP or DOWN, START or END) {
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder,
//                ): Boolean {
//                    val adapter = recyclerView.adapter as CurrencyAdapter
//                    val from = viewHolder.adapterPosition
//                    val to = target.adapterPosition
//                    adapter.moveItem(from, to)
//                    adapter.notifyItemMoved(from, to)
//
//                    return true
//                }
//
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    val position = viewHolder.adapterPosition
//                    adapterRecyclerView.deleteItem(position)
//                    adapterRecyclerView.notifyItemRemoved(position)
//                }
//
//                override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
//                    super.onSelectedChanged(viewHolder, actionState)
//                    val position = viewHolder?.adapterPosition
//                    if (position != null) {
//                        showDeleteConfirmationDialog(position)
//                    }
//                }
//
//                override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
//                    super.clearView(recyclerView, viewHolder)
//                    if (isDeleteMode) {
//                        resetToolBar()
//                        isDeleteMode = false
//                    }
//                }
//
//
//            })
//        }
//
//        itemToucheHelper.attachToRecyclerView(recyclerView)
//        setupAddCurrencyButton()
//
//    }
//    private fun showDeleteConfirmationDialog(position: Int) {
//        changeToolBarToDeleteState()
//        val deleteConfirmationDialog = DeleteConfirmationDialogFragment.newInstance(position)
//        deleteConfirmationDialog.show(supportFragmentManager, "deleteConfirmationDialog")
//    }

        fun changeToolBarToDeleteState() {
            supportActionBar?.apply {
                val spannableTitle = SpannableString("Item Selected")
                spannableTitle.setSpan(
                    ForegroundColorSpan(Color.BLACK),
                    0,
                    spannableTitle.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                title = spannableTitle
                setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        this@ConvertorActivity,
                        R.color.primary_text
                    )
                )
                setHomeAsUpIndicator(R.drawable.ic_menu_delete)
                setDisplayHomeAsUpEnabled(true)
            }
        }

        fun resetToolBar() {
            supportActionBar?.apply {
                title = "Converter"
                setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        this@ConvertorActivity,
                        R.color.primary_text
                    )
                )
                setDisplayHomeAsUpEnabled(false)
            }
        }
    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit()
    }

    override fun onButtonClick(listSize: Int) {
        TODO("Not yet implemented")
    }
}


//    private fun setupAddCurrencyButton() {
//        val addCurrencyButton = findViewById<Button>(R.id.choose_add_bottom_btn)
//        addCurrencyButton.setOnClickListener {
//            val currencyName = findViewById<TextInputEditText>(R.id.name_new_currency).text.toString()
//            val flagIcon = determineFlagIcon(currencyName)
//            val amount = findViewById<TextInputEditText>(R.id.name_new_intg).text.toString().toIntOrNull()
//
//            if (currencyName.isNotBlank() && amount != null) {
//                val flagIcon = determineFlagIcon(currencyName)
//                val conversionRate = conversionRates[currencyName] ?: 1.0
//                val newCurrency = Currency(flagIcon, currencyName, amount, conversionRate)
//                adapterRecyclerView.setItem(newCurrency)
//            }  else {
//
//            }
//        }
//    }
//    private fun determineFlagIcon(currencyName: String): Int {
//        return currencyFlags[currencyName] ?: R.drawable.kz
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val infalter: MenuInflater = menuInflater
//        infalter.inflate(R.menu.main_menu, menu)
//
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.sort_by_alphabet -> {
//                adapterRecyclerView.sortByAlphabet()
//                return true
//            }
//            android.R.id.home -> {
//                if (isDeleteMode) {
//                    // Handle back button press in delete mode
//                    resetToolBar()
//                    isDeleteMode = false
//                    return true
//                }
//            }
//            R.id.delete_currency -> {
//                // Show your delete confirmation dialog here
//                val deleteConfirmationDialog = DeleteConfirmationDialogFragment.newInstance(-1)
//                deleteConfirmationDialog.show(supportFragmentManager, "deleteConfirmationDialog")
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun onButtonClick(size: Int) {
//        val smoothScroller = object : LinearSmoothScroller(this) {
//            override fun getVerticalSnapPreference(): Int = LinearSmoothScroller.SNAP_TO_START
//        }
//
//        val position = size-1
//        smoothScroller.targetPosition = position
//        managerRecycleView?.startSmoothScroll(smoothScroller) // плавная прокрутка
//
//
//        adapterRecyclerView.setItemToPosition(
//            Currency(
//                R.drawable.usa,
//                getString(R.string.dollars),
//                500, 1.0
//            ),
//            position+1
//        )
//    }
//
//
//}
//
interface OnButtonClickListener {
    fun onButtonClick(listSize: Int)
}