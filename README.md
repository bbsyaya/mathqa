# MathQA for Android

## Best Practices
Below are Android libraries that I have used throughout the project.
1. Dependency Injection
  - [Android Annotation Tool](https://github.com/androidannotations): faster android development using annotations. It provides powerful dependency injection tools between views, activity and fragments; simplifies multi-threading between background and UI threads; communicates shared preferences and event listeners; and much more.
  - [Parceler](https://github.com/johncarl81/parceler): serialize-deserialize Java objects.
2. REST APIs: [Retrofit2](https://github.com/square/retrofit), a HTTP client library. This library is used because it has better performance and relatively simple to use compared to other HTTP libraries.
2. Observer pattern: 
  - Combines [Retrofit2](https://github.com/JakeWharton/retrofit2-rxjava2-adapter) with [RXJava2](https://github.com/ReactiveX/RxAndroid) for REST APIs.
3. Incorporate Material Design during UI Development. Here, I made use of the following libraries to develop material design views a better user experience.
  - [Material Values](https://github.com/AoDevBlue/MaterialValues): Used for consistent color and sizes across Android UIs
  - [Flexible Adapters](https://github.com/davideas/FlexibleAdapter/): Used for developing expandable and list views.
  - [Floating Action Buttons](https://github.com/Clans/FloatingActionButton): Used for displaying different search input queries.
  - [Material Dialogs](https://github.com/afollestad/material-dialogs): Used for showing different search dialogs, and creating LaTeX input editor.
  - [Material Icons](https://github.com/mikepenz/Android-Iconics): Used for displaying icons representing certain user actions.
  - [Progress Activity](https://github.com/vlonjatg/progress-activity): Used for displaying progress to the user.
4. Runtime Permissions: Android version Marshmallow or later require permissions before allowing the app to use sensitive system data and features. Some of these sensitive features include camera and file system access which is used by MathQA during OCR. [Dexter](https://github.com/Karumi/Dexter) library is incorporated to handle these permissions.
5. Class Inheritance and Polymorphism. In this principle, a class may extend from a base object that have similar behaviours. In the project, I incorporate these two principles for developing customised Fragments and Activities, and also implementing both preprocessors and recognisers for OCR.

## Other Libraries
1. File Storage management: [SimpleStorage](https://github.com/sromku/android-simple-storage). This library is used to copy Tesseract trained models into the phone.
2. Tesseract fork for Android: [tess-two](https://github.com/rmtheis/tess-two) library

## Features (screenshot will be added soon):
### Models
- [X] Java object models: Java classes which are equivalent to the database scheme available in MathQA server.

### Network and Data Services
- [X] RestAPI with Retrofit2 and RxJava observables
  - [X] Progress related views for better UX
    - [X] loading spinner if a request is ongoing
    - [X] display error if there is an error or connection with the server cannot be established
    - [X] empty activity if there is no data available from the server's response
  - [X] REST API services for receiving object models from MathQA database server
  - [X] REST API services for searching mathematical questions

### Camera and OCR
- [X] Permission handling: Handles camera and file access permisssions for Android version Marshmallow or later.
- [X] OCR Related Features:
  - [X] Image Picker: select image from file album or image capture from camera
    - [X] Crop selected image
  - [X] Image Preprocessing with Catalano Framework or Leptonica
    - [X] Convert image into a grayscale bitmap
    - [X] Preprocess grayscale bitmaps using binary thresholding, edge detection, deskewing
  - [X] OCR with Tesseract: recognise text from preprocessed bitmaps
    - [X] Copying trained Tesseract data models to phone.
  - [X] OCR with Google Text API: recognise text from grayscale bitmaps


### Views
- [X] ExpandableViews using Flexible Adapters: header items + content preview subitems
- [X] ListViews using Flexible Adapters: display list of items
- [X] DetailViews using Android activities, Fragments and ViewPager
- [X] LaTeX rendering using KaTeX, alternative views if there is syntax errors
- [X] **Formula LaTeX Dialog Editor**
- [X] SearchResults: display question results from user's query
- [X] Search Related Features:
  - [X] Search dialogs:
    - [X] Text input query
    - [X] LaTeX input query
    - [X] Image picker
