#Locus Weather App
##Task Description 
Take user input for latitude and longitude. Show the atmospheric pressure at 4am for the
past 3 days. If you choose to build a script for command line execution, format the
terminal output to be clearly readable. Write documentation about your code in the
README file about the dependencies, logic of your code, and instructions to run the
program.

##Summary
This is a locus weather application written in Java.Using:
1. HttpUrlConnection to fetch the Api responses.
2. ExecutorService to make concurrent calls.
3. Java Calendar for date operation.
4. Jackson for Json deserialisation.

We are using below openweather api to fetch the weather data.
>https://api.openweathermap.org/data/2.5/onecall/timemachine?lat={lat}&lon={lon}&dt={time}&appid={API_key}

## Project Dependencies
1. Java8+
2. Jackson

## Logic of the Code
1. Create custom object for required API responses.
2. Create custom object for Output.
3. Create a class Task1.java with methods to fetch required fieds.
4. Create a method to fetch last 3 days timestamp at 4 am.
5. Iterate over the timestamps and invoke method to connect the weather API.
6. Run executor service method to fetch parallel response from the 3 api calls.
7. Once the weather data  is received as API reponse for past 3 days, iterate over list of API response and set date and atmospheric pressure values of Output.
8. Return the output object to main.


##How to run the application
1. Download the application jar.
2. Get the API key from openweatherapi.org
3. Run java -cp <_PATH_TO_JARFILE_> locus.Main <_API_KEY_> <_LATITUDE_> <_LONGITUDE_> 
   >sample: java -cp D:\LocusWeatherApp\LocusWeatherApp.jar locus.Main demo_api_key 60.99 30.9
                                                                                  
  