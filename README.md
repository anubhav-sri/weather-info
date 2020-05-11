# weather-info
# Tech Stack
* Java 11
* Mockito
* Junit jupiter
* Mongodb
* Spring

# Functionalities Covered
* Calling external service to get the current weather.
* Saving the result in local store.
* Get the history of last 5 calls

# Running the project
`./gradlew run`
The application runs on port 8080. Can be accessed via http://localhost:8080/

* To get the current weather: http://localhost:8080/current?location=<city_name>
* To get the history: http://localhost:8080/history?location=<city_name>

# Areas of Improvement
The application doesn't handle the states for history. Which means, Berlin would be treated same as Berlin,de. In case there comes another city the name of Berlin in another country, it would still be treated as Berlin,De may be. Given time it could be fixed.
Also I have assumed that the possible values for weatherType - Cloud etc, are :
* RAINY
* CLEAR
* THUNDERSTORM
* DRIZZLE
* CLOUDS

Any other value will throw an exception.
