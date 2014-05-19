package coreference.automatic.markables;

import ixa.kaflib.KAFDocument;
import ixa.kaflib.Span;
import ixa.kaflib.Term;

import java.util.ArrayList;
import java.util.List;

import coreference.automatic.markables.mention.Mention;
import coreference.automatic.markables.mention.MentionDetector;
import coreference.automatic.markables.mention.Resources;

public class Annotate {

  public static boolean DEBUG = false;
  Resources resources;

  public Annotate(String lang) {
    resources = new Resources(lang);

  }

  public void annotateSingletonsToKAF(KAFDocument kaf) {
    MentionDetector mentionDetector = new MentionDetector();
    List<List<Mention>> detectedMentions = mentionDetector.getDetectedMentions(
        kaf, resources);

    for (List<Mention> mentions : detectedMentions) {
      for (Mention mention : mentions) {
        List<Span<Term>> mentionList = new ArrayList<Span<Term>>();
        mentionList.add(mention.originalSpan);
        if (DEBUG) {
          System.err.println(mention.headTerm.getForm());
        }
        kaf.newCoref(mentionList);
      }
    }
  }

}
