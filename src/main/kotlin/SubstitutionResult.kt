import kotlin.math.max

typealias  SubstitutionResult = Pair<String,SubstitutionResultStat>

data class SubstitutionResultStat(val imageColors:UInt,
                                  val unknownColor: List<RgbColor>){

    operator fun plus(other:SubstitutionResultStat):SubstitutionResultStat{
        val mergedColor = mutableSetOf<RgbColor>()
        mergedColor.addAll(unknownColor)
        mergedColor.addAll(other.unknownColor)
        return SubstitutionResultStat(max(imageColors,other.imageColors),mergedColor.toList())
    }

}