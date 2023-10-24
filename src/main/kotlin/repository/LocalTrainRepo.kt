package repository

import Sighting
import Train
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.time.LocalDateTime

class LocalTrainRepo() : TrainRepo {

    private var trainInfo = mutableListOf (Train("FSE34-fSFes2", "Thomas", "Blue", "T1192A"),
        Train("FSE34-fSFes3", "Martin", "Green","T1222B"), Train("FSE34-fSFes5", "Suzy", "Orange", "T2445A"))

    private var sightings = mutableListOf(Sighting(0, "Liverpool Street",
        Train("FSE34-fSFes2", "Thomas", "Blue", "T1192A"),
        LocalDateTime.now()))

    fun setTrainInfo(trains : MutableList<Train>) {
        trainInfo = trains
    }

    override fun getAllTrains(): List<Train> {
        return trainInfo
    }

    override fun getTrain(id: String?): Train? {
        for (train in trainInfo) {
            if (train.id == id) (
                return train
            )
        }
        return null
    }

    override fun getTrainFromJson(json: String): Train {
        val mapper = jacksonObjectMapper()
        val node = mapper.readTree(json).get("train")
        return mapper.readValue<Train>(node.toString())
    }

    override fun getSightingFromJson(json: String): Sighting {
        val mapper = jacksonObjectMapper()
        val node = mapper.readTree(json)
        println(node)
        return Sighting(0, "test", Train("f", "f", "f", "F"), LocalDateTime.now())
    }

    override fun postSighting(sighting: Sighting) {
        sightings.add(sighting)
    }
}