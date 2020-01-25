import java.io.File
import java.io.InputStream
import java.lang.NumberFormatException
import java.util.*
//TODO ADD SUPPORT FOR COMMENT LINE, SKIP MALFORMED LINES
@Throws(NumberFormatException::class)
fun loadColorMapperFrom(input:InputStream):Map<RgbColor,RgbColor>{
    val inputScanner = Scanner(input)
    val colorMap = mutableMapOf<RgbColor,RgbColor>()
    while(inputScanner.hasNext()){
        val startColor = inputScanner.next().toInt(16)
        val destColor = inputScanner.next().toInt(16)
        colorMap[RgbColor(startColor)] = RgbColor(destColor)
    }
    return colorMap
}

fun loadColorMapperFrom(file: File):Map<RgbColor,RgbColor>{
    return loadColorMapperFrom(file.inputStream())
}