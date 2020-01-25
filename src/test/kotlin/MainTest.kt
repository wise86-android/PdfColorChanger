import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class MainTest {

    private val systemOut = ByteArrayOutputStream()

    @TempDir
    lateinit var baseDir: File

    @BeforeEach
    fun changeSystemOutput(){
        System.setOut(PrintStream(systemOut))
    }


    @Test
    fun check3PramArePassedToTheMain(){
        main(emptyArray())

        val mainOut = systemOut.toString()
        assertEquals("Usage: InputDir mappingFile outDir"+System.lineSeparator(),mainOut)
    }

    @Test
    fun firstParamMustBeADirectory(){

        main(arrayOf("ciao","file","dir"))

        val mainOut = systemOut.toString()
        assertEquals("ciao must be a directory"+System.lineSeparator(),mainOut)

    }

    @Test
    fun secondParamMustBeAFile(){

        main(arrayOf(baseDir.path,"noExisting","dir"))

        val mainOut = systemOut.toString()
        assertEquals("noExisting do not exist"+System.lineSeparator(),mainOut)
    }

    @Test
    fun thirdParamMustBeADirectory(){

        val existingFile = File(baseDir,"existingFile")
        existingFile.createNewFile()
        main(arrayOf(baseDir.path,existingFile.path,"dir"))

        val mainOut = systemOut.toString()
        assertEquals("dir must be a directory"+System.lineSeparator(),mainOut)
    }

}