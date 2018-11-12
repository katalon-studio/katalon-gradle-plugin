package com.katalon.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

public class CopyDependencyTask extends DefaultTask {


  @TaskAction
  public void copy() {
    Project project = this.getProject();
    project.copy(copySpec -> copySpec
      .from(project.getConfigurations().getByName("compile"))
      .into("Drivers")
      .rename(s -> "katalon_generated_" + s));
  }
}
