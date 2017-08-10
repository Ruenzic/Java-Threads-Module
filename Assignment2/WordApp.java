//package skeletonCodeAssgnmt2;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


import java.util.Scanner;
import java.util.concurrent.*;
//model is separate from the view.

public class WordApp {
//shared variables
	static int noWords=4;
	static int totalWords;

   	static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;

	static WordDictionary dict = new WordDictionary(); 
	static WordRecord[] words;
	static volatile boolean done; // volatile shared variable
	static 	Score score = new Score();

	static WordPanel w;
	
	static String argument1 = "30";
	static String argument2 = "3";
	static String argument3 = "example_dict.txt";
	static boolean start = false;
	static volatile int count; // volatile shared variable
	static volatile int max; // volatile shared variable
	private static BufferedImage image;
	
	
	public static void setupGUI(int frameX,int frameY,int yLimit) {
		// Frame init and dimensions
    	JFrame frame = new JFrame("WordGame"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameX, frameY);
    	
      	JPanel g = new JPanel();
			try
			{
				image = ImageIO.read(new File("background.png"));
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
      	g.setSize(frameX,frameY);
 
	    
	    JPanel txt = new JPanel();
	    txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS)); 
	    JLabel caught =new JLabel("Caught: " + score.getCaught() + "    ");
	    JLabel missed =new JLabel("Missed:" + score.getMissed()+ "    ");
	    JLabel scr =new JLabel("Score:" + score.getScore()+ "    ");  
		 JLabel message =new JLabel(""); 
		 txt.add(message); 
	    txt.add(caught);
	    txt.add(missed);
	    txt.add(scr);
		 
		 
		 w = new WordPanel(words,yLimit, caught, score, missed, scr, count, message, image); 
		w.setSize(frameX,yLimit+100);
	    g.add(w);

    
	    //[snip]
  
	    final JTextField textEntry = new JTextField("",20);
	   textEntry.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent evt) {
	          String text = textEntry.getText();
				boolean check = false;
				for (int i = 0; i < noWords; i++)
				{
					if (words[i].checkVisible() == true) // check if current word is visible on the screen. 
					{
						if (words[i].getWord().equals(text) && !check)// check if entered word is equals to a word on the screen.
						{
							check = true;
							words[i].makeInvisible();
							score.caughtWord(words[i].getWord().length()); // catch the word (increment catch and score)
							w.count++; // increment the total words fallen count
							words[i].matchWord(text); // match the word and create a new word.
						}
					}
					
				}
				check = false;

	          //[snip]
	          textEntry.setText("");
	          textEntry.requestFocus();
	      }
	    });
	   
	   txt.add(textEntry);
	   txt.setMaximumSize( txt.getPreferredSize() );
	   g.add(txt);
	    
	    JPanel b = new JPanel();
        b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS)); 
	   	JButton startB = new JButton("Start");;
		
			// add the listener to the jbutton to handle the "pressed" event
			startB.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
		    	  //[snip]
				  if (!start) // start all the threads 
				  {
				 	 for (int i=0;i<noWords;i++)
					 {
				 	 	new Thread(words[i]).start();
				    }
					 new Thread(w).start();
					 start = true;
				  }
				  else if (start == true) // start a new game without having to reopen the game. 
				  {
				  		w.done = false;
						new Thread(w).start();
						score.resetScore();
					   count = 0;
						w.count = 0;
						  for (int i = 0; i < noWords; i++)
						  {
						  		words[i].resetWord();
								words[i].done = true;
								new Thread(words[i]).start();

						  }

				  }

		    	  textEntry.requestFocus();  //return focus to the text entry field
		      }
		    });
		JButton endB = new JButton("End");;
			
				// add the listener to the jbutton to handle the "pressed" event
				endB.addActionListener(new ActionListener()
			    {
			      public void actionPerformed(ActionEvent e)
			      {
			    	  //[snip]
					  w.count = max;
					  for (int i = 0; i < noWords; i++)
					  {
					   	words[i].resetWord();
							words[i].done = false;
					  }
					}
			    });
		JButton quitB = new JButton("Quit");;
			
				// add the listener to the jbutton to handle the "pressed" event
				quitB.addActionListener(new ActionListener()
			    {
			      public void actionPerformed(ActionEvent e)
			      {
						System.exit(0);
			    	  //[snip]
			      }
			    });

		
		b.add(startB);
		b.add(endB);
		b.add(quitB);
		
		g.add(b);
    	
      	frame.setLocationRelativeTo(null);  // Center window on screen.
      	frame.add(g); //add contents to window
        frame.setContentPane(g);     
       	//frame.pack();  // don't do this - packs it into small space
        frame.setVisible(true);

		
	}

	
public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename)); //FileInputStream
			int dictLength = dictReader.nextInt();
			//System.out.println("read '" + dictLength+"'");

			dictStr=new String[dictLength];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]=new String(dictReader.next());
				//System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
			}
			dictReader.close();
		} catch (IOException e) {
	        System.err.println("Problem reading file " + filename + " default dictionary will be used");
	    }
		return dictStr;

	}
	
	
	public static void main(String[] args) {
    	
		//deal with command line arguments
		totalWords=Integer.parseInt(args[0]);  //total words to fall args[0]/argument1 for testing
		max = Integer.parseInt(args[0]); // change to args[0]/argument1 for testing
		w.max = max;
		noWords=Integer.parseInt(args[1]); // total words falling at any point args[1]/argument2 for testing
		count = 0;
		assert(totalWords>=noWords); // this could be done more neatly
		String[] tmpDict=getDictFromFile(args[2]); //file of words args[2]/argument3 for testing
		if (tmpDict!=null)
			dict= new WordDictionary(tmpDict);
		
		WordRecord.dict=dict; //set the class dictionary for the words.
		
		words = new WordRecord[noWords];  //shared array of current words
		
		//[snip]
		
		setupGUI(frameX, frameY, yLimit);  
    	//Start WordPanel thread - for redrawing animation

		int x_inc=(int)frameX/noWords;
	  	//initialize shared array of current words

		for (int i=0;i<noWords;i++) {
			words[i]=new WordRecord(dict.getNewWord(),i*x_inc,yLimit);
			//new Thread(words[i]).start();//moved to start button 
			}
		
		
		//new Thread(w).start(); //commeneted out to put in start button 

	}

}
