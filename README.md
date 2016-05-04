# Wrapper-Program --Enju-Parser
Java program to convert Enju parser output to Moses Format for Training Syntax Model on Moses Decoder.

Moses accepts data for training syntax model in XML format. There are a number of parsers available for parsing. Each parser has its own idiosyncratic input and output format. Hence, we need to process the output of these parser in the format compatible with Moses for syntax model.

We used Enju parser for our experiment we were motivated to write a wrapper script for this purpose.
Hence we developed a wrapper script to convert Enju parser output to Moses compatible XML format for syntax trees.

Credits-
This wrapper program was developed while our performing our thesis "Experimenting with different Models of SMT" at CDAC ,Noida for fullfilment of M.Tech(CS) degree from Banasthali University. I would like to thank my guides Mr. K.K. Arora,Group Corrdinator,SNLP Lab and Sunita Arora,Joint Director SNLP, for motivating me for this task. I am very grateful Mr. Mukund Kumar Roy,STO  for invaluale suggestions and guidance. 
