package com.github.azbh111.ideaplugin.environmentvariable.providers;

import com.intellij.openapi.project.Project;

import java.util.Map;

public interface EnvProviderFactory {
    EnvProvider newInstance(Map<String, String> params);

    Map<String, String> createParams(Project project);
}