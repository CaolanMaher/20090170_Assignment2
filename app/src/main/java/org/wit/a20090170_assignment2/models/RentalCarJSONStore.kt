package org.wit.a20090170_assignment2.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.a20090170_assignment2.helpers.exists
import org.wit.a20090170_assignment2.helpers.read
import org.wit.a20090170_assignment2.helpers.write
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

const val JSON_FILE = "rentalCars.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting().registerTypeAdapter(Uri::class.java, UriParser()).create()
val listType: Type = object : TypeToken<ArrayList<RentalCarModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class RentalCarJSONStore(private val context: Context) : RentalCarStore {

    var rentalCars = mutableListOf<RentalCarModel>()

    init {
        if(exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<RentalCarModel> {
        logAll()
        return rentalCars
    }

    override fun create(rentalCar: RentalCarModel) {
        rentalCar.id = generateRandomId()
        rentalCars.add(rentalCar)
        serialize()
    }

    override fun update(rentalCar: RentalCarModel) {
        var foundRentalCar: RentalCarModel? = rentalCars.find{ r -> r.id == rentalCar.id }
        if(foundRentalCar != null) {
            foundRentalCar.brand = rentalCar.brand
            foundRentalCar.year = rentalCar.year
            foundRentalCar.image = rentalCar.image
            foundRentalCar.lat = rentalCar.lat
            foundRentalCar.lng = rentalCar.lng
            foundRentalCar.zoom = rentalCar.zoom
        }
        serialize()
    }

    override fun delete(rentalCar: RentalCarModel) {
        var foundRentalCar: RentalCarModel? = rentalCars.find{ r -> r.id == rentalCar.id }
        if(foundRentalCar != null) {
            rentalCars.remove(foundRentalCar)
            serialize()
        }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(rentalCars, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        rentalCars = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        rentalCars.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>, JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}