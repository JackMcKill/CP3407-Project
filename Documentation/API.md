# API
## API Overview
### Volley 
In order to make network requests to our weather data provider [metaweather.com](https://www.
metaweather.com), our app is utilizing the [Volley HTTP Library](https://developer.android.com/training/volley). At a high-level, Volley is used by creating a `RequestQueue` and passing it `Request` objects.

### Setup
Volley is added to the `build.gradle` file, and is automatically added to the project at the next *Gradle Sync*.  
```groovy
dependencies {
    implementation 'com.android.volley:volley:1.2.1'
}
```

### Components
There are 2 components involved in making an API call to fetch data from [metaweather.com](https://www.metaweather.com).  

1. The `WeatherDataService` class  
This class holds multiple different API calls, each for returning a different piece of information, and is used as a layer of abstraction to simplify the process of making a network request.   
2. The `RequestQueue` / `Singleton`  
We have created a `RequestQueue` class following the [Singleton Design Pattern](https://developer.android.com/training/volley/requestqueue#java). This class is responsible for creating a `RequestQueue`, and ensuring that no more than one is ever created. This is important to ensure multiple `RequestQueue`'s are NOT created, and all requests are channeled through the one `RequestQueue`. 

### Usage
First, a `WeatherDataService` object is created to manage the API calls. 
```java
WeatherDataService weatherDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiactivity);

        weatherDataService = new WeatherDataService(APIactivity.this);
    }
```
When an API call needs to be made, it is done via the `WeatherDataService` methods:
```java
weatherDataService.getCityForecastByName("Brisbane", new WeatherDataService.ForecastByNameCallback() {
    @Override
    public void onError(String errorMessage) {
        // code to execute if the request fails
    }

    @Override
    public void onResponse(List<WeatherReportModel> weatherReportModels) {
        // code to execute if the request receives a response
        // weatherReportModels = a list of WeatherReportModels
    }
});
```
In this case, the code is trying to get the weather forecast for Brisbane. First, the `getCityForecastByName` method is called, and the city name of `"Brisbane"` is passed as the first parameter. The second parameter that is passed in is a callback method that's been defined inside `WeaterDataService`. Creating a new `WeatherDataService.ForecastByNameCallback()` will auto-complete the `@Override` methods, where we just need to define what happens in the event of a response, or an error. By creating this layer of abstraction, it keeps our codebase clean and simple to understand when making API calls. All methods inside `WeatherDataService` have an accompanying callback method/response listener.