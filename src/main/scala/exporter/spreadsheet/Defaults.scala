package exporter.spreadsheet

import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport

object Defaults {
  implicit val jsonFactory = JacksonFactory.getDefaultInstance()
  implicit val transport = GoogleNetHttpTransport.newTrustedTransport()
}
