package TestCompetition.JavaAgent;

import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class GenerateTestSuiteForJUnit4 
{	
	//This method writes a header to Test Suite.
	public static String getHeader()
	{
		String header = "import org.junit.runner.RunWith;\n";
		header += "import org.junit.runners.Suite;\n";
		return header;
	}
	
	//This method adds the classes to the suite
	public static String getBody(List<String> testsClasses, String name)
	{
		String body = "";
        body = "@RunWith(Suite.class)\n";
		body += "@Suite.SuiteClasses({\n";
		
		for (String item : testsClasses)
			   body += item + ".class,\n";
		body += "})\n";
		body += "public class "+name+"{\n";
		body += "}\n";
		return body;
	}
        
	//This method generates the suite code.
	public static void generate(String name, List<String> testsClasses) 
	{
		String header = "";
		String body = "";
		String code = "";
		header = getHeader();
		body =  getBody(testsClasses,name);
        code = header + body;		
		try 
		{
		  File file = new File(""+name+".java");
		  if (file.exists())
               file.delete();
          else		   
		       file.createNewFile();
		  FileWriter writer = new FileWriter(""+name+".java",true);		
		  writer.write(code);
		  writer.close();
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}		  
    }
}
	
	
