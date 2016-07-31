# ListOfThings app
This app is a contrived example to illustrate some recommended techniques for Android. It displays a list of things retrieved from an external source and cached to locally to disk.The external source implementation is currently just a mock object (see MockThingService), but could easily be connected to real sources using a retrofit implementation of ThingService.

<img src="art/list2.gif" style="width:210px;border:1px solid #eeeeee"/>
 
## The app demonstrates the following UI techniques: 
 20. List animations for refresh, remove, undo, and toggle view modes
 21. Shared element transitions between Activities - updated implementation of [this](https://github.com/toddway/MaterialTransitions)
 10. CollapsibleToolbarLayout with CoordinatorLayout
 22. Overscroll to return to previous Activity
 23. translucent system bars
 
## These architectural techniques:
1. [Uncle Bob's Clean Artchitecture](https://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html) with [SOLID principles](http://blog.cleancoder.com/uncle-bob/2016/01/04/ALittleArchitecture.html)
2. Separate app, core, and data modules to enforce independent layers 
2. Dev and Prod product flavors to keep essential non-production code out of production builds. 
3. [Dagger 2](https://engineering.circle.com/instrumentation-testing-with-dagger-mockito-and-espresso-f07b5f62a85b#.ssgoilb3y) for dependency injection
4. [RxJava](http://blog.danlew.net/2014/09/15/grokking-rxjava-part-1/) for asynchronous data operations
5. [Shelf](https://github.com/toddway/Shelf) for basic object disk caching
6. [Retrofit](http://square.github.io/retrofit/) for web service integration
9. Activities and Views _without of Fragments_.  [Based on this.](https://corner.squareup.com/2014/10/advocating-against-android-fragments.html)
7. Junit and Mockito for unit tests
8. Retrolambda for Java 8 lambda support
2. Example Interactors to isolate business cases
2. Example Repository and DataSource to demonstrate separation of caching policies, entity mapping, and external source integration
2. Injectable Mock DataSource (MockThingService)
10. BasePresenter to separate presentation work from Views (e.g. manage view-related Rx subscriptions)
14. Retrofit ServiceFactory
15. Rx Scheduler injection
16. Mocked delay (2 seconds) for Thing Service to demonstrate UI progress feedback
17. Switchable Environments
18. Git build integration
19. Global error handling
12. Image loading with Picasso



<img src="art/settings.gif" style="width:210px;border:1px solid #eeeeee"/>


License
-------

    Copyright 2015 Todd Way

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.






