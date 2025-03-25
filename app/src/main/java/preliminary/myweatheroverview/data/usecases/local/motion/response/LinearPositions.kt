package preliminary.myweatheroverview.data.usecases.local.motion.response

data class LinearPositions(
   val positions: Array<Double> = Array<Double>(
       size = 3,
       init = { 0.0 }
   )
) {
   override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as LinearPositions

      return positions.contentEquals(other.positions)
   }

   override fun hashCode(): Int {
      return positions.contentHashCode()
   }
}