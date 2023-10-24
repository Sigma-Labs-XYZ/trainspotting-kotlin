package repository

import Sighting
import Train
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.time.LocalDateTime

class LocalTrainRepo() : TrainRepo {

    companion object {
        private var trainInfo = mutableListOf (Train("FSE34-fSFes2", "Thomas", "Blue", "T1192A"),
            Train("FSE34-fSFes3", "Martin", "Green","T1222B"), Train("FSE34-fSFes5", "Suzy", "Orange", "T2445A"))

        private var sightings = mutableListOf(Sighting(0, "Liverpool Street",
            Train("FSE34-fSFes2", "Thomas", "Blue", "T1192A"),
            LocalDateTime.now()))
    }

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

    override fun getSightingFromJson(json: String): Sighting {
        val mapper = jacksonObjectMapper()
        mapper.registerModule(JavaTimeModule())
        val node = mapper.readTree(json)
        val sighting =  mapper.readValue<Sighting>(json)
        sighting.id = sightings.size
        return sighting
    }

    override fun postSighting(sighting: Sighting) {
        sightings.add(sighting)
    }

    fun getSightings(): MutableList<Sighting> {
        return sightings
    }
}