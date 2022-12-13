package org.wit.a20090170_assignment2.main

import android.app.Application
import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.wit.a20090170_assignment2.models.*
import timber.log.Timber.i

class MainApp : Application() {

    //val rentalCars = ArrayList<RentalCarModel>()
    //val rentalCars = RentalCarMemStore()

    // Firestore
    //val db = Firebase.firestore
    //val db = FirebaseFirestore.getInstance()

    lateinit var rentalCars: RentalCarStore
    //val rentalCarFireStore: RentalCarFireStore
    //val rentalCars = ArrayList<RentalCarModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //rentalCars = RentalCarMemStore()
        //rentalCars = RentalCarJSONStore(applicationContext)
        rentalCars = RentalCarFireStore()


        i("App Started")

        //rentalCars.findAll().forEach {
        //    i(it.brand + it.year)
        //}

        // TESTING
        /*
        val user: MutableMap<String, Any> = HashMap()
        user["email"] = "jm@gmail.com"

        db.collection("users")
            .add(user)
            .addOnSuccessListener { print("SUCCESS") }
            .addOnFailureListener { print("FAILURE") }

         */
    }
}