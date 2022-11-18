package org.wit.a20090170_assignment2.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.a20090170_assignment2.R
import org.wit.a20090170_assignment2.adapters.RentalCarAdapter
import org.wit.a20090170_assignment2.databinding.ActivityRentalCarListBinding
import org.wit.a20090170_assignment2.main.MainApp

class RentalCarListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityRentalCarListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRentalCarListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // creates action bar on top
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = RentalCarAdapter(app.rentalCars)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            // if the menu item's id is item_add id which is the plus icon
            R.id.item_add -> {
                // launch the add page
                val launcherIntent = Intent(this, RentalCarActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if(it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.rentalCars.size)
            }
        }
}