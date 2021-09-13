package com.example.http4squickstart

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.Headers
import org.http4s.circe._
import io.circe._, io.circe.syntax._

object Http4squickstartRoutes {
  implicit val HeaderDecoder: Encoder[org.http4s.Headers] = new Encoder[org.http4s.Headers] {
    final def apply(hs: Headers): Json = {
      hs.foldLeft(JsonObject.empty)((o, header) => o.add(header.name.value, header.value.asJson)).asJson
    }
  }

  def jokeRoutes[F[_]: Sync](J: Jokes[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "joke" =>
        for {
          joke <- J.get
          resp <- Ok(joke)
        } yield resp
    }
  }

  def helloWorldRoutes[F[_]: Sync](): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case request @ GET -> Root / "hello" =>
        for {
          resp <- Ok(request.headers.asJson)
        } yield resp
    }
  }
}
