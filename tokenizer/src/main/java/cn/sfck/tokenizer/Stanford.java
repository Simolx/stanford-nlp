package cn.sfck.tokenizer;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;

public class Stanford {
    public static void main(String[] args) {
        Stanford stanford = new Stanford();
        stanford.tokenizerChinese();
        stanford.tokenizerEnglist();
    }
    public void tokenizerEnglist() {
        Properties props = new Properties();
//        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse,natlog");
        props.setProperty("annotators", "tokenize, ssplit");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        String text = "All that remains for me to do is to say good-bye.";
        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            for(CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                System.out.println(word);
            }
            System.out.println("======================");
        }
    }
    public void tokenizerChinese() {
        Properties pros = new Properties();
        pros.setProperty("annotators", "tokenize, ssplit");
        pros.setProperty("tokenize.language", "zh");
        pros.setProperty("segment.model", "edu/stanford/nlp/models/segmenter/chinese/ctb.gz");
        pros.setProperty("segment.sighanCorporaDict", "edu/stanford/nlp/models/segmenter/chinese");
        pros.setProperty("segment.serDictionary", "edu/stanford/nlp/models/segmenter/chinese/dict-chris6.ser.gz");
        pros.setProperty("segment.sighanPostProcessing", "true");
        pros.setProperty("ssplit.boundaryTokenRegex", "[.。]|[!?！？]+");
        StanfordCoreNLP corenlp = new StanfordCoreNLP(pros);
        String text = "克林顿说，华盛顿将逐步落实对韩国的经济援助。金大中对克林顿的讲话报以掌声：克林顿总统在会谈中重申，他坚定地支持韩国摆脱经济危机。";
        Annotation document = new Annotation(text);
        corenlp.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            for(CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                System.out.println(word);
            }
            System.out.println("======================");
        }


    }
}
