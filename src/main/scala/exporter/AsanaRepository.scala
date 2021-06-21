package exporter

import sttp.client3._
import schema._
import schema.DateTimeDecoder._
import sttp.model.Uri

class AsanaRepository(pat: String) {
  val backend = HttpURLConnectionBackend()

  def authorizedRequest() =
    basicRequest.header("Authorization", s"Bearer ${this.pat}")
  def getProjectInfo0(
      projectGid: String,
      offset: Option[String] = None
  ): Option[schema.Response] = {
    import io.circe.generic.auto._, io.circe.syntax._
    val limit = 100
    val originalUri =
      uri"https://app.asana.com/api/1.0/projects/$projectGid/tasks?opt_fields=completed_at,created_at,modified_at,custom_fields,name,assignee,due_on,due_at,start_on,notes,tags,projects,parent"
        .addParam("limit", limit.toString)
    val uri = offset match {
      case Some(offset) => originalUri.addParam("offset", offset)
      case None         => originalUri
    }

    request[schema.Response](uri)
  }
  def getProjectInfo(projectGid: String): Seq[Datum] = {
    val fi = new FetcherIterator[schema.Response, Seq[Datum], Option[String]](
      fetch = getProjectInfo0(projectGid, _),
      nextPred = (r) => !r.data.isEmpty && r.next_page.isDefined,
      joiner = _.map(_.data).getOrElse(Seq()),
      initialOffset = None,
      offsetExtractor = _.next_page.map(_.offset)
    )
    fi.toSeq.flatten
  }

  def getProjectSectionInfo(
      projectGid: String
  ): Option[ProjectSectionInfoList] = {
    // ignoring paging due to few columns
    import io.circe.generic.auto._, io.circe.syntax._
    val limit = 100
    val uri =
      uri"https://app.asana.com/api/1.0/projects/$projectGid/sections?opt_fields=gid,name"
        .addParam("limit", limit.toString)

    request[ProjectSectionInfoList](uri)
  }

  def getProjectInfoBySection0(
      sectionGid: String,
      offset: Option[String] = None
  ): Option[schema.Response] = {
    import io.circe.generic.auto._, io.circe.syntax._
    val limit = 100
    val originalUri =
      uri"https://app.asana.com/api/1.0/sections/$sectionGid/tasks?opt_fields=completed_at,created_at,modified_at,custom_fields,name,assignee,due_on,due_at,start_on,notes,tags,projects,parent"
        .addParam("limit", limit.toString)
    val uri = offset match {
      case Some(offset) => originalUri.addParam("offset", offset)
      case None         => originalUri
    }

    request[schema.Response](uri)
  }

  def getProjectInfoBySection(
      sectionGid: String
  ): Seq[Datum] = {
    val fi = new FetcherIterator[schema.Response, Seq[Datum], Option[String]](
      fetch = getProjectInfoBySection0(sectionGid, _),
      nextPred = (r) => !r.data.isEmpty && r.next_page.isDefined,
      joiner = _.map(_.data).getOrElse(Seq()),
      initialOffset = None,
      offsetExtractor = _.next_page.map(_.offset)
    )
    fi.toSeq.flatten
  }

  def request[A](uri: Uri)(implicit ev: io.circe.Decoder[A]): Option[A] = {
    val res = authorizedRequest
      .get(uri)
      .send(backend)

    val response = for {
      body <- res.body.right
      parsed <- JSON.parse[A](body).left.map(_.toString).right
    } yield parsed

    response match {
      case Left(value)  => println(s"*** $value"); None
      case Right(value) => Some(value)
    }
  }
}
