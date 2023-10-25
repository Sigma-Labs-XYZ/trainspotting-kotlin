package repository

import DBConnect
import Sighting
import Station
import Train
import com.google.cloud.firestore.QueryDocumentSnapshot
import java.rmi.NoSuchObjectException

class DatabaseRepo : TrainRepo {

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
            allTrains.add(Train(
                id = train.getLong("id")?.toInt(),
                name = train.getString("name"),
                colour = train.getString("colour"),
                trainNumber = train.getString("trainNumber")
            ))
        }
        return allTrains
    }

    override fun getTrain(id: Int): Train {
        val trainsData = getAllTrains()
        val matchingTrain = trainsData.filter{ it.id == id}
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
        TODO("Not yet implemented")
    }

    override fun getSightings(id: Int): List<Sighting> {
        val sightingDocs = queryCollections("trains-kotlin")
        val allSightings = mutableListOf<Sighting>()
        print(sightingDocs)
        /*for (sighting in sightingDocs) {
            allSightings.add(Sighting(
                id = sighting.getLong("id")?.toInt(),
                station = Station(sighting.getLong("id")?.toInt(), sighting.getString("name")),
                train = Train(sighting.getLong("id")?.toInt(), sighting.getString("name"), sighting.getString("colour"), sighting.getString("trainNumber")),
                timestamp = sighting.getTimestamp("timestamp")
            ))*/
        val relevantSightings = allSightings.filter{ it.train.id == id}
        if (relevantSightings.isNotEmpty()) {
            return relevantSightings
        } else {
            throw NoSuchElementException("No sightings found for train $id")
        }
    }
}
