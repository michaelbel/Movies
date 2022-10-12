<!------------------------------------------------------------------------------------------------------>
<img src="screenshots/mockup2.png"/>
<img src="../master/icons/ic_launcher_playstore.png" width="192" align="right" hspace="0"/>

Movies
=

[![check-pr-badge](https://github.com/michaelbel/movies/actions/workflows/check_pr.yml/badge.svg?branch=develop)](https://github.com/michaelbel/movies/actions/workflows/check_pr.yml)
[![code-size-badge](https://img.shields.io/github/languages/code-size/michaelbel/android-app-template?color=FF5252)]()
[![paypal-badge](https://img.shields.io/badge/Donate-Paypal-FF5252.svg)](https://paypal.me/michaelbel)
[![last-commit-badge](https://img.shields.io/github/last-commit/michaelbel/moviemade?color=FF5252)](https://github.com/michaelbel/moviemade/commits)

Movies - easy way to discover popular movies. This is a simple TMDb client for Android with material design.

## Build
Take a look at <b>`local.properties`</b> and fill it with [your own](https://developers.themoviedb.org/3/getting-started/introduction) <b>tmdb_api_key</b> like this:
```gradle
TMDB_API_KEY=your_own_tmdb_api_key
```

## Download
[<img src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" alt="" height="80">](https://play.google.com/store/apps/details?id=org.michaelbel.moviemade)
[<img src="screenshots/direct-apk.png" alt="" height="80">](https://github.com/michaelbel/Moviemade/releases/download/1.3.1/moviemade-v1.3.1-release.apk)

## Screenshots
<div style="dispaly:flex">
    <img src="screenshots/screen1.png" width="32%">
    <img src="screenshots/screen2.png" width="32%">
    <img src="screenshots/screen3.png" width="32%">
    <img src="screenshots/screen4.png" width="32%">
    <img src="screenshots/screen5.png" width="32%">
    <img src="screenshots/screen6.png" width="32%">
</div>

## Technologies

- [x] Multi-module project
- [x] MVVM
- [x] [TMDB API](https://developers.themoviedb.org/3/getting-started)
- [x] Gradle Kotlin DSL
- [x] Gradle Plugin 7.3.0
- [x] MinSDK 21
- [x] TargetSDK 33
- [x] CompileSDK 33
- [x] [Material3](https://m3.material.io)
- [x] Dark Theme
- [x] Dynamic Colors
- [x] [Themed App Icon](https://d.android.com/develop/ui/views/launch/icon_design_adaptive)
- [x] 100% Kotlin 1.7.20
- [x] 100% Jetpack Compose, No XML
- [x] [KotlinX Coroutines](https://github.com/Kotlin/kotlinx.coroutines) 1.6.4
- [x] [KotlinX Serialization](https://github.com/Kotlin/kotlinx.serialization) 1.4.0
- [x] [Dagger Hilt](https://github.com/google/dagger) 2.44
- [x] [OkHttp](https://github.com/square/okhttp) 4.10.0
- [x] [Retrofit](https://github.com/square/retrofit) 2.9.0
- [x] [Retrofit Kotlinx Converter Serialization](https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter) 0.8.0
- [x] [Chucker](https://github.com/ChuckerTeam/chucker) 3.5.2
- [x] [Coil](https://github.com/coil-kt/coil) 2.2.1
- [x] [DataStore](https://d.android.com/datastore) 1.0.0
- [x] [Startup](https://d.android.com/jetpack/androidx/releases/startup) 1.1.1
- [x] [Timber](https://github.com/JakeWharton/timber) 5.0.1
- [x] [Accompanist](https://github.com/google/accompanist) 0.25.1
- [x] [Navigation](https://d.android.com/guide/navigation) 2.5.2
- [x] [Paging](https://d.android.com/topic/libraries/architecture/paging/v3-overview)
- [x] [ConstraintLayout](https://d.android.com/develop/ui/views/layout/constraint-layout)
- [x] [ViewModel](https://d.android.com/topic/libraries/architecture/viewmodel)
- [x] [Lifecycle](https://d.android.com/topic/libraries/architecture/lifecycle) 2.6.0-alpha01
- [x] [Firebase Crashlytics](https://firebase.google.com/products/crashlytics) 18.2.13
- [x] [Firebase App Distribution](https://firebase.google.com/products/app-distribution) 3.0.3
- [x] [Firebase Remote Config](https://firebase.google.com/products/remote-config) 21.1.2
- [x] [App Shortcuts](https://d.android.com/develop/ui/views/launch/shortcuts)
- [x] [Dependabot](https://github.com/dependabot)
- [x] Github Actions CI/CD
- [x] Distribute App via Telegram Bot
- [ ] [Google Analytics for Firebase](https://firebase.google.com/products/analytics) 21.1.1
- [ ] [Room](https://d.android.com/training/data-storage/room)
- [ ] Deep Links
- [ ] [Clean Architecture](https://d.android.com/topic/architecture) (Interactor, Repository, UseCase)
- [ ] Unit Tests
- [ ] UI Tests
- [ ] [SplashScreen API](https://d.android.com/develop/ui/views/launch/splash-screen)
- [ ] [In-App Updates](https://d.android.com/guide/playcore/in-app-updates)
- [ ] [In-App Reviews](https://d.android.com/guide/playcore/in-app-review)
- [ ] [Google Admob](https://developers.google.com/admob)
- [ ] [Downloadable Fonts](https://d.android.com/develop/ui/views/text-and-emoji/downloadable-fonts)
- [ ] [WorkManager](https://d.android.google.cn/topic/libraries/architecture/workmanager)
- [ ] [Lint](https://d.android.com/studio/write/lint)
- [ ] [KtLint](https://github.com/pinterest/ktlint)
- [ ] [Detekt](https://github.com/detekt/detekt)
- [ ] [Spotless](https://github.com/diffplug/spotless)
- [ ] OAuth
- [ ] Animations
- [ ] Landscape Orientation
- [ ] Create Github Releases
- [ ] Upload Bundle to Google Play Console
- [ ] Gradle Version Catalog
- [ ] Baseline Profiles

## Issues
If you find any problems or would like to suggest a feature, please feel free to file an [issue](https://github.com/michaelbel/moviemade/issues).

## License
<a href="http://www.apache.org/licenses/LICENSE-2.0" target="_blank">
  <img alt="Apache License 2.0" src="screenshots/apache.png" height="110"/>
</a>

    Copyright 2017 Michael Bely

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
