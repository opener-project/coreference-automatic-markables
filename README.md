coreference-automatic-markables
===============================

Produces automatic markables for assisting manual coreference annotation. The
input must be a KAF file containing word forms, terms, entities, constituent
parsing (with headwords marked). Example: 

   cat input.kaf | java -jar coreference-automatic-markables-$version.jar 

The output will be a KAF file contaning every markable (e.g., a coreference
singleton) which can be taken as the input to perform gold-standard manual
annotation of coreference resolution.


