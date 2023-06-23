package zio.example

import zio.logging.backend.SLF4J
import zio.{ExitCode, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

object Slf4jExampleApp extends ZIOAppDefault {

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = Runtime.removeDefaultLoggers >>> SLF4J.slf4j

  override def run: ZIO[Scope, Any, ExitCode] =
    for {
      _ <- ZIO.logDebug("Start")
      _ <- ZIO.logInfo("Done")
    } yield ExitCode.success

}
