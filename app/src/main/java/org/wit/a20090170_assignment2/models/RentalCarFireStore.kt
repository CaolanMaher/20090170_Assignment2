package org.wit.a20090170_assignment2.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import org.wit.a20090170_assignment2.helpers.exists
import timber.log.Timber.i
import java.util.*

class RentalCarFireStore : RentalCarStore{

    private lateinit var auth: FirebaseAuth

    private val db = FirebaseFirestore.getInstance()

    private val rentalCar = RentalCarModel()

    var rentalCars = mutableListOf<RentalCarModel>()

    init {
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

        auth = Firebase.auth
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
        rentalCars.add(rentalCar)
    }

    override fun update(rentalCar: RentalCarModel) {
        i("UPDATING")

        var foundRentalCar: RentalCarModel? = rentalCars.find{ r -> r.id == rentalCar.id }
        if(foundRentalCar != null) {

            rentalCars.remove(rentalCar)

            db.collection("rentalCars")
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        for(document in it.result!!) {
                            if(document.data.getValue("id").toString() == rentalCar.id.toString()) {
                                // UPDATE
                                document.reference.set(rentalCar)
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

    private fun generateRandomId(): Long {
        return Random().nextLong()
    }
}