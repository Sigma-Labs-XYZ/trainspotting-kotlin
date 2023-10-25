package repository

import DBConnect
import Sighting
import Train
import com.google.cloud.firestore.QueryDocumentSnapshot

class DatabaseRepo : TrainRepo {

    private val db = DBConnect.getConnection()

    fun query
    override fun getAllTrains(): List<Train> {
        val query = db.collection("trains-kotlin").get()
        val querySnapshot = query.get()
        val trainDocs = querySnapshot.documents
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
        val query = db.collection("stations-kotlin").get()
        val querySnapshot = query.get()
        val trainDocs = querySnapshot.documents
        val allTrains = mutableListOf<Train>()
        for (train in trainDocs) {
            allTrains.add(Train(
                id = train.getLong("id")?.toInt(),
                name = train.getString("name"),
                colour = train.getString("colour"),
                trainNumber = train.getString("trainNumber")
            ))
        }


    override fun getSightings(id: Int): List<Sighting> {
        TODO("Not yet implemented")
    }

}