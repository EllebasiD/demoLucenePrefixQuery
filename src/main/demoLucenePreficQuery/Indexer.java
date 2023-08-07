package main.demoLucenePreficQuery;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
/**
 * This class creates an indexer.
 * 
 * @author Isabelle Deligniere
 *
 */
public class Indexer {
	// An index writer creates and maintains an index. 
	private IndexWriter writer;
	// ======================================
    // =            Constructor             =
    // ====================================== 
	public Indexer(String indexDirectoryPath) throws IOException {
		Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
	    StandardAnalyzer analyzer = new StandardAnalyzer();
	    IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
	    writer = new IndexWriter(indexDirectory, iwc);
	}
	/**
	 * This method closes all open resources and releases the write lock.
	 * 
	 * @throws CorruptIndexException	This exception is thrown when Lucene detects an inconsistency in the index.
	 * @throws IOException	This exception is thrown for signaling run-time failure of reading and writing operations.
	 */
	
	public void close() throws CorruptIndexException, IOException {
		writer.deleteAll();
		System.out.println("Index deleted");
		writer.close();
	}
	/**
	 * This method gets a document.
	 * 
	 * @param file	The file to get.
	 * @return document	The wanted document.
	 * @throws IOException	This exception is thrown for signaling run-time failure of reading and writing operations.
	 */
	private Document getDocument(File file) throws IOException {
		Document document = new Document();
	    TextField contentField = new TextField(LuceneConstants.CONTENTS, new FileReader(file));
	    TextField fileNameField = new TextField(LuceneConstants.FILE_NAME, file.getName(),TextField.Store.YES);
	    TextField filePathField = new TextField(LuceneConstants.FILE_PATH, file.getCanonicalPath(),TextField.Store.YES);
	    document.add(contentField);
	    document.add(fileNameField);
	    document.add(filePathField);
	    
	    return document;
	}   
	/**
	 * This method indexes a file. 
	 * 
	 * @param file	The file.
	 * @throws IOException	This exception is thrown for signaling run-time failure of reading and writing operations.
	 */
	 private void indexFile(File file) throws IOException {
		 System.out.println("Indexing "+file.getCanonicalPath());
	     Document document = getDocument(file);
	     writer.addDocument(document);
	 }
	 /**
	  * This method returns the number of documents currently buffered in RAM.
	  *   
	  * @param dataDirPath	The path direction of the data.
	  * @param filter	The filter.
	  * @return the number of documents currently buffered in RAM.
	  * @throws IOException	This exception is thrown for signaling run-time failure of reading and writing operations.		
	  */
	 public int createIndex(String dataDirPath, FileFilter filter) throws IOException {
		 File[] files = new File(dataDirPath).listFiles();
	    for (File file : files) {
	    	if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file)){
	    		indexFile(file);
	        }
	    }
	    return writer.numRamDocs();
	 }

}
