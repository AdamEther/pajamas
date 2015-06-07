package info.pot420.lucene

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


object Ask extends App {
	println("answer my question")
	val indexPath = "/tmp/lucene-index"
	val reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)))
	val searcher = new IndexSearcher(reader)
	val analyzer = new StandardAnalyzer()


	val queryString = "Anyang"
	val fieldId = "contents"
	val parser = new QueryParser(fieldId, analyzer)
	val query = parser.parse(queryString)

	val results = searcher.search(query, 5)
	val hits = results.scoreDocs

	val numTotalHits = results.totalHits
	println(numTotalHits + " total matching documents")

	val urlDoc = searcher.doc(hits(0).doc)
	println(urlDoc.get("path"))
//	val urls = hits.map { d =>
//		val hitdoc = searcher.doc(d.doc)
//		val filedId = "path"
//		hitdoc.get(fieldId)
//	}
//	urls foreach println

	reader.close()

}