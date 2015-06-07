package info.pot420.jsoup

import org.jsoup.Jsoup

object Util {
	def getText(url: String) = {
		val doc = Jsoup.connect(url).get()
		doc.getElementsByTag("p").text()
	}
}
