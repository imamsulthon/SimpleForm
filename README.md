# SimpleForm
Simple registration form apps

Developer: Imam Sulthon

Libraries:
- [Hilt](https://dagger.dev/hilt/) - Hilt provides a standard way to incorporate Dagger dependency injection into an Android application.
- [Retrofit](https://square.github.io/retrofit/) - Type-safe http client
  and supports coroutines out of the box.
- [GSON](https://github.com/square/gson) - JSON Parser,used to parse
  requests on the data layer for Entities and understands Kotlin non-nullable
  and default parameters.
- [OkHttp Logging Interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md)
  Logs HTTP request and response data.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines.
- [Flows](https://developer.android.com/kotlin/flow)
  Flows are built on top of coroutines and can provide multiple values.
  A flow is conceptually a stream of data that can be computed asynchronously.

This App contains some page that following form task requirement
- Splash Activity
- Main Page Activity with 4 Optional buttons: 
  * Registration Page -> user fill registration page in sequential order (Personal Info -> Address Info -> Review All Info),
  * Registration page menu -> (disabled), user can fill registration info in parallel, 
  * Configuration -> (disabled) To config some basic behavior of this apps/features
  * Show All Data -> User can see all data that have been submitted to local database/remote API (repositories)