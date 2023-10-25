package repository

import Sighting
import Station
import Train
import java.rmi.NoSuchObjectException
import java.time.LocalDateTime

class LocalTrainRepo() : TrainRepo {

    private var trainInfo = mutableListOf (Train(2, "Thomas", "Blue", "T1192A"),
        Train(3, "Martin", "Green","T1222B"), Train(5, "Suzy", "Orange", "T2445A"))

    private var sightingsInfo = mutableListOf(Sighting(1, Station(1, "LBG"), Train(2, "Thomas", "Blue", "T1192A"), LocalDateTime.now()))
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

    override fun getTrain(id: Int): Train {
        val matchingTrain = trainInfo.filter { it.id == id }
        if (matchingTrain.isNotEmpty()) {
            return matchingTrain[0]
        } else {
            throw NoSuchObjectException("No matching train with id $id found")
        }
    }

    override fun getSightings(id: Int): List<Sighting> {
        val relevantSightings = sightingsInfo.filter{ it.train.id == id}
        if (relevantSightings.isNotEmpty()) {
            return relevantSightings
        } else {
            throw NoSuchElementException("No sightings found for train $id")
        }
    }

}