package com.github.azbh111.ideaplugin.environmentvariable.ui;

import com.github.azbh111.ideaplugin.environmentvariable.EnvSourceEntry;
import com.github.azbh111.ideaplugin.environmentvariable.services.EnvService;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ToolWindowPanel extends SimpleToolWindowPanel {
    private EnvService service;

    public ToolWindowPanel(Project project) {
        super(true, true);
        service = EnvService.getInstance(project);

        CollectionListModel<EnvSourceEntry> listModel = new CollectionListModel<>(service.getEnvFiles());
        listModel.addListDataListener(new EnvFileListDataListener(service, listModel));

        JBList<EnvSourceEntry> list = new JBList<>(listModel);
        list.setCellRenderer(new EnvFileListCellRender(project));
        list.addMouseListener(new EnvFileListMoseListener(project, list));
        DefaultActionGroup actionGroup = getActionGroup(project, listModel);

        ToolbarDecorator decorator = ToolbarDecorator.createDecorator(list);
        decorator.setAddAction(button -> {
            ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                    "Add...",
                    actionGroup,
                    button.getDataContext(),
                    true,
                    null,
                    -1
            );
            popup.show(button.getPreferredPopupPoint());
        });

        setContent(decorator.createPanel());
    }

    @NotNull
    private static DefaultActionGroup getActionGroup(Project project, CollectionListModel<EnvSourceEntry> listModel) {
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(new AnAction(".env", null, AllIcons.Actions.AddFile) {
            @Override
            public void actionPerformed(AnActionEvent e) {
                Map<String, String> params = EnvSourceEntry.getProviderFactory(EnvSourceEntry.EnvType.ENV)
                        .createParams(project);
                if (!params.isEmpty()) {
                    listModel.add(new EnvSourceEntry(params, EnvSourceEntry.EnvType.ENV));
                }
            }
        });
        return actionGroup;
    }


}