import java.time.LocalDateTime

data class Sighting(
    val station: String,
    val train: Train,
    val timestamp: LocalDateTime
)