package com.example.http4squickstart

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]) =
    Http4squickstartServer.stream[IO].compile.drain.as(ExitCode.Success)
}
