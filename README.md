# simple-bayes-filter
The following is an implementation of a simple Bayes filter used to calculate the belief distribution of states of a mobile robot.

## Brief Description
The program computes the belief probability distribution of the states of a mobile robot. At each iterated time step, the program first computes the predcition probability distribution and then using the newly computed values calculates and prints out the belief probability distribution. The prediction takes into the account the action the robot takes and later computes the belief based on the measurements from the sensors. 

## Implementation 
The initial state distribution and probabilities are all declared in the initialize() method of the main class. The program is initially preloaded with a short Bayes Filter example from Probabilistic Robotics by Sebastian Thrun. 

## Getting Started
To run the program you will first have to clean and recompile the bytecode. To do so run the following commands:  
>make clean  
>make  

After compilation you can run the program by executing bytecode like so:
>java main  

The probability distribution and actions at each time step will be printed to std in.

## Authors
Nikita Kim
