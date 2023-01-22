## HeroAPI

A simple spring boot rest service to get and create Heroes and Villains (JDK 11+ required).
It uses H2 (in-memory) database to store information. This is a demo project.
<br/><br/>
build project: `.\gradlew build`
<br/>
run project: `.\gradlew bootRun`
<br/>
access application: `localhost:8080`

### End points
```
/ - displays welcome message

GET:
/hero - retrieves all the heroes in the database
/hero?name={hero name} - retrieve a specific hero by given name

/villain - retrieves all the villains in the database
/villain?name={villain name} - retrieve a specific villain by given name

POST:
/villain - adds a new villain to the database, accepts data as JSON
/hero - adds a new hero to the database, accepts data as JSON

PUT
/villain/{villain Id} - updates this villain data, accepts data as JSON
/hero/{hero Id} - updates this hero data, accepts data as JSON
```

Example Villain JSON Request Body:
```
{
  villain_name: "Lex Luthor",
  real_identity: "Alexander Joseph Luthor",
  powers: "Expert engineer, Genius-level intellect",
  weaknesses: "Superman"
}
```

Example Hero JSON Request Body:
```
{
  hero_name: "Superman",
  real_identity: "Clark Kent",
  powers: "Flight, Super speed, Heat Vision, Super strength",
  weaknesses: "Kryponite"
}
```
