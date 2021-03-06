package exporter

import com.github.tototoshi.csv._
import java.io.File

class CSV(formatter: CSVFormatter) {
  def writeResponse(dataMap: Map[String, Seq[schema.Datum]], fileName: String): Unit = {
    val f = new File(fileName)
    val writer = CSVWriter.open(f)
    writer.writeRow(formatter.header)
    dataMap.foreach { case (sectionGid, data) =>
      data.foreach(datum => writer.writeRow(formatter.format(datum, sectionGid)))
    }
    writer.close()
  }
  def getRows(dataMap: Map[String, Seq[schema.Datum]]): Seq[Seq[String]] = {
      val header = formatter.header
      val rows = dataMap.map { case (sectionGid, data) =>
        data.map ( datum => formatter.format(datum, sectionGid) )
      } flatten

      Seq(header) ++ rows
    }
}
