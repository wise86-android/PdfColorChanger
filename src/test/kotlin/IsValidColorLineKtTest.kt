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

internal class IsValidColorLineKtTest {

    private val String.isColorLine: Boolean
        get() = PdfContentStreamLine.buildFrom(this).getColor() != null

    @Test
    fun validLineIsDetected() {
        assertTrue("0.73333 0.8 0 rg".isColorLine)
    }

    @Test
    fun validLineMustHave3Number() {
        assertFalse("rg".isColorLine)
        assertFalse("0 rg".isColorLine)
        assertFalse("0 0 rg".isColorLine)
        assertTrue("0 0 0 rg".isColorLine)
    }

    @Test
    fun validLineTheNumberCanBeFloat() {
        assertTrue("0 0.12345 1 rg".isColorLine)
    }

    @Test
    fun oneCanHaveOneDecimal() {
        assertTrue("1 1.0 0 rg".isColorLine)
    }

    @Test
    fun theNumberAreBetween0And1() {
        assertFalse("0 2 0 rg".isColorLine)
        assertFalse("1.1 0 0 rg".isColorLine)
        assertFalse("0 0 -0.1 rg".isColorLine)
    }


}