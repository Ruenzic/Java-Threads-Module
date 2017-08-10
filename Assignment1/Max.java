// Josh di Bona
// DBNJOS001
// 10 August 2014
// Max.java
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.*;

public class Max extends RecursiveAction
{
	int low;
	int high;
	double[] max; // Shared array to store max value
	double maxS;
	int filesize;
	float[] Cor; // Correlated signal
	static final int CUT_OFF = 1000; 

	Max(int h, int l, float[] CorA, double[] m) // Constructor
	{
		high = h;
		low = l;
		Cor = CorA;
		filesize = Cor.length;
		max = m;
	}
	
	@Override
	protected void compute() // Method to do Parallel find max
		{
			if((high-low) <= CUT_OFF)
			{
				for (int i = 0; i < filesize; i++)
				{
					if (Cor[i] > max[0])
					{
						max[0] = Cor[i];
					}
				}
			}
			else
			{
				int mid = (high+low)/2;
				Max left = new Max(mid, low, Cor, max);
			   Max right= new Max(high,mid, Cor, max);
			   left.fork();
				right.compute();
				left.join();
			}
		}
		
		
		public double SequentialgetMax() // Method to do sequential find max
		{
			maxS = 0;
			for (int i = 0; i < filesize; i++)
			{
				if (Cor[i] > maxS)
				{
					maxS = Cor[i];
				}
			}
			return maxS;
		}

}
