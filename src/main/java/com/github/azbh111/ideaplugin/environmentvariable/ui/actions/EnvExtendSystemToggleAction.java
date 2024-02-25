package com.github.azbh111.ideaplugin.environmentvariable.ui.actions;

import com.github.azbh111.ideaplugin.environmentvariable.services.EnvService;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareToggleAction;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class EnvExtendSystemToggleAction extends DumbAwareToggleAction {

    public EnvExtendSystemToggleAction() {
        super("Inheriting System Environments");
    }

    @Override
    public boolean isSelected(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        return project != null && EnvService.getInstance(project).isInheritingSystemEnvironments();
    }

    @Override
    public void setSelected(@NotNull AnActionEvent event, boolean isSelected) {
        Project project = event.getProject();
        if (project != null) {
            EnvService.getInstance(project).setInheritingSystemEnvironments(isSelected);
        }
    }
}