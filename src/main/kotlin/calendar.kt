import biweekly.ICalendar
import biweekly.component.VEvent
import biweekly.property.DateEnd
import biweekly.property.DateStart
import biweekly.property.DurationProperty
import biweekly.property.Summary
import java.io.FileReader
import java.io.FileWriter
import java.util.*
import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvToBeanBuilder

class ScheduleInfo {
    @CsvBindByName
    val month: Int = 0
    @CsvBindByName
    val day: Int = 0
    @CsvBindByName
    val scripture: String? = null
}

fun main(args: Array<String>) {
    val map: HashMap<String, String> = hashMapOf(
            "1 Nephi" to "https://www.lds.org/scriptures/bofm/1-ne",
            "2 Nephi" to "https://www.lds.org/scriptures/bofm/2-ne",
            "Jacob" to "https://www.lds.org/scriptures/bofm/jacob",
            "Enos" to "https://www.lds.org/scriptures/bofm/enos",
            "Jarom" to "https://www.lds.org/scriptures/bofm/jarom",
            "Omni" to "https://www.lds.org/scriptures/bofm/omni",
            "Words of Mormon" to "https://www.lds.org/scriptures/bofm/w-of-m",
            "Mosiah" to "https://www.lds.org/scriptures/bofm/mosiah",
            "Alma" to "https://www.lds.org/scriptures/bofm/alma",
            "Helaman" to "https://www.lds.org/scriptures/bofm/hel",
            "3 Nephi" to "https://www.lds.org/scriptures/bofm/3-ne",
            "4 Nephi" to "https://www.lds.org/scriptures/bofm/4-ne",
            "Mormon" to "https://www.lds.org/scriptures/bofm/morm",
            "Ether" to "https://www.lds.org/scriptures/bofm/ether",
            "Moroni" to "https://www.lds.org/scriptures/bofm/moro"
    )

    try {
        if (args.size != 2) throw Exception("include csv with schedule as first argument and output file as second")
        val inputFilename = args[0]
        val outputFilename = args[1]
        val chunks = CsvToBeanBuilder<ScheduleInfo>(FileReader(inputFilename)).withType(ScheduleInfo::class.java).build().parse()
        val ical = ICalendar()
        chunks.forEach { chunk ->
            ical.addEvent(VEvent().apply {
                summary = Summary(chunk.scripture)
                dateStart = DateStart(GregorianCalendar(2018, chunk.month - 1, chunk.day).time,false)
            }
            )
        }
        ical.write(FileWriter(outputFilename))
    } catch (e: Exception) {
        println("Terminating with errors\n $e")
    }
}
