import java.time.LocalDateTime

data class Sighting(
    var id: Int = -1,
    val station: Station,
    val train: Train,
    val timestamp: LocalDateTime
)
