package com.katalon.gradle.plugin.list;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.DisableCachingByDefault;

import java.io.File;
import java.util.List;

@DisableCachingByDefault(because = "Only prints results to the console")
public class ListTestCasesTask extends DefaultTask {

  private final File rootDir;

  public ListTestCasesTask() {
    this.rootDir = getProject().getRootDir();
  }

  @TaskAction
  void scanTestCase() {
    String rootPath = rootDir.getAbsolutePath();
    Scanner scanner = new Scanner();
    try {
      List<TestCase> testCases = scanner.scanTestCases(rootPath);
      testCases.forEach(tc -> System.out.println(tc.getPath()));
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }
}
