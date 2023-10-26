
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

data class Sighting(
    var id: Int = -1,
    val station: Station = Station(),
    val train: Train = Train(),
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    constructor(id: Long, station: Station, train: Train, timestamp: Date) : this()
}
