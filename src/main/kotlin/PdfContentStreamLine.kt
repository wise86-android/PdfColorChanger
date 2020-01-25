data class PdfContentStreamLine internal constructor(
    private val prefix: String,
    private val color: RgbColor?,
    private val suffix: String
) {

    companion object {
        fun buildFrom(rawLineContent: String): PdfContentStreamLine {
            val color = rawLineContent.toRgbColor
            val colorAsString = color?.toColorLine.toString()
            val pieces = rawLineContent.split(colorAsString)
            val prefix = pieces[0]
            val suffix = pieces.getOrElse(1, { "" })
            return PdfContentStreamLine(prefix, color, suffix)
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
