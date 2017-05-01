
Jnan Test Prioritization Tool
===========================

## Authors ##

* Jithin Paul (paul.jitin@gmail.com)
* Nandita Balasubramanian (nandita.sbalaji@gmail.com)

##### University of Texas at Dallas (UTD) #####
April 2017


An Automated coverage collection tool that can capture the statement coverage for the program under test and then use the information to prioritize the test classes in the test suite.

The tool uses ASM byte-code manipulation framework to manipulate the bytecode. Bytecode manipulation is performed on the fly by a 
Java Agent which makes use of the Intrumentation API. A JUnit listener is used to to capture the start and end events for each JUnit
test method. The agent jar file and the JUnit listener class can be integrated with any maven project to perform code coverage. This is done by updating the pom.xml file present in the maven project rool directory.

In order to use the tool with your maven project, please follow the below steps.

A) Following needs to be added to the 'pom.xml' file.
   1) Add the below code snippet to include the 'JPAgent.jar' file and the 'Listener.java' file as plugins.
   ```
   <plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-surefire-plugin</artifactId>
   <configuration>
   <argLine>-javaagent:JPAgent.jar</argLine>
   <properties>
   <property>
   <name>listener</name>
   <value>org.apache.commons.dbutils.Listener</value>
   </property>
   </properties>
   </configuration>
   </plugin>
   ```
   2) Add the 'JPAgent.jar' file as a dependency as it is used by the 'Listener.java' file. 
   ```
   <dependency>
   <artifactId>TestCompetition.JavaAgent</artifactId>
   <groupId>JPAgent</groupId>
   <version>1.0</version>
   <scope>system</scope>
   <systemPath>${basedir}/JPAgent.jar</systemPath>
   </dependency>
   ```
   3) Add the asm package as a dependency if needed as it is used by multiple files.
   ```
   <dependency>
   <groupId>org.ow2.asm</groupId>
   <artifactId>asm</artifactId>
   <version>5.0.3</version>
   </dependency>
   ```
   4) Add the junit package as a dependency if needed.
   ```
   <dependency>
   <groupId>junit</groupId>
   <artifactId>junit</artifactId>
   <version>4.11</version>
   <scope>test</scope>
   </dependency>
   ```
B) Place the 'JPAgent.jar' file in the root folder of the project under test.

C) Place the 'listener.java' file under the test folder. ('commons-dbutils-trunk\src\test\java\org\apache\commons\dbutils\')

D) Execute 'mvn test' command.
   After successful execution, you can see four new files generated into the root folder of the test project.
   'total_prior.txt', 'additional_prior.txt', 'A_TestSuite.java', 'T_TestSuite.java'
   
E) Place the 'A_TestSuite.java', 'T_TestSuite.java' files in the test folder (same location as listener.java)

D) Execute 'mvn test -Dtest=T_TestSuite' to execute tests based on total prioritization.'\n'
   Execute 'mvn test -Dtest=A_TestSuite' to execute tests based on additional prioritization.


PS:- A 'JPAgent.jar' file has been attached for reference. This jar file can be directly used with the 'commons-dbutils' project. For other projects, you would need to modfiy a line (line 17) in the 'MyClassFileTransformer.java' file and re-create the jar file. The jar file contains MethodPrinter.java, ClassPrinter.java, MyClassFileTransformer.java, StatementCoverageData.java', 'GenerateTestSuiteForJUnit4.java' and 'Agent.java' files in it.

