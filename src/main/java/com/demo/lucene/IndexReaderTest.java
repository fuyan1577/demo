package com.demo.lucene;

import org.apache.lucene.index.*;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by ivan on 2015/8/4.
 */
public class IndexReaderTest {
    public static void main(String args[]) throws Exception {
        File file = new File("d:/comment/commentIndex");
        //File file = new File("d:/comment/index");

        MMapDirectory directory = new MMapDirectory(file.toPath());
        IndexReader indexReader = DirectoryReader.open(directory);
        List<LeafReaderContext> leafReaderContexts =  indexReader.leaves();
        System.out.println("document count: " + indexReader.getDocCount("content"));

        File termIDF = new File("d:/comment/termIDF.txt");
        //File termIDF = new File("d:/comment/testIDF.txt");

        Map<String, Double> idfMap = new HashMap<String, Double>();
        BufferedWriter writer = new BufferedWriter(new FileWriter(termIDF));
        try {
            for(LeafReaderContext context: leafReaderContexts) {
                LeafReader reader = context.reader();
                Terms terms = reader.terms("content");
                TermsEnum tn = terms.iterator();
                BytesRef text;

                int docCount = terms.getDocCount();
                while((text = tn.next()) != null) {
                    String key  = text.utf8ToString();


                    Double idfVal = idfMap.get(key);
                    idfVal = (idfVal != null? idfVal: 0.0);
                    idfVal = idfVal + Math.log((double)docCount/tn.docFreq());
                    idfMap.put(key, idfVal);

                    writer.newLine();
                    writer.write(text.utf8ToString() + " " + Math.log((double)docCount/tn.docFreq()));
                    //System.out.println(text.utf8ToString() + ": " +Math.log((double)docCount/tn.docFreq()));
                }

            }
        } finally {
            writer.close();
        }

        List<TermIDF> keywords = new ArrayList<TermIDF>();
        for(String key: idfMap.keySet()) {
            TermIDF termIDF1 = new TermIDF();
            termIDF1.setWord(key);
            termIDF1.setIdfVal(idfMap.get(key));
            keywords.add(termIDF1);
        }
        Collections.sort(keywords, new Comparator<TermIDF>() {
            @Override
            public int compare(TermIDF termIDF1, TermIDF termIDF2) {
                return termIDF1.getIdfVal().compareTo(termIDF2.getIdfVal());
            }
        });


        File file1 = new File("d:/comment/sortedTermIDF.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file1));
        for(TermIDF idf: keywords) {
            bufferedWriter.write(idf.getWord() + " " + idf.getIdfVal());
            bufferedWriter.newLine();
        }
    }
}
