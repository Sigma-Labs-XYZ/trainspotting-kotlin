
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StationTest {
    private fun setup(): Station {
        return Station(0, "Fake_Stn")
    }

    private val stationToTest = setup()

    @Test
    fun getId() {
        assertTrue(stationToTest.id == 0)
    }

    @Test
    fun getName() {
        assertTrue(stationToTest.name == "Fake_Stn")
    }
}