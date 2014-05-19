package coreference.automatic.markables.mention;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Resources {

  public enum MentionType {
    PRONOMINAL, NOMINAL, PROPER
  }

  public enum Gender {
    MALE, FEMALE, NEUTRAL, UNKNOWN
  }

  public enum Number {
    SINGULAR, PLURAL, UNKNOWN
  }

  public enum Animacy {
    ANIMATE, INANIMATE, UNKNOWN
  }

  public enum Person {
    I, YOU, HE, SHE, WE, THEY, IT, UNKNOWN
  }

  public Set<String> nonWords;
  public Set<String> quantifiers;
  public Set<String> partitives;
  public Set<String> temporals;
  public Set<String> adjectiveNation;
  public Set<String> oneWordMentions;
  
  public static String QUANTITY = "QUANTITY";
  public static String PERCENT = "PERCENT";
  public static String CARDINAL = "CARDINAL";
  public static String MONEY = "MONEY";

  public static String PRONOUN;
  public static String NOUN_PHRASE;
  public static String PREPOSITION;
  public static String SINGULAR_COMMON_NOUN;
  public static String PLURAL_COMMON_NOUN;
  public static String SINGULAR_ADJECTIVE;
  public static String COORD;
  public static String CARDINAL_NUMBER;

  public Resources(String lang) {
    if (lang.equalsIgnoreCase("en")) {
      
      // tagsets
      PRONOUN = "PRP\\$?";
      NOUN_PHRASE = "NP";
      PREPOSITION = "of";
      SINGULAR_COMMON_NOUN = "NN";
      PLURAL_COMMON_NOUN = "NNS";
      SINGULAR_ADJECTIVE = "JJ";
      COORD = "CC";
      CARDINAL_NUMBER = "CD";
      
      nonWords = new HashSet<String>(Arrays.asList("mm", "hmm", "ahem", "um"));
      quantifiers = new HashSet<String>(Arrays.asList("not", "every", "any",
          "none", "everything", "anything", "nothing", "all", "enough"));
      partitives = new HashSet<String>(Arrays.asList("half", "one", "two",
          "three", "four", "five", "six", "seven", "eight", "nine", "ten",
          "hundred", "thousand", "million", "billion", "tens", "dozens",
          "hundreds", "thousands", "millions", "billions", "group", "groups",
          "bunch", "number", "numbers", "pinch", "amount", "amount", "total",
          "all", "mile", "miles", "pounds"));
      temporals = new HashSet<String>(Arrays.asList("second", "minute", "hour",
          "day", "week", "month", "year", "decade", "century", "millennium",
          "monday", "tuesday", "wednesday", "thursday", "friday", "saturday",
          "sunday", "now", "yesterday", "tomorrow", "age", "time", "era",
          "epoch", "morning", "evening", "day", "night", "noon", "afternoon",
          "semester", "trimester", "quarter", "term", "winter", "spring",
          "summer", "fall", "autumn", "season", "january", "february", "march",
          "april", "may", "june", "july", "august", "september", "october",
          "november", "december"));

      adjectiveNation = new HashSet<String>();
      oneWordMentions = new HashSet<String>(Arrays.asList("u.s.", "u.k.",
          "u.s.s.r", "there", "etc.", "ltd.", "'s"));
    }
    if (lang.equalsIgnoreCase("es")) {
      
      // tagsets
      //TODO check Ancora-CO and parole tagset as in coreference.org 
      PRONOUN = "PP.+";
      NOUN_PHRASE = "SN|GRUP\\.NOM|RELATIU";
      PREPOSITION = "de";
      SINGULAR_COMMON_NOUN = "NC.S.*";
      PLURAL_COMMON_NOUN = "NC.P.*";
      SINGULAR_ADJECTIVE = "A.S.*";
      COORD = "CS";
      CARDINAL_NUMBER = "Z.*";

      nonWords = new HashSet<String>(Arrays.asList("mm", "hmm", "ahem", "um",
          "ehem", "bah", "ejem", "hm", "psst", "ajá", "jo"));
      // cuantificadores; they overlap with indefinite_articles in Spanish
      quantifiers = new HashSet<String>(Arrays.asList("no", "nada",
          "suficientemente", "suficiente", "harto", "alguna", "algún",
          "algunas", "alguno", "algunos", "ambas", "ambos", "bastante",
          "bastantes", "cada", "cualesquier", "cualquier", "cuantas",
          "cuantos", "demás", "demasiada", "demasiadas", "demasiado",
          "demasiados", "mucha", "muchas", "mucho", "muchos", "ninguna",
          "ningunas", "ningún", "ninguno", "ningunos", "otra", "otras", "otro",
          "otros", "poca", "pocas", "poco", "pocos", "sendas", "sendos",
          "tantas", "tanta", "tantos", "tanto", "todas", "toda", "todos",
          "todo", "unas", "una", "unos", "un", "varias", "varios"));
      // partitivos (we also include here cardinales and ordinales)
      partitives = new HashSet<String>(Arrays.asList("grupo", "grupos",
          "equipo", "algunos", "cantidad", "total", "todo", "miles", "kilos",
          "kilo", "medio", "media", "cuarto", "cuarta", "quinto", "quinta",
          "sexta", "sexto", "seisava", "seisavo", "séptimo", "séptima",
          "octava", "octavo", "novena", "noveno", "décima", "décimo",
          "undécimo", "undécima", "onceavo", "onceava", "duodécima",
          "duodécimo", "doceavo", "doceava", "decimosegunda", "decimosegundo",
          "decimotercera", "decimotercero", "decimotercer", "decimatercer",
          "treceava", "treceavo", "decimocuarta", "decimocuarto", "quinceava",
          "quinceavo", "decimoquinto", "decimoquinta", "decimosexta",
          "decimosexto", "dieciseisavo", "dieciseisava", "decimoséptima",
          "decimoséptimo", "diecisieteava", "diecisieteavo", "decimooctava",
          "decimoctavo", "dieciochoavo", "dieciochoava", "decimonovena",
          "decimonoveno", "diecinueveava", "diecinueveavo", "vigésimo",
          "vigésima", "veinteava", "veinteavo", "trigésima", "trigésimo",
          "treintavo", "treintava", "cuadragésima", "cuadragésimo",
          "cuarentava", "cuarentavo", "quincuagésima", "quincuagésimo",
          "cincuentava", "cincuentavo", "sexagésima", "sexagésimo",
          "sesentavo", "sesentava", "septuagésima", "septuagésimo",
          "setentavo", "sententava", "octogésima", "octogésimo", "ochentava",
          "ochentavo", "nonagésima", "nonagésimo", "centava", "centavo",
          "centésima", "centésimo", "milimésima", " un", "uno", "dos", "tres",
          "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez", "once",
          "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete",
          "dieciocho", "diecinueve", "veinte", "veintiuno", "veintidós",
          "veintitrés", "veinticuatro", "veinticinco", "veintiséis",
          "veintisiete", "veintiocho", "veintinueve", "treinta", "cuarenta",
          "cincuenta", "sesenta", "setenta", "ochenta", "noventa", "cien",
          "doscientos", "doscientas", "trescientos", "trescientas",
          "cuatrocientas", "cuatrocientos", "quinientos", "quinientas",
          "seiscientas", "seiscientos", "ochocientas", "ochocientos",
          "novecientas", "novecientos", "mil", "millón", "millones", "billón",
          "billones"));

      temporals = new HashSet<String>(Arrays.asList("segundo", "minuto",
          "hora", "día", "semana", "mes", "año", "década", "siglo", "milenio",
          "lunes", "martes", "miércoles", "jueves", "viernes", "sábado",
          "domingo", "ahora", "ayer", "mañana", "edad", "tiempo", "era",
          "época", "noche", "mediodía", "tarde", "semestre", "trimestre",
          "cuatrimestre", "término", "invierno", "primavera", "verano",
          "otoño", "estación", "enero", "febrero", "marzo", "abril", "mayo",
          "junio", "julio", "agosto", "septiembre", "octubre", "noviembre",
          "diciembre"));

      adjectiveNation = new HashSet<String>();
      oneWordMentions = new HashSet<String>(Arrays.asList("u.s.", "u.k.",
          "u.s.s.r", "there", "etc.", "ltd.", "'s", "s.a.", "s.l."));
    }
    if (lang.equalsIgnoreCase("it")) {
      
      // tagsets
      //TODO
      PRONOUN = "PRO\\~PE|PRO\\~PO";
      NOUN_PHRASE = "NP.*";
      PREPOSITION = "di";
      SINGULAR_COMMON_NOUN = "NOU\\~CS";
      PLURAL_COMMON_NOUN = "NOU\\~CP";
      SINGULAR_ADJECTIVE = "ADJ.*";
      COORD = "CONJ";
      CARDINAL_NUMBER = "CD";
      
      nonWords = new HashSet<String>(Arrays.asList("mm", "hmm", "ahm", "uhm",
          "ehm", "ah", "eh", "oh", "uh", "ih"));
      quantifiers = new HashSet<String>(Arrays.asList("niente", "nessun",
          "nessuno", "nessuna", "tutto", "tutti", "tutte", "tutta", "alcun",
          "alcuno", "alcuna", "alcuni", "alcune", "ogni", "ognun", "ognuno",
          "ognuna", "ciascuno", "ciascuna", "abbastanza", "qualche",
          "qualunque", "qualsiasi", "poco", "pochi", "alquanto", "molti",
          "molte", "molta", "molto", "tanti", "tante", "tanta", "tanto",
          "parecchi", "parecchio", "parecchie", "parecchia", "numerosi",
          "numerose", "vari", "varie"));
      partitives = new HashSet<String>(Arrays.asList("metà", "meta'", "uno",
          "due", "tre", "quattro", "cinque", "sei", "sette", "otto", "nove",
          "dieci", "cento", "mille", "milioni", "miliardi", "milione",
          "miliardo", "decine", "decina", "dozzine", "dozzina", "centinaia",
          "centinaio", "migliaia", "migliaio", "gruppo", "gruppi", "manciata",
          "manciate", "mucchio", "mucchi", "numero", "numeri", "po'",
          "pizzico", "pugno", "pugni", "quantità", "quantita'", "totale",
          "chilometro", "chilometri", "metro", "metri", "centimetro",
          "centimetri", "millimetro", "millimetri", "km", "chilo", "chili",
          "kg", "chilogrammo", "chilogrammi", "etto", "etti", "grammo",
          "grammi"));
      temporals = new HashSet<String>(Arrays.asList("secondo", "secondi",
          "minuto", "minuti", "ora", "ore", "giorno", "giorni", "settimana",
          "settimane", "mese", "mesi", "anno", "anni", "decade", "decadi",
          "secolo", "secoli", "millennio", "millenni", "lunedì", "martedì",
          "mercoledì", "giovedì", "venerdì", "sabato", "sabati", "domenica",
          "domeniche", "adesso", "ieri", "domani", "dopodomani", "età",
          "tempo", "tempi", "periodo", "periodi", "era", "ere", "epoca",
          "epoche", "mattino", "mattini", "mattine", "sera", "sere",
          "giornata", "giornate", "notte", "notti", "mezzogiorno",
          "mezzogiorni", "pomeriggio", "pomeriggi", "semestre", "semestri",
          "trimestre", "trimestri", "quadrimestre", "quadrimestri", "semestre",
          "semestri", "inverno", "inverni", "primavera", "primavere", "estate",
          "estati", "autunno", "autunni", "stagione", "stagioni", "gennaio",
          "febbraio", "marzo", "aprile", "maggio", "giugno", "luglio",
          "agosto", "settembre", "ottobre", "novembre", "dicembre"));
      adjectiveNation = new HashSet<String>();
      oneWordMentions = new HashSet<String>(Arrays.asList("u.s.", "u.k.",
          "u.s.s.r", "there", "etc.", "ltd.", "'s", "c'è", "c'e'", "spa",
          "s.p.a.", "s.r.l.", "ecc", "l'"));
    }
    if (lang.equalsIgnoreCase("de")) {
      
      // tagsets
      PRONOUN = "PPER|PPOS";
      NOUN_PHRASE = "C?NP.*|MPN";
      PREPOSITION = "von";
      SINGULAR_COMMON_NOUN = "NN";
      PLURAL_COMMON_NOUN = "NN";
      SINGULAR_ADJECTIVE = "ADJA|ADJD";
      COORD = "KON|KOUS|KOUI|KOKOM";
      CARDINAL_NUMBER = "CD";
      
      
      nonWords = new HashSet<String>(Arrays.asList("mm", "hmm", "ahem", "um"));
      quantifiers = new HashSet<String>(Arrays.asList("nicht", "jeder", "nein", "all", "alles", "nichts", "ausreichend",
          "ausreichend", "einige", "einige", "einige", "jeder", "jeder",
          "viel", "viele", "viele", "nichts"));
      partitives = new HashSet<String>(Arrays.asList("halb", "halbe", "eins",
          "zwei", "drie", "vier", "fuenf", "sechs", "sieben", "acht",
          "neun zehn", "hundert", "tausend", "hundertausend", "millionen",
          "billion", "hunderten", "tausenden", "miljonen", "zahlen", "mengen",
          "menge", "alle", "pfund", "kilometer", "meter"));
      // TODO
      temporals = new HashSet<String>(Arrays.asList(""));
      adjectiveNation = new HashSet<String>();
      oneWordMentions = new HashSet<String>(Arrays.asList("u.s.", "u.k.",
          "u.s.s.r", "there", "etc.", "ltd.", "'s"));

    }
    if (lang.equalsIgnoreCase("nl")) {
      
      // tagsets
      PRONOUN = "pron|det";
      NOUN_PHRASE = "np|noun";
      PREPOSITION = "van";
      SINGULAR_COMMON_NOUN = "noun";
      PLURAL_COMMON_NOUN = "noun";
      SINGULAR_ADJECTIVE = "adj";
      COORD = "vg";
      CARDINAL_NUMBER = "CD";
      
      nonWords = new HashSet<String>(Arrays.asList("mm", "hmm", "ahem", "um"));
      quantifiers = new HashSet<String>(Arrays.asList("niet", "iedere", "ieder", "geen", "alle", "alles", "niets", "voldoende", "genoeg", "enkele", "enige", "sommige", "elk", "elke", "heleboel", "menig", "menige", "niks"));
      partitives = new HashSet<String>(Arrays.asList("half", "halve", "een", "twee", "drie", "vier", "vijf", "zes", "zeven", "acht", "negen", "tien" , "honderd","duizend", "miljoen", "biljoen", "tientallen",  "honderden", "duizenden", "miljoenen", "biljoenen","groep", "groepen", "aantal", "aantallen", "hoeveelheid", "hoeveelheden", "totaal", "alle", "pond", "kilo", "meter","kilometer", "kilometers", "ponden", "kilo's",  "stapels"));
      // TODO
      temporals = new HashSet<String>(Arrays.asList(""));
      adjectiveNation = new HashSet<String>();
      oneWordMentions = new HashSet<String>(Arrays.asList("u.s.", "u.k.",
          "u.s.s.r", "there", "etc.", "ltd.", "'s"));
    }
    if (lang.equalsIgnoreCase("fr")) {
      
      // mention detection tagsets
      // TODO PROms is also used for WH pronouns
      PRONOUN = "PROms";
      NOUN_PHRASE = "NP.*|WHNP";
      PREPOSITION = "de";
      SINGULAR_COMMON_NOUN = "NC";
      PLURAL_COMMON_NOUN = "NCms";
      SINGULAR_ADJECTIVE = "Ams";
      //TODO
      COORD = "";
      CARDINAL_NUMBER = "CD";
      
      nonWords = new HashSet<String>(Arrays.asList("mm", "hmm", "ahem", "um"));
      quantifiers = new HashSet<String>(Arrays.asList("pas", "chaque",
          "chacun", "chacune", "tout", "aucun", "aucune", "tous", "rien",
          "assez", "trop"));
      partitives = new HashSet<String>(Arrays.asList("half", "un", "deux",
          "trois", "quatre", "cinq", "six", "sept", "huit", "neuf", "dix",
          "cent", "mille", "million", "milliard", "dizaines", "douzaines",
          "centaines", "milliers", "millions", "milliards", "groupe",
          "groupes", "bouquet", "pincer", "montant", "totale", "tous", "mile",
          "miles", "livres"));
      // TODO
      temporals = new HashSet<String>(Arrays.asList(""));
      adjectiveNation = new HashSet<String>();
      oneWordMentions = new HashSet<String>(Arrays.asList("u.s.", "u.k.",
          "u.s.s.r", "there", "etc.", "ltd.", "'s"));
    }
  }
}
