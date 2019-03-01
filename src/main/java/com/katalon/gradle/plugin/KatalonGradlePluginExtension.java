package com.katalon.gradle.plugin;

import static com.katalon.gradle.plugin.KatalonGradlePlugin.RESOURCE_DIR;

public class KatalonGradlePluginExtension {
    private String dependencyPrefix;
    private String metadataDir;
    private boolean minimize;

    public KatalonGradlePluginExtension() {
        this.dependencyPrefix = "";
        this.metadataDir = RESOURCE_DIR;
        this.minimize = false;
    }

    public String getDependencyPrefix() {
        return dependencyPrefix;
    }

    public void setDependencyPrefix(String dependencyPrefix) {
        this.dependencyPrefix = dependencyPrefix;
    }

    public boolean isMinimize() {
        return minimize;
    }

    public void setMinimize(boolean minimize) {
        this.minimize = minimize;
    }

    public String getMetadataDir() {
        return metadataDir;
    }

    public void setMetadataDir(String metadataDir) {
        this.metadataDir = metadataDir;
    }
}
