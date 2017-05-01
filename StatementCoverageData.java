package TestCompetition.JavaAgent;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.io.*;
import java.util.*;

public class StatementCoverageData {
	static String key;
	static int hashSize;
	static final HashMap<String,Set<String>> h = new HashMap<>();
	static final HashMap<String,Integer> hMap = new HashMap<>();
	static ValueComparator bvc = new ValueComparator(hMap);
	static TreeMap <String, Integer> sorted_map = new TreeMap <String, Integer> (bvc);
	static List<String> testsTotal = new ArrayList<String>();
	static List<String> testsAdditional = new ArrayList<String>();
	
	public static void testExecuted(String className, String methodName) 
	{
		  key = className;
		  h.put(key,new HashSet<>());
	}
	
	public static void lineExecuted(String className) 
	{
		  String str = className;
		  if(h.containsKey(key))
		    h.get(key).add(str);
	}
	
	
	public static List<String> totalPriorIntoFile()
	{	
		
		try 
		{
		  File file = new File("total_prior.txt");
		  if (file.exists())
               file.delete();
          else		   
		       file.createNewFile();
		  FileWriter writer = new FileWriter("total_prior.txt",true);		
		  for (String key : h.keySet()) 
			 hMap.put(key, (h.get(key)).size()); 
          sorted_map.putAll(hMap);
		  for (String key : sorted_map.keySet())
	      {		  
  		     writer.write("" + key);
			 testsTotal.add(key);
             writer.write(System.getProperty("line.separator"));	
		  }		  
		  writer.close();
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		return testsTotal;	
	}
	
	public static void prioritize(String keyCurrent)
	{				 			
        for (String temp : h.get(keyCurrent))
	    {	
		    for (String key : hMap.keySet())
            {
			   if(h.get(key).contains(temp))
				   hMap.put(key, hMap.get(key) - 1);
			}						
		}
	}
	
	public static List<String> additionalPriorIntoFile()
	{	    
		try
		{
		  File file = new File("additional_prior.txt");
		  if (file.exists())
              file.delete();
          else		   
		      file.createNewFile();
		  FileWriter writer = new FileWriter("additional_prior.txt",true);
		  String keyCurrent;	
          hashSize = hMap.size();		  
          int i =1;		  
		  while(i<=hashSize)
		  {	          		    
			keyCurrent = ""+sorted_map.keySet().toArray()[0];			
		    writer.write(keyCurrent);
			testsAdditional.add(keyCurrent);
		    writer.write(System.getProperty("line.separator"));
			hMap.remove(keyCurrent);
		    prioritize(keyCurrent);
			h.remove(keyCurrent);
            sorted_map.clear();			
            sorted_map.putAll(hMap);	
            i++;			
		  }
          writer.close();
		}		  
        catch (Exception ex) 
		{
			 ex.printStackTrace();
		}
        return testsAdditional;		
	}
}

class ValueComparator implements Comparator<String> 
{
    HashMap<String, Integer> base;
	
    public ValueComparator(HashMap<String, Integer> base) 
	{
        this.base = base;
    }
	
    public int compare(String a, String b) 
	{
        if (base.get(a) >= base.get(b))
            return -1;
         else
            return 1;
    }
}	
