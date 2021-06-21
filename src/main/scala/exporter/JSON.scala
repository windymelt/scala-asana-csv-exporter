package exporter

import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

object JSON {
  def parse[A](s: String)(implicit ev: io.circe.Decoder[A]): Either[io.circe.Error, A] = decode[A](s)
}
