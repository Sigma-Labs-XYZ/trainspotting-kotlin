package repository

import DBConnect
import Sighting
import Station
import Train
import com.google.cloud.firestore.QueryDocumentSnapshot
import java.rmi.NoSuchObjectException


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
            /*Train
                id = train.getLong("id")!!.toInt(),
                name = train.getString("name")!!,
                colour = train.getString("colour")!!,
                trainNumber = train.getString("trainNumber")!!*/
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
        TODO("Not yet implemented")
    }

    override fun getSightings(id: Int): List<Sighting> {
        println(id)
        val sightingDocs = queryCollections("sightings-kotlin")
        println(sightingDocs)
        val allSightings = mutableListOf<Sighting>()
        for (sighting in sightingDocs) {
            println(sighting.data)
            //allSightings.add(convertSightingToClass(sighting))
        }
        println(allSightings)
        return allSightings
}


    private fun convertSightingToClass(dataSightingSnapshot: QueryDocumentSnapshot): Sighting {
        return Sighting(
            id = dataSightingSnapshot.getLong("id")!!,
            station = Station(),
            train = Train(
                dataSightingSnapshot.getLong("train.id")!!.toInt(),
                dataSightingSnapshot.getString("train.name")!!,
                dataSightingSnapshot.getString("train.colour")!!,
                dataSightingSnapshot.getString("train.trainNumber")!!
            ),
            timestamp = dataSightingSnapshot.getDate("timestamp")!!
        )
    }
}

//allSightings.add(sighting.toObject(Sighting::class.java))
/*val sighting2 =  mapper.readValue<Sighting>(sighting)
println(sighting.get("Station").toString())
val station: Map<String, Any> = HashMap()
allSightings.add(sighting2)*/

/*val relevantSightings = allSightings.filter { it.train.id == id }
if (relevantSightings.isNotEmpty()) {
    return relevantSightings
} else {
    throw NoSuchElementException("No sightings found for train $id")
}*/