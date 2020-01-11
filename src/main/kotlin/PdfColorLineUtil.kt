import com.sun.org.apache.xpath.internal.operations.Bool
import java.util.regex.Pattern

private const val normalizerFloatRegExp = "(1|0.?\\d*)"
private val colorLineMatcher = Pattern.compile("$normalizerFloatRegExp $normalizerFloatRegExp $normalizerFloatRegExp rg")

val CharSequence.isColorLine:Boolean
get() = colorLineMatcher.matcher(this).matches()

val CharSequence.toRgbColor:RgbColor?
get() {
    val match = colorLineMatcher.matcher(this)
    return if(match.find()){
        val r = match.group(1).toFloat()
        val g = match.group(2).toFloat()
        val b = match.group(3).toFloat()
        RgbColor(r,g,b)
    }else{
        null
    }
}
