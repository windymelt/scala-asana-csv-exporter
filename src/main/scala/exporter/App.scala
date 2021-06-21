package exporter

object App extends App {
  override def main(args: Array[String]): Unit = {
    val pat = sys.env("ASANA_PAT")
    val asana = new AsanaRepository(pat)

    val sectionInfo = asana.getProjectSectionInfo(sys.env("ASANA_PROJECT_GID"))
    sectionInfo match {
      case Some(si) => {
        val infos = si.data.map(si => si.gid -> asana.getProjectInfoBySection(si.gid)).toMap
        val csv = new CSV(new CSVFormatter(si))
        csv.writeResponse(infos, "out.csv")
      }
      case None => // do nothing
    }
  }
}
