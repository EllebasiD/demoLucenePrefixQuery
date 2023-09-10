package main.demoLucenePreficQuery;

import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
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
	      // Create index
	      System.out.println("Index creation :");
	      tester = new LucenePrefixQueryTester();
	      try {    	  
		      tester.createIndex();
	      }catch (IOException e){
	    	  e.printStackTrace();
	      }
	      System.out.println("");
	      System.out.println("Searches of documents by name according to their prefixes :");
	      System.out.println("");
	      // First search 
	      try {
	    	 System.out.println("- First search of document by name with 'cond' as prefix:"); 	         
	         tester.searchDocumentByNameUsingPrefixQuery("cond");
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (ParseException e) {
	         e.printStackTrace();
	      }
	      System.out.println("");
	      // Second search
	      try {
	    	  System.out.println("- Second search of document by name with 'semi' as prefix:");    	  
	    	  tester.searchDocumentByNameUsingPrefixQuery("semi");
		  } catch (IOException e) {
			  e.printStackTrace();
		  } catch (ParseException e) {
			  e.printStackTrace();
		  }
	      System.out.println("");
	      // Third search
	      try {
	    	  System.out.println("- Third search of document by name with 'luc' as prefix:");
	    	  tester.searchDocumentByNameUsingPrefixQuery("luc");
		  } catch (IOException e) {
			  e.printStackTrace();
		  } catch (ParseException e) {
			  e.printStackTrace();
		  }
	      System.out.println("");
	      // Search in Document using "gal" prefix for gallium (present in the two documents)
	      System.out.println("Searches in documents containing words with a determined prefix :");
	      System.out.println("");
	      try {
	    	  System.out.println("- First search in document with 'gal' as prefix:");
	    	  tester.searchInDocumentUsingPrefixQuery("gal");
	      } catch (IOException e) {
			  e.printStackTrace();
		  } catch (ParseException e) {
			  e.printStackTrace();
		  }
	      System.out.println("");
	      // Search in Document using "ion" prefix (only present as a suffix)
	      try {
	    	  System.out.println("- Second search in document with 'ion' as prefix:");
	    	  tester.searchInDocumentUsingPrefixQuery("ion");
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
	 * This method searches a document from a prefix query contained in its name.
	 * 
	 * @param searchPrefixQuery	The searched prefix query. 
	 * @throws IOException	This exception is thrown for signaling run-time failure of reading and writing operations.
	 * @throws ParseException	This exception is thrown when parse errors are encountered.
	 */
	private void searchDocumentByNameUsingPrefixQuery(String searchPrefixQuery) throws IOException, ParseException {
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
	/**
	 * This method searches documents containing words with a given prefix query.
	 * 
	 * @param searchPrefixQuery	The searched prefix query.
	 * @throws IOException	This exception is thrown for signaling run-time failure of reading and writing operations.
	 * @throws ParseException	This exception is thrown when parse errors are encountered.
	 */
	public void searchInDocumentUsingPrefixQuery(String searchPrefixQuery) throws IOException, ParseException {
		searcher = new Searcher(indexDir);
		long startTime = System.currentTimeMillis();
		//create a term to search in contents
		Term term = new Term(LuceneConstants.CONTENTS, searchPrefixQuery);
		//create the term query object
		Query query = new PrefixQuery(term);
		//do the search
		TopDocs hits = searcher.search(query);
		long endTime = System.currentTimeMillis();  
		System.out.println(hits.totalHits + " documents found. Time :" + (endTime - startTime));
		for(ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = searcher.getDocument(scoreDoc);
			System.out.println("File: " + doc.get(LuceneConstants.FILE_PATH));
		}
		searcher.close();
	}	
}
