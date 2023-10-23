package repository

import Train

class LocalTrainRepo() : TrainRepo {

    private var trainInfo = mutableListOf (Train("FSE34-fSFes2", "Thomas", "Blue", "T1192A"),
        Train("FSE34-fSFes3", "Martin", "Green","T1222B"), Train("FSE34-fSFes5", "Suzy", "Orange", "T2445A"))

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
}