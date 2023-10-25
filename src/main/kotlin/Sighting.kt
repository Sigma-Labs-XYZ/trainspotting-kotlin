import java.time.LocalDateTime

data class Sighting(
    val id: Int,
    val station: Station,
    val train: Train,
    val timestamp: LocalDateTime
)
