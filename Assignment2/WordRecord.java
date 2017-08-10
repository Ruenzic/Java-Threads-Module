//package skeletonCodeAssgnmt2;
import java.lang.System;

	public class WordRecord implements Runnable {
	private String text;
	private  int x;
	private int y;
	private int maxY;
	private boolean dropped;
	private int length;
	
	private int fallingSpeed;
	private static int maxWait=1500;
	private static int minWait=100;
	private long time;

	public static WordDictionary dict;
	private boolean visible = false;
	public static volatile boolean done = true; 

	
	WordRecord() {
		text="";
		x=0;
		y=0;	
		maxY=480;//300
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		//fallingSpeed = 250; // for testing for race conditions and concurrency.
	}
	
	WordRecord(String text) {
		this();
		this.text=text;
	}
	
	WordRecord(String text,int x, int maxY) {
		this(text);
		this.x=x;
		this.maxY=maxY;
	}
	
	public void run() // run method to move the words down at a randomly generated speed. 
	{
		while (done)
		{
			if (getY() < 480)
					{
						drop(20);
					}
				   else if (getY() == 480)
					{
						//resetWord();
					}
					try {
							Thread.sleep(maxWait-fallingSpeed); //sleep for a certain amount of time to imitate a speed. 
							} catch (InterruptedException e) {
							e.printStackTrace();}
		}

		
		
	}
	
// all getters and setters must be synchronized
	public synchronized boolean checkVisible()
	{
		return visible;
	}
	
	public synchronized void makeVisible()
	{
		visible = true;
	}
	
	public synchronized void makeInvisible()
	{
		visible = false;
	}

	public synchronized  void setY(int y) {
		if (y>maxY) {
			y=maxY;
			dropped=true;
		}
		this.y=y;
	}
	
	public synchronized  void setX(int x) {
		this.x=x;
	}
	
	public synchronized  void setWord(String text) {
		this.text=text;
	}

	public synchronized  String getWord() {
		return text;
	}
	
	public synchronized  int getX() {
		return x;
	}	
	
	public synchronized  int getY() {
		return y;
	}
	
	public synchronized  int getSpeed() {
		return fallingSpeed;
	}

	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}
	public synchronized void resetPos() {
		setY(0);
	}

	public synchronized void resetWord() {
		resetPos();
		text=dict.getNewWord();
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		//System.out.println(getWord() + " falling speed = " + getSpeed());  

	}
	
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);  
		if (typedText.equals(this.text)) {
			resetWord();
			return true;
		}
		else
			return false;
	}
	

	public synchronized  void drop(int inc) {
		setY(y+inc);
	}
	
	public synchronized  boolean dropped() {
		return dropped;
	}

}
