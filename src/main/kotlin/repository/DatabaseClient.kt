package repository

import DBConnect
import Sighting
import Station
import Train
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.cloud.firestore.QueryDocumentSnapshot
import java.rmi.NoSuchObjectException
import java.text.SimpleDateFormat
import java.time.LocalDateTime


class DatabaseClient : TrainRepo {

    private val db = DBConnect.getConnection()

    private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private val mapper = jacksonObjectMapper().registerModule(JavaTimeModule()).apply {
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        dateFormat = (DATE_FORMAT)
    }

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

    private fun getAllStations(): List<Station> {
        val stationDocs = queryCollections("stations-kotlin")
        val allStations = mutableListOf<Station>()
        for (station in stationDocs) {
            allStations.add(station.toObject(Station::class.java))
        }
        return allStations
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
        val sightingsCount = queryCollections("sightings-kotlin").size
        val sightingsRef = db.collection("sightings-kotlin").document("$sightingsCount")
        val stations = getAllStations()
        val trains = getAllTrains()
        val stationNames = stations.map { it.name }
        val trainNumbers = trains.map { it.trainNumber }

        if (sighting.station.name in stationNames) {
            sighting.station.id = stationNames.indexOf(sighting.station.name)
        } else {
            sighting.station.id = stations.size
            val stationsRef = db.collection("stations-kotlin").document("${stations.size}")
            val station = mapper.convertValue(sighting.station, Map::class.java)
            stationsRef.set(station)
        }

        if (sighting.train.trainNumber in trainNumbers) {
            sighting.train.id = trainNumbers.indexOf(sighting.train.trainNumber)
        } else {
            sighting.train.id = trains.size
            val trainsRef = db.collection("trains-kotlin").document("${trains.size}")
            val train = mapper.convertValue(sighting.train, Map::class.java)
            trainsRef.set(train)
        }

        val sightingData = mapper.convertValue(sighting, Map::class.java)
        println(sightingData)

        sightingsRef.set(sightingData)
    }

    override fun getSightingFromJson(json: String): Sighting {
        val sightingDocs = queryCollections("sightings-kotlin")
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
                dataSightingSnapshot.getLong("station.id")!!.toInt(),
                dataSightingSnapshot.getString("station.name")!!
            ),
            train = Train(
                dataSightingSnapshot.getLong("train.id")!!.toInt(),
                dataSightingSnapshot.getString("train.name")!!,
                dataSightingSnapshot.getString("train.colour")!!,
                dataSightingSnapshot.getString("train.trainNumber")!!
            ),
            timestamp = LocalDateTime.parse(dataSightingSnapshot.getString("timestamp")!!)
        )
    }
}
