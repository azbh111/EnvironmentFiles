package com.github.azbh111.ideaplugin.environmentvariable.providers;

import com.intellij.openapi.project.Project;

import java.util.Map;

public interface EnvProvider {
    boolean handleDoubleClick(Project project);

    Map<String, String> getEnvValues();

    boolean isValid();

    boolean isFile();

    String getName();
}