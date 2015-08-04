package com.demo.lucene;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Created by ivan on 2015/8/4.
 */
public class Test {
    public static void main(String args[]) throws  Exception {
        StandardAnalyzer englishAnalyzer = new StandardAnalyzer();
        System.out.println("analyzer version is: " + englishAnalyzer.getVersion());
        TokenStream tokenStream = englishAnalyzer.tokenStream("test123", "this is a test, fuck you. Did you study math now? Do you want to go swimming. Yes, I want to swim. I went to school yesterday.");
        //TokenStream filterStream = new EnglishMinimalStemFilter(tokenStream);
        tokenStream.reset();
        while(tokenStream.incrementToken()) {
            CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
            System.out.println("attribute is: " + attribute.toString());
        }




       /* FieldType fieldType = new FieldType();
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
        fieldType.setStored(false);
        fieldType.setTokenized(true);
        fieldType.setStoreTermVectors(true);

        Document document1 = new Document();
        document1.add(new Field("content", "This is a test case, do you want to go swim today? No, I don't want to go swim today.",fieldType));

        Document document2 = new Document();
        document2.add(new Field("content", "I want to go swim today", fieldType));

        IndexWriterConfig config = new IndexWriterConfig(englishAnalyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        RAMDirectory ramDirectory = new RAMDirectory();
        IndexWriter writer = new IndexWriter(ramDirectory, config);
        writer.addDocument(document1);
        writer.addDocument(document2);
        writer.commit();


        IndexReader indexReader = DirectoryReader.  open(ramDirectory);


        List<LeafReaderContext> leafReaderContexts =  indexReader.leaves();
        System.out.println("leafReaderContext count: " + leafReaderContexts.size());

        System.out.println("document count: " + indexReader.getDocCount("content"));
        for(int i = 0; i < indexReader.getDocCount("content"); i++) {
            Document document = indexReader.document(i);
            System.out.println("document: " + document.getFields());
        }

        for(LeafReaderContext context: leafReaderContexts) {
            LeafReader reader = context.reader();
            Terms terms = reader.terms("content");
            TermsEnum tn = terms.iterator();
            BytesRef text;

            while((text = tn.next()) != null) {
                System.out.println(text.utf8ToString() + ": doc freq: " + tn.docFreq() + ", total term freq:" + tn.totalTermFreq());
            }

            System.out.println(terms.getDocCount());

            System.out.println("term frequency perm document: "+ terms.hasFreqs());
            System.out.println(terms);
         }*/

    }
}
