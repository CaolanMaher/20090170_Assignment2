package org.wit.a20090170_assignment2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.a20090170_assignment2.R
import org.wit.a20090170_assignment2.databinding.ActivityCarRentalBinding
import org.wit.a20090170_assignment2.main.MainApp
import org.wit.a20090170_assignment2.models.RentalCarModel
import timber.log.Timber
import timber.log.Timber.i

class RentalCarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarRentalBinding
    var rentalCar = RentalCarModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarRentalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        binding.btnAdd.setOnClickListener() {
            try {
                rentalCar.brand = binding.rentalCarBrand.text.toString()
                rentalCar.year = binding.rentalCarYear.text.toString().toInt()
                app.rentalCars.add(rentalCar.copy())

                for (i in 0 until app.rentalCars.size) {
                    i(app.rentalCars[i].brand + " " + app.rentalCars[i].year)
                }

                setResult(RESULT_OK)
                finish()
            }
            catch (e : Exception) {
                i("Need Number for year")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_rental_car, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}