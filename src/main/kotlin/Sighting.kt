import java.time.LocalDateTime

data class Sighting(
    val id: String,
    val station: Station,
    val train: Train,
    val timestamp: LocalDateTime
)
