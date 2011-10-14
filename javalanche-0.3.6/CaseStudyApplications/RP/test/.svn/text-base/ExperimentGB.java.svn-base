/*---------------------------------------------------------------------
 *  File: $Id: ExperimentGB.java,v 1.0 2007/07/02 13:36:17 smitha Exp $   
 *  Version:  $Revision: 1.26 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package redupr;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Vector;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream; 
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class ExperimentGB
{

	public static void main(String[] args)throws IOException
	{
		final String DATA = "data/diatoms/reduce/xmlSetCovers/GB-SetCover.xml";
		LinkedHashSet outSet = new LinkedHashSet();
		
		ArrayList metrics = new ArrayList();
		metrics.add("coverage");
		metrics.add("ratio");
		metrics.add("time");
		
		boolean i = true;
		
		long stop=0;
		long start=0;
		long reduceTime=0;
		long prioritizeTime=0;
		long totalTime=0;
		
		File resultsFileHGS = new File("data/diatoms/reduce/xmlSetCoversResults/ExperimentGBResults_HGS.dat");
		File resultsFileDG = new File("data/diatoms/reduce/xmlSetCoversResults/ExperimentGBResults_DG.dat");
		File resultsFileG = new File("data/diatoms/reduce/xmlSetCoversResults/ExperimentGBResults_G.dat");
		File resultsFileKW = new File("data/diatoms/reduce/xmlSetCoversResults/ExperimentGBResults_KW.dat");
		
		if(resultsFileHGS.exists())
		{
			resultsFileHGS.delete();
		}
	
		if(resultsFileDG.exists())
		{
			resultsFileDG.delete();
		}
		
		if(resultsFileG.exists())
		{
			resultsFileG.delete();
		}
		
		if(resultsFileKW.exists())
		{
			resultsFileKW.delete();
		}
	
		resultsFileHGS.createNewFile();
		resultsFileDG.createNewFile();
		resultsFileG.createNewFile();
		resultsFileKW.createNewFile();		
		
		BufferedWriter resultsWriterHGS = new BufferedWriter(new FileWriter(resultsFileHGS, true));
		BufferedWriter resultsWriterDG = new BufferedWriter(new FileWriter(resultsFileDG, true));
		BufferedWriter resultsWriterG = new BufferedWriter(new FileWriter(resultsFileG, true));
		BufferedWriter resultsWriterKW = new BufferedWriter(new FileWriter(resultsFileKW, true));

		try 
		{
			resultsWriterHGS.write("alg\t"+ "metric\t"+"reduceTime\t"+"reduceSet\t"+"priorTime\t"+"priorOrder\t"+"looksAhead\n");
			resultsWriterDG.write("alg\t"+ "metric\t"+"reduceTime\t"+"reduceSet\t"+"priorTime\t"+"priorOrder\n");
			resultsWriterG.write("alg\t"+ "metric\t"+"reduceTime\t"+"reduceSet\t"+"priorTime\t"+"priorOrder\n");
			resultsWriterKW.write("alg\t"+ "metric\t"+"reduceTime\t"+"reduceSet\t"+"priorTime\t"+"priorOrder\n");
		
		}
		
		catch (IOException e) 
		{
			System.out.println("IOException on write");
		}				

		Iterator metricsIterator = metrics.iterator();
				
		SetCover g = new SetCover();
		g = SetCover.constructSetCoverFromXML(DATA);	
		System.out.println("Made the SetCover!");	
		
		FastByteArrayOutputStream pristeneCover = g.createFastByteArrayOutputStream();
		
		while (metricsIterator.hasNext())
		{
			String metric = (String) metricsIterator.next();
			
			System.out.println("HGS");
			for(int looks = 0;looks<3;looks++)
			{			
							
				if(i)
				{
					SetCover.saveSetCoverAsCoverageAndTimeFile(g,"data/diatoms/reduce/xmlSetCovers/GBCoverage.dat","data/diatoms/reduce/xmlSetCovers/GBTime.dat");
					System.out.println("Saved the coverage and time files");
				}
				
				if(!i)
				{
					try 
					{		
						// Retrieve an input stream from the byte array and read
				      // a copy of the object back in.
				     ObjectInputStream in = new ObjectInputStream(pristeneCover.getInputStream());
				     g = (SetCover) in.readObject();
										
					}
			
					catch(java.lang.ClassNotFoundException e) 
					{
		         		e.printStackTrace();
		     		}
				}
				i = false;
				ReduceUsingHarroldGuptaSoffa hgs = new ReduceUsingHarroldGuptaSoffa(g);
						
				hgs.setLooksAhead(looks);
			
				System.gc();
				start = System.currentTimeMillis();
				hgs.reduceUsingHGS(metric);
				stop = System.currentTimeMillis();
				System.out.println("Reduced!");
			
				reduceTime = stop-start;
										
				String coveringSet = SetCover.getCoveringTestSetString(hgs.getCoverPickSets(),"Q");
			
				try 
				{		
					// Retrieve an input stream from the byte array and read
		      	// a copy of the object back in.
		      	ObjectInputStream in = new ObjectInputStream(pristeneCover.getInputStream());
		      	g = (SetCover) in.readObject();
				}
		
				catch(java.lang.ClassNotFoundException e) 
				{
		      	e.printStackTrace();
		   	}
		   		
				hgs = new ReduceUsingHarroldGuptaSoffa(g);
				hgs.setLooksAhead(looks);
			
				System.gc();
				start = System.currentTimeMillis();
				hgs.prioritizeUsingHGS(metric);
				stop= System.currentTimeMillis();
				System.out.println("Prioritized!");
							
				prioritizeTime = stop-start;
				totalTime = prioritizeTime+reduceTime;
				String prioritizeSet = SetCover.getCoveringTestSetString(hgs.getPrioritizedSets(),"Q");
			
				try 
				{
					resultsWriterHGS.write("HGS\t"+metric+"\t+"+reduceTime+"\t"+
											  coveringSet+"\t"+prioritizeTime+"\t"+prioritizeSet+"\t"+looks+"\n");
				}

				catch (IOException e) 
				{
					System.out.println("IOException on write");
				}
				
				///**************************************************************************************/
			}
					
			System.out.println("DG");				
			try 
			{		
				// Retrieve an input stream from the byte array and read
	         // a copy of the object back in.
	      	ObjectInputStream in = new ObjectInputStream(pristeneCover.getInputStream());
	      	g = (SetCover) in.readObject();
			}
			
			catch(java.lang.ClassNotFoundException e) 
			{
	     		e.printStackTrace();
	   	}
				
			ReduceUsingDelayedGreedy dg = new ReduceUsingDelayedGreedy(g);
						
			System.gc();
			start = System.currentTimeMillis();
			dg.reduceUsingDG(metric);
			stop = System.currentTimeMillis();
			System.out.println("Reduced!");
			
			reduceTime = stop-start;
										
			String coveringSet = SetCover.getCoveringTestSetString(dg.getCoverPickSets(),"Q");
			
			try 
			{		
				// Retrieve an input stream from the byte array and read
	      	// a copy of the object back in.
	      	ObjectInputStream in = new ObjectInputStream(pristeneCover.getInputStream());
	      	g = (SetCover) in.readObject();
			}
		
			catch(java.lang.ClassNotFoundException e) 
			{
	      	e.printStackTrace();
	   	}
	   		
			dg = new ReduceUsingDelayedGreedy(g);
							
			System.gc();
			start = System.currentTimeMillis();
			dg.prioritizeUsingDG(metric);
			stop= System.currentTimeMillis();
			System.out.println("Prioritized!");
							
			prioritizeTime = stop-start;
			totalTime = prioritizeTime+reduceTime;
			String prioritizeSet = SetCover.getCoveringTestSetString(dg.getPrioritizedSets(),"Q");
			
			try 
			{
				resultsWriterDG.write("DG\t"+metric+"\t+"+reduceTime+"\t"+
										  coveringSet+"\t"+prioritizeTime+"\t"+prioritizeSet+"\n");
			}

			catch (IOException e) 
			{
				System.out.println("IOException on write");
			}
			
			///**************************************************************************************/
			
			System.out.println("G");
			try 
			{		
				// Retrieve an input stream from the byte array and read
		      // a copy of the object back in.
		      ObjectInputStream in = new ObjectInputStream(pristeneCover.getInputStream());
		      g = (SetCover) in.readObject();
			}
		
			catch(java.lang.ClassNotFoundException e) 
			{
	     		e.printStackTrace();
	  		}
		
			
			ReduceUsingGreedy rug = new ReduceUsingGreedy(g);
						
			System.gc();
			start = System.currentTimeMillis();
			rug.reduceUsingGreedy(metric);
			stop = System.currentTimeMillis();
			System.out.println("Reduced!");
			
			reduceTime = stop-start;
										
			coveringSet = SetCover.getCoveringTestSetString(rug.getCoverPickSets(),"Q");
			
			try 
			{		
				// Retrieve an input stream from the byte array and read
	      	// a copy of the object back in.
	      	ObjectInputStream in = new ObjectInputStream(pristeneCover.getInputStream());
	      	g = (SetCover) in.readObject();
			}
		
			catch(java.lang.ClassNotFoundException e) 
			{
	      	e.printStackTrace();
	   	}
	   		
			rug = new ReduceUsingGreedy(g);
							
			System.gc();
			start = System.currentTimeMillis();
			rug.prioritizeUsingGreedy(metric);
			stop = System.currentTimeMillis();
			System.out.println("Prioritized!");
							
			prioritizeTime = stop-start;
			totalTime = prioritizeTime+reduceTime;
			prioritizeSet = SetCover.getCoveringTestSetString(rug.getPrioritizedSets(),"Q");
			
			try 
			{
				resultsWriterG.write("G\t"+metric+"\t+"+reduceTime+"\t"+
										  coveringSet+"\t"+prioritizeTime+"\t"+prioritizeSet+"\n");
			}

			catch (IOException e) 
			{
				System.out.println("IOException on write");
			}
			
			///**************************************************************************************/
			
			System.out.println("KW");
			try 
			{		
				// Retrieve an input stream from the byte array and read
		      // a copy of the object back in.
		      ObjectInputStream in = new ObjectInputStream(pristeneCover.getInputStream());
		      g = (SetCover) in.readObject();					
			}
		
			catch(java.lang.ClassNotFoundException e) 
			{
	   		e.printStackTrace();
			}
			
			ReduceUsingKWay kw = new ReduceUsingKWay(g);
						
			System.gc();
			start = System.currentTimeMillis();
			kw.reduceUsingKW(metric);
			stop = System.currentTimeMillis();
			System.out.println("Reduced!");
			
			reduceTime = stop-start;
										
			coveringSet = SetCover.getCoveringTestSetString(kw.getCoverPickSets(),"Q");
			
			try 
			{		
				// Retrieve an input stream from the byte array and read
	      	// a copy of the object back in.
	      	ObjectInputStream in = new ObjectInputStream(pristeneCover.getInputStream());
	      	g = (SetCover) in.readObject();
			}
		
			catch(java.lang.ClassNotFoundException e) 
			{
	      	e.printStackTrace();
	   	}
	   		
			kw = new ReduceUsingKWay(g);
							
			System.gc();
			start = System.currentTimeMillis();
			kw.prioritizeUsingKW(metric);
			stop = System.currentTimeMillis();
			System.out.println("Prioritized!");
							
			prioritizeTime = stop-start;
			totalTime = prioritizeTime+reduceTime;
			prioritizeSet = SetCover.getCoveringTestSetString(kw.getPrioritizedSets(),"Q");
			
			try 
			{
				resultsWriterKW.write("KW\t"+metric+"\t+"+reduceTime+"\t"+
										  coveringSet+"\t"+prioritizeTime+"\t"+prioritizeSet+"\n");
			}

			catch (IOException e) 
			{
				System.out.println("IOException on write");
			}
			
			///**************************************************************************************/
		}
		resultsWriterHGS.close();	
		resultsWriterDG.close();	
		resultsWriterG.close();	
		resultsWriterKW.close();	
	}
}

