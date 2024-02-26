# Katalon Studio's Gradle Plugin

This Gradle plugin helps simplify/automate some tasks in Katalon Studio. It is published at https://plugins.gradle.org/plugin/com.katalon.gradle-plugin.

## How to build and test
* **Build**
Run `gradle build`
* **Test** 
<br>Suggestion plugin: [katalon-studio-zip-keywords-plugin](https://github.com/katalon-studio/)
<br>Remove line `'com.katalon.gradle-plugin' version '<version>'`
<br>Adding this script to gradle.build in the tested plugin.
```
buildscript{
    dependencies{
        classpath files('<path>/katalon-gradle-plugin-<version>.jar')
    }
}
.....
apply plugin: com.katalon.gradle.plugin.KatalonGradlePlugin
``` 



## Gradle Tasks

* `katalonCopyDependencies`
* `katalonListTestCases`
* `katalonListTestSuites`

## Sample

Please see https://github.com/katalon-studio-samples/framework-integration.

## Tutorial: Automatically download dependencies for Katalon Studio projects

### Install Gradle

Please follow this [guide](https://gradle.org/install/) to install Gradle on your machine.

### Add `build.gradle` file

Add a `build.gradle` file inside Katalon Studio project contains the below code. Dependencies can be changed or modified based on your libraries prerferences.

```gradle
plugins {
  id 'java-library'
  id "com.katalon.gradle-plugin" version "0.1.1"
}

repositories {
  mavenCentral()
}

dependencies {
  // sample dependencies
  // rest-assured
  implementation 'io.rest-assured:rest-assured:3.2.0'
  // JsonPath
  implementation 'io.rest-assured:json-path:3.2.0'
  // XmlPath
  implementation 'io.rest-assured:json-path:3.2.0'
  // JSON Schema Validation
  implementation 'io.rest-assured:json-schema-validator:3.2.0'
}

```

### Download the dependencies

> **Due to the way Java loading libraries, please close all Katalon Studio applications before execute the following command, and re-open it after the command has been completed.**

```
gradle katalonCopyDependencies
```

## License

Copyright (c) Katalon LLC. All rights reserved.

Licensed under the LICENSE AGREEMENT FOR KATALON AUTOMATION FRAMEWORK.

## Companion products

### Katalon TestOps

[Katalon TestOps](https://analytics.katalon.com) is a web-based application that provides dynamic perspectives and an insightful look at your automation testing data. You can leverage your automation testing data by transforming and visualizing your data; analyzing test results; seamlessly integrating with such tools as Katalon Studio and Jira; maximizing the testing capacity with remote execution.

* Read our [documentation](https://docs.katalon.com/katalon-analytics/docs/overview.html).
* Ask a question on [Forum](https://forum.katalon.com/categories/katalon-analytics).
* Request a new feature on [GitHub](CONTRIBUTING.md).
* Vote for [Popular Feature Requests](https://github.com/katalon-analytics/katalon-analytics/issues?q=is%3Aopen+is%3Aissue+label%3Afeature-request+sort%3Areactions-%2B1-desc).
* File a bug in [GitHub Issues](https://github.com/katalon-analytics/katalon-analytics/issues).

### Katalon Studio
[Katalon Studio](https://www.katalon.com) is a free and complete automation testing solution for Web, Mobile, and API testing with modern methodologies (Data-Driven Testing, TDD/BDD, Page Object Model, etc.) as well as advanced integration (JIRA, qTest, Slack, CI, Katalon TestOps, etc.). Learn more about [Katalon Studio features](https://www.katalon.com/features/).
