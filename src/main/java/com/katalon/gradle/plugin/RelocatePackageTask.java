package com.katalon.gradle.plugin;

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.DisableCachingByDefault;

@DisableCachingByDefault(because = "Mutates the shadowJar task's configuration as a side effect; has no cacheable outputs")
public class RelocatePackageTask extends DefaultTask {
    @Input
    private KatalonGradlePluginExtension extension;
    private final ShadowJar shadowTask;

    public RelocatePackageTask() {
        Project project = this.getProject();
        this.shadowTask = (ShadowJar) project.getTasks().getByName("shadowJar");
    }

    public KatalonGradlePluginExtension getExtension() {
        return extension;
    }

    public void setExtension(KatalonGradlePluginExtension extension) {
        this.extension = extension;
    }

    @TaskAction
    public void configureRelocation() {
        String prefix = getExtension().getDependencyPrefix();
        if (!prefix.isEmpty()) {
            shadowTask.getEnableAutoRelocation().set(true);
            shadowTask.getRelocationPrefix().set(prefix);
        }
        minimizePackage();
    }

    private void minimizePackage() {
        if (getExtension().isMinimize()) {
            shadowTask.minimize();
        }
    }
}
