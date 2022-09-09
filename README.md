# slack-assignment
Slack assignment to search user by keyword

Note: I have created new project from scratch as the project provided is using older libs and not working with Mac M1, due to the timing issues, I though it would be best to create new on my system.Plesse let me know if this is not acceptable, I will update the provided project accordingly. Thanks!  


[![CodeStyle](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![Kotlin Version](https://img.shields.io/badge/kotlin-1.7.10-blue.svg)](http://kotlinlang.org/)


## Table of Contents

-   [Project Description](#project-description)
-   [Development](#development)
-   [Deployment](#deployment)
-   [Tech-stack](#tech-stack)
-   [Credits](#credits)

## Project Description
Requirement doc -> https://docs.google.com/document/d/1WMb2KrYUTsk4PeyL3ZRmuyrAe7VjPCbZNngprXq5By0/edit

* When user install the app for the first time, a prepopulated db containing denylist keywords will be installed in the user device. And all the new search request will first pass through the denylist database.

* When user tries to search a keyword, debounce search query listener will kick in and hit the repository to find out what should be the response viewModel should send back to view. At the repository layer, app will first check if the keyword is part of denylist table, if yes then return "Username does not exist error" as should in screenshot.

* If the keyword is not part of existing denylist table then hit api and proceed with the response as such :
  * If `ok==true` and `user list not empty` -> Save the user to database for offline access and display then.
  * If `ok==false && error=='Not found'` : It means the keyword is not allowed -> Save the keyword to denylist table and display error to user.
  * If api gives no internet error -> Search the existing user details table and send back no internet error with cached data to display.
  
  
### Trying to search when no internet and keyword is not part of denylist:
<img src="https://user-images.githubusercontent.com/7509084/188776040-11a98279-69b0-4454-88c5-92db4b873eaa.png" width="40%"/>


### When there is NO internet and user is trying to search keyword which is part of denylist: 
<img src="https://user-images.githubusercontent.com/7509084/188776057-07657900-d6c5-4ca0-a489-23bbfcf18a15.png" width="40%"/> 

### When there is internet access and user is trying to search keyword which is part of denylist:
<img src="https://user-images.githubusercontent.com/7509084/188776063-0ae10e72-91eb-4c8c-9bda-81b27a843f18.png" width="40%"/>

### Trying to search when there is internet and keyword is not part of denylist:
<img src="https://user-images.githubusercontent.com/7509084/188776069-6666a4de-3a09-4afd-ace8-6a8ee6aa37cb.png" width="40%"/> 

### Trying to search when there is no internet and keyword is not part of denylist
<img src="https://user-images.githubusercontent.com/7509084/188776080-3d360e02-97f4-4447-a656-c14b928a8534.png" width="40%"/>


## Tech-stack

The project follows MVVM arhitecture, Android Arhitecture Component pattens and Single Activity pattens.
- [MVVM](https://developer.android.com/topic/architecture?gclid=Cj0KCQjwyOuYBhCGARIsAIdGQRN_OGJN0CwW_QnepirHZcz7T2cAhgimWn4Rjv_atQKMndWOVz2Ji7UaAjwIEALw_wcB&gclsrc=aw.ds#recommended-app-arch)



You can check which dependencies need to be updated by running:
```bash
./gradlew dependencyUpdates
```
### Dependencies

-   [Jetpack](https://developer.android.com/jetpack):
    -   [Android KTX](https://developer.android.com/kotlin/ktx.html) - Write more concise, idiomatic Kotlin code.
    -   [Navigation](https://developer.android.com/guide/navigation/) - Helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.
    -   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
    -   [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
    -   [Room](https://developer.android.com/jetpack/androidx/releases/room) - To provide offline capabilities for user search. 
-   [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API which provides utility on top of Android's normal Log class.
-   [Koin](https://insert-koin.io/) - A pragmatic lightweight dependency injection framework for Kotlin.
-   [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Manage background threads with simplified code and reduce needs for callbacks.
-   [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android.
-   [Kotlin Serializer](https://kotlinlang.org/docs/serialization.html) - A modern JSON library for Kotlin.
-   [Coil](https://github.com/coil-kt/coil) - Image loading for Android backed by Kotlin Coroutines.
