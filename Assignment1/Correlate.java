// Josh di Bona
// DBNJOS001
// 10 August 2014
// Correlate.java
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.*;

public class Correlate extends RecursiveAction
{
	int low;
	int high;
	int max;
	int filesize;
	float[] Cor; // Correlated signal, shared array
	float[] Rec;   // Received signal
	float[] Trans; // Transmited signal
	static final int CUT_OFF = 1000; // Sequential Cut off point

	Correlate(int h, int l, float[] Received, float[] Transmited, float[] CorA) // constructor
	{
		high = h;
		low = l;
		Rec = Received;
		Trans = Transmited;
		Cor = CorA;
		filesize = Received.length;
	}
	
	@Override
	protected void compute() // method to do parallel cross-correlation
	{
		if((high-low) <= CUT_OFF)
		{
			for (int i = low; i < high; ++i)
			{
				float sum = 0;
				for (int j = 0; j < filesize; ++j)
				{
					if (i+j < filesize)
					{
						sum += Rec[i+j]*Trans[j];
					}
				}
			Cor[i] = sum;
				}
		}
		
		else
		{
			int mid = (high+low)/2;
			Correlate left = new Correlate(mid, low, Rec, Trans, Cor);
		   Correlate right= new Correlate(high,mid, Rec, Trans, Cor);
		   left.fork();
			right.compute();
			left.join();
						
		}
		
	}

	public float[] Sequentialcompute() // method to do the sequential cross-correlate
	{
		for (int i = 0; i < high; ++i)
		{
			float sum = 0;
			for (int j = 0; j < high; ++j)
			{
				if (i+j < high)
				{
					sum += Rec[i+j]*Trans[j];
				}
			}
			Cor[i] = sum;
		}
		return Cor;
	}


}
