package com.github.azbh111.ideaplugin.environmentvariable.ui;

import com.github.azbh111.ideaplugin.environmentvariable.EnvSourceEntry;
import com.github.azbh111.ideaplugin.environmentvariable.services.EnvService;
import com.intellij.ui.CollectionListModel;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class EnvFileListDataListener implements ListDataListener {
    private final EnvService service;
    private final CollectionListModel<EnvSourceEntry> listModel;

    public EnvFileListDataListener(EnvService service, CollectionListModel<EnvSourceEntry> listModel) {
        this.service = service;
        this.listModel = listModel;
    }

    @Override
    public void intervalAdded(ListDataEvent e) {
        service.setEnvFiles(listModel.toList());
    }

    @Override
    public void intervalRemoved(ListDataEvent e) {
        service.setEnvFiles(listModel.toList());
    }

    @Override
    public void contentsChanged(ListDataEvent e) {
        service.setEnvFiles(listModel.toList());
    }
}
