Author: Josh di Bona (DBNJOS001)
Date: August 10 2014

Name: Radar signal correlation

Description: This is a java program to compute the cross-correlation and find the max of a given transmited and received radio signal. Using sequential and parallel methods to get a faster computational speed. 

Radar.java is the driver class and calls Max.java and Correlation.java when running the code sequentially or parallel.

Instructions to run program with given lists:
1. In the Ubuntu Terminal, go to the directory where Radar.java is stored. (cd DirectoryAddress).
2. In the Terminal, type "make" to compile all nessassary files. 
3. Then type "java Radar" to run the program.
4. Follow the instructions printed to the command line. Make sure to enter the names of the files correctly with the ".txt" at the end. You can check the names of the files where Radar.java and the other class files are store. transmit10K.txt, transmit1M.txt etc. Make sure to use upper case "K" and "M". receive10K.txt receive100K.txt etc. 

If you want to configure the program to go through a different list or make any other changes such as the sequential cut-off:
1. You can change the sequential cut-off for either the cross-correlate or the find max in the class files Correlate.java and Max.java respectably. Change the variable named 'CUT_OFF'.
2. If you want to run other transmitted and received files, give them relevant names and add them to the same folder where you run Radar.java from.

List of files: Radar.java, Correlate.java, Max.java, Makefile, Readme.txt, Assignment 1 Report.docx (dont try open from command line because of spaces in the name), and all the received and transmit text files. 
