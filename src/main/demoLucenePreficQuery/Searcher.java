package main.demoLucenePreficQuery;

import java.io.IOException;
import java.nio.file.Path;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


/**
 * This class builds a search function.
 * 
 * @author Isabelle Deligniere
 *
 */
public class Searcher {
   // A searcher searching the provided index.
   IndexSearcher indexSearcher;
   // A reader for the provided index.
   IndexReader indexReader;
   // A query parser.
   QueryParser queryParser;
   // A query
   Query query;
   // ======================================
   // =            Constructor             =
   // ======================================
   public Searcher(String indexDirectoryPath) throws IOException {
      Directory indexDirectory = FSDirectory.open(Path.of(indexDirectoryPath));
      indexReader = DirectoryReader.open(indexDirectory);
      indexSearcher = new IndexSearcher(indexReader);
      queryParser = new QueryParser(LuceneConstants.CONTENTS, new StandardAnalyzer());
   }
   /**
    * This method search a query from a String type.
    * 
    * @param searchQuery	The searched query.
    * @return The hits returned by IndexSearcher.search(Query,int).
    * @throws IOException	This exception is thrown for signaling run-time failure of reading and writing operations.
    * @throws ParseException	This exception is thrown when parse errors are encountered.
    */
   public TopDocs search(String searchQuery) throws IOException, ParseException {
	   query = queryParser.parse(searchQuery);
	   return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
   }
   /**
    * This method search a query from a Query type.
    * 
    * @param query	The query.
    * @return The hits returned by IndexSearcher.search(Query,int).
    * @throws IOException	This exception is thrown for signaling run-time failure of reading and writing operations.
    * @throws ParseException	This exception is thrown when parse errors are encountered.
    */
   public TopDocs search(Query query) throws IOException, ParseException {
	   return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
   }
   /**
    * This method gets the document stored fields.
    * 
    * @param scoreDoc	 One hit in TopDocs.
    * @return The stored fields.	
    * @throws CorruptIndexException	This exception is thrown when Lucene detects an inconsistency in the index.
    * @throws IOException	This exception is thrown for signaling run-time failure of reading and writing operations.
    */
   public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
	   return indexSearcher.storedFields().document(scoreDoc.doc);
   }
   /**
    * This method closes the index searcher.
    * 
    * @throws IOException	This exception is thrown for signaling run-time failure of reading and writing operations.
    */
   public void close() throws IOException {
      indexReader.close();
   }
}
