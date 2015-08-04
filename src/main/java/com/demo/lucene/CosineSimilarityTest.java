package com.demo.lucene;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.*;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by ivan on 2015/8/4.
 */
public class CosineSimilarityTest {
    private static final String INDEX_FIELD_NAME = "content";

    private Map<String, Double> getIdfVals() throws IOException {
        File file = new File("d:/comment/commentIndex");
        // File file = new File("d:/comment/index");

        MMapDirectory directory = new MMapDirectory(file.toPath());
        IndexReader indexReader = DirectoryReader.open(directory);
        List<LeafReaderContext> leafReaderContexts = indexReader.leaves();
        System.out.println("document count: " + indexReader.getDocCount("content"));

        File termIDF = new File("d:/comment/termIDF.txt");
        // File termIDF = new File("d:/comment/testIDF.txt");

        Map<String, Double> idfMap = new HashMap<String, Double>();
        for (LeafReaderContext context : leafReaderContexts) {
            LeafReader reader = context.reader();
            Terms terms = reader.terms("content");
            TermsEnum tn = terms.iterator();
            BytesRef text;

            int docCount = terms.getDocCount();
            while ((text = tn.next()) != null) {
                String key = text.utf8ToString();

                Double idfVal = idfMap.get(key);
                idfVal = (idfVal != null ? idfVal : 0.0);
                idfVal = idfVal + Math.log((double) docCount / tn.docFreq());
                idfMap.put(key, idfVal);
            }
        }
        return idfMap;
    }

    private Collection<String> getWords(String input) throws  Exception{
        EnglishAnalyzer analyzer = new EnglishAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream(INDEX_FIELD_NAME, input);
        tokenStream.reset();

        List result = new ArrayList();
        while(tokenStream.incrementToken()) {
            CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
            result.add(attribute.toString());
        }
        return result;
    }

    public static void main(String args[]) throws Exception {
        CosineSimilarityTest test = new CosineSimilarityTest();
        Map<String, Double> idfVals = test.getIdfVals();

        CosineSimilarity cosineSimilarity = new CosineSimilarity(CosineSimilarity.WeightingModeTf.FREQUENCY,
                CosineSimilarity.WeightingModeIdf.LOGPLUSONE, CosineSimilarity.NormalizationMode.L2, idfVals);

        String s1 = "Go to the hell.";
        String s2 = "Go to hell.";
       System.out.println("similarity is: " + cosineSimilarity.getSimilarity(test.getWords(s1), test.getWords(s2)));
    }
}
