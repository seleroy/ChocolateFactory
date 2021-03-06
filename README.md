# Chocolate Factory Challenge

An Implementation of the following Chocolate Factory

## Problem

A  group of friends from the Dublin visit the chocolate shop. The shop have different kind of chocolates and the friends decide to buy as many as 3 type of chocolate each if they are available to purchase. Unfortunately, shop have a  restriction that they can not sell more than one chocolate of the same type. So the showowner come up with the following scheme: They ask each person to write down a list of up to 10 chocoloates that they enjoyed and would be happy buying. With this information, please help the showowner maximize the number of chocolates that they can sell to the group of friends.

Input 
A two-column TSV file with the first column containing the ID (just a string) of a person and the second column the ID of the chocolate that they like. Here are  input data sets of increasing sizes. 

person_choc_1.txt


Output 
First line contains the number of chocolates sold in aggregate with your solution. Each subsequent line should be two columns, tab separated. The first column is an ID of a person and the second column should be the ID of the chocolate that they will buy.

Please check your work. Note that the IDs of the output second column should be unique since a single chocolates can not be sold to two people and an ID on the first column can appear at most three times since each person can only buy up to 3 chocolates.



### Project Hierarchy

The following files and folder are part of this zip file:

- src/com/slr/:  Source file of the code.
- resources: example input file which was provided and corresponding output file generated by the program.
- bin folder with the compiled class file.

### Prerequisites

You will need maven installed to be able to compile this application

### Installing

Test and build the application:

```
maven clean package
```

## Executing the jar file

The execution of the application requires an input file and generates an output file that can be
passed as argument of the execution.
If no arguments are passed, the default behaviour of the application is to:
- read the input file from resources/sample_input.txt.txt
- write the ouput file in resources/sample_input.txt.txt

```
java -jar ./target/ChocolateFactory-0.0.1-SNAPSHOT.jar [<absolute_path_to_input_file> <absolute_path_to_output_file>]
```

Note:
	The input file must exist
	The output file will be created by the program but the output folder must exist

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Contact



