# Kotlin to JavaScript Transpiler

## What project does
This project is a transpiler that translates code from Kotlin data classes to ES6 JavaScript classes
and Kotlin enums to JavaScript objects.

## Background
The interaction between Kotlin server side code and for example MongoDB is today smooth sailing. 
You write your Kotlin code and Mongo documents are created without having to create the same data structures in both Kotlin and the database.

In the client side layer things are not as smooth. 
If you want to have JavaScript classes that represent the data that comes from the server you have to manually create these classes.
Writing simple JavaScript classes that represents data is tedious and error prone. 
Manually updating stuff that are in more than one place always breaks, sooner rather than later.

## Problem to solve
The problem this project solves is to be able to have the same data classes server and client side. 

## How the problem is solved
- Compile time, Kotlin data classes are transpiled to ES6 JavaScript classes and Kotlin enums
 are transpiled to JavaScript objects.
- Runtime, a class on either server or client side is serialized to JSON and sent to the other 
layer and deserialized to a class. 

## Supported
- Converts Kotlin data classes to JavaScript classes
- Converts Kotlin enums to JavaScript objects
- Classes can have properties of types:
  - primitives (i.e. String, Int, Float, Long, Double and Boolean)
  - java.lang.Instant (see section below about Support for Instant)
  - classes of other Kotlin data classes in the same directory
  - lists of:
     - primitives
     - classes of other transpiled Kotlin data classes
     - simple enum without data
- Enum can have properties of types:
  - String and Int
- Kotlin property names cannot start with "is", "get" or "set". 
This as these prefixes are removed by default by for example Jackson when serializing to JSON.
This will then cause a problem when converting JSON to Kotlin data classes.


## Instructions
- To have the transpiler compile a class or an enum add the annotation
 `@JsTranspiler_CompileToJavaScript` to the class
- To have setter methods in JavaScript annotate the Kotlin property with `@JsTranspiler_CreateSetter`
- Add the Jackson annotation `@JsonIgnore` and that property will not be present in the JavaScript 
class.

## Sample code

```kotlin
@JsTranspiler_CompileToJavaScript
data class Person(
        val age: Int,
        @JsTranspiler_CreateSetter 
        val firstName: String,
        @JsTranspiler_CreateSetter 
        val lastName: String,
        @JsonIgnore 
        val iq: Int)
```

The below compiles kotlin code in the argument dir to JavaScript code in the argument file.
```kotlin
    JsTranspiler(
        sourcePackageName = "io.schinzel.jstranspiler.example.dataclasses.dir",
        destinationFile = "src/main/resources/my_site/js/classes.js"
)
```


## Sample
The code in the repo contains sample code. The code is found in the test source in
the directory `io.schinzel.jstranspiler.example`.
1. Run the main function in `GenerateJavaScript.kt`.
This reads a set of Kotlin data classes and enums and converts them to JavaScript code.
The Kotlin code compiled is found in package `example.dataclasses`.
The generated JavaScript code is written to `resources/my_site/js/classes.js`.
2. Start a web server by running the main function in file `WebServer.kt`
3. In a browser open http://localhost:5555/.
The page will retrieve a serialized Kotlin data class Person instance from the server.
The retrieved JSON will be used to create an instance of the auto generated JavaScript Person class.
Data is changed in the browser, and the JavaScript classes are serialized to JSON and sent to server.
On the server, the JSON is deserialized back to the Person object. 

## Support for java.lang.Instant
To be able to transpile Instants to JavaScript, you need to Kotlin how you want an Instant to be 
represented in string. You do this by adding support in your object serialization. An example can 
be found in file `example/misc/Serialization.kt`.

## Maven
```xml
<repositories>
	<repository>
		<id>maven-repo.schinzel.io</id>
		<url>http://maven-repo.schinzel.io/release</url>
	</repository>
</repositories>    
```

```xml
<dependencies>
	<dependency>
		<groupId>io.schinzel</groupId>
		<artifactId>js-transpiler</artifactId>
		<version>1.4</version>
	</dependency>
</dependencies>    
```

## Version history
### 1.4
_2020-0X-XX_
- `KotlinClass` and `KotlinEnum` are now public classes so that these can be invoked directly to 
generate JavaScript without using annotations.
- `KotlinClass` and `KotlinEnum` accept Java classes as arguments. Note, annotations
do not work on Java classes.
- Support for snake case properties added. A snake case property for example `first_name` will
get be transpile to camel case, i.e. `getFirstName` and `setFirstName(firstName)` 
### 1.3.3
_2020-08-13_
- The order of the transpiled classes is consistent. This so that new versions of the generated
file are not created when nothing has changed.   
### 1.3.2
_2020-08-01_
- Moved sample code to test source. This so that dependency to Atexpose does not become 
a transitive dependency. 
### 1.3.1
_2020-07-26_
- Bug Fix: If there is no enum in package or subpackages "SubTypeScanner was not configured" is 
thrown.
### 1.3
_2020-07-22_
- Enum properties of types String and Int are now transpiled.
- To get the string representation of an enum in JavaScript has changed 
from `Species.DOG` to `Species.DOG.name`
### 1.2
_2020-07-15_
- Instead of setting a set of source package to scan for code to transpile, one source package 
is set and it and all it's subpackages are scanned for code to transpile
- `JsTranspilerBuilder` removed. Use `JsTranspiler` instead.
- Outputs a report with the execution time and number of classes and enum transpiled. 
### 1.1
_2020-04-22_
- Added support for annotation JsonIgnore
### 1.0
_2019-07-25_
- Initial release

## Ideas 
- Remove support for Float, Double and Long?
- Is it possible to let boolean start with "is" and/or "has" instead of "get"
- add-methods when there is a setter for an array?
- Is slice deep enough? https://stackoverflow.com/questions/7486085/copy-array-by-value
