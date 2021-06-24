package exporter.spreadsheet

import scala.collection.JavaConverters._

import com.google.api.client.auth.oauth2.Credential
import com.google.api.services.sheets.v4.Sheets
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.services.sheets.v4.model.CellData
import com.google.api.services.sheets.v4.model.ValueRange

object SpreadSheet {
  import Defaults.{transport, jsonFactory}
  def writeRange(spreadsheetId: String, range: String, rows: Seq[Seq[Object]])(implicit
      credential: Credential,
      transport: HttpTransport = transport,
      jsonFactory: JsonFactory = jsonFactory
  ) = {
    val service = new Sheets.Builder(transport, jsonFactory, credential)
      .setApplicationName("scala-asana-csv-exporter")
      .build()

      val body = new ValueRange().setValues(rows.map(_.asJava).asJava)

      val res = service.spreadsheets().values().update(spreadsheetId, range, body)
      .setValueInputOption("RAW")
      .execute()
  }
}
