package Infrastructure

import java.net.{ConnectException, HttpCookie}

import scalaj.http.{Http, HttpOptions, HttpResponse}

/**
 * Hemanshu
 */
object ApiRequest {
  def call(
            url: String,
            method: String,
            jsonData: String = "",
            headerArray: Array[(String, String)] = Array(),
            cookie: Option[Seq[HttpCookie]] = None
          ): HttpResponse[String] = {
    
    var basicRequest = Http(url)
    if (jsonData != "") {
      basicRequest = basicRequest
        .postData(jsonData)
        .options(HttpOptions.connTimeout(5000),
          HttpOptions.readTimeout(5000),
          HttpOptions.followRedirects(true))
    }
    basicRequest = basicRequest.method(method)
    if (cookie.isDefined) {
      basicRequest = basicRequest.cookies(cookie.get)
    }
    headerArray
      .foldLeft(basicRequest)((request, head) => {
        request.header(head._1, head._2)
      })
      .asString
  }
}


object TestHttp extends App {
  var statusCode = -1
  var error = ""
  try {
//    throw new ConnectException("Connection Refused (dsfgjshgh)")
    val response = ApiRequest.call(
      "http://127.0.0.1:5000",
      "GET"
    )
    
    println(response.code.toString)
    statusCode = response.code
    error = ""
  } catch {
    case e: Exception =>
      e.printStackTrace()
      error = e.getLocalizedMessage
      println(error)
  }
}
