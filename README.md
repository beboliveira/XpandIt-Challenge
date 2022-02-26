#Xpand IT Backend Challenge

##How to RUN:
* mvn spring-boot:run

##How to RUN Tests:
* mvn test

### Api Endpoints
* GET movies/{id}
* POST movies/
* PUT movies/{id}
* DELETE movies/{id}
* GET movies/launchDate?="someDate"


##Some Disclaimers about the Solution:
There is a private method at the end of the MovieController file that is used to make some validations 
when the user tries to create/update a movie. The verifications are:
If the revenue is not negative, if the rank is between 0 and 10, and if the release date 
is no later than the current date.(Although we know release dates of movies that are coming out,
but for the purpose we have assumed that only movies that have already come out could be added).

The methods declared in MovieRepository are  Derived Query Methods from Spring JPA.
Which allows spring to derive the query we want to make from the method name.

###Model: Movie 
* title:String 
* launchDate: LocalDate 
* rank: float 
* revenue: int
  
I know that there is a difference between DAO and DTO.
That the DAO is more for abstracting everything related to the database
and the DTO is what the user knows and makes requests with.
But in this case I was in doubt if it was to distinguish between these 
two design patterns.


