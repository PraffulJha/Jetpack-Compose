## 🧠 Kotlin Coroutines: `async`, `launch`, `await`, and `Unit` Pitfalls

---

### 🔹 `launch` vs `async`

| Feature              | `launch`                        | `async`                         |
|----------------------|----------------------------------|----------------------------------|
| Return Type          | `Job`                            | `Deferred<T>`                    |
| Use Case             | Fire-and-forget background tasks | Need to return a value           |
| Exception Handling   | Caught via `join()`              | Caught via `await()`             |

---

### ✅ `launch` Example

```kotlin
val job = CoroutineScope(Dispatchers.Default).launch {
    delay(1000)
    println("Hello from launch")
}
job.join()
```

**Output:**
```
Hello from launch
```

---

### ✅ `async` + `await` Example

```kotlin
val result = CoroutineScope(Dispatchers.Default).async {
    delay(1000)
    "Hello from async"
}.await()

println(result)
```

**Output:**
```
Hello from async
```

---

### ❌ Common Mistake: Ignoring Return Value

```kotlin
val result = CoroutineScope(Dispatchers.Default).async {
    delay(1000)
    "This will be ignored"
    println("Last line = Unit")
}.await()

println(result)
```

**Output:**
```
Last line = Unit
kotlin.Unit
```

---

### ✅ Fix: Return Value as Last Expression

```kotlin
val result = CoroutineScope(Dispatchers.Default).async {
    delay(1000)
    "This will be returned"
}.await()

println(result)
```

**Output:**
```
This will be returned
```

---

### 🔁 Nested `async` Example

```kotlin
val result = CoroutineScope(Dispatchers.Default).async {
    val inner = async {
        delay(1000)
        "Inner value"
    }.await()

    println(inner)
    "Outer value"
}.await()

println("Final: $result")
```

**Output:**
```
Inner value
Final: Outer value
```

---

### ⚠️ Unit Return Confusion

```kotlin
val result = CoroutineScope(Dispatchers.Default).async {
    val inner = async {
        delay(1000)
    }.await()

    println(inner)
}.await()

println(result)
```

**Output:**
```
kotlin.Unit
kotlin.Unit
```

---

### 🔥 Exceptions in `launch`

```kotlin
val job = CoroutineScope(Dispatchers.Default).launch {
    delay(500)
    throw RuntimeException("Exception kyu be !!!!")
}

try {
    job.join()
} catch (e: Exception) {
    println("Caught: ${e.message}")
}
```

**Output:**
```
Caught: Exception kyu be !!!!
```

---

### ✅ Exceptions in `async`

```kotlin
val result = CoroutineScope(Dispatchers.Default).async {
    delay(500)
    throw RuntimeException("Async fail be")
}

try {
    result.await()
} catch (e: Exception) {
    println("Caught: ${e.message}")
}
```

**Output:**
```
Caught: Async fail be
```

---

## ✅ Summary Cheat Sheet

- `launch {}` -> for background tasks without result.
- `async {}` -> for background tasks **with result** (use `await()`).
- Don't let `println()` be the last line if you expect a return.
- Coroutine result = **last expression** in lambda.
- Handle exceptions using `try-catch` around `join()` or `await()`.
