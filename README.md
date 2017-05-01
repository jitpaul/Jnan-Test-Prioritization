
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

In order to use the tool on your maven project, please follow the below steps.

A) Following needs to be added to the pom.xml file.
   1) Replace [path-to-your-agent.jar] with your java agent jar’s absolute path, and 
   replace [YourListener] with your JUnit listener’s full name.
   ```
   <plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-surefire-plugin</artifactId>
   <configuration>
   <argLine>-javaagent:[path-to-your-agent.jar]</argLine>
   <properties>
   <property>
   <name>listener</name>
   <value>[YourListener]</value>
   </property>
   </properties>
   </configuration>
   </plugin>
   ```
   2) Add the agent.jar file as a dependency as it is used by the 'Listener.java' file. 
   (The following dependency was written for my 'JPAgent.jar' file which is attached 
   in the repository for your reference)
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
B) Place the JPAgent.jar file in the root folder of the project under test.

C) Place the listener.java file under the test folder.

D) Execute 'mvn test' command.
   After successful execution, you can see four new files generated into the root folder of the test project.
   'total_prior.txt', 'additional_prior.txt', 'A_TestSuite.java', 'T_TestSuite.java'
   
E) Place the 'A_TestSuite.java', 'T_TestSuite.java' in the test folder (same location as listener.java)

D) Execute 'mvn test -Dtest=T_TestSuite' to execute tests based on total prioritization.
   Execute 'mvn test -Dtest=A_TestSuite' to execute tests based on additional prioritization.


PS:- A 'JPAgent.jar' file has been attached for reference. This jar file can be directly used with the 'commons-dbutils' project. For other projects, you would need to modfiy a line in the 'MyClassFileTransformer.java' file and re-create the jar file. The jar file contains MethodPrinter.class, ClassPrinter.class, MyClassFileTransformer.class, StatementCoverageData.class, GenerateTestSuiteForJUnit4.class, ValueComparator.class and Agent.class in it.

