import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import java.io.File


fun main() {
    val document = PDDocument.load(File("./SearchButton.pdf"))
    val page = document.documentCatalog.pages.get(0) as PDPage
    page.contentStreams.forEach { stream ->
        val data = stream.toByteArray()
        val str = String(data)
        val newStr = str.replace("0.73333 0.8 0 rg","0 0 0 rg")
            .replace("0.22353 0.66275 0.86275 rg","0 0 0 rg")

        val new = stream.createOutputStream()
        new.write(newStr.toByteArray())
        new.close()
    }

    document.save("./changeColor.pdf")
}