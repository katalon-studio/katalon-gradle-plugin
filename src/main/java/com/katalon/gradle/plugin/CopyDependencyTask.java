package com.katalon.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

public class CopyDependencyTask extends DefaultTask {

  private static final String LIBRARY_PREFIX = "katalon_generated_";

  @TaskAction
  public void copy() {
    Project project = this.getProject();
    project.delete(deleteSpec ->
        deleteSpec.delete(
            project.fileTree(
                "Drivers",
                files -> files.include("**/" + LIBRARY_PREFIX + "*")
            )
        )
    );
    project.copy(copySpec ->
        copySpec
            .from(project.getConfigurations().getByName("compile"))
            .into("Drivers")
            .rename(s -> LIBRARY_PREFIX + s));
  }
}
