
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class SightingTest {

    private fun setup(): Sighting {
        return Sighting("001", Station("1", "Fake_Stn"), Train("Fake-1032", "Faker", "Yellow", "beas234"), LocalDateTime.of(
            LocalDate.of(2023, 10, 24),
            LocalTime.of(10, 24, 13, 58)))
    }

    private val sightingToTest = setup()

    @Test
    fun getId() {
        assertTrue(sightingToTest.id == "001")
    }

    @Test
    fun getStation() {
        assertTrue(sightingToTest.station.name == "Fake_Stn")
    }

    @Test
    fun getTrain() {
        assertTrue(sightingToTest.train.trainNumber == "beas234")
    }

    @Test
    fun getTimestamp() {
        assertTrue(sightingToTest.timestamp.hour == 10)
        assertTrue((sightingToTest.timestamp.minute == 24))
        assertTrue(sightingToTest.timestamp.year == 2023)
    }
}