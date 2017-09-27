package cn.sfck.tokenizer;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Stanford {
    public static void main(String[] args) {
        Stanford stanford = new Stanford();
        stanford.tokenizerChinese();
        stanford.tokenizerEnglish();
    }

    private String getTokenizerString(StanfordCoreNLP pipeline, String text) {
        return this.getTokenizerString(pipeline, text, " ");
    }

    private String getTokenizerString(StanfordCoreNLP pipeline, String text, String spliter) {
      Annotation document = new Annotation(text);
      pipeline.annotate(document);
      List<String> sentenceList = new ArrayList<String>();
      List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
      for(CoreMap sentence: sentences) {
          for(CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
              String word = token.get(CoreAnnotations.TextAnnotation.class);
              sentenceList.add(word);
          }
      }
      return StringUtils.join(sentenceList, spliter);
      
    }

    public void tokenizerEnglish() {
        Properties props = new Properties();
//        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse,natlog");
        props.setProperty("annotators", "tokenize, ssplit");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        String filename = "";
        File input = new File("");
        File output = new File("");
        try(BufferedReader reader = Files.newReader(input, Charsets.UTF_8)) {
            BufferedWriter writer = Files.newWriter(output, Charsets.UTF_8);
          while(true) {
            String text = reader.readLine();
            if (text == null) {
              break;
            }
            writer.write(this.getTokenizerString(pipeline, text));
          }
          reader.close();
          writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
