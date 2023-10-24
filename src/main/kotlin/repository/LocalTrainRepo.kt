package repository

import Sighting
import Station
import Train
import java.rmi.NoSuchObjectException
import java.time.LocalDateTime

class LocalTrainRepo() : TrainRepo {

    private var trainInfo = mutableListOf (Train("FSE34-fSFes2", "Thomas", "Blue", "T1192A"),
        Train("FSE34-fSFes3", "Martin", "Green","T1222B"), Train("FSE34-fSFes5", "Suzy", "Orange", "T2445A"))

    private var sightingsInfo = mutableListOf(Sighting("1", Station("001", "LBG"), Train("FSE34-fSFes2", "Thomas", "Blue", "T1192A"), LocalDateTime.now()))
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
            if (train.id == id) {
                return train
            }
        }
        throw NoSuchObjectException("No matching train with id $id found")
    }

    override fun getSightings(id: String): List<Sighting> {
        val relevantSightings = mutableListOf<Sighting>()
        for (sighting in sightingsInfo) {
            if (sighting.train.id == id) {
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

}