
import java.time.LocalDateTime

data class Sighting(
    var id: Int = -1,
    val station: Station = Station(),
    val train: Train = Train(),
    val timestamp: LocalDateTime = LocalDateTime.now()
)
