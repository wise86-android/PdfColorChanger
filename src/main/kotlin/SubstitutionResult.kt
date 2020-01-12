typealias  SubstitutionResult = Pair<String,SubstitutionResultStat>

data class SubstitutionResultStat(val imageColors:UInt,
                                  val unknownColor: List<RgbColor>)