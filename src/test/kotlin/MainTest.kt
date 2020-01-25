import org.apache.pdfbox.pdmodel.PDDocument
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import java.lang.System.lineSeparator

class MainTest {

    private val systemOut = ByteArrayOutputStream()

    @TempDir
    lateinit var tempDir: File

    @BeforeEach
    fun changeSystemOutput() {
        System.setOut(PrintStream(systemOut))
    }

    @Test
    fun check3PramArePassedToTheMain() {
        main(emptyArray())

        val mainOut = systemOut.toString()
        assertEquals("Usage: InputDir mappingFile outDir" + lineSeparator(), mainOut)
    }

    @Test
    fun firstParamMustBeADirectory() {
        main(arrayOf("ciao", "file", "dir"))

        val mainOut = systemOut.toString()
        assertEquals("ciao must be a directory" + lineSeparator(), mainOut)

    }

    @Test
    fun secondParamMustBeAFile() {
        main(arrayOf(tempDir.path, "noExisting", "dir"))

        val mainOut = systemOut.toString()
        assertEquals("noExisting do not exist" + lineSeparator(), mainOut)
    }

    @Test
    fun thirdParamMustBeADirectory() {
        val existingFile = File(tempDir, "existingFile")
        existingFile.createNewFile()

        main(arrayOf(tempDir.path, existingFile.path, "dir"))

        val mainOut = systemOut.toString()
        assertEquals("dir must be a directory" + lineSeparator(), mainOut)
    }


    @Test
    fun thePdfIsCreated() {
        val inputFolderPath = javaClass.getResource("input").file
        val inputMapFilePath = javaClass.getResource("input/colorMapping.txt").file

        main(arrayOf(inputFolderPath, inputMapFilePath, tempDir.path))

        val outFile = File(tempDir,"input.pdf")
        assertTrue(outFile.exists())

        val document = PDDocument.load(outFile)
        val outData = String(document.getPage(0).contentStreams.asSequence().first().toByteArray()).lines()

        assertEquals("1 1 1 rg /a0 gs",outData[2] )
        assertEquals("0 0 1 RG 0.762505 w",outData[6])
        assertEquals("0 1 0 rg BT",outData[21])
        assertEquals("1 0 0 rg BT",outData[26])
        assertEquals("0 0 1 rg BT",outData[31])
        assertEquals("0 1 0 rg BT",outData[36])

    }

}