package exporter.spreadsheet

import com.google.api.client.auth.oauth2
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.SheetsScopes

import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader
import scala.util.control.Exception._

object CredentialFactory {
  import Defaults.{transport, jsonFactory}
  val spreadsheetScope = Seq(SheetsScopes.SPREADSHEETS)

  def getCredential(
      path: String,
      tokenPath: String
  ): Option[oauth2.Credential] = {
    import collection.JavaConverters._

    val f = new File(path)
    return for {
      fs <- allCatch opt { new FileInputStream(f) }
      secrets <- allCatch opt {
        GoogleClientSecrets.load(jsonFactory, new InputStreamReader(fs))
      }
    } yield {
      val flow: GoogleAuthorizationCodeFlow =
        new GoogleAuthorizationCodeFlow.Builder(
          transport,
          jsonFactory,
          secrets,
          spreadsheetScope.asJava
        )
          .setDataStoreFactory(
            new FileDataStoreFactory(new java.io.File(tokenPath))
          )
          .setAccessType("offline")
          .build()

      val receiver = new LocalServerReceiver.Builder().setPort(8888).build()
      new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
  }
}
