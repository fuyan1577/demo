package com.demo.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.MMapDirectory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by ivan on 2015/8/4.
 */
public class IndexCreator {
    public static void main(String args[]) throws  Exception {
        StandardAnalyzer analyzer = new StandardAnalyzer();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        //File file = new File("d:/comment/commentIndex");
        File file = new File("d:/comment/index");
        MMapDirectory directory = new MMapDirectory(file.toPath());
        IndexWriter writer = new IndexWriter(directory, config);

        FieldType fieldType = new FieldType();
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
        fieldType.setStored(false);
        fieldType.setTokenized(true);
        fieldType.setStoreTermVectors(true);

        //BufferedReader bufferedReader = new BufferedReader(new FileReader("d:/comment/2345.txt"));
        BufferedReader bufferedReader = new BufferedReader(new FileReader("d:/comment/TEST.txt"));

        String str = null;
        while((str = bufferedReader.readLine()) != null) {
            Document document = new Document();
            document.add(new Field("content", str, fieldType));
            writer.addDocument(document);
        }

        writer.close();
    }
}
