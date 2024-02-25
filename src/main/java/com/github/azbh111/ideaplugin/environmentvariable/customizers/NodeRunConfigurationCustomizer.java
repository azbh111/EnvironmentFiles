package com.github.azbh111.ideaplugin.environmentvariable.customizers;

import com.github.azbh111.ideaplugin.environmentvariable.services.EnvService;
import com.github.azbh111.ideaplugin.environmentvariable.utils.NodeJsUtils;
import com.github.azbh111.ideaplugin.environmentvariable.utils.ReflectUtils;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configuration.EnvironmentVariablesData;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.javascript.nodejs.execution.AbstractNodeTargetRunProfile;
import com.intellij.javascript.nodejs.execution.runConfiguration.AbstractNodeRunConfigurationExtension;
import com.intellij.javascript.nodejs.execution.runConfiguration.NodeRunConfigurationLaunchSession;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class NodeRunConfigurationCustomizer extends AbstractNodeRunConfigurationExtension {
    // 兼容u231：版本<231时，这个方法是抽象的，必须实现
    @Nullable
    public SettingsEditor createEditor(@NotNull AbstractNodeTargetRunProfile configuration) {
        return null;
    }

    // 兼容u231：版本<231时，这个方法是抽象的，必须实现
    @Nullable
    public String getEditorTitle() {
        return null;
    }

    @Override
    public boolean isApplicableFor(@NotNull AbstractNodeTargetRunProfile abstractNodeTargetRunProfile) {
        return true;
    }

    @Nullable
    @Override
    public NodeRunConfigurationLaunchSession createLaunchSession(@NotNull AbstractNodeTargetRunProfile configuration, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        Project project = configuration.getProject();
        EnvService envService = EnvService.getInstance(project);
        EnvironmentVariablesData envData = configuration.getEnvData();
        try {
            if (envService.isEnableNodeJsConfiguration()) {
                /*
                    nodejs的环境变量，运行后，需要恢复，不然会影响RunConfiguration里面的配置
                    官方没有提供扩展点，这里用反射实现环境变量的注入，兼容性可能不是很好
                    233测试没问题
                 */
                Map<String, String> myEnvs = (Map<String, String>) ReflectUtils.get(envData, "myEnvs");
                Map<String, String> envMap = envService.getEnvValues(myEnvs);
                ReflectUtils.set(envData, "myEnvs", envMap);
                // 注册回调，用来恢复myEnvs
                registerRecoverCallback(environment, envData, myEnvs, envService);
            }
        } catch (Throwable e) {
            System.out.println("不支持向NodeJs运行项注入环境变量，自动禁用");
            envService.setEnableNodeJsConfiguration(false);
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
        return super.createLaunchSession(configuration, environment);
    }

    private static void registerRecoverCallback(@NotNull ExecutionEnvironment environment, EnvironmentVariablesData envData, Map<String, String> myEnvs, EnvService envService) {
        NodeJsUtils.addOnceCallback(environment, new ProgramRunner.Callback() {
            @Override
            public void processStarted(RunContentDescriptor runContentDescriptor) {
                try {
                    ReflectUtils.set(envData, "myEnvs", myEnvs);
                } catch (Throwable e) {
                    System.out.println("不支持向NodeJs运行项注入环境变量，自动禁用");
                    envService.setEnableNodeJsConfiguration(false);
                    if (e instanceof RuntimeException) {
                        throw (RuntimeException) e;
                    } else {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void processNotStarted() {
                try {
                    ReflectUtils.set(envData, "myEnvs", myEnvs);
                } catch (Throwable e) {
                    System.out.println("不支持向NodeJs运行项注入环境变量，自动禁用");
                    envService.setEnableNodeJsConfiguration(false);
                    if (e instanceof RuntimeException) {
                        throw (RuntimeException) e;
                    } else {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

}
