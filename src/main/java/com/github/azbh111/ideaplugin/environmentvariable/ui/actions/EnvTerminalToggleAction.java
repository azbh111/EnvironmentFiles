package com.github.azbh111.ideaplugin.environmentvariable.ui.actions;

import com.github.azbh111.ideaplugin.environmentvariable.services.EnvService;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareToggleAction;

public class EnvTerminalToggleAction extends DumbAwareToggleAction {

    public EnvTerminalToggleAction() {
        super("Enable in Terminal");
    }

    @Override
    public boolean isSelected(AnActionEvent event) {
        return (event.getProject() != null) ?
                EnvService.getInstance(event.getProject()).isEnableTerminal() :
                true;
    }

    @Override
    public void setSelected(AnActionEvent event, boolean isSelected) {
        if (event.getProject() != null) {
            EnvService.getInstance(event.getProject()).setEnableTerminal(isSelected);
        }
    }
}