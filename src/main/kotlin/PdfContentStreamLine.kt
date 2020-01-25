data class PdfContentStreamLine private constructor(private val prefix: String, private val color: RgbColor?) {

    companion object {
        fun buildFrom(rawLineContent: String): PdfContentStreamLine {
            return PdfContentStreamLine(rawLineContent, rawLineContent.toRgbColor)
        }

        fun buildFrom(color: RgbColor) = buildFrom(color.toColorLine.toString())
    }


    fun containsColor(): Boolean {
        return this.color != null
    }

    fun getColor(): RgbColor? {
        return this.color
    }

    override fun toString(): String {
        return prefix
    }

    fun changeColor(newColor: RgbColor): PdfContentStreamLine {
        return if (this.containsColor())
            buildFrom(newColor)
        else
            this.copy()
    }

}
