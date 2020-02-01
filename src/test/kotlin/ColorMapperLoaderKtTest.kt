/*
 * Copyright 2020 Giovanni Visentini
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted (subject to the limitations in the disclaimer
 * below) provided that the following conditions are met:
 *
 *      * Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *
 *      * Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *
 *      * Neither the name of the copyright holder nor the names of its
 *      contributors may be used to endorse or promote products derived from this
 *      software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY
 * THIS LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

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