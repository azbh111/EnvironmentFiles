package com.github.azbh111.ideaplugin.environmentvariable.services;

import com.github.azbh111.ideaplugin.environmentvariable.EnvSourceEntry;
import com.github.azbh111.ideaplugin.environmentvariable.domain.val.DataStorage;
import com.github.azbh111.ideaplugin.environmentvariable.domain.val.EnvValue;
import com.github.azbh111.ideaplugin.environmentvariable.MyLinkedHashMap;
import com.github.azbh111.ideaplugin.environmentvariable.providers.EnvSourceException;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@State(name = "EnvService", storages = {@Storage("environment_files.xml")})
public final class EnvService implements PersistentStateComponent<DataStorage> {
    public static EnvService getInstance(Project project) {
        EnvService pes = project.getService(EnvService.class);
        return pes;
    }

    private final Project project;

    private DataStorage data = new DataStorage();

    public EnvService(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setEnvFiles(List<EnvSourceEntry> envFiles) {
        if (envFiles == null) {
            envFiles = new ArrayList<>();
        }
        Set<String> set = new HashSet<>();
        List<EnvSourceEntry> list = new ArrayList<>();
        for (EnvSourceEntry envFile : envFiles) {
            if (set.add(envFile.getParams().get("path"))) {
                list.add(envFile);
            }
        }
        data.setEnvFiles(list);
    }

    public List<EnvSourceEntry> getEnvFiles() {
        return data.getEnvFiles();
    }

    public void setEnableTerminal(boolean enableTerminal) {
        data.setEnableTerminal(enableTerminal);
    }

    public boolean isEnableTerminal() {
        return data.isEnableTerminal();
    }

    public boolean isEnableRunConfiguration() {
        return data.isEnableRunConfiguration();
    }

    public void setEnableRunConfiguration(boolean flag) {
        data.setEnableRunConfiguration(flag);
    }


    public boolean isIncludeTestConfiguration() {
        return data.isIncludeTestConfiguration();
    }

    public void setIncludeTestConfiguration(boolean flag) {
        data.setIncludeTestConfiguration(flag);
    }

    public boolean isEnableNodeJsConfiguration() {
        return data.isEnableNodeJsConfiguration();
    }

    public void setEnableNodeJsConfiguration(boolean enableNodeJsConfiguration) {
        this.data.setEnableNodeJsConfiguration(enableNodeJsConfiguration);
    }

    public boolean isInheritingSystemEnvironments() {
        return this.data.isInheritingSystemEnvironments();
    }

    public void setInheritingSystemEnvironments(boolean flag) {
        this.data.setInheritingSystemEnvironments(flag);
    }

    @Override
    public @NotNull DataStorage getState() {
        return data;
    }

    @Override
    public void loadState(@NotNull DataStorage state) {
        this.data = state;
    }

    @Override
    public void noStateLoaded() {
        if (this.data == null) {
            this.data = new DataStorage();
        }
    }

    public Map<String, String> getEnvValues(Map<String, String> parent) {
        if (parent == null) {
            parent = new LinkedHashMap<>();
        }
        // 优先级：插件提供 > 运行是自带 > 系统提供
        Map<String, String> ctx = new LinkedHashMap<>(System.getenv());
        ctx.putAll(parent);
        Map<String, String> result = new MyLinkedHashMap();
        if (isInheritingSystemEnvironments()) {
            result.putAll(System.getenv());
        }
        result.putAll(parent);
        for (EnvSourceEntry envFile : data.getEnvFiles()) {
            if (!envFile.isEnable()) {
                continue;
            }
            if (envFile.getProvider().isValid()) {
                try {
                    Map<String, String> map = envFile.getProvider().getEnvValues();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        value = new EnvValue(value)
                                .render(ctx);
                        ctx.put(key, value);
                        result.put(key, value);
                    }
                } catch (EnvSourceException ex) {
                    NotificationGroupManager.getInstance()
                            .getNotificationGroup("EnvironmentFiles")
                            .createNotification(
                                    "Cannot process " + envFile.getName() + ": " + ex.getMessage(),
                                    NotificationType.WARNING)
                            .notify(null);
                }
            }
        }

        return result;
    }

}