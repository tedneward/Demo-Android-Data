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

