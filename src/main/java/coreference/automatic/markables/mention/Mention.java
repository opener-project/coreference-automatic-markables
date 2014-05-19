package coreference.automatic.markables.mention;

import ixa.kaflib.Span;
import ixa.kaflib.Term;

public class Mention {

  public Mention() {

  }

  public Mention(int id, int startIndex, int endIndex) {
    this.id = id;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  public Mention(int id, int startIndex, int endIndex, Span<Term> mentionSpan,
      Term headTerm) {
    this.id = id;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
    this.originalSpan = mentionSpan;
    this.headTerm = headTerm;
  }

  // mention fields

  public int id = -1;
  public int startIndex;
  public int endIndex;
  public Span<Term> originalSpan;
  public Term headTerm;

  // attributes
  public boolean bare = false; // bare plurals, generic pronoun or noun
  public String neType; // type mention headTerm when mention is Named Entity

  public boolean insideIn(Mention mention) {
    return (mention.startIndex <= this.startIndex && this.endIndex <= mention.endIndex);
  }
}
