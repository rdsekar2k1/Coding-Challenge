REQUIREMENT Sometimes items cannot be shipped to certain zip codes, and the rules for these restrictions are stored as a series of ranges of 5 digit codes. For example if the ranges are:

[94133,94133] [94200,94299] [94600,94699]

Then the item can be shipped to zip code 94199, 94300, and 65532, but cannot be shipped to 94133, 94650, 94230, 94600, or 94299.

Any item might be restricted based on multiple sets of these ranges obtained from multiple sources.

PROBLEM Given a collection of 5-digit ZIP code ranges (each range includes both their upper and lower bounds), provide an algorithm that produces the minimum number of ranges required to represent the same restrictions as the input.

EXAMPLES: If the input = [94133,94133] [94200,94299] [94600,94699] Then the output should be = [94133,94133] [94200,94299] [94600,94699]

If the input = [94133,94133] [94200,94299] [94226,94399] Then the output should be = [94133,94133] [94200,94399]

HOW to USE?

Use JDK 1.5 and above

ZIPCodeRangeMain.java under src/main folder - This is main program. Run this program as Java application

restriction.txt - contains restricted ZIP code ranges. Give the ZIP code range in specific format in line by line. The format should be as below.

[94133,94133] [94200,94299] [94226,94399] If the format does not match, system will throw the error message and stop.

Run the main class without argument. If there is no argument, program will execute and return just the excluded ZIP code ranges

Run the main class with argument. If there is a argument, program will execute and return the excluded ZIP code ranges. Also it checks if the given input is valid or not. If the given ZIP code falls under excluded ranges, system will return Not valid message.

Also this project has JUnit test (ZIPCodeRangeUnitTest.java) where we can write multiple unit test case for automate testing.
