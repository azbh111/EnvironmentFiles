package com.github.azbh111.ideaplugin.environmentvariable.providers.files;

import com.github.azbh111.ideaplugin.environmentvariable.providers.EnvProviderFactory;
import com.github.azbh111.ideaplugin.environmentvariable.providers.EnvSourceException;
import com.intellij.openapi.project.Project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class DotEnvFileProvider extends EnvFileProvider {
    public static final Factory FACTORY = new Factory();

    public DotEnvFileProvider(Map<String, String> params) {
        super(params);
    }

    public static class Factory implements EnvProviderFactory {
        @Override
        public DotEnvFileProvider newInstance(Map<String, String> params) {
            return new DotEnvFileProvider(params);
        }

        @Override
        public Map<String, String> createParams(Project project) {
            return EnvFileProvider.createParams(project);
        }
    }

    @Override
    public Map<String, String> getEnvValues() throws EnvSourceException {
        String path = params.get("path");
        if (path == null) {
            throw new EnvSourceException("No valid path to env file");
        }

        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        try {
            for (String line : Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8)) {
                String strippedLine = line.trim();
                if (strippedLine.startsWith("#") || strippedLine.startsWith("//")) {
                    continue;
                }
                if (!strippedLine.contains("=")) {
                    continue;
                }
                String[] tokens = strippedLine.split("=", 2);
                String key = tokens[0];
                String value = tokens[1];
                result.put(key, value);
            }
        } catch (IOException ex) {
            throw new EnvSourceException(ex);
        }
        return result;
    }
}