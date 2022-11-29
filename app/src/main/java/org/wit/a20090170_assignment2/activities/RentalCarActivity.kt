package org.wit.a20090170_assignment2.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.a20090170_assignment2.R
import org.wit.a20090170_assignment2.databinding.ActivityCarRentalBinding
import org.wit.a20090170_assignment2.helpers.showImagePicker
import org.wit.a20090170_assignment2.main.MainApp
import org.wit.a20090170_assignment2.models.Location
import org.wit.a20090170_assignment2.models.RentalCarModel
import timber.log.Timber
import timber.log.Timber.i

class RentalCarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarRentalBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var rentalCar = RentalCarModel()
    lateinit var app : MainApp

    //var location = Location()

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
            Picasso.get().load(rentalCar.image).into(binding.rentalCarImage)
            if(rentalCar.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.button_changeImage)
            }

            // change add button text
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

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher, this)
        }

        registerImagePickerCallback()

        binding.rentalCarLocation.setOnClickListener {
            i("Launching Map")
            val location = Location(52.245696, -7.139102, 15f)

            // Check if the default has been changed (we have set a location)
            if(rentalCar.zoom != 0f) {
                location.lat = rentalCar.lat
                location.lng = rentalCar.lng
                location.zoom = rentalCar.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java).putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_rental_car, menu)
        if(edit) {
            // make delete button visible
            menu.getItem(0).isVisible = true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_delete -> {
                app.rentalCars.delete(rentalCar)
                setResult(99)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image, Intent.FLAG_GRANT_READ_URI_PERMISSION)

                            rentalCar.image = image
                            Picasso.get().load(rentalCar.image).into(binding.rentalCarImage)
                            binding.chooseImage.setText(R.string.button_changeImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    result -> when(result.resultCode) {
                RESULT_OK -> {
                    if(result.data != null) {
                        i("Got location ${result.data.toString()}")
                        val location = result.data!!.extras?.getParcelable<Location>("location")!!
                        i("Location == $location")
                        rentalCar.lat = location.lat
                        rentalCar.lng = location.lng
                        rentalCar.zoom = location.zoom
                    }
                }
                RESULT_CANCELED -> { i("CANCELLED") } else -> {}
            }
            }
    }
}