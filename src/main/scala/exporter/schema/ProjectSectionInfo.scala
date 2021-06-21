package exporter.schema

final case class ProjectSectionInfoList(data: Seq[ProjectSectionInfo])
final case class ProjectSectionInfo(gid: String, name: String)