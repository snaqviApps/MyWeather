package preliminary.myweatheroverview.util.motion

sealed interface MovePositionsState {
    data object Empty: MovePositionsState
    data class Loading(val isLoading: Boolean = false) : MovePositionsState

    data class Error(val message: String) : MovePositionsState
    data class Success(val positions: FloatArray, val logging: ()->Unit) : MovePositionsState {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as Success
            return positions.contentEquals(other.positions)
        }

        override fun hashCode(): Int {
            return positions.contentHashCode()
        }

    }
}