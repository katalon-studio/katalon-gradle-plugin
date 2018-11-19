## Katalon Studio's Gradle Plugin

This Gradle plugin helps simplify/automate some tasks in Katalon Studio. It is published at https://plugins.gradle.org/plugin/com.katalon.gradle-plugin.

## Gradle Tasks

* `katalonCopyDependencies`
* `katalonListTestCases`
* `katalonListTestSuites`


## Tutorial: Automatically download dependencies for Katalon Studio projects

### Install Gradle

Please follow this [guide](https://gradle.org/install/) to install Gradle on your machine.

### Add `build.gradle` file

Add a `build.gradle` file inside Katalon Studio project contains the below code. Dependencies can be changed or modified based on your libraries prerferences. 

```gradle
plugins {
  id 'java'
  id "com.katalon.gradle-plugin" version "0.0.3"
}

repositories {
  mavenCentral()
}

dependencies {
  // sample dependencies
  // rest-assured
  compile 'io.rest-assured:rest-assured:3.2.0'
  // JsonPath
  compile 'io.rest-assured:json-path:3.2.0'
  // XmlPath
  compile 'io.rest-assured:json-path:3.2.0'
  // JSON Schema Validation
  compile 'io.rest-assured:json-schema-validator:3.2.0'
}

```

### Download the dependencies

Execute the following command:

```
gradle katalonCopyDependencies 
```
	
> **Please close and re-open your Katalon Studio project after the process has been completed.**
