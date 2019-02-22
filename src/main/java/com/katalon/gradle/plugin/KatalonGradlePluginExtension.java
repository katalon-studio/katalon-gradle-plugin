package com.katalon.gradle.plugin;

public class KatalonGradlePluginExtension {
    private String dependencyPrefix;
    private boolean minimize;

    public KatalonGradlePluginExtension() {
        this.dependencyPrefix = "katalon";
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
}
