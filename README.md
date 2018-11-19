> Please reload your Katalon Studio project once finished running Gradle jobs.

# gradle-plugin

Katalon Gradle repository: https://plugins.gradle.org/plugin/com.katalon.gradle-plugin  

# Install gradle
Please follow this [guide](https://gradle.org/install/) to install gradle on your machine.

# Create a local gradle properties file

Create a local gradle file named "build.gradle" inside Katalon Studio project contains the below code. Dependencies can be changed or modified based on your libraries prerferences. 

```gradle
plugins {
  id 'java'
  id "com.katalon.gradle-plugin" version "0.0.3"
}

repositories {
  mavenCentral()
}

dependencies {
	//rest-assured
	compile 'io.rest-assured:rest-assured:3.2.0'

	//JsonPath
	compile 'io.rest-assured:json-path:3.2.0'
	
	//XmlPath
	compile 'io.rest-assured:json-path:3.2.0'

	//JSON Schema Validation
	compile 'io.rest-assured:json-schema-validator:3.2.0'
}

```

# Syntax for Katalon Studio gradle plugin

- To install Katalon Dependencies

	gradle katalonCopyDependencies 

- Other Syntax

	gradle katalonListTestCases  
	gradle katalonListTestSuites  
 
