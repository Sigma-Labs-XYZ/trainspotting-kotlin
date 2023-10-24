import java.time.LocalDateTime

data class Sighting(
    val id: Int,
    val station: String,
    val train: Train,
    val timestamp: LocalDateTime
)
