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

fun getAllPdfFileFrom(directory:File): Sequence<File> {
    return directory.walk().filter { it.extension == "pdf" }
}

fun PDDocument.changeColor(colorMapping:Map<RgbColor,RgbColor>):SubstitutionResultStat{
    val page = documentCatalog.pages.get(0) as PDPage
    var allStat = SubstitutionResultStat(0u, emptyList())
    page.contentStreams.forEach { stream ->
        val data = stream.toByteArray()
        val str = String(data)

        val (newStr,stat) = changeColors(str,colorMapping)
        allStat += stat
        val new = stream.createOutputStream()
        new.write(newStr.toByteArray())
        new.close()

    }
    return allStat
}

fun changeColors(inputDir:File,colorMapping:Map<RgbColor,RgbColor>, outputDir:File) {
    getAllPdfFileFrom(inputDir).forEach { pdfFile ->
        val document = PDDocument.load(pdfFile)
        val stat = document.changeColor(colorMapping)
        println("File ${pdfFile.name}")
        println("\tImage colors: ${stat.imageColors}")
        println("\tUnknown colors: ${stat.unknownColor.size}")
        if(stat.unknownColor.isNotEmpty()){
            println(stat.unknownColor.joinToString(separator = "\n\t\t",prefix = "\t\t"))
        }
        //file path starting from inputDir
        val relativePath = inputDir.toPath().relativize(pdfFile.toPath())
        // outputPath + currentFile path
        val outputPath = outputDir.toPath().resolve(relativePath)
        //create destination dir
        outputPath.parent.toFile().mkdirs()
        //write the new file with changed color
        document.save(outputPath.toFile())
    }
}


fun main(args: Array<String>) {
    if(args.size!=3){
        println("Usage: InputDir mappingFile outDir")
        return
    }

    val inputDir = File(args[0])
    val mappingFile = File(args[1])
    val outputDir = File(args[2])

    if(!inputDir.isDirectory){
        println("${args[0]} must be a directory")
        return
    }

    if(!mappingFile.exists()){
        println("${args[1]} do not exist")
        return
    }

    if(!outputDir.isDirectory){
        println("${args[2]} must be a directory")
        return
    }

    val mapping = loadColorMapperFrom(mappingFile)

    changeColors(inputDir,mapping,outputDir)
}