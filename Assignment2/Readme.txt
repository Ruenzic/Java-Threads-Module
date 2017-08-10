Author: Josh di Bona (DBNJOS001)
Date: August 31 2014

Name: Concurrency in typing game.

Description: This is a java program to run a typing game where the user has to type out and enter the words that are falling down the screen before they reach the bottom. Using concurrency and thread safety to do this. 

The program uses the Java Model-View-Controller method. The model is WordRecord.java, view is WordPanel.java and the controller is WordApp.java.

Instructions to run program with given lists:
1. In the Ubuntu Terminal, go to the directory where WordApp.java is stored. (cd DirectoryAddress).
2. In the Terminal, type "make" to compile all nessassary files. 
3. Then to run the program, type "java WordApp" followed by the paramters for the program on the same like.
the first parameter is the total number of words you want to fall before the game ends, the second parameter is the total number of words you want to have on the screen at any given time. and the 3rd parameter is the name of the txt file containing the words. For example "java WordApp 30 3 example_dict.txt" will run the game. 

The instructions to play the game follow the instructions we were given to follow, open the game, press start to start the game, the game will end after the specified number of words have fallen or if the user presses end, in which case a message "GAME OVER" will be displayed. The user can then press start again to restart the game which will reset all the previous games data. The user can also quit at any time using the quit buttion or by closing the gui. 

If you want to configure the program to go through a different list of words, simply put it in the parameter, make sure the txt file is in the same file as the rest of the program though.

List of files: Score.java, WordRecord.java, WordApp.java, WordPanel.java, WordDictionary.java, Readme.txt, Makefile. 
