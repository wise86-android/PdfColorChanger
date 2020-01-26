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
            val suffix = pieces.getOrElse(1) { "" }
            return PdfContentStreamLine(prefix, color, suffix)
        }
    }

    fun getColor(): RgbColor? {
        return this.color
    }

    fun changeColor(newColor: RgbColor): PdfContentStreamLine {
        if (this.color == null)
            return this.copy()

        return this.copy(color = newColor)
    }

    override fun toString(): String {
        return prefix +
                (this.color?.toColorLine?.toString() ?: "") +
                suffix
    }

}
