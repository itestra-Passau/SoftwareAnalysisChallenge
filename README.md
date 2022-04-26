# itestra Software Analysis Challenge

## Assignments
In this challenge the goal is to create a tool which can analyze any given Java code and make certain statements about that code. Tools like this can help if you need to understand new or unknown code.

The provided example code in `CodeExamples` contains three open source projects:
1. [Cron Utils](https://github.com/jmrozanec/cron-utils): A utility library for creating crons in Java
2. [fig](https://github.com/percyliang/fig): A general-purpose collection with different Java utilities
3. [Spark](https://github.com/perwendel/spark): A framework for creating Java web applications

The three projects have been modified so that there are some dependencies between them. 

There is no build file provided that builds the example project, as running the project is not part of the assignment.

### 1. Analyze the number of source lines
To get a first idea of the project size it is a good measurement to look at the lines of code it consists of.
For this reason the first feature we want for our project is to get the number of source lines for each of the files provided in the input. The project is already setup to automatically give us the input directory which we want to analyze so your task is to write the code which can count the number of source code lines.

The output should be a Map containing the filename of each provided Java file as key and and `Output` element as value. The Output should contain the number of source lines of code of the file. (SLOC: lines of code excluding empty lines and lines containing comments).

Example Code:
```Java
Map<String, Output> result = new HashMap<String, Output>();
result.put("file1.java", new Output(123, null));
```

### 2. Analyze the project dependencies
To better understand how the given projects are dependent on each other our tool should be able to detect, if a file/class is dependent on other files/classes. The goal is to be able to tell if a file is dependent on a file from another project either directly or indirectly through local dependencies.

This means that if `A/file1.java` is dependent on `A/file2.java` which is dependent on `B/file3.java`, both `A/file1.java` and `A/file2.java` are dependent on project `B`. 

To detect if a file has dependencies it is sufficient to look at the packages that are imported into the file. It is not nescessary to check if an import statement might be unused, as those could be removed as a first step before running the tool in the real world.

To make the output more readable please only provide a list of project names the file is dependent on and only projects contained inside the `CodeExamples` folder.
For this reason it is not nescessary to go through all dependencies, but it is enough to look at dependencies inside the `CodeExamples` folder. External dependencies to code outside the known three projects can be ignored.

Example Code:
```Java
Map<String, Output> result = new HashMap<String, Output>();
List<String> dependencies = new ArrayList<String>();
dependencies.add("spark");
result.put("file1.java", new Output(123, dependencies));
```

### 3. BONUS: Analyze the number of source lines excluding getters and setters
In Java programming it is very common pattern to use getters and setters in classes for the properties of that class. The code for these getters and setters is mostly generated automatically by the IDE, so counting them as source code lines might be missleading in an analysis of the results. That's why it makes sense  to add an additional field to our output counting the source lines of code without these code blocks.

The resulting lines should follow the rules of task 1, which means empty lines and comments do not count into the result and additionally we remove lines which are part of a getter or a setter.

---

## Project Setup
### Eclipse
1. Setup your GitHub classroom assignment and clone the created project
2. In Eclipse go to File > Import...
3. Select Maven > Existing Maven Projects
4. Select the cloned repository containing the `pom.xml`
5. Wait for Maven to setup the project and start editing the file `SourceCodeAnalyzer.java`

### IntelliJ
1. Setup your GitHub classroom assignment and clone the created project
2. In IntelliJ go to File > Open...
3. Select the `pom.xml` file from the cloned repository found in `Implementation/pom.xml` and open it as project
4. Wait for Maven to setup the project and start editing the file `SourceCodeAnalyzer.java`

---
## Running the project
Running the project without arguments selects the `CodeExamples` folder by default. If you want to test your implementation against some other code you can specify a path to that using `-i <path>`
### Running in the shell
1. Make sure Maven and Java are installed and registered as path variable 
2. `mvn clean package`
3. `java -jar .\target\SourceCodeAnalyser-1.0-SNAPSHOT.jar`

### Running in Eclipse 
1. Right click `SourceCodeAnalyzer.java` and click Run As > Java Application (Alt+Shift+X > J)
### Running in IntelliJ
1. Right click `SourceCodeAnalyzer.java` and click Run (Ctrl+Shift+F10)


