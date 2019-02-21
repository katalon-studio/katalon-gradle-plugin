package com.katalon.gradle.plugin;

import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation;
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar;
import org.gradle.api.Project;

public class RelocatePackageTask extends ConfigureShadowRelocation {
    public RelocatePackageTask() {
        super();
    }

    public void setPackagePrefix(String prefix) {
        Project project = this.getProject();
        this.setTarget((ShadowJar) project.getTasks().getByName("shadowJar"));
        this.setPrefix(prefix);
    }
}
