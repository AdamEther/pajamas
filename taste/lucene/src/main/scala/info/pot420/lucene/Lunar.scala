package info.pot420.lucene


//import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
//import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField
import org.apache.lucene.document.TextField
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig.OpenMode
import org.apache.lucene.index.IndexWriterConfig
//import org.apache.lucene.index.Term;
//import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.util.Version
import info.pot420.jsoup.Util

import java.io.File


object Lunar extends App {
	val version = Version.LATEST
	println("Lunatic")

	val analyzer = new StandardAnalyzer()
	val iwc = new IndexWriterConfig(Version.LATEST, analyzer)
	iwc.setOpenMode(OpenMode.CREATE)
	val indexPath = "/tmp/lucene-index"
	val dir = FSDirectory.open(new File(indexPath))
	val writer = new IndexWriter(dir, iwc)

	val urls = Seq(
		"http://en.wikipedia.org/wiki/Eugene,_Oregon",
		"https://en.wikipedia.org/wiki/Anyang"
	) foreach { doc =>
		writer.addDocument(urlToDoc(doc))
	}


	writer.close()

	println("Indexing Done")

	def urlToDoc(url: String): Document = {
		val text = Util.getText(url)

		val doc = new Document()

		val pathField = new StringField("path", url, Field.Store.YES)
		doc.add(pathField)
		val contentField = new TextField("contents", text, Field.Store.NO)
		doc.add(contentField)

		doc

	}
}