# Entertainment

Please build a Spring Boot project written in Kotlin that contains CRUD operations for movies and actors with json representation like the following example:

Result of GETting a movie:
{
    "id": 1,
    "title": "Pulp Fiction",
    "releaseDate": "1994–10-14",
    "actors": [
        {
            "id": 1,
            "name": "John Travolta"
        },
        {
            "id": 2,
            "name": "Uma Thurman"
        },
        {
            "id": 3,
            "name": "Samuel L. Jackson"
        }
    ]
}


Result of GETting an actor:
{
    "id": 1,
    "actor": "John Travolta"
}


- Store and retrieve data using a persistent storage solution as you see fit.
- Ensure that actors and movies are individually unique.
- An individual actor should not appear more than once in a movie.
- Ensure that endpoints return the appropriate return codes such as 400 for invalid input.
- Movie title and release date are required and the combination of these two fields  must be unique.
- Actor names must be unique.
- Each movie must have at least one actor.
- The list of actors must not be duplicated for the same movie.
- An Actor cannot be deleted if they are a part of any movie(s).
- Write unit tests as you deem necessary and ensure that edge cases are covered.

Code will be evaluated primarily on reliably meeting criteria, code organization, following S.O.L.I.D. principles, and ensuring reliability through testing.
