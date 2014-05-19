package coreference.automatic.markables;

import ixa.kaflib.KAFDocument;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import org.jdom2.JDOMException;

/**
 * Automatically creates coreference markables to assist manual annotation of
 * gold-standard coreference corpora.
 * 
 * 
 */

public class CLI {
  /**
   * Get dynamically the version of ixa-pipe-nerc by looking at the MANIFEST
   * file.
   */
  private final String version = CLI.class.getPackage()
      .getImplementationVersion();
  /**
   * Name space of the arguments provided at the CLI.
   */
  private Namespace parsedArguments = null;
  /**
   * Argument parser instance.
   */
  private ArgumentParser argParser = ArgumentParsers.newArgumentParser(
      "coreference-automatic-markables-" + version + ".jar").description(
      "coreference-automatic-markables-" + version
          + " creates the markables for manual coreference annotation.\n");

  public CLI() {
    loadParameters();

  }

  public static void main(String[] args) throws IOException, JDOMException {

    CLI cmdLine = new CLI();
    cmdLine.parseCLI(args);
  }

  public final void parseCLI(String[] args) throws IOException {

    try {
      parsedArguments = argParser.parseArgs(args);
      System.err.println("CLI options: " + parsedArguments);
      annotate(System.in, System.out);
    } catch (ArgumentParserException e) {
      argParser.handleError(e);
      System.out.println("Run java -jar coreference-automatic-markables-"
          + version + ".jar" + "-help for details");
      System.exit(1);
    }
  }

  private void loadParameters() {
    argParser.addArgument("-l", "--lang")
        .choices("de", "en", "es", "fr", "it", "nl").required(false)
        .help("Choose a language to create automatic markables.");
  }

  public final void annotate(final InputStream inputStream,
      final OutputStream outputStream) throws IOException {

    BufferedReader breader = new BufferedReader(new InputStreamReader(
        inputStream, "UTF-8"));
    BufferedWriter bwriter = new BufferedWriter(new OutputStreamWriter(
        outputStream, "UTF-8"));
    KAFDocument kaf = KAFDocument.createFromStream(breader);
    String lang;
    if (parsedArguments.get("lang") == null) {
      lang = kaf.getLang();
    } else {
      lang = parsedArguments.getString("lang");
    }
    Annotate annotator = new Annotate(lang);
    kaf.addLinguisticProcessor("coreferences",
        "coreference-automatic-markables-" + lang, version);
    annotator.annotateSingletonsToKAF(kaf);
    bwriter.write(kaf.toString());

    breader.close();
    bwriter.close();
  }

}
