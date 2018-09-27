package com.katalon.gradle.plugin.list;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.util.List;

public class ListTestCasesTask extends DefaultTask {

  @TaskAction
  void scanTestCase() {
    Project project = this.getProject();
    String rootPath = project.getRootDir().getAbsolutePath();
    Scanner scanner = new Scanner();
    try {
      List<TestCase> testCases = scanner.scanTestCases(rootPath);
      testCases.forEach(tc -> System.out.println(tc.getPath()));
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }
}
