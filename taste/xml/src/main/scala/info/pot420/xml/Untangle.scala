package info.pot420.xml

import java.io.File

import info.pot420.jsoup.Util
import info.pot420.xml.thriftscala.OsmNode

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
//import org.apache.lucene.document.LongField;
import org.apache.lucene.document._
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig.OpenMode
import org.apache.lucene.index.IndexWriterConfig
//import org.apache.lucene.index.Term;
//import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.util.Version

object Untangle extends App {
	println("XML story start")
	val filePath = "/Users/yan/pajamas/data/eugene.osm"
	val poiNodes = OsmUtil.getPoi(filePath)

	println(poiNodes.size)

	val analyzer = new StandardAnalyzer()
	val iwc = new IndexWriterConfig(Version.LATEST, analyzer)
	iwc.setOpenMode(OpenMode.CREATE)
	val indexPath = "/tmp/eugene-index"
	val dir = FSDirectory.open(new File(indexPath))
	val writer = new IndexWriter(dir, iwc)

	poiNodes foreach { doc =>
		writer.addDocument(nodeToDoc(doc))
	}


	writer.close()

	println("Indexing Done")

	def nodeToDoc(node: OsmNode): Document = {

		val doc = new Document()

		val idField = new LongField("id", node.id, Field.Store.YES)
		doc.add(idField)
		node.attributes.get("name") foreach {text =>
			val contentField = new TextField("contents", text, Field.Store.NO)
			doc.add(contentField)
		}

		doc

	}

}
