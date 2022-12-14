package org.wit.a20090170_assignment2.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.wit.a20090170_assignment2.R
import org.wit.a20090170_assignment2.adapters.RentalCarAdapter
import org.wit.a20090170_assignment2.adapters.RentalCarListener
import org.wit.a20090170_assignment2.databinding.ActivityRentalCarListBinding
import org.wit.a20090170_assignment2.main.MainApp
import org.wit.a20090170_assignment2.models.RentalCarModel
import timber.log.Timber.i

class RentalCarListActivity : AppCompatActivity(), RentalCarListener {

    private var auth: FirebaseAuth = Firebase.auth

    lateinit var app: MainApp
    private lateinit var binding: ActivityRentalCarListBinding

    private var position: Int = 0

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
        //binding.recyclerView.adapter = RentalCarAdapter(app.rentalCars)

        binding.loadingBar.visibility = View.VISIBLE

        val user = auth.currentUser
        if (user != null) {

            val carList = app.rentalCars.getCarsForUser(user.uid)

            Handler().postDelayed(Runnable {
                binding.recyclerView.adapter =
                    RentalCarAdapter(carList, this)

                //i("LIST " + carList.size)

                //carList.forEach {
                //    i("CAR ID " + it.id)
                //}

                binding.loadingBar.visibility = View.GONE
            }, 2000)

            //i("THEME: " + app.theme.toString())
        }

        binding.searchButton.setOnClickListener {

            var brandToSearch = ""
            var yearToSearch = 0

            if(binding.searchBrand.text.isNotEmpty()) {
                brandToSearch = binding.searchBrand.text.toString()
            }
            if(binding.searchYear.text.isNotEmpty()) {
                yearToSearch = binding.searchYear.text.toString().toInt()
            }

            val newList = mutableListOf<RentalCarModel>()

            app.rentalCars.findAll().forEach {
                if(it.brand == brandToSearch && it.year == yearToSearch) {
                    newList.add(it)

                    //binding.recyclerView.adapter =
                    //    RentalCarAdapter(newList, this)
                }
                else if(it.brand == brandToSearch) {
                    newList.add(it)
                }
                else if(it.year == yearToSearch) {
                    newList.add(it)
                }
                binding.recyclerView.adapter =
                    RentalCarAdapter(newList, this)
            }
        }

        binding.cancelButton.setOnClickListener {
            binding.recyclerView.adapter =
                RentalCarAdapter(app.rentalCars.findAll(), this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        //i(binding.toolbar.menu.getItem(0))
        //return true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            // if the menu item's id is item_add id which is the plus icon
            R.id.item_add -> {
                // launch the add page
                val launcherIntent = Intent(this, RentalCarActivity::class.java)
                getResult.launch(launcherIntent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, RentalCarsMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.item_signOut -> {
                // sign out and go to sign in page
                auth.signOut()
                //val launcherIntent = Intent(this, SignInActivity::class.java)
                //signInIntentLauncher.launch(launcherIntent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val mapIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.rentalCars.findAll().size)
        }
    }

    override fun onRentalCarClick(rentalCar: RentalCarModel, pos: Int) {
        val launcherIntent = Intent(this, RentalCarActivity::class.java)
        launcherIntent.putExtra("rentalCar_edit", rentalCar)
        position = pos
        //i("ID: " + rentalCar.id)
        getClickResult.launch(launcherIntent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun finish() {
        super.finish()
        auth.signOut()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private val getClickResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.rentalCars.findAll().size)
        }
        else { // deleting
            if(it.resultCode == 99) {
                (binding.recyclerView.adapter)?.notifyItemRemoved(position)
            }
        }
    }
}