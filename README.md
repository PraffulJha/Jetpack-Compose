# Kotlin Coroutines: Complete Notes

## Table of Contents
1. [Updating Values in Coroutines](#updating-values-in-coroutines)
2. [Shared Mutable State](#shared-mutable-state)
3. [Coroutine Scopes: coroutineScope vs supervisorScope](#coroutine-scopes-coroutinescope-vs-supervisorscope)
4. [Best Practices](#best-practices)

## Updating Values in Coroutines

When working with coroutines in Kotlin, updating values requires careful consideration to ensure thread safety and predictable behavior.

### Basic Value Updates

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    // Simple value in the same scope
    var counter = 0
    
    launch {
        delay(100)
        counter++ // Update the value
        println("Counter in coroutine: $counter")
    }
    
    println("Initial counter: $counter")
    delay(200)
    println("Final counter: $counter")
}
```

### Thread-Safe Updates

For shared state that might be accessed from different coroutines or threads:

```kotlin
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

fun main() = runBlocking {
    val atomicCounter = AtomicInteger(0)
    
    launch {
        delay(100)
        atomicCounter.incrementAndGet() // Thread-safe update
        println("Atomic counter in coroutine: ${atomicCounter.get()}")
    }
    
    println("Initial atomic counter: ${atomicCounter.get()}")
    delay(200)
    println("Final atomic counter: ${atomicCounter.get()}")
}
```

### State Flow for Reactive Updates

```kotlin
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CounterViewModel {
    private val _counterState = MutableStateFlow(0)
    val counterState = _counterState.asStateFlow()

    fun incrementCounter() {
        viewModelScope.launch {
            _counterState.value = _counterState.value + 1
        }
    }
}
```

## Shared Mutable State

When multiple coroutines access and modify the same variable, the outcome can be unpredictable due to timing and execution order.

### Example of Shared Mutable State Issues

```kotlin
suspend fun sharedStateExample() = coroutineScope {
    var counter = "Initial value"

    launch {
        delay(100)
        counter = "First update"
        println("First coroutine: $counter")
        
        launch {
           counter = "Nested update"
           delay(100)
           println("Nested coroutine: $counter") // Might not print "Nested update"
        }
        
        delay(100)
        counter = "Final update"
        println("First coroutine again: $counter")
    }
    
    println("Main scope: $counter") // Prints immediately
}
```

### Explanation of Execution Flow

Consider this example:

```kotlin
suspend fun test() = coroutineScope {
    var counter = "BC string hu main"

    launch {
        delay(100)
        counter = "Chal hatt be !!!"
        println(" launch ke andar :$counter")
        
        launch {
           counter = "Bache ko hatao bc!!"
           delay(100)
           println("bacha wla $counter")
           
           launch {
               println("Chud gaye guru !!!!")
           }
        }
        
        delay(100)
        counter = "Are bc main dadaJi"
        println(counter)
    }
    
    println(counter)
}
```

Output:
```
BC string hu main
launch ke andar :Chal hatt be !!!
Are bc main dadaJi
bacha wla Are bc main dadaJi
Chud gaye guru !!!!
```

Why this happens:
1. The last println in the main scope executes immediately, printing "BC string hu main"
2. After 100ms delay, the first launch block updates and prints "Chal hatt be !!!"
3. It then creates a nested launch that updates counter to "Bache ko hatao bc!!" and starts a delay
4. While nested launch is delayed, the parent coroutine continues, waits 100ms, updates counter to "Are bc main dadaJi" and prints it
5. When the nested coroutine's delay completes, it prints "bacha wla Are bc main dadaJi" (not "Bache ko hatao bc!!") because the value was changed by the parent coroutine
6. Finally, the innermost coroutine prints "Chud gaye guru !!!!"

This demonstrates how shared mutable state can lead to unexpected values depending on execution timing.

## Coroutine Scopes: coroutineScope vs supervisorScope

Both create a new coroutine scope, but they handle exceptions and cancellation differently.

### coroutineScope

```kotlin
suspend fun coroutineScopeExample() = coroutineScope {
    launch {
        println("Task 1 starting")
        throw RuntimeException("Task 1 failed")
    }
    
    launch {
        delay(100)
        println("Task 2 - This won't be printed because Task 1 failed")
    }
}
```

Key characteristics:
- Uses a regular `Job` internally
- If any child coroutine fails, all other children are cancelled
- Exception propagates to the parent
- "All or nothing" execution model

### supervisorScope

```kotlin
suspend fun supervisorScopeExample() = supervisorScope {
    launch {
        println("Task 1 starting")
        throw RuntimeException("Task 1 failed")
        // Only this coroutine is cancelled
    }
    
    launch {
        delay(100)
        println("Task 2 - This WILL be printed despite Task 1's failure")
    }
}
```

Key characteristics:
- Uses a `SupervisorJob` internally
- Child failures don't affect siblings
- Only the failing coroutine and its children are cancelled
- Parent still receives the exception
- Allows for partial success

### Complex supervisorScope Example

```kotlin
suspend fun testQ() = supervisorScope {
    println("hello guyz this superVisorScope 😁")
    launch {
        println("BC ek naya randi rona 🥹")
        launch {
            println("mera ek aur launda hai 😎")
            launch {
                println("main kuch nahi jaanta 🤨")
            }
            println("batayi na jaaye zuban se haalat mere jism jahan ko tumhari chahat !! 😍")
        }
        println("ke kitne mohabaat hai tumse jara pass aake toh dekho!! 😘")
    }
    launch {
        println("kitna bechain hoke tumse mila 🥰🥰🥰🥰🥰")
    }
}
```

- Each coroutine executes its println statements
- No delays, so execution is mostly sequential within each coroutine
- If any coroutine were to throw an exception, others would continue running

### When to Use Each

Use **coroutineScope** when:
- Tasks are interdependent
- Partial completion doesn't make sense
- You need "all or nothing" execution semantics

Use **supervisorScope** when:
- Tasks are independent
- Some tasks can fail without affecting others
- Partial success is acceptable
- Building fault-tolerant systems

## Best Practices

1. **Avoid shared mutable state** when possible
2. **Use thread-safe constructs** like `AtomicInteger`, `MutableStateFlow` when sharing state
3. **Choose the right scope** based on your error handling needs
4. **Consider structured concurrency** principles
5. **Be aware of timing issues** with delays and shared variables
6. **Use withContext** for changing dispatchers without creating new coroutines
7. **Handle exceptions explicitly** with try-catch or CoroutineExceptionHandler
