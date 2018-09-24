package kat.studio;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.util.List;

public class ListTestCaseTask extends DefaultTask {

  private Project project;

  public void setProject(Project project) {
    this.project = project;
  }

  @TaskAction
  void scanTestCase() {
    String rootPath = project.getRootDir().getAbsolutePath();
    Scanner scanner = new Scanner();
    try {
      List<TestCase> testCases = scanner.scanTestCases(rootPath);
      testCases.forEach(tc -> {
        System.out.println(tc.getPath());
      });
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }
}
