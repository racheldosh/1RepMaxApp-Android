What is this project?
---------------------------------------------------------------------------

This stand-alone Android App calculates the one rep max (1RM) from set of data for a workout and displays in a vertical list of exercises.
Upon clicking any exercise, it displays a graph of historical 1RM data.

Inspired by the Fitbod iOS app, which is an app whose UI/UX I love.

How to run the project
-----
Download the project, then in Android Studio, run the app. The app will start up, and display a list of exercises.

Data is formatted as below :
---------------------------------------------------------------------------
Date of workout (MMM dd YYYY), Exercise Name, Sets, Reps, Weight

Some assumptions about the data
---------------------------------------------------------------------------
The 1RM calculation assumes that the highest calculated 1RM of the day is the *best* 1RM of the day -- the 1RM is calculated per set, and the best calculated 1RM is saved as the best 1RM for the day.

What's in the code?
---------------------------------------------------------------------------
1. The ```ExerciseListFragment``` handles the display of, to no surprise, the exercise list. It takes the input data, extracts the exercises, and calculates their 1RM. The 1RM is the greatest data point for each exercise: the "record." 
2. The ```GraphFragment``` handles the display of, once again to no surprise, the graphs. The ExerciseListFragment the 1RM value as well as an array of 1RM data points for each date to display in graphical form. 
3. The only type is an ```Exercise``` -- this represents an exercise (not a lot of shockers here). An Exercise is made up of three variables: the date of the exercise, the name of the exercise, and the one rep max for that date. 

Future work:
---------------------------------------------------------------------------
1. The graphs' formatting is sub-par and I would like to spend more time improving it.
2. Adding tests is always helpful!
3. The calculation of the 1RM could be run on a background thread.
4. Add more functionality! Other stats, for example. Or the ability to add new data. 


