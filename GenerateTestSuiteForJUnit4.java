package TestCompetition.JavaAgent;

import java.util.ArrayList;
import java.util.List;
import java.io.*;


// This class generate a Test Suite for JUnit4.x
public class GenerateTestSuiteForJUnit4 {
	
	
	// This method writes a header to Test Suite.
	public static String getHeader(String packageName){
		String header = "import org.junit.runner.JUnitCore;\n";
		header += "import org.junit.runner.Request;\n";
		header += "import org.junit.runner.Result;\n";
		return header;
	}
	
	// This method create the suite class declaration and field declarations.
	public static String getBodyHigher(String className){
		String body = "";
        body = "public class "+ className + " {\n";
		body += "\tprivate int failureCount = 0;\n";
		body += "\tprivate int runCount = 0;\n";
		body += "\tprivate int errorCount = 0;\n";
		return body;
	}
        
	// This method generate the suite code.
	public static String generate(String packageName, String suiteClassName, List<String> tests) {
		List<String> jaInstanciada = new ArrayList<String>();
        String code = getHeader(packageName);
        code += getBodyHigher(suiteClassName);
        
        code += "\tpublic void run(){\n";
        code += "\t\ttry{\n";
        code += "\t\t\tJUnitCore jc = new JUnitCore();\n";
        code += "\t\t\tResult result = null;\n";
       
        for (Object string : tests) {
           String caminho = (String) string;
           caminho = caminho.replace(".java", "");
           String[] paths = caminho.split("\\.");
           String tcName = paths[paths.length-1];
           String suiteName = caminho.replaceAll("\\.", "").replace(tcName, "");
           
           String instance = getPathInstance(paths);
           String requestName = generateRequestName(suiteName, tcName);
           if(!jaInstanciada.contains(suiteName)){
               code += "\t\t\tClass "+suiteName+" = Class.forName(\""+instance+"\");\n";
               jaInstanciada.add(suiteName);
           }
           code += "\t\t\tRequest "+requestName+" = Request.method("+suiteName+",\""+tcName+"\");\n";
           code += "\t\t\tresult = jc.run("+requestName+");\n";
           code += "\t\t\tsetResult(\""+tcName+"\",result);\n";         
        }
        code += "\t\t} catch (Exception e) {\n";
        code += "\t\t\te.printStackTrace();\n";
        code += "\t\t}\n";  
        code += "\t}\n";
        code += getFinalSuite(suiteClassName, tests);
		
		try 
		{
		  File file = new File(""+ suiteClassName +".java");
		  if (file.exists())
               file.delete();
          else		   
		       file.createNewFile();
		  FileWriter writer = new FileWriter(""+ suiteClassName +".java",true);		
		  writer.write(code);
		  writer.close();
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}		  
        return code;
    }
		
	// This method create the main method to test suite.
	private static String getFinalSuite(String className, List<String> tests){
		String code = "";
		code += "\tprivate void setResult(String testCaseName,Result result){\n";
		code += "\t\tSystem.out.println(\"TestCase: \" + testCaseName +\" \" + getStatus(result));\n";
		code += "\t\trunCount += result.getRunCount();\n";
		code += "\t\tfailureCount += result.getFailureCount();\n";
		code += "\t}\n";
		code += "\tprivate String getStatus(Result result){\n";
		code += "\t\tint failureCount = result.getFailureCount();\n";
		code += "\t\tif(failureCount > 0) return \"[FAILURE]\";\n";
		code += "\t\telse return \"[ACCEPTED]\";\n";
		code += "\t}\n";
		code += "\tpublic void printResult(){\n";
		code += "\t\tSystem.out.println(\"=================== Test Suite Prioritized - PriorJ =================\");\n";
		code += "\t\tSystem.out.println(\"Run: \" + this.runCount);\n";
		code += "\t\tSystem.out.println(\"Faults: \" + this.failureCount);\n";
		code += "\t\tSystem.out.println(\"Errors: \" + this.errorCount);\n";
		code += "\t}\n";
		code += "\tpublic static void main(String[] args) {\n";
		
		if (className.isEmpty())
            code += "\t\tPrioritizedSuite st = new PrioritizedSuite();\n";
	    else
	            code += "\t\t" + className + " st = new " + className + "();\n";

	    code += "\t\tst.run();\n";
		
		code += "\t\tst.printResult();\n";
		code += "\t}\n";
		code += "}\n";
		
		return code;
		
	}
	
	
	//This method generate a Name to suite file.
	public static String generateNameSuite(String[] paths) {

		String caminho = "tc";
		for (String string : paths) {
			caminho += string.substring(0, 1).toUpperCase().concat(string.substring(1));
		}
		return caminho;
	}
	
	//
	private static String generateRequestName(String suiteName, String tcName){
		String result = suiteName+tcName;
		return result.replaceAll("\\.", "");
	}
	
	
	//This method get a instance from file path.
	public static String getPathInstance(String[] paths) {
		String caminho = "";
		for (int i = 0; i < paths.length - 1; i++) {
			caminho += paths[i] + ".";
		}
		return caminho.isEmpty() ? "" : caminho.substring(0, caminho.length() - 1) ; 
	}
	
}