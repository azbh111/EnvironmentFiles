package com.github.azbh111.ideaplugin.environmentvariable;

import com.github.azbh111.ideaplugin.environmentvariable.providers.EnvProvider;
import com.github.azbh111.ideaplugin.environmentvariable.providers.EnvProviderFactory;
import com.github.azbh111.ideaplugin.environmentvariable.providers.files.DotEnvFileProvider;
import com.intellij.icons.AllIcons;
import com.intellij.util.xmlb.annotations.Attribute;
import com.intellij.util.xmlb.annotations.MapAnnotation;
import com.intellij.util.xmlb.annotations.Tag;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Tag("env-source-entry")
public class EnvSourceEntry {

    @MapAnnotation(entryTagName = "params")
    public Map<String, String> params = new HashMap<>();

    @Attribute("type")
    public EnvType type;
    @Attribute("enable")
    public boolean enable = true;

    public EnvType getType() {
        return type;
    }

    public EnvSourceEntry() {
        this.type = null;
    }

    public EnvSourceEntry(Map<String, String> params, EnvType type) {
        this.params.putAll(params);
        this.type = type;
    }

    public static enum EnvType {ENV, JSON, YAML}

    public static EnvProviderFactory getProviderFactory(EnvType type) {
        switch (type) {
            case ENV:
                return DotEnvFileProvider.FACTORY;
            default:
                throw new IllegalArgumentException("Unknown EnvType: " + type);
        }
    }

    public static final Map<EnvType, Icon> typeIcons = Map.of(
            EnvType.ENV, AllIcons.FileTypes.JsonSchema,
            EnvType.JSON, AllIcons.FileTypes.Json,
            EnvType.YAML, AllIcons.FileTypes.Yaml
    );

    public EnvProvider getProvider() {
        if (type == null) {
            throw new IllegalStateException("EnvType is not specified");
        }
        return getProviderFactory(type).newInstance(params);
    }

    public String getName() {
        return getProvider().getName();
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setType(EnvType type) {
        this.type = type;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    public boolean isValid() {
        String pathStr = getParams().get("path");
        if (pathStr == null) {
            return false;
        }
        Path path = Paths.get(pathStr);
        if (!Files.exists(path) || Files.isDirectory(path)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EnvSourceEntry{" +
                "params=" + params +
                ", type=" + type +
                ", enable=" + enable +
                '}';
    }
}