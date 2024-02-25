package com.github.azbh111.ideaplugin.environmentvariable.domain.val;

import com.github.azbh111.ideaplugin.environmentvariable.EnvSourceEntry;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.components.StoredPropertyBase;

import java.util.List;

public class DataStorage extends BaseState {
    private StoredPropertyBase<List<EnvSourceEntry>> envFiles = list();

    private StoredPropertyBase<Boolean> enableTerminal = property(true);
    private StoredPropertyBase<Boolean> enableRunConfiguration = property(true);
    private StoredPropertyBase<Boolean> includeTestConfiguration = property(true);
    private StoredPropertyBase<Boolean> enableNodeJsConfiguration = property(true);
    private StoredPropertyBase<Boolean> inheritingSystemEnvironments = property(true);

    public DataStorage() {
        envFiles.setName("envFiles");
        enableTerminal.setName("enableTerminal");
        enableRunConfiguration.setName("enableRunConfiguration");
        includeTestConfiguration.setName("includeTestConfiguration");
        enableNodeJsConfiguration.setName("enableNodeJsConfiguration");
        inheritingSystemEnvironments.setName("inheritingSystemEnvironments");
    }

    public List<EnvSourceEntry> getEnvFiles() {
        return envFiles.getValue(this);
    }

    public void setEnvFiles(List<EnvSourceEntry> envFiles) {
        this.envFiles.setValue(this, envFiles);
    }

    public boolean isEnableTerminal() {
        return enableTerminal.getValue(this);
    }

    public void setEnableTerminal(boolean enableTerminal) {
        this.enableTerminal.setValue(this, enableTerminal);
    }

    public boolean isEnableRunConfiguration() {
        return enableRunConfiguration.getValue(this);
    }

    public void setEnableRunConfiguration(boolean enableRunConfiguration) {
        this.enableRunConfiguration.setValue(this, enableRunConfiguration);
    }

    public boolean isIncludeTestConfiguration() {
        return includeTestConfiguration.getValue(this);
    }

    public void setIncludeTestConfiguration(boolean includeTestConfiguration) {
        this.includeTestConfiguration.setValue(this, includeTestConfiguration);
    }

    public boolean isEnableNodeJsConfiguration() {
        return enableNodeJsConfiguration.getValue(this);
    }

    public void setEnableNodeJsConfiguration(boolean enableNodeJsConfiguration) {
        this.enableNodeJsConfiguration.setValue(this, enableNodeJsConfiguration);
    }
    public boolean isInheritingSystemEnvironments() {
        return inheritingSystemEnvironments.getValue(this);
    }

    public void setInheritingSystemEnvironments(boolean flag) {
        this.inheritingSystemEnvironments.setValue(this, flag);
    }
}
