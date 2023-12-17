import com.example.android_app.repository.Starship

data class UIState<T> (
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class UIStateSimple(
    val data: List<Starship>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)