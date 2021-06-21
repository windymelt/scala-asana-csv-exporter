package exporter

class CSVFormatter(sectionInfo: schema.ProjectSectionInfoList) {
  def header: Seq[String] = Seq(
    "Task ID",
    "Created At",
    "Completed At",
    "Last Modified",
    "Name",
    "Section/Column",
    "Assignee",
    "Email",
    "Start Date",
    "Due Date",
    "Tags",
    "Notes",
    "Projects",
    "Parent Task",
    "平均見積もり",
    "最悪見積もり"
  )

  val gidToSectionMap = sectionInfo.data.map(si => si.gid -> si.name).toMap

  def format(datum: schema.Datum, sectionGid: String): Seq[String] = {
    Seq(
      datum.gid,
      dateTimeToLocalDateTime(datum.created_at),
      datum.completed_at.map(dateTimeToLocalDateTime).getOrElse(""),
      dateTimeToLocalDateTime(datum.modified_at),
      datum.name,
      gidToSectionMap.get(sectionGid).getOrElse(""),
      datum.assignee.map(_.gid).getOrElse(""), // stub. prepare name
      datum.assignee.map(_.gid).getOrElse(""), // stub. email.
      datum.start_on.map(dateTimeToLocalDateTime).getOrElse(""),
      datum.due_on.map(dateTimeToLocalDateTime).getOrElse(""),
      datum.tags.mkString(" "),
      datum.notes.replaceAll("""\n""", " "),
      datum.projects.headOption.map(_.gid).getOrElse(""), // stub. project name
      datum.parent.getOrElse(""), // stub. parent task name
      datum.custom_fields
        .find(_.name == "平均見積もり")
        .flatMap(_.display_value)
        .getOrElse(""),
      datum.custom_fields
        .find(_.name == "最悪見積もり")
        .flatMap(_.display_value)
        .getOrElse("")
    )
  }
  def dateTimeToLocalDateTime(dt: org.joda.time.DateTime): String = {
      dt.withZone(org.joda.time.DateTimeZone.forOffsetHours(9))
            .toLocalDateTime
            .toString("yyyy-MM-dd")
  }
}
