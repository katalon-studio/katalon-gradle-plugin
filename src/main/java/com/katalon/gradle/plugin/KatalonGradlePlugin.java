package com.katalon.gradle.plugin;

import com.katalon.gradle.plugin.list.ListTestCasesTask;
import com.katalon.gradle.plugin.list.ListTestSuitesTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.Copy;

public class KatalonGradlePlugin implements Plugin<Project> {

    public void apply(Project project) {
        project.getTasks().create("katalonListTestCases", ListTestCasesTask.class);

        project.getTasks().create("katalonListTestSuites", ListTestSuitesTask.class);

        project.getTasks().create("katalonCopyDependencies", CopyDependencyTask.class);
    }
}
