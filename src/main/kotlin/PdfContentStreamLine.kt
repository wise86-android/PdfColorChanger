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

import java.text.DecimalFormat
import java.util.regex.Pattern

data class PdfContentStreamLine internal constructor(
    private val prefix: String,
    private val color: PDFColor?,
    private val suffix: String
) {

    companion object {
        private const val FLOAT_MATCH = "0\\.\\d+|0|1|1\\.0"
        private val RGB_COLOR_LINE_MATCHER= Pattern.compile("^(.*?\\s)?($FLOAT_MATCH)\\s($FLOAT_MATCH)\\s($FLOAT_MATCH)\\s(rg|RG)(.*)")
        private val CMKY_COLOR_LINE_MATCHER= Pattern.compile("^(.*?\\s)?($FLOAT_MATCH)\\s($FLOAT_MATCH)\\s($FLOAT_MATCH)\\s($FLOAT_MATCH)\\s([kK])(.*)")

        private fun buildRGBColorLine(rawLineContent:String):PdfContentStreamLine?{
            val regexResult = RGB_COLOR_LINE_MATCHER.matcher(rawLineContent)
            if (!regexResult.find())
                return null

            val prefix = regexResult.group(1) ?: ""
            val r = regexResult.group(2).toFloat()
            val g = regexResult.group(3).toFloat()
            val b = regexResult.group(4).toFloat()
            val colorTaget = if(regexResult.group(5) == "RG"){
                PDFColor.Target.STROKE
            }else{
                PDFColor.Target.FILL
            }
            val suffix = regexResult.group(6)
            val color = RgbColor(r, g, b)
            return PdfContentStreamLine(prefix, PDFColor(color,colorTaget), suffix)
        }

        private fun buildCMYKColorLine(rawLineContent: String): PdfContentStreamLine? {
            val regexResult = CMKY_COLOR_LINE_MATCHER.matcher(rawLineContent)
            if (!regexResult.find())
                return null

            val prefix = regexResult.group(1) ?: ""
            val c = regexResult.group(2).toFloat()
            val m = regexResult.group(3).toFloat()
            val y = regexResult.group(4).toFloat()
            val b = regexResult.group(5).toFloat()
            val colorTaget = if(regexResult.group(6) == "K"){
                PDFColor.Target.STROKE
            }else{
                PDFColor.Target.FILL
            }
            val suffix = regexResult.group(7)
            val color = RgbColor(c,m,y, b)
            return PdfContentStreamLine(prefix, PDFColor(color,colorTaget), suffix)
        }

        fun buildFrom(rawLineContent: String): PdfContentStreamLine {
            val rgbLine = buildRGBColorLine(rawLineContent)
            if(rgbLine !=null)
                return rgbLine
            val cmykLine = buildCMYKColorLine(rawLineContent)
            if(cmykLine !=null)
                return cmykLine
            return PdfContentStreamLine(rawLineContent,null,"")
        }
    }

    fun getColor(): RgbColor? {
        return this.color?.color
    }

    fun changeColor(newColor: RgbColor): PdfContentStreamLine {
        if (this.color == null)
            return this.copy()

        return this.copy(color = color.copy(color=newColor))
    }

    override fun toString(): String {
        val colorToString = color?.toString() ?: ""
        return prefix + colorToString + suffix
    }

}
