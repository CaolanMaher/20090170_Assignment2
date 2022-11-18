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

    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarRentalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if(intent.hasExtra("rentalCar_edit")) {
            edit = true

            rentalCar = intent.extras?.getParcelable("rentalCar_edit")!!

            // fill in text fields with data
            binding.rentalCarBrand.setText(rentalCar.brand)
            binding.rentalCarYear.setText(rentalCar.year.toString())

            // change button text
            binding.btnAdd.setText(R.string.button_saveRentalCar)
        }

        binding.btnAdd.setOnClickListener() {
            try {
                // get data from text fields
                rentalCar.brand = binding.rentalCarBrand.text.toString()
                rentalCar.year = binding.rentalCarYear.text.toString().toInt()

                if(edit) {
                    // if we are editing
                    app.rentalCars.update(rentalCar.copy())
                }
                else {
                    // if we are creating
                    app.rentalCars.create(rentalCar.copy())
                }

                setResult(RESULT_OK)
                finish()
            } catch (e: Exception) {
                Snackbar.make(it, R.string.snackbar_addRentalCar, Snackbar.LENGTH_LONG).show()
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