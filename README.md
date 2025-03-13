# State Management
- In this branch I have pratised about State in Jetpack Compose
## State in Jetpack Compose
**Introduction**
- An app's "state" is any value that can change over time.
All Android apps display state to the user. A few examples of state in Android apps are:

- The most recent messages received in a chat app.
- The user's profile photo.
- The scroll position in a list of items.
### Remeber
- Preventing value being change everytime recomposition is done
- Persisting value across the recomposition
![Example](http://res.cloudinary.com/dkxmrz3j0/image/upload/v1741893420/ritqakfyr6cbgeei2flk.jpg)
- example code  ```
var name by remember {
        mutableStateOf("")
    } ```
### RemeberSavable
- Allow value persist even screen rotate 
- example code 
``` 
 var email by rememberSaveable {
        mutableStateOf("")
    }
```
### Viewmodel 
- Hoist the state for reuseability
- example code
```
val password  by viewmodel.password.observeAsState("")

```
