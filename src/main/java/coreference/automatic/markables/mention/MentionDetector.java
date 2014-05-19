package coreference.automatic.markables.mention;

import ixa.kaflib.Entity;
import ixa.kaflib.KAFDocument;
import ixa.kaflib.NonTerminal;
import ixa.kaflib.Term;
import ixa.kaflib.Terminal;
import ixa.kaflib.Tree;
import ixa.kaflib.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MentionDetector {

  public static boolean DEBUG = false;

  protected boolean assignIds = true;
  protected int maxID = -1;

  public MentionDetector() {

  }

  public List<List<Mention>> getDetectedMentions(KAFDocument kaf,
      Resources resources) {

    List<List<Mention>> detectedMentions = new ArrayList<List<Mention>>();
    List<Mention> mentions = new ArrayList<Mention>();
    Set<IntPair> mentionSpanSet = new HashSet<IntPair>();
    Set<IntPair> neSpanSet = new HashSet<IntPair>();

    getNamedEntityMentions(kaf, mentions, mentionSpanSet, neSpanSet);
    getPronounMentions(kaf, mentions, mentionSpanSet, neSpanSet);
    getNounPhraseMentions(kaf, mentions, mentionSpanSet, neSpanSet);

    setBarePlural(mentions);
    filterDetectedMentions(mentions, resources);
    detectedMentions.add(mentions);

    return detectedMentions;
  }

  // TODO get headTerm of NE mentions using getNounPhrasesHeadTerm
  protected static void getNamedEntityMentions(KAFDocument kaf,
      List<Mention> mentions, Set<IntPair> mentionSpanSet,
      Set<IntPair> neSpanSet) {

    List<Entity> entityList = kaf.getEntities();
    for (Entity ne : entityList) {
      if (!ne.getType().equalsIgnoreCase(Resources.QUANTITY)
          || !ne.getType().equalsIgnoreCase(Resources.CARDINAL)
          || !ne.getType().equalsIgnoreCase(Resources.PERCENT)) {

        List<Term> neTerms = ne.getTerms();
        int startSpan = Integer.parseInt(neTerms.get(0).getId().substring(1));
        int endSpan = Integer.parseInt(neTerms.get(neTerms.size() - 1).getId()
            .substring(1));
        IntPair mentionSpan = new IntPair(startSpan, endSpan);

        if (startSpan < endSpan && !mentionSpanSet.contains(mentionSpan)) {
          mentionSpanSet.add(mentionSpan);
          neSpanSet.add(mentionSpan);
          Mention entityMention = new Mention(1, startSpan, endSpan,
              KAFDocument.newTermSpan(neTerms), neTerms.get(neTerms.size() - 1));
          mentions.add(entityMention);
          entityMention.neType = ne.getType().toUpperCase();
        }
      }
    }
  }

  // TODO get headTerm of Pronoun mentions using getNounPhrasesHeadTerm
  protected static void getPronounMentions(KAFDocument kaf,
      List<Mention> mentions, Set<IntPair> mentionSpanSet,
      Set<IntPair> neSpanSet) {
    List<Term> termList = kaf.getTerms();
    for (Term term : termList) {
      if (term.getMorphofeat().matches(Resources.PRONOUN)) {
        int startSpan = Integer.parseInt(term.getId().substring(1));
        int endSpan = Integer.parseInt(term.getId().substring(1));
        IntPair mentionSpan = new IntPair(startSpan, endSpan);

        if (!mentionSpanSet.contains(mentionSpan)
            && !isInsideNE(mentionSpan, neSpanSet)) {
          List<Term> termSpan = Arrays.asList(term);
          mentionSpanSet.add(mentionSpan);
          Mention pronounMention = new Mention(1, startSpan, endSpan,
              KAFDocument.newTermSpan(termSpan), termSpan.get(0));
          mentions.add(pronounMention);
          pronounMention.neType = "0";
        }
      }
    }
  }

  /** Check whether a mention is inside of a named entity */
  private static boolean isInsideNE(IntPair mSpan,
      Set<IntPair> namedEntitySpanSet) {
    for (IntPair span : namedEntitySpanSet) {
      if (span.get(0) <= mSpan.get(0) && mSpan.get(1) <= span.get(1))
        return true;
    }
    return false;
  }

  protected static void getNounPhraseMentions(KAFDocument kaf,
      List<Mention> mentions, Set<IntPair> mentionSpanSet,
      Set<IntPair> neSpanSet) {
    List<TreeNode> nps = getNounPhraseConstituents(kaf);
    for (TreeNode np : nps) {
      if (DEBUG) {
        System.err.println("visiting node " + np.getId());
      }
      List<Term> targetList = new ArrayList<Term>();
      List<Term> headTerm = new ArrayList<Term>();
      getNounPhraseTargets(np, targetList);
      getNounPhraseHeadTerm(np, headTerm);

      int startSpan = Integer.parseInt(targetList.get(0).getId().substring(1));
      int endSpan = Integer.parseInt(targetList.get(targetList.size() - 1)
          .getId().substring(1));
      IntPair mentionSpan = new IntPair(startSpan, endSpan);

      if (!mentionSpanSet.contains(mentionSpan)
          && !isInsideNE(mentionSpan, neSpanSet)) {
        mentionSpanSet.add(mentionSpan);
        Mention npMention;
        try { 
          npMention = new Mention(1, startSpan, endSpan,
              KAFDocument.newTermSpan(targetList), headTerm.get(0));
        }
        catch (IndexOutOfBoundsException headId) { 
          System.err.println("No headword annotation in syntactic trees.");
          System.err.println("Performing coreference resolution without headWords will negatively affect performance!");
          npMention = new Mention(1, startSpan, endSpan,KAFDocument.newTermSpan(targetList),KAFDocument.newTermSpan(targetList).getFirstTarget());
        }
        mentions.add(npMention);
        npMention.neType = "O";
      }
    }
  }

  private static void getNounPhraseHeadTerm(TreeNode np, List<Term> headId) {
    if (!np.isTerminal()) {
      for (TreeNode npChild : np.getChildren()) {
        if (npChild.getHead() && npChild.getChildren().size() > 1) {
          getNounPhraseHeadTerm(npChild, headId);
        } 
        else if (npChild.getHead() && npChild.getChildren().size() == 1) {
          if (npChild.getChildren().get(0).isTerminal()) { 
            Term headTerm = ((Terminal) npChild.getChildren().get(0)).getSpan()
                .getFirstTarget();
            headId.add(headTerm);
            if (DEBUG) {
              System.err.println("headTerm of " + npChild.getId() + " is "
                  + headTerm.getForm());
            }
          }
          else { 
            getNounPhraseHeadTerm(npChild, headId);
          }
        }
      }
    }
  }

  private static void getNounPhraseTargets(TreeNode constituent,
      List<Term> targetList) {

    if (constituent.isTerminal()) {
      targetList.add(((Terminal) constituent).getSpan().getFirstTarget());
      if (DEBUG) {
        System.err.println("target added!");
      }

    } else {
      for (TreeNode constituentChild : constituent.getChildren()) {
        if (DEBUG) {
          System.err.println("seeing " + constituentChild.getId());
          System.err.println("go to next child");
        }
        getNounPhraseTargets(constituentChild, targetList);
      }
    }
  }

  public static List<TreeNode> getNounPhraseConstituents(KAFDocument kaf) {
    List<TreeNode> nps = new ArrayList<TreeNode>();
    List<Tree> trees = kaf.getConstituents();
    for (Tree tree : trees) {
      TreeNode treeRoot = tree.getRoot();
      processNounPhraseConstituents(treeRoot, nps);
    }
    return nps;
  }

  private static void processNounPhraseConstituents(TreeNode troot,
      List<TreeNode> nps) {
    if (troot instanceof NonTerminal) {
      if (((NonTerminal) troot).getLabel().matches(Resources.NOUN_PHRASE)) {
        nps.add(troot);
        if (DEBUG) {
          System.err.println(troot.getId() + ":: "
              + ((NonTerminal) troot).getLabel());
        }
      }
      for (TreeNode trootChild : troot.getChildren()) {
        processNounPhraseConstituents(trootChild, nps);
      }
    }
  }

  protected static void setBarePlural(List<Mention> mentions) {
    for (Mention mention : mentions) {
      String pos = mention.headTerm.getMorphofeat();
      if (mention.originalSpan.size() == 1 && pos.matches(Resources.PLURAL_COMMON_NOUN)) {
        mention.bare = true;
      }
    }
  }

  // TODO filter out pleonastic it
  protected static void filterDetectedMentions(List<Mention> mentions,
      Resources resources) {
    Set<Mention> filteredMentions = new HashSet<Mention>();

    for (Mention mention : mentions) {
      String headTermPOS = mention.headTerm.getMorphofeat();
      String headTermNE = mention.neType;

      // meaningless words
      if (resources.nonWords.contains(mention.headTerm.getForm().toLowerCase())) {
        filteredMentions.add(mention);
      }
      //TODO
      // mentions starting with words such as any, all
      if (resources.quantifiers.contains(mention.originalSpan.getFirstTarget()
          .getForm().toLowerCase())) {
        //filteredMentions.add(mention);
      }
      // mentions containing partitives followed by preposition "of"
      if (isPartitive(mention, resources)) {
        filteredMentions.add(mention);
      }
      // bareNP mentions: two rules one out;
      // this rule will also remove common mistakes such as with
      // "english speaking room boy"
      if (headTermPOS.matches(Resources.SINGULAR_COMMON_NOUN)
          && !resources.temporals.contains(mention.headTerm.getForm()
              .toLowerCase())
          && (mention.originalSpan.size() == 1 || mention.originalSpan
              .getFirstTarget().getMorphofeat().matches(Resources.SINGULAR_ADJECTIVE))) {
        filteredMentions.add(mention);
      }
      /*
       * if (mention.bare) { filteredMentions.add(mention); }
       */
      // remove PERCENTS and NUMERICAL EXPRESSIONS
      if (mention.headTerm.getForm().equals("%")
          || mention.headTerm.getMorphofeat().matches(Resources.CARDINAL_NUMBER)) {
        filteredMentions.add(mention);
      }
      if (headTermNE.equals(Resources.PERCENT) || headTermNE.equals(Resources.MONEY)) {
        filteredMentions.add(mention);
      }
      // remove adjective form of nations
      // TODO

      // remove one word mentions
      if (mention.originalSpan.size() == 1
          && resources.oneWordMentions.contains(mention.originalSpan
              .getFirstTarget().getForm().toLowerCase())) {
        filteredMentions.add(mention);
      }
      // remove mention if ends with "etc."
      if (mention.originalSpan.getTargets()
          .get(mention.originalSpan.getTargets().size() - 1).getForm()
          .equals("etc.")) {
        filteredMentions.add(mention);
      }
      // remove mentions with same headword if one is a subset of the other and
      // except appositives and enumerations
      for (Mention mention1 : mentions) {
        for (Mention mention2 : mentions) {
          if (mention1 == mention2 || filteredMentions.contains(mention1)
              || filteredMentions.contains(mention2)) {
            continue;
          }
          if (mention1.headTerm.getId().equals(mention2.headTerm.getId())
              && mention2.insideIn(mention1)) {
            if ((mention1.originalSpan.getTargets()
                .get(mention2.originalSpan.getTargets().size()).getForm()
                .equals(",")
                || mention2.originalSpan.getTargets()
                    .get(mention2.originalSpan.getTargets().size() - 1)
                    .getForm().equals(",")
                || mention1.originalSpan.getTargets()
                    .get(mention2.originalSpan.getTargets().size())
                    .getMorphofeat().matches(Resources.COORD) || mention2.originalSpan
                .getTargets()
                .get(mention2.originalSpan.getTargets().size() - 1)
                .getMorphofeat().matches(Resources.COORD))) {
              continue;
            }
            filteredMentions.add(mention2);
          }
        }
      }
    }
    mentions.removeAll(filteredMentions);
  }

  private static boolean isPartitive(Mention mention, Resources resources) {
    return (mention.originalSpan.size() >= 2
        && mention.originalSpan.getTargets().get(1).getForm().matches(Resources.PREPOSITION) && resources.partitives
          .contains(mention.originalSpan.getFirstTarget().getForm()
              .toLowerCase()));
  }

}
