package zio.example

import zio.logging.consoleJsonLogger
import zio.{ExitCode, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

object ExampleApp extends ZIOAppDefault {

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = Runtime.removeDefaultLoggers >>> consoleJsonLogger()

  override def run: ZIO[Scope, Any, ExitCode] =
    for {
      _ <- ZIO.logDebug("Start")
      _ <- ZIO.logInfo("Done")
    } yield ExitCode.success

}
