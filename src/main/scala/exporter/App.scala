package exporter

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport

import Config.config

object App extends App {
  override def main(args: Array[String]): Unit = {
    val pat = config.getString("asana.pat")
    val asana = new AsanaRepository(pat)

    val sectionInfo =
      asana.getProjectSectionInfo(config.getString("asana.projectGid"))
    sectionInfo match {
      case Some(si) => {
        val infos = si.data.map(si => si.gid -> asana.getProjectInfoBySection(si.gid)).toMap
        val csv = new CSV(new CSVFormatter(si))
        csv.writeResponse(infos, "out.csv")

        implicit val credential = spreadsheet.CredentialFactory
          .getCredential(
            config.getString("google.credentialsPath"),
            config.getString("google.tokenPath")
          )
          .get
        spreadsheet.SpreadSheet.writeRange(
          config.getString("google.spreadsheetId"),
          config.getString("google.spreadsheetRange"),
          Map()
        )
      }
      case None => // do nothing
    }
  }
}
