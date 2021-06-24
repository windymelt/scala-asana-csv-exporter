package exporter.spreadsheet

import scala.collection.JavaConverters._

import com.google.api.client.auth.oauth2.Credential
import com.google.api.services.sheets.v4.Sheets
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.services.sheets.v4.model.CellData

object SpreadSheet {
  import Defaults.{transport, jsonFactory}
  def writeRange(spreadsheetId: String, range: String, putMap: Map[String, String])(implicit
      credential: Credential,
      transport: HttpTransport = transport,
      jsonFactory: JsonFactory = jsonFactory
  ) = {
    val service = new Sheets.Builder(transport, jsonFactory, credential)
      .setApplicationName("scala-asana-csv-exporter")
      .build()

    val result = service
      .spreadsheets()
      .values()
      .get(spreadsheetId, range)
      .execute()
    println(result.getValues())
  }
}
