package com.github.azbh111.ideaplugin.environmentvariable.customizers;

import com.github.azbh111.ideaplugin.environmentvariable.services.EnvService;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.terminal.LocalTerminalCustomizer;

import java.util.Map;

public class TerminalCustomizer extends LocalTerminalCustomizer {
    public String[] customizeCommandAndEnvironment(@NotNull Project project, @Nullable String workingDirectory, @NotNull String[] command, @NotNull Map<String, String> envs) {
        if (project == null) {
            return command;
        }

        if (envs == null) {
            return command;
        }

        if (command == null) {
            return command;
        }
        EnvService envService = EnvService.getInstance(project);
        if (envService.isEnableTerminal()) {
            envs.putAll(envService.getEnvValues(envs));
        }
        return command;
    }

    public String[] customizeCommandAndEnvironment(Project project, String[] command, Map<String, String> envs) {
        EnvService envService = EnvService.getInstance(project);
        if (envService.isEnableTerminal()) {
            envs.putAll(envService.getEnvValues(envs));
        }
        return command;
    }


}