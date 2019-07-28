App calculates 1RM from set of data (txt file labeled "data")
Displays graph of historical 1RM data

Assumes highest calculated 1RM of the day is the best 1RM of the day -- the rep max is calculated per set, and the best calculated 1RM is saved as the rep max for the day

Data in the format :
Date (MMM dd YYYY) of workout, Exercise Name, Sets, Reps, Weight

Exercise class defines exercise name, date, and maxRep calculated during the set

Future work: reformat graphs, adds tests, run 1RM calculation on background thread


