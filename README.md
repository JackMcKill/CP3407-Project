# CP3407-Project
 JCU CP3407 Advanced Software Engineering Project

## Description
This is repo contains the source code for our mobile app, developed for the JCU CP3407 Advanced Software Engineering class of 2021. The project is based on the [Wilderness Weather Station](https://iansommerville.com/software-engineering-book/case-studies/weather/) case study, created by Ian Sommerville.

### Project Brief
Our task is to design, develop, test and deploy a weather monitoring mobile application. Our target audience are users that live remotely, or in a location that has unreliable internet connectivity.  

The first key requirement of our project brief is that the app must be useful to the user even without an internet connection. This is achieved by saving weather information locally, so that the user can access weather information even without an internet connection.

The second key requirement is the ability to save weather information to an external cloud-based database, that the user can access whenever they want (with an internet connection). 

### Problem Solution
Our solution is centered around using a local database that exists on the users phone, and is updated whenever the app has an internet connection. In order to work effectively, the user should be able to connect to the internet once per day, at a *minimum*. This allows for the app to receive the latest weather information at that time and save it on the phone.  

An example of where this is useful, could be someone that works in a remote area during the day, but lives in a location with some form of internet connection. In this situation the user's app can receive and store the latest weather information for that day in the morning before work. Then, once the user is at work with no internet connection, they can still check their phone to see what the weather was forecast to be (based off of data received at the last point in time there was an internet connection). This ability to save and recall weather information even without an internet connection is what sets this application apart from other similar products.  

In order to meet the second requirement, our app automatically manages the two databases (the local database, and the external database), and keeps them in sync whenever the app is connected to the internet. A check is made every time the app has an internet connection, to see whether the external database is "out of sync" with the local database, and if so, will update the external database with the relevant information.

## Useful links
* [Documentation](/Documentation) — A detailed description of the technical aspects of our app (including dependencies and third-party libraries)
* [Contributing Guidelines](/Contributing.md) — Our guidelines around contributing to this project. Our code-style and guidelines are outlined here.

## Contributors
* [Caleb Webster](https://github.com/CalebWebsterJCU)
* [Harper Thurlow](https://github.com/HarperThurlow)
* [Caroline Hao](https://github.com/CarolineYHao)
* [Jack McGill](https://github.com/JackMcKill)
* [☕️☕️☕️](https://nerdlettering.com/blogs/articles/why-do-programmers-love-coffee)

Interested in contributing? Visit the [contributing](/Contributing.md) page to find out more.