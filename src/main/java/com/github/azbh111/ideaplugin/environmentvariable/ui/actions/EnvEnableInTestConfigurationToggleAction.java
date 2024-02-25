package com.github.azbh111.ideaplugin.environmentvariable.ui.actions;

import com.github.azbh111.ideaplugin.environmentvariable.services.EnvService;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareToggleAction;
import com.intellij.openapi.project.Project;

public class EnvEnableInTestConfigurationToggleAction extends DumbAwareToggleAction {

    public EnvEnableInTestConfigurationToggleAction() {
        super("Enable in Java Test Run Configurations");
    }

    @Override
    public boolean isSelected(AnActionEvent event) {
        Project project = event.getProject();
        return project != null && EnvService.getInstance(project).isIncludeTestConfiguration();
    }

    @Override
    public void setSelected(AnActionEvent event, boolean isSelected) {
        Project project = event.getProject();
        if (project != null) {
            EnvService.getInstance(project).setIncludeTestConfiguration(isSelected);
        }
    }
}