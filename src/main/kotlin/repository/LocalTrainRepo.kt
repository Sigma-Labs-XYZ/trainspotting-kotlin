package repository

import Sighting
import Station
import Train
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.rmi.NoSuchObjectException
import java.time.LocalDateTime

class LocalTrainRepo() : TrainRepo {

    private var trainInfo = mutableListOf (Train(0, "Thomas", "Blue", "T1192A"),
        Train(1, "Martin", "Green","T1222B"), Train(2, "Suzy", "Orange", "T2445A"))

    private var sightingsInfo = mutableListOf(Sighting(0, Station(0, "LBG"), Train(0, "Thomas", "Blue", "T1192A"), LocalDateTime.now()))

    private var stations = mutableListOf(Station(0, "Liverpool Street"))

    fun setTrainInfo(trains : MutableList<Train>) {
        trainInfo = trains
    }

    override fun getAllTrains(): List<Train> {
        if (trainInfo.size != 0) {
            return trainInfo
        }
        else {
            throw NoSuchElementException("No trains found in database")
        }
    }

    override fun getTrain(id: String): Train {
        for (train in trainInfo) {
            if (train.id == id.toInt()) {
                return train
            }
        }
        throw NoSuchObjectException("No matching train with id $id found")
    }

    override fun getSightings(id: String): List<Sighting> {
        val relevantSightings = mutableListOf<Sighting>()
        for (sighting in sightingsInfo) {
            if (sighting.train.id == id.toInt()) {
                relevantSightings.add(sighting)
            }
        }
        if (relevantSightings.size != 0) {
            return relevantSightings
        }
        else {
            throw NoSuchElementException("No sightings found for train $id")
        }
    }

    override fun getSightingFromJson(json: String): Sighting {
        val mapper = jacksonObjectMapper()
        mapper.registerModule(JavaTimeModule())
        val sighting =  mapper.readValue<Sighting>(json)
        sighting.id = sightingsInfo.size
        sighting.station.id = stations.size
        sighting.train.id = trainInfo.size
        return sighting
    }

    override fun postSighting(sighting: Sighting) {
        val stationNames = stations.map { it.name }
        val trainNumbers = trainInfo.map { it.trainNumber }

        if (sighting.station.name in stationNames) {
            sighting.station.id = stationNames.indexOf(sighting.station.name)
        } else {
            stations.add(sighting.station)
        }

        if (sighting.train.trainNumber in trainNumbers) {
            sighting.train.id = trainNumbers.indexOf(sighting.train.name)
        } else {
            trainInfo.add(sighting.train)
        }

        sightingsInfo.add(sighting)
    }

    fun getSightingsInfo(): MutableList<Sighting> {
        return sightingsInfo
    }

    fun getStations(): MutableList<Station> {
        return stations
    }

}