# Wrapper-Script--Enju-Parser
Java script to convert Enju parser output to Moses Format for Training Syntax Model on Moses Decoder.

Moses accepts data for training syntax model in XML format. There are a number of parsers available for parsing. Each parser has its own idiosyncratic input and output format. Hence, we need to process the output of these parser in the format compatible with Moses for syntax model.

We used Enju parser for our experiment we were motivated to write a wrapper script for this purpose.
Hence we wrote a wrapper script to convert Enju parser output to Moses format compatible for syntax trees.
We designed a program to convert XML output of Enju parser to Moses compatible XML format.

