import org.junit.jupiter.api.Test

class TrainTest {

    private fun setup(): Train {
        return Train("Fake-1032", "Faker", "Yellow", "beas234")
    }

    private val trainToTest = setup()

    @Test
    fun getId() {
        assert(trainToTest.id == "Fake-1032")
    }

    @Test
    fun getName() {
        assert(trainToTest.name == "Faker")
    }

    @Test
    fun getColour() {
        assert(trainToTest.colour == "Yellow")
    }

    @Test
    fun getTrainNumber() {
        assert(trainToTest.trainNumber == "beas234")
    }

}