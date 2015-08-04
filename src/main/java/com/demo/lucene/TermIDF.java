package com.demo.lucene;

/**
 * Created by ivan on 2015/8/4.
 */
public class TermIDF {
    private String word;

    private Double idfVal;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Double getIdfVal() {
        return idfVal;
    }

    public void setIdfVal(Double idfVal) {
        this.idfVal = idfVal;
    }
}
