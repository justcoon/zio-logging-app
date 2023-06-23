package zio.example

import zio.logging.slf4j.bridge.Slf4jBridge
import zio.logging._
import zio.{ExitCode, LogLevel, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

object Slf4jBridgeExampleApp extends ZIOAppDefault {

  private val slf4jLogger = org.slf4j.LoggerFactory.getLogger("SLF4J-LOGGER")

  private val loggerName = LoggerNameExtractor.annotationOrTrace(loggerNameAnnotationKey)

  private val logFilter: LogFilter[String] = LogFilter.logLevelByGroup(
    LogLevel.Info,
    loggerName.toLogGroup(),
    "zio.logging.slf4j" -> LogLevel.Debug,
    "SLF4J-LOGGER" -> LogLevel.Warning
  )

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
    Runtime.removeDefaultLoggers >>> consoleJsonLogger(
      ConsoleLoggerConfig(LogFormat.label("name", loggerName.toLogFormat()) + LogFormat.default, logFilter)
    ) >+> Slf4jBridge.initialize

  override def run: ZIO[Scope, Any, ExitCode] =
    for {
      _ <- ZIO.logDebug("Start")
      _ <- ZIO.succeed(slf4jLogger.debug("Test {}!", "DEBUG"))
      _ <- ZIO.succeed(slf4jLogger.warn("Test {}!", "WARNING"))
      _ <- ZIO.logInfo("Done")
    } yield ExitCode.success

}
