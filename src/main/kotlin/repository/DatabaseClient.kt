package repository

import DBConnect
import Sighting
import Station
import Train
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.cloud.firestore.QueryDocumentSnapshot
import java.rmi.NoSuchObjectException
import java.time.LocalDateTime


class DatabaseClient : TrainRepo {

    private val db = DBConnect.getConnection()
    private fun queryCollections(collectionName: String): MutableList<QueryDocumentSnapshot> {
        val query = db.collection(collectionName).get()
        val querySnapshot = query.get()
        return querySnapshot.documents
    }

    override fun getAllTrains(): List<Train> {
        val trainDocs = queryCollections("trains-kotlin")
        val allTrains = mutableListOf<Train>()
        for (train in trainDocs) {
            allTrains.add(train.toObject(Train::class.java))
        }
        return allTrains
    }

    override fun getTrain(id: Int): Train {
        val trainsData = getAllTrains()
        val matchingTrain = trainsData.filter { it.id == id }
        if (matchingTrain.isNotEmpty()) {
            return matchingTrain[0]
        } else {
            throw NoSuchObjectException("No matching train with id $id found")
        }
    }

    override fun postSighting(sighting: Sighting) {
        TODO("Not yet implemented")
    }

    override fun getSightingFromJson(json: String): Sighting {
        val sightingDocs = queryCollections("sightings-kotlin")
        val mapper = jacksonObjectMapper()
        mapper.registerModule(JavaTimeModule())
        val sighting =  mapper.readValue<Sighting>(json)
        sighting.id = sightingDocs.size
        return sighting
    }

    override fun getSightings(id: Int): List<Sighting> {
        val sightingDocs = queryCollections("sightings-kotlin")
        val allSightings = mutableListOf<Sighting>()
        for (sighting in sightingDocs) {
            allSightings.add(convertSightingToClass(sighting))
        }
        val relevantSightings = allSightings.filter { it.train.id == id }
        if (relevantSightings.isNotEmpty()) {
            return relevantSightings
        } else {
            throw NoSuchElementException("No sightings found for train $id")
        }
}


    private fun convertSightingToClass(dataSightingSnapshot: QueryDocumentSnapshot): Sighting {

        return Sighting(
            id = dataSightingSnapshot.getLong("id")!!.toInt(),
            station = Station(
                dataSightingSnapshot.getLong("Station.id")!!.toInt(),
                dataSightingSnapshot.getString("Station.name")!!
            ),
            train = Train(
                dataSightingSnapshot.getLong("Train.id")!!.toInt(),
                dataSightingSnapshot.getString("Train.name")!!,
                dataSightingSnapshot.getString("Train.colour")!!,
                dataSightingSnapshot.getString("Train.trainNumber")!!
            ),
            timestamp = LocalDateTime.parse(dataSightingSnapshot.getString("timestamp")!!)
        )
    }
}
