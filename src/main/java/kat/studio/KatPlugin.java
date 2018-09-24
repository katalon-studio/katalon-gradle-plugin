package kat.studio;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class KatPlugin implements Plugin<Project> {
    public void apply(Project project) {
        project.getTasks().create("listTestCase", ListTestCaseTask.class, (task) -> { // <1>
            task.setProject(project);                           // <2>
        });

        project.getTasks().create("listTestSuite", ListTestSuiteTask.class, (task) -> { // <1>
            task.setProject(project);                           // <2>
        });
    }
}
