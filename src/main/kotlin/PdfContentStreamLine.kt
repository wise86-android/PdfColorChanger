data class PdfContentStreamLine internal constructor(private val prefix: String, private val color: RgbColor?) {

    companion object {
        fun buildFrom(rawLineContent: String): PdfContentStreamLine {
            val color = rawLineContent.toRgbColor
            return if (color != null)
                PdfContentStreamLine("", color)
            else
                PdfContentStreamLine(rawLineContent, null)
        }

    }


    fun containsColor(): Boolean {
        return this.color != null
    }

    fun getColor(): RgbColor? {
        return this.color
    }

    override fun toString(): String {
        return if (this.color != null)
            this.color.toColorLine.toString()
        else
            prefix
    }

    fun changeColor(newColor: RgbColor): PdfContentStreamLine {
        return if (this.containsColor())
            this.copy(color = newColor)
        else
            this.copy()
    }

}
