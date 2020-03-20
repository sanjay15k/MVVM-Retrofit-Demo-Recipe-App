<img class="img-fluid text-center" src="https://user-images.githubusercontent.com/35829879/77058902-1d272080-69fc-11ea-9f81-e9e5cec06717.png"/>

# Food Recipe App - MVVM and Retrofit2
[![GitHub license](https://img.shields.io/badge/License-MIT-blue.svg)](https://github.com/sanjay15k/MVVM-Retrofit-Demo-Recipe-App/blob/master/LICENSE)

## About
The project is a primary food recipe searching android app. The app follows the most popular architecture - Model-View-ViewModel.
For consuming the rest API from <a href="https://spoonacular.com/food-api/" target="_blank">Spoonacular API</a>, I have used retrofit2.

<br>

## APP Screenshots
<div align="center">
<img width="250" height="450" class="img-fluid text-center" src="https://user-images.githubusercontent.com/35829879/77052502-eb5d8c00-69f2-11ea-97cd-d29cd8dcea03.jpg"/>&nbsp;&nbsp;&nbsp;&nbsp;
<img width="250" height="450" class="img-fluid text-center" src="https://user-images.githubusercontent.com/35829879/77060015-e7833700-69fd-11ea-8ca1-adcaf7925488.jpg"/>&nbsp;&nbsp;&nbsp;&nbsp;
<img width="250" height="450" class="img-fluid text-center" src="https://user-images.githubusercontent.com/35829879/77060059-fbc73400-69fd-11ea-88e0-54f96331eab3.jpg"/>&nbsp;&nbsp;&nbsp;&nbsp;
<img width="250" height="450" class="img-fluid text-center" src="https://user-images.githubusercontent.com/35829879/77060081-071a5f80-69fe-11ea-87c3-9b551a0f7381.jpg"/>&nbsp;&nbsp;&nbsp;&nbsp;
<img width="250" height="450" class="img-fluid text-center" src="https://user-images.githubusercontent.com/35829879/77060115-139eb800-69fe-11ea-806d-1eb0a90e6b71.jpg"/>&nbsp;&nbsp;&nbsp;&nbsp;
<img width="250" height="450" class="img-fluid text-center" src="https://user-images.githubusercontent.com/35829879/77060141-1dc0b680-69fe-11ea-98e0-6b184856c476.jpg"/>&nbsp;&nbsp;&nbsp;&nbsp;
</div>

<br>

## Built With 🛠
 - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
 - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
 - [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
 - [Gson Converter](https://github.com/google/gson) - A Converter which uses Gson for serialization to and from JSON.
 - [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android
 
## MVVM Architecture

<div class="text-center">
<img class="img-fluid text-center" src="https://miro.medium.com/max/960/1*KnYBBZIDDeg4zVDDEcLw2A.png"/>
</div>

<br>

## Features/Learnings
* Consuming(or communicating with) REST API using retrofit2.
* Uses MVVM architecture : Activity/Fragment(Views), ViewModel, Repository and ClientStructure.
* Singletons
* Used Observables, LiveData, MutableLiveData and MediatorLiveData.
* Displaying images using Glide.
* Uses Searchview for searching recipes.
* AppExecutors, creating and running Threads, Background Thread, ThreadPools etc
and a lot more...
<br>

## How to run?
* Download/Clone the project and open in Android studio.
* Build the apk.
* <b>NOTE: Please verify that your internet is working correctly before running the app.</b>

## Contact
If you need any help, you can connect with me.

Contact me @[LinkedIn](https://www.linkedin.com/in/sanjay15k/)

## License
```
MIT License

Copyright (c) 2020 Sanjay Kumar

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
