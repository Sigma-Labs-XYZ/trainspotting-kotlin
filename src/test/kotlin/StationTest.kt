
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StationTest {
    private fun setup(): Station {
        return Station(1, "Fake_Stn")
    }

    private val stationToTest = setup()

    @Test
    fun getId() {
        assertTrue(stationToTest.id == 1)
    }

    @Test
    fun getName() {
        assertTrue(stationToTest.name == "Fake_Stn")
    }
}