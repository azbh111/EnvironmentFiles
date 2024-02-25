package com.github.azbh111.ideaplugin.environmentvariable.ui;

import com.github.azbh111.ideaplugin.environmentvariable.EnvSourceEntry;
import com.intellij.openapi.project.Project;
import com.intellij.ui.ColoredListCellRenderer;

import javax.swing.*;
import java.io.File;

public class EnvFileListCellRender extends ColoredListCellRenderer<EnvSourceEntry> {
    private final Project project;

    public EnvFileListCellRender(Project project) {
        this.project = project;
    }

    @Override
    protected void customizeCellRenderer(JList<? extends EnvSourceEntry> list, EnvSourceEntry value,
                                         int index, boolean selected, boolean hasFocus) {
        if (value instanceof EnvSourceEntry) {
            setIcon(EnvSourceEntry.typeIcons.get(value.getType()));
            String prefix = null;
            if (!value.isValid()) {
                prefix = "❓";
            } else if (value.isEnable()) {
                prefix = "✅";
            } else {
                prefix = "⛔";
            }
            append(prefix);
            String projectPath = (project.getBaseDir().getPath() != null ? project.getBaseDir().getPath() : "") + File.separator;
            String filePath = value.getName().replaceFirst(projectPath, "");
            append(filePath);
        }
    }
}
