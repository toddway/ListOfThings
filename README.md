# ListOfThings app
This app is a contrived example to illustrate some best practices for Android architecture.  
It displays a list of things retrieved from an external source and cached to locally to disk.  
The external source implementation is currently just a mock object (see MockThingService), 
but could easily be connected to real sources using a retrofit implementation of ThingService.

  
 
## The app demonstrates the following:
1. [Uncle Bob's Clean Artchitecture](https://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html)
2. Separate app, core, and data modules to enforce layered architecture 
2. Dev and Prod product flavors to keep essential non-production code out of production builds. 
3. Dagger 2 for dependency injection
4. RxJava for asynchronous data operations
5. Shelf for basic object disk caching
6. Retrofit for web service integration
7. Junit and Mockito for unit tests
8. Retrolambda for Java 8 lambda support
2. Example Interactors to isolate business use cases
2. Example Repository and DataSource to demonstrate separation of caching, entity mapping, and external source integration
2. Injectable Mock DataSource (MockThingService)
9. Custom views instead of Fragments.  Based on [this](https://corner.squareup.com/2014/10/advocating-against-android-fragments.html) 
10. BasePresenter to separate presentation work from Views (e.g. manage view-related Rx subscriptions)
10. AppCompat toolbar
11. Coordinator layout example with quick-return toolbar
12. RecyclerView implementation



##  TODO:
1. Switchable Environments
2. Retrofit ServiceFactory
3. Rx Scheduler injection


