package exporter.schema

case class Response(data: Seq[Datum], next_page: Option[NextPageInfo])

case class NextPageInfo(offset: String, path: String, uri: String)