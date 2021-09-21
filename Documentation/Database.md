# Database

## Local database storage

### Room Library
Our app uses an SQLite database to provide local persistent storage. We are using the [The Room Persistence Library](https://developer.android.com/training/data-storage/room), which provides an abstraction layer over SQLite. Room provides the following benefits:
* Compile-time verification of SQL queries
* Convenience annotations that minimize repetitive and error-prone boilerplate code
* Streamlined database migration paths

### Setup
Room is added to the `build.gradle` file, and is automatically added to the project at the next *Gradle Sync*.  
```java
// Room dependency used for SQLite database
    def room_version = "2.3.0"
    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"
    androidTestImplementation "androidx.room:room-testing:$room_version"
```

### Components
There are four main components in our Room database:
* The database class (`WeatherReportDatabase.java`)  
This class holds the database, and serves as the main access point for the underlying connection to our app's data. We are following the Singleton Design Pattern when instantiating a `WeatherReportDatabase` object.
* The Data Entity class (`WeatherReportModel.java`)  
This class represents the table in our apps database. Rather than creating a new class who's only purpose was to act as a Data Entity Class, we were able to use the existing `WeatherReportModel` class by just adding the correct Room annotations to it.
* The Data Access Object (DAO) (`WeatherReportDao.java`)  
This object provides methods that our app can use to query, update, insert and delete data in our database.
* The ViewModel (`WeatherReportViewModel.java`)  
This class is responsible for preparing and managing the database data for the activity that wants the database data.

### Usage
First, a `WeatherReportViewModel` object is created to manage the data between the database and the activity:
```java
// This is called inside whichever activity wants to access the database
WeatherReportViewModel viewModel;
viewModel = new ViewModelProvider(this).get(WeatherReportViewModel.class);
```
Inside the `WeatherReportViewModel` code, an instance of the database and the DAO are created:
```java
weatherDB = WeatherReportDatabase.getDatabase(application); 
weatherReportDao = weatherDB.weatherReportDao(); 
```

This hides away the complexities of interacting with the SQLite database behind a layer of abstraction, and the `ViewModel` handles the data:
```java
viewModel.insert(someWeatherReportModel);
```


## External database storage
*Harper to complete this section*