package com.katalon.gradle.plugin;

import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation;
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

public class RelocatePackageTask extends ConfigureShadowRelocation {
    private KatalonGradlePluginExtension extension;
    private ShadowJar shadowTask;

    public RelocatePackageTask() {
        super();

        Project project = this.getProject();
        this.shadowTask = (ShadowJar) project.getTasks().getByName("shadowJar");
        this.setTarget(this.shadowTask);
    }

    public KatalonGradlePluginExtension getExtension() {
        return extension;
    }

    public void setExtension(KatalonGradlePluginExtension extension) {
        this.extension = extension;
    }

    public void setPackagePrefix(String prefix) {
        this.setPrefix(prefix);
        this.minimizePackage();
    }

    @TaskAction
    public void configureRelocation() {
        this.setPrefix(getExtension().getDependencyPrefix());
        super.configureRelocation();
        this.minimizePackage();
    }

    private void minimizePackage() {
        if (getExtension().isMinimize()) {
            shadowTask.minimize();
        }
    }
}
