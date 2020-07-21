# knanoid

![BUILD (Ubuntu 20.04)](https://github.com/alekseinovikov/knanoid/workflows/BUILD%20(Ubuntu%2020.04)/badge.svg)


## Description

Allows generating random ID with the provided alphabet and size;

## Example

```kotlin
fun org.knanoid.randomNanoId(
    size: Int = defaultSize, // 21
    alphabet: CharArray = defaultAlphabet, // _-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
    random: Random = defaultRandom // Just Random
): String


val result = randomNanoId()
println(result) //o3FfXLK_lFOiB01-jdXgY
```

## Usage

Add to your gradle build file: 

```groovy
repositories {
    ...

    maven {
        url  "https://dl.bintray.com/alekseinovikov/utils"
    }

    ...
}

dependencies {
    ...

    implementation 'org.knanoid:knanoid:1.0'
    
    ...
}
```
