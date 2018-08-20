BACKGROUND
Sometimes items cannot be shipped to certain zip codes, and the rules for these restrictions are stored as a series of ranges of 5 digit codes. For example if the ranges are:

[94133,94133] [94200,94299] [94600,94699]

Then the item can be shipped to zip code 94199, 94300, and 65532, but cannot be shipped to 94133, 94650, 94230, 94600, or 94299.

Any item might be restricted based on multiple sets of these ranges obtained from multiple sources.

PROBLEM
Given a collection of 5-digit ZIP code ranges (each range includes both their upper and lower bounds), provide an algorithm that produces 
the minimum number of ranges required to represent the same restrictions as the input.

EXAMPLES:
If the input = [94133,94133] [94200,94299] [94600,94699]
Then the output should be = [94133,94133] [94200,94299] [94600,94699]

If the input = [94133,94133] [94200,94299] [94226,94399] 
Then the output should be = [94133,94133] [94200,94399]

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Requirement: JDK 1.5 and above

There are 2 files.

1) ZIPCodeRange.java - For quick and testing purpose, all the methods including main method written in this single file. Comments are added in each method.

2) input.txt - contains ZIP code range inputs. Give the ZIP code ranges in specific format in line by line. The format should be as below.  

[94133,94133]
[94200,94299]
[94226,94399]

If the format does not match, program will throw the error message and stop. 

3) Compile the program in command line if ZIPCodeRange.class file not exist

javac ZIPCodeRange.java

Note: Set the Java path if not already setup. In Command line , goto the folder where ZIPCodeRange.jaav exist and set path=%path%;D:\jdk1.7.0_17\bin; 
This is just an example. You need to point to your JDK path 

4) Run the program using the below command without argument. If there is no argument, program will execute and return just the excluded ZIP code ranges in the compressed format.

java ZIPCodeRange

5) Run the program with argument. If there is a argument, program will execute and return the excluded ZIP code ranges. 
Also it checks if the given input is valid or not. If the given ZIP code falls under excluded ranges, system will throw the error message.

java ZIPCodeRange 94230

6)  Test all the scenario by updating input parameters in the input.txt file.