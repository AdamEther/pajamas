package info.pot420.xml

import info.pot420.xml.thriftscala.OsmNode

import scala.util.Try
import scala.xml.XML

object OsmUtil {

	def getPoi(filePath: String): Seq[OsmNode] = {
		val osm = XML.loadFile(filePath)
		val nodes = scala.xml.Utility.trim(osm) \ "node"
		nodes flatMap { n =>
			val id = n \ "@id"
			val lat = n \ "@lat"
			val lon = n \ "@lon"
			val tags = n.child map { c =>
				val key = (c \ "@k").text
				val value = (c \ "@v").text
				(key, value)
			}
			val nameTags = tags collect { case ("name", value) => ("name", value)}
			for {id <- Try(id.text.toLong).toOption
		     lat <- Try(lat.text.toDouble).toOption
		     lon <- Try(lon.text.toDouble).toOption
		     if nameTags.nonEmpty
			} yield OsmNode(id, lat, lon, nameTags.toMap)

		}
	}

}
