# Demo-Android-Data
Different ways to store (and retrieve) data on Android

## Shared Preferences (`sharedprefs`)
Using the Android SharedPreferences mechanic to store data in what are essentially name-value pairs. The nice thing about this approach is that Android has some baked-in mechanisms for viewing/editing this data, making it an excellent choice for application-wide settings.

## Simple File I/O (`fileio`)
Open and close a file stream on Android. We use a `java.util.Properties` instance to give the file some simple structure and formatting, as is common with a lot of Java projects.

## Simple File JSON (`filejson`)
Same as `fileio`, except we use JSON storage for the data being stored. This is to show off how we combine local file storage and the use of the JSON classes Android provides.

## Serialized-Object Files (`serial`)
Java's Object Serialization Specification, while deeply maligned by many in the Java community, makes for a simple-yet-powerful way to store/load complex object graphs or collections to disk.

## SQLite (`sqlite`)
Data often finds itself stored in a relational database, and data on Android can do the same if desired; as a matter of fact, much of the data that is stored by the pre-installed apps is stored in SQLite databases, which have been an option on Android since API 1. This sample shows how to use SQLite to write SQL directly; other classes in the Android library can abstract away the SQL-ness, but it's generally useful to know how to run a SQL statement directly (and harvest the results).

Note that using SQLite to store "servername" and "port" is really overkill and somewhat clumsy; this will be true for just about any "database", but still serves to show how to connect to the database, issue a query, and harvest the results.

## MongoDB (`mongodb`)
One of the popular "NoSQL" databases that challenge the relational database for ownership of data in the server room, MongoDB is a document database that uses a binary protcol (BSON) to communicate between client and server. This sample shows how to use the MongoDB Kotlin (coroutine) driver--which uses a binary TCP/IP protocol, not HTTP/REST--to connect to a MongoDB instance running on your local laptop. To work against a cloud-hosted database, all that should need to change is the `mongodb` URL in the MainActivity. MongoDB must be running and available (on the default port, 27017) to the emulator for this sample to work--if it isn't, the app will crash.

Note that using MongoDB to store "servername" and "port" is really overkill and somewhat clumsy; this will be true for just about any "database", but still serves to show how to connect to the database, issue a query, and harvest the results.
