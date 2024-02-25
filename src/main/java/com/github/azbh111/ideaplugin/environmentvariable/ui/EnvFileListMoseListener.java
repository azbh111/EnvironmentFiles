package com.github.azbh111.ideaplugin.environmentvariable.ui;

import com.github.azbh111.ideaplugin.environmentvariable.EnvSourceEntry;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.JBMenuItem;
import com.intellij.openapi.ui.JBPopupMenu;
import com.intellij.ui.ClickListener;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EnvFileListMoseListener extends MouseAdapter {
    private final JBList<EnvSourceEntry> list;
    private final Project project;
    private Point pressPoint;
    private Point lastClickPoint;
    private long lastTimeClicked = -1L;
    private int clickCount = 0;
    JBPopupMenu jbPopupMenu;

    public EnvFileListMoseListener( Project project, JBList<EnvSourceEntry> list) {
        this.list = list;
        this.project = project;
        jbPopupMenu = new JBPopupMenu();
        jbPopupMenu.add(getSwitch(jbPopupMenu, "enable", true));
        jbPopupMenu.add(getSwitch(jbPopupMenu, "disable", false));
    }

    @Nullable
    private JBMenuItem getSwitch(JBPopupMenu popupMenu, String title, boolean value) {
        JBMenuItem menuItem = new JBMenuItem(title);
        new ClickListener() {
            @Override
            public boolean onClick(@NotNull MouseEvent event, int i) {
                popupMenu.setVisible(false);
                int index = list.locationToIndex(event.getPoint());
                List<EnvSourceEntry> items = list.getSelectedValuesList();
                if (items == null || index < 0 || items.size() <= index) {
                    return true;
                }
                EnvSourceEntry envSourceEntry = items.get(index);
                envSourceEntry.setEnable(value);
                return true;
            }
        }.installOn(menuItem);
        return menuItem;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        SwingUtilities.convertPointToScreen(point, e.getComponent());
        if (Math.abs(this.lastTimeClicked - e.getWhen()) > (long) UIUtil.getMultiClickInterval() || this.lastClickPoint != null && !isWithinEps(this.lastClickPoint, point)) {
            this.clickCount = 0;
            this.lastClickPoint = null;
        }

        ++this.clickCount;
        this.lastTimeClicked = e.getWhen();
        this.pressPoint = point;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point releasedAt = e.getPoint();
        SwingUtilities.convertPointToScreen(releasedAt, e.getComponent());
        Point clickedAt = this.pressPoint;
        this.lastClickPoint = clickedAt;
        this.pressPoint = null;
        if (!e.isConsumed() && clickedAt != null && e.getComponent().contains(e.getPoint())) {
            if ((isWithinEps(releasedAt, clickedAt)) && this.onClick(e, this.clickCount)) {
                e.consume();
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!e.isConsumed() && this.onClick(e, e.getClickCount())) {
            e.consume();
        }
    }

    protected boolean onClick(@NotNull MouseEvent event, int clickCount) {
        if (event == null) {
            return false;
        }
        if (event.getButton() == 1) {
            // 左键
            if (clickCount == 2) {
                return this.onDoubleLeftClick(event);
            }
        } else {
            // 右键
            return onRightClick(event);
        }
        return true;
    }

    protected boolean onDoubleLeftClick(@NotNull MouseEvent event) {
        if (list.getSelectedValue() == null) return true;
        list.getSelectedValue().getProvider().handleDoubleClick(project);
        return true;
    }

    protected boolean onRightClick(@NotNull MouseEvent event) {
        jbPopupMenu.show(event.getComponent(), (int) event.getPoint().getX(), (int) event.getPoint().getY());
        return true;
    }

    private static boolean isWithinEps(Point releasedAt, Point clickedAt) {
        return Math.abs(clickedAt.x - releasedAt.x) < 4 && Math.abs(clickedAt.y - releasedAt.y) < 4;
    }
}
