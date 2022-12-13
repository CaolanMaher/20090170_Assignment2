package org.wit.a20090170_assignment2.models

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import org.wit.a20090170_assignment2.helpers.exists
import timber.log.Timber.i
import java.util.*

class RentalCarFireStore : RentalCarStore{

    private var auth: FirebaseAuth = Firebase.auth

    private val db = FirebaseFirestore.getInstance()

    private val rentalCar = RentalCarModel()

    var rentalCars = mutableListOf<RentalCarModel>()

    init {
        /*
        db.collection("rentalCars")
            .get()
            .addOnCompleteListener {
                //val rentalCar = RentalCarModel()
                if(it.isSuccessful) {
                    for(document in it.result!!) {
                        rentalCar.brand = document.data.getValue("brand").toString()
                        rentalCar.year = document.data.getValue("year").toString().toInt()
                        rentalCars.add(rentalCar)
                        i("CAR:" + rentalCar.brand + rentalCar.year)
                    }
                }
            }
            */

    }

    override fun findAll(): List<RentalCarModel> {
        return rentalCars
    }

    override fun create(rentalCar: RentalCarModel) {
        var user = auth.currentUser
        if (user != null) {
            rentalCar.userId = user.uid
        }
        rentalCar.id = generateRandomId()

        db.collection("rentalCars")
            .add(rentalCar)
            .addOnSuccessListener {
                i("SUCCESSFULLY ADDED CAR")
            }
            .addOnFailureListener {
                // to do
            }
        rentalCars.add(rentalCar.copy())
    }

    override fun update(rentalCar: RentalCarModel) {
        i("UPDATING")

        var foundRentalCar: RentalCarModel? = rentalCars.find{ r -> r.id == rentalCar.id }
        if(foundRentalCar != null) {

            //rentalCars.remove(rentalCar)

            foundRentalCar.brand = rentalCar.brand
            foundRentalCar.year = rentalCar.year
            foundRentalCar.registration = rentalCar.registration
            foundRentalCar.rate = rentalCar.rate
            foundRentalCar.isAvailable = rentalCar.isAvailable
            foundRentalCar.dateRented = rentalCar.dateRented
            foundRentalCar.dateReturn = rentalCar.dateReturn
            foundRentalCar.fuelSource = rentalCar.fuelSource
            foundRentalCar.image = rentalCar.image
            foundRentalCar.lat = rentalCar.lat
            foundRentalCar.lng = rentalCar.lng
            foundRentalCar.zoom = rentalCar.zoom

            db.collection("rentalCars")
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        for(document in it.result!!) {
                            i("ID: " + document.data.getValue("id") + " " + rentalCar.id)
                            if(document.data.getValue("id").toString() == rentalCar.id.toString()) {
                                // UPDATE
                                //i("REFERENCE " + document.reference)
                                document.reference.set(rentalCar.copy())
                                //document.reference.update("brand", rentalCar.brand)
                            }
                        }
                    }
                }
        }
    }

    override fun delete(rentalCar: RentalCarModel) {
        i("DELETING")

        var foundRentalCar: RentalCarModel? = rentalCars.find{ r -> r.id == rentalCar.id }
        if(foundRentalCar != null) {

            rentalCars.remove(rentalCar)

            db.collection("rentalCars")
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        for(document in it.result!!) {
                            if(document.data.getValue("id").toString() == rentalCar.id.toString()) {
                                // DELETE
                                document.reference.delete()
                            }
                        }
                    }
                }
        }
    }

    override fun getCarsForUser(userId : String): List<RentalCarModel> {
        db.collection("rentalCars")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    for(document in it.result!!) {
                        if(document.data.getValue("userId").toString() == userId) {
                            // Add to list
                            //document.reference.delete()
                            rentalCar.id = document.data.getValue("id") as Long
                            rentalCar.userId = document.data.getValue("userId").toString()
                            rentalCar.brand = document.data.getValue("brand").toString()
                            rentalCar.year = document.data.getValue("year").toString().toInt()
                            rentalCar.registration = document.data.getValue("registration").toString()
                            rentalCar.rate = document.data.getValue("rate").toString().toDouble()
                            rentalCar.isAvailable = document.data.getValue("available").toString()
                            rentalCar.dateRented = document.data.getValue("dateRented").toString()
                            rentalCar.dateReturn = document.data.getValue("dateReturn").toString()
                            rentalCar.fuelSource = document.data.getValue("fuelSource").toString()
                            rentalCar.image = Uri.parse(document.data.getValue("image").toString())
                            rentalCar.lat = document.data.getValue("lat").toString().toDouble()
                            rentalCar.lng = document.data.getValue("lng").toString().toDouble()
                            rentalCar.zoom = document.data.getValue("zoom").toString().toFloat()
                            rentalCars.add(rentalCar.copy())
                        }
                    }
                }
            }

        return rentalCars
    }

    private fun generateRandomId(): Long {
        return Random().nextLong()
    }
}