import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions

object DBConnect {
    fun getConnection(): Firestore {
        val cred = Firestore::class.java.classLoader.getResourceAsStream("key.json")
        val firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
            .setProjectId("large-fruit-company")
            .setCredentials(GoogleCredentials.fromStream(cred))
            .build()
        return firestoreOptions.getService()!!
    }

}