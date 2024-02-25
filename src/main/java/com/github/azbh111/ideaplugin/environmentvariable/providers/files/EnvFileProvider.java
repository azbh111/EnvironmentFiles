package com.github.azbh111.ideaplugin.environmentvariable.providers.files;

import com.github.azbh111.ideaplugin.environmentvariable.providers.EnvProvider;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.OpenSourceUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EnvFileProvider implements EnvProvider {

    protected final Map<String, String> params;

    public EnvFileProvider(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public String getName() {
        return params.getOrDefault("path", "");
    }

    @Override
    public boolean isFile() {
        return true;
    }

    @Override
    public boolean isValid() {
        return isValidPath();
    }

    private boolean isValidPath() {
        return Files.isReadable(Paths.get(params.getOrDefault("path", "")));
    }

    private String getPath() {
        return params.getOrDefault("path", "");
    }

    public static List<Map<String, String>> createParams(Project project) {
        FileChooserDescriptor fileDescriptor = FileChooserDescriptorFactory.createMultipleFilesNoJarsDescriptor();
        fileDescriptor.withShowHiddenFiles(true);
        fileDescriptor.setTitle("Choose Env File");
        fileDescriptor.setHideIgnored(false);
        fileDescriptor.setForcedToUseIdeaFileChooser(true);
        fileDescriptor.withFileFilter(new Condition<VirtualFile>() {
            @Override
            public boolean value(VirtualFile virtualFile) {
                return !virtualFile.isDirectory() && virtualFile.getPath().endsWith(".env");
            }
        });

        List<Map<String, String>> list = new ArrayList<>();
        VirtualFile[] virtualFiles = FileChooser.chooseFiles(fileDescriptor, project, null);
        if (virtualFiles != null) {
            for (VirtualFile file : virtualFiles) {
                if (file.isDirectory()) {
                    continue;
                }
                Map<String, String> params = new HashMap<>();
                params.put("path", file.getPath());
                list.add(params);
            }
        }

        return list;
    }

    @Override
    public boolean handleDoubleClick(Project project) {
        VirtualFile file = VfsUtil.findFile(Paths.get(getPath()), true);
        if (file != null) {
            OpenSourceUtil.navigate(new OpenFileDescriptor(project, file));
        }
        return true;
    }
}