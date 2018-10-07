package cn.sfck.examples;

import edu.stanford.nlp.simple.Sentence;

import java.util.List;

public class SimpleCoreNLPExamples {
    public static void main(String[] args) {
        Sentence sent = new Sentence("Lucy is in the sky with diamonds.");
        sent = new Sentence("This is an apple");
        List<String> nerTags = sent.nerTags();
        for(String nerTag: nerTags) {
            System.out.print(nerTag + "\t");
        }
        System.out.println();
    }
}
