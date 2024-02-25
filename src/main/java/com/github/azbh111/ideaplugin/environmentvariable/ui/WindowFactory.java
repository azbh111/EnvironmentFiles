package com.github.azbh111.ideaplugin.environmentvariable.ui;

import com.github.azbh111.ideaplugin.environmentvariable.ui.actions.*;
import com.github.azbh111.ideaplugin.environmentvariable.utils.ApplicationUtils;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.intellij.ui.content.ContentFactory;

public class WindowFactory implements ToolWindowFactory, DumbAware {
    @Override
    @SuppressWarnings("*")
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        ToolWindowPanel toolWindowPanel = new ToolWindowPanel(project);

        // 兼容v<=203: ContentFactory.SERVICE.getInstance()
        ContentFactory contentFactory;
        if (ApplicationUtils.isVersionGtThan(233)) {
            contentFactory = ContentFactory.getInstance();
        } else {
            contentFactory = ContentFactory.SERVICE.getInstance();
        }
        com.intellij.ui.content.Content content = contentFactory.createContent(toolWindowPanel.getComponent(), null, false);
        toolWindow.getContentManager().addContent(content);

        if (toolWindow instanceof ToolWindowEx) {
            ((ToolWindowEx) toolWindow).setAdditionalGearActions(new DefaultActionGroup(
                    new EnvTerminalToggleAction(),
                    new EnvRunConfigurationToggleAction(),
                    new EnvEnableInTestConfigurationToggleAction(),
                    new EnvNodeJsRunConfigurationToggleAction(),
                    new EnvExtendSystemToggleAction()
            ));
        }
    }
}