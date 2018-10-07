package cn.sfck.examples;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ChineseExamples {
    public static void main(String[] args) throws IOException {
        String text = "克林顿说，华盛顿将逐步落实对韩国的经济援助。"
                + "金大中对克林顿的讲话报以掌声：克林顿总统在会谈中重申，他坚定地支持韩国摆脱经济危机。";
        text = "你收到消息，给个回复，无论你在哪里，马上来见你，接你回家。";
        text = "张主任，今晚您这边几位？";
        text = "足您临时资金需求，10万起，今年以来年化表现4.22%。  理财经理：阮金玲（13111111111）";
        Annotation document = new Annotation(text);
        Properties props = new Properties();
        props.load(IOUtils.readerFromString("StanfordCoreNLP-chinese.properties"));
        StanfordCoreNLP corenlp = new StanfordCoreNLP(props);
        corenlp.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                System.out.println(word + "\t" + pos + "\t" + ne + "\t");
            }
        }
    }
}
