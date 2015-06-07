package info.pot420.jsoup

import org.jsoup.Jsoup

object Spoon extends App {
  val url = "http://en.wikipedia.org/wiki/Eugene,_Oregon"
  val doc = Jsoup.connect(url).get()
  val text = doc.getElementsByTag("p").text()
  println(text)
  println("Drinking soup with a SPOON!")
}