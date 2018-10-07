


import biweekly.ICalendar
import biweekly.component.VEvent
import biweekly.property.DateStart
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
    try {
        if (args.size != 2) throw Exception("include csv with schedule as first argument and output file as second")
        val inputFilename = args[0]
        val outputFilename = args[1]
        val chunks = CsvToBeanBuilder<ScheduleInfo>(FileReader(inputFilename)).withType(ScheduleInfo::class.java).build().parse()
        val ical = ICalendar()
        chunks.forEach { chunk ->
            ical.addEvent(VEvent().apply {
                summary = Summary(chunk.scripture)
                dateStart = DateStart(GregorianCalendar(2018, chunk.month - 1, chunk.day).time)
            }
            )
        }
        ical.write(FileWriter(outputFilename))
    } catch (e: Exception) {
        println("Terminating with errors\n $e")
    }
}
