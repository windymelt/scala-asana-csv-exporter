package exporter

import com.typesafe.config.ConfigFactory

object Config {
  lazy val config = ConfigFactory.load()
}
