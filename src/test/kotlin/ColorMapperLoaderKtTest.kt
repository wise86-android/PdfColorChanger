import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardOpenOption

internal class ColorMapperLoaderKtTest {

    @Test
    fun emptyFileReturnEmptyMap() {
        val mapping = loadColorMapperFrom("".byteInputStream())
        assertTrue(mapping.isEmpty())
    }


    @Test
    fun lineContainsThe2Colors() {
        val content = """
            00010203 040506
        """.trimIndent()
        val mapping = loadColorMapperFrom(content.byteInputStream())
        assertEquals(1, mapping.size)
        assertEquals(RgbColor(0x01u, 0x02u, 0x03u), mapping.keys.first())
        assertEquals(RgbColor(0x04u, 0x05u, 0x06u), mapping.values.first())
    }


    @Test
    fun theInputCanContaisMultipleLines() {
        val content = """
            00010203 040506
            00070809 0A0B0C
        """.trimIndent()
        val mapping = loadColorMapperFrom(content.byteInputStream())
        assertEquals(2, mapping.size)
        assertTrue(mapping.keys.contains(RgbColor(0x01u, 0x02u, 0x03u)))
        assertEquals(RgbColor(0x04u, 0x05u, 0x06u), mapping[RgbColor(0x01u, 0x02u, 0x03u)])
        assertTrue(mapping.keys.contains(RgbColor(0x07u, 0x08u, 0x09u)))
        assertEquals(RgbColor(0x0Au, 0x0Bu, 0x0Cu), mapping[RgbColor(0x07u, 0x08u, 0x09u)])
    }

    @TempDir
    lateinit var filePath: File

    @Test
    fun canBeReadFromFile() {
        val inputFile = File(filePath, "input.txt")
        val content = """
            00010203 040506
            00070809 0A0B0C
        """.trimIndent()
        Files.write(inputFile.toPath(), content.toByteArray(), StandardOpenOption.CREATE_NEW)

        val mapping = loadColorMapperFrom(inputFile.inputStream())

        assertEquals(2, mapping.size)
        assertTrue(mapping.keys.contains(RgbColor(0x01u, 0x02u, 0x03u)))
        assertEquals(RgbColor(0x04u, 0x05u, 0x06u), mapping[RgbColor(0x01u, 0x02u, 0x03u)])
        assertTrue(mapping.keys.contains(RgbColor(0x07u, 0x08u, 0x09u)))
        assertEquals(RgbColor(0x0Au, 0x0Bu, 0x0Cu), mapping[RgbColor(0x07u, 0x08u, 0x09u)])
    }

}