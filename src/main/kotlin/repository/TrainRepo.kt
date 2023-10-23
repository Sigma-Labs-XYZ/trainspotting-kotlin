package repository


interface TrainRepo {

    fun getAllTrains(): Any {
        return LocalTrainRepo()
    }
}