package repository

import Sighting
import Station
import Train

class LocalTrainRepo() : TrainRepo {

    private var trainInfo = mutableListOf (Train("FSE34-fSFes2", "Thomas", "Blue", "T1192A"),
        Train("FSE34-fSFes3", "Martin", "Green","T1222B"), Train("FSE34-fSFes5", "Suzy", "Orange", "T2445A"))

    private var sightingsInfo = mutableListOf(Sighting("1", Station("001", "LBG"), Train("FSE34-fSFes2", "Thomas", "Blue", "T1192A"), "fake time"))
    fun setTrainInfo(trains : MutableList<Train>) {
        trainInfo = trains
    }

    override fun getAllTrains(): List<Train> {
        return trainInfo
    }

    override fun getTrain(id: String): Train? {
        for (train in trainInfo) {
            if (train.id == id) {
                return train
            }
        }
        return null
    }

    override fun getSightings(id: String): List<Sighting> {
        val relevantSightings = mutableListOf<Sighting>()
        for (sighting in sightingsInfo) {
            if (sighting.train.id == id) {
                relevantSightings.add(sighting)
            }
        }
        return relevantSightings
    }

}