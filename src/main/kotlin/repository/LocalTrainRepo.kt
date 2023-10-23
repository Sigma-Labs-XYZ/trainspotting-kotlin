package repository

import Sighting
import Train
import com.fasterxml.jackson.databind.ObjectMapper

class LocalTrainRepo : TrainRepo {

    val trainInfo = mutableListOf (Train("FSE34-fSFes2", "Thomas", "Blue", "T1192A"),
        Train("FSE34-fSFes3", "Martin", "Green","T1222B"), Train("FSE34-fSFes5", "Suzy", "Orange", "T2445A"))

    override fun getAllTrains(): Any {
        return trainInfo
    }

    override fun getTrain(id: Int): Train {
        TODO("Not yet implemented")
    }

    override fun getTrainFromJson(json: String): Train {
        val mapper = ObjectMapper()
        return Train("bruh", "bruh", "bruh", "bruh")
    }

    override fun postSighting(sighting: Sighting) {
        TODO("Not yet implemented")
    }
}