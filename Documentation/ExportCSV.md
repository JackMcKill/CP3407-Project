# ExportCSV

## Creating file
After the user presses the export button we begin the process of creating the .csv file. This file has a title of the date and the City name separated by a hyphen.
Using the [WeatherReportModel](WeatherReportModel.java) class we create a list of statistics to save
This list is then pushed into the ICsvListWriter along with a header.


## Saving file
Still subject to review. Need a better way to do this

## Sharing the file
We use the default android Share Tray to be able to share the .csv file created by using a new intent in [SingleWeatherReportActivity](SingleWeatherReportActivity.java)

