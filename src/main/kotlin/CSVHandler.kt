package axan18

import org.apache.commons.csv.CSVFormat
import java.io.InputStream
import java.time.LocalDate

class CSVHandler {
    fun readCsv(inputStream : InputStream) : List<Transaction>{
        return CSVFormat.Builder.create(CSVFormat.DEFAULT).apply {
            setIgnoreSurroundingSpaces(true)
        }.get().parse(inputStream.reader())
            .drop(1)
            .map{
                Transaction(
                    id = null,
                    date = LocalDate.parse(it[0]),
                    category = it[1],
                    value = it[2].toDouble(),
                    description = it[3]
                )
            }
    }
}