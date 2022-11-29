package org.wit.a20090170_assignment2.main

import android.app.Application
import com.github.ajalt.timberkt.Timber
import org.wit.a20090170_assignment2.models.RentalCarJSONStore
import org.wit.a20090170_assignment2.models.RentalCarMemStore
import org.wit.a20090170_assignment2.models.RentalCarModel
import org.wit.a20090170_assignment2.models.RentalCarStore
import timber.log.Timber.i

class MainApp : Application() {

    //val rentalCars = ArrayList<RentalCarModel>()
    //val rentalCars = RentalCarMemStore()
    lateinit var rentalCars: RentalCarStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //rentalCars = RentalCarMemStore()
        rentalCars = RentalCarJSONStore(applicationContext)
        i("App Started")
    }
}