import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

internal class PdfListFileKtTest{

    @TempDir
    lateinit var baseDir:File

    private fun createFile(parentDir: File, name: String):File{
        val file = File(parentDir, name)
        file.createNewFile()
        return file
    }

    @Test
    fun getTheFileFromADir(){
        val pdfFile = createFile(baseDir,"test1.pdf")
        val pdfFiles = getAllPdfFileFrom(baseDir).toList()

        assertEquals(1,pdfFiles.size)
        assertEquals(pdfFile.path,pdfFiles.first().path)
    }

    @Test
    fun onlyThePdfFileAreListed(){
        val pdfFileA = createFile(baseDir,"test1.pdf")
        createFile(baseDir,"test1.txt")
        val pdfFileB = createFile(baseDir,"test2.pdf")
        val pdfFiles = getAllPdfFileFrom(baseDir).map { it.path }.toList()

        assertEquals(2,pdfFiles.size)
        assertTrue(pdfFiles.contains(pdfFileA.path))
        assertTrue(pdfFiles.contains(pdfFileB.path))
    }

    @Test
    fun alsoTheSubdirectoryAreScanned(){
        val pdfFileA = createFile(baseDir,"test1.pdf")
        createFile(baseDir,"test1.txt")

        val subDir = File(baseDir,"subDir").apply { mkdir() }

        val pdfFileB = createFile(subDir,"test2.pdf")
        createFile(subDir,"test2.txt")

        val pdfFiles = getAllPdfFileFrom(baseDir).map { it.path }.toList()

        assertEquals(2,pdfFiles.size)
        assertTrue(pdfFiles.contains(pdfFileA.path))
        assertTrue(pdfFiles.contains(pdfFileB.path))
    }

}