import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import java.io.File

fun changeColors(data:CharSequence, colorMapping:Map<RgbColor,RgbColor>):SubstitutionResult{
    val out = StringBuilder(data.length)
    val foundColors = mutableSetOf<RgbColor>()
    val unknownColors = mutableSetOf<RgbColor>()
    data.lineSequence()
        .forEach {line ->
            val inColor = line.toRgbColor
            if(inColor!=null){
                foundColors.add(inColor)
                val outColor = colorMapping[inColor]
                if(outColor!=null) {
                    out.append(outColor.toColorLine)
                }else{
                    unknownColors.add(inColor)
                    out.append(line) // no substitution
                }
            }else{
                out.append(line)
            }
            out.append('\n')
        }
    val finalString = out.removeSuffix("\n").toString()

    return Pair(finalString,
        SubstitutionResultStat(foundColors.size.toUInt(),unknownColors.toList()))
}


fun main() {

    //File("dasd").walk().filter { it.extension == "pdf" }

    val document = PDDocument.load(File("./SearchButton.pdf"))
    val page = document.documentCatalog.pages.get(0) as PDPage
    page.contentStreams.forEach { stream ->
        val data = stream.toByteArray()
        val str = String(data)

        val colorMapping = mapOf(
            RgbColor(red = 0.22353f,green = 0.66275f,blue = 0.86275f) to RgbColor(red = 0.0f,green = 1.0f,blue = 1.0f)
        )

        val (newStr,stat) = changeColors(str,colorMapping)

        val new = stream.createOutputStream()
        new.write(newStr.toByteArray())
        new.close()

        println("Image colors: ${stat.imageColors}")
        println("Unknown colors: ${stat.unknownColor.size}")
        if(stat.unknownColor.isNotEmpty()){
            println(stat.unknownColor.joinToString(separator = "\n\t",prefix = "\t"))
        }

    }

    document.save("./changeColor.pdf")
}