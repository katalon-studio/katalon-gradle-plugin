> Please reload your Katalon Studio project once finished running Gradle jobs.

# Install gradle
Please follow this guide to install gradle on your machine
{link}

# Create a local gradle properties file 
plugins {
  id 'java'
  id "com.katalon.gradle-plugin" version "0.0.3"
}

repositories {
  mavenCentral()
}

dependencies {
                //input desired plugins
                // example rest-assured
	              //compile 'io.rest-assured:rest-assured:3.2.0'

}


# gradle-plugin

Gradle repository:  
https://plugins.gradle.org/plugin/com.katalon.gradle-plugin  


# Syntax list for Katalon Studio gradle plugin

gradle katalonListTestCases  
gradle katalonListTestSuites  
gradle katalonCopyDependencies  
