# ExportCSV

## Creating file
After the user presses the export button we begin the process of creating the .csv file. This file has a title of the date and the City name separated by a hyphen.
Using the [WeatherReportModel](WeatherReportModel.java) class we create a list of statistics to save
This list is then pushed into the ICsvListWriter along with a header.
We use the dependency [SuperCsv](http://super-csv.github.io/super-csv/) to be able to export our data into csv format.


## Saving file
File is saved in "data/data/com.cp3407.wildernessweather" with the SingleWeatherReports City name + "-" + SingleWeatherReports applicable data + ".csv"
This file will be overrided anytime someone presses the export button again (most likely with a new "the_temp")

## Sharing the file
We use the default android Share Tray to be able to share the .csv file created by using a new intent in [SingleWeatherReportActivity](SingleWeatherReportActivity.java)
We have also created a callback method to wait for the .csv file to be completly created.

