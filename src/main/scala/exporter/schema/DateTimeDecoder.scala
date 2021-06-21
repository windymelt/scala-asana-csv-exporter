package exporter.schema

import com.github.nscala_time.time.Imports._
import io.circe.{ Decoder, Encoder, HCursor, Json }

object DateTimeDecoder {
    implicit val decodeDateTime: Decoder[org.joda.time.DateTime] = new Decoder[org.joda.time.DateTime] {
        final def apply(c: HCursor): Decoder.Result[org.joda.time.DateTime] = {
            for {
                str <- c.as[String]
            } yield {
                DateTime.parse(str)
            }
        }
    }
}
