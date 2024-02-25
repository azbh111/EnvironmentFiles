package com.github.azbh111.ideaplugin.environmentvariable.customizers;

import com.github.azbh111.ideaplugin.environmentvariable.services.EnvService;
import com.intellij.execution.JavaTestConfigurationBase;
import com.intellij.execution.RunConfigurationExtension;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunnerSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaRunConfigurationCustomizer extends RunConfigurationExtension {

    @Override
    public boolean isApplicableFor(RunConfigurationBase<?> configuration) {
        return true;
    }

    @Override
    public boolean isEnabledFor(@NotNull RunConfigurationBase applicableConfiguration, @Nullable RunnerSettings runnerSettings) {
        EnvService envService = EnvService.getInstance(applicableConfiguration.getProject());

        if (!envService.isIncludeTestConfiguration() && isTestConfiguration(applicableConfiguration)) {
            return false;
        }

        return envService.isEnableRunConfiguration();
    }

    private boolean isTestConfiguration(RunConfigurationBase<?> applicableConfiguration) {
        return applicableConfiguration instanceof JavaTestConfigurationBase;
    }

    @Override
    public <T extends RunConfigurationBase<?>> void updateJavaParameters(T configuration, JavaParameters params, RunnerSettings runnerSettings) {
        EnvService envService = EnvService.getInstance(configuration.getProject());
        params.getEnv().putAll(envService.getEnvValues(params.getEnv()));
    }
}