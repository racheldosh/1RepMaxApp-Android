What is this project?
---------------------------------------------------------------------------

This stand-alone Android App calculates the one rep max (1RM) from set of data for a workout.
It displays a graph of historical 1RM data.
Inspired by the Fitbod iOS app, which is an app whose UI/UX I love.

Data is formatted as below :
---------------------------------------------------------------------------
Date of workout (MMM dd YYYY), Exercise Name, Sets, Reps, Weight

Some assumptions about the data
---------------------------------------------------------------------------
The 1RM calculation assumes that the highest calculated 1RM of the day is the *best* 1RM of the day -- the 1RM is calculated per set, and the best calculated 1RM is saved as the best 1RM for the day.

What's in the code?
---------------------------------------------------------------------------
The Exercise class defines exercise name, date, and maxRep calculated during the set.

Future work:
---------------------------------------------------------------------------
reformat graphs, adds tests, run 1RM calculation on background thread


