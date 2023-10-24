import java.time.LocalDateTime

data class Sighting(
    var id: Int,
    val station: Station,
    val train: Train,
    val timestamp: LocalDateTime
)
