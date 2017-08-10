//package skeletonCodeAssgnmt2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class WordPanel extends JPanel implements Runnable {
		public static volatile boolean done;
		public static volatile int count; // volatile shared variable
		private WordRecord[] words;
		private int noWords;
		private int maxY;
		private Score score;
		private JLabel caught;
		private JLabel missed;
		private JLabel message;
		private JLabel scr;
		public static volatile int max; // volatile shared variable
		private static BufferedImage image;

		
		public void paintComponent(Graphics g) {
			 super.paintComponent(g);
			 int width = getWidth();
		    int height = getHeight();
			 g.drawImage(image, 0,0, null);
		    //g.clearRect(0,0,width,height);
		    //g.setColor(Color.red);
		    //g.fillRect(0,0,width,height);//maxY-10

		    g.setColor(Color.black);
		    g.setFont(new Font("Helvetica", Font.PLAIN, 26));
		   //draw the words
		   //animation 
				for (int i=0;i<noWords;i++){
			 	if(words[i].getY() >= 480)
				{
					score.missedWord();
					count++;
					words[i].resetWord();
					
					
				}	
					
			   //g.drawString(words[i].getWord(),words[i].getX(),words[i].getY()-20);	
			   //g.drawString(words[i].getWord(),words[i].getX(),words[i].getY()+20);  //y-offset for skeleton so that you can see the words
				words[i].makeVisible();
				caught.setText("Caught: " + score.getCaught() + "    ");
				missed.setText("Missed:" + score.getMissed()+ "    ");
				scr.setText("Score:" + score.getScore()+ "    ");
				//System.out.println(count);
				if (count >= max)
				{
					message.setText("GAME OVER       ");
					for (int j = 0; j < noWords; j++)
					  {
							words[i].done = false;
					  }

				}
				else
				{
					message.setText("");
					g.drawString(words[i].getWord(),words[i].getX(),words[i].getY()-20);
				}

		    }
			 		   
		  }
		
		WordPanel(WordRecord[] words, int maxY, JLabel caught, Score score, JLabel missed, JLabel scr, int count, JLabel message, BufferedImage image) {
			this.words=words;
			noWords = words.length;
			done=false;
			this.maxY=maxY;	
			this.caught=caught;	
			this.score=score;
			this.missed = missed;
			this.scr=scr; 
			this.count = count;
			this.message = message;
			this.image = image;
		}
		
		public void run() { // run method to call repaint contantly and sleep to animate movement. 
			while (!done) { 
				repaint();
				try {
				Thread.sleep(10); 
				} catch (InterruptedException e) {
				e.printStackTrace();
			};
}		
		
		
			
		}  
		

	}


