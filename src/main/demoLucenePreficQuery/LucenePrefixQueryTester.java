package main.demoLucenePreficQuery;

import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

/**
 * This class performs a specific prefix query.
 * 
 * @author Isabelle Deligniere
 *
 */
public class LucenePrefixQueryTester {
	String indexDir = ".\\src\\main\\resources\\static\\index";
	String dataDir = ".\\src\\main\\resources\\static\\files";
	Indexer indexer;
	Searcher searcher;

	public static void main(String[] args) {
	      LucenePrefixQueryTester tester;
	      try {
	    	 System.out.println("First search with 'cond' as prefix:"); 
	         tester = new LucenePrefixQueryTester();
	         tester.createIndex();
	         tester.searchUsingPrefixQuery("cond");
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (ParseException e) {
	         e.printStackTrace();
	      }
	      System.out.println("");
	      try {
	    	  System.out.println("Second search with 'semi' as prefix:");
	    	  tester = new LucenePrefixQueryTester();
	    	  tester.createIndex();
	    	  tester.searchUsingPrefixQuery("semi");
		  } catch (IOException e) {
			  e.printStackTrace();
		  } catch (ParseException e) {
			  e.printStackTrace();
		  }
	      System.out.println("");
	      try {
	    	  System.out.println("Third search with 'luc' as prefix:");
	    	  tester = new LucenePrefixQueryTester();
	    	  tester.createIndex();
	    	  tester.searchUsingPrefixQuery("luc");
		  } catch (IOException e) {
			  e.printStackTrace();
		  } catch (ParseException e) {
			  e.printStackTrace();
		  }
	   }
	/**
	 * This method creates an index.
	 * 
	 * @throws IOException	This exception is thrown for signaling run-time failure of reading and writing operations.
	 */
	private void createIndex() throws IOException {
	      indexer = new Indexer(indexDir);
	      int numIndexed;
	      long startTime = System.currentTimeMillis();	
	      numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
	      long endTime = System.currentTimeMillis();
	      indexer.close();
	      System.out.println(numIndexed+" File indexed, time taken: "
	         +(endTime-startTime)+" ms");		
	}
	/**
	 * This method searches from a prefix query.
	 * 
	 * @param searchPrefixQuery	The searched prefix query. 
	 * @throws IOException	This exception is thrown for signaling run-time failure of reading and writing operations.
	 * @throws ParseException	This exception is thrown when parse errors are encountered.
	 */
	private void searchUsingPrefixQuery(String searchPrefixQuery) throws IOException, ParseException {
		searcher = new Searcher(indexDir);
		long startTime = System.currentTimeMillis();
			   
		//create a term to search file name
		Term term = new Term(LuceneConstants.FILE_NAME, searchPrefixQuery);
		//create the term query object
		Query query = new PrefixQuery(term);
		//do the search
		TopDocs hits = searcher.search(query);
		long endTime = System.currentTimeMillis();

		System.out.println(hits.totalHits + " documents found. Time :" + (endTime - startTime) + "ms");
		for(ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = searcher.getDocument(scoreDoc);
			System.out.println("File: "+ doc.get(LuceneConstants.FILE_PATH));
		}
		searcher.close();  
	   }

}
