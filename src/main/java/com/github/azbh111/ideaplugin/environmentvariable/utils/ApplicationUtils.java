package com.github.azbh111.ideaplugin.environmentvariable.utils;

import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.util.BuildNumber;

public class ApplicationUtils {

    public static boolean isVersionGtThan(int version) {
        int v = getVersion();
        return v > version;
    }

    public static boolean isVersionGteThan(int version) {
        int v = getVersion();
        return v >= version;
    }

    public static boolean isVersionLtThan(int version) {
        int v = getVersion();
        return v < version;
    }

    public static boolean isVersionLteThan(int version) {
        int v = getVersion();
        return v <= version;
    }

    public static boolean isVersionEqThan(int version) {
        int v = getVersion();
        return v == version;
    }

    private static int getVersion() {
        int[] components = ApplicationInfo.getInstance()
                .getBuild()
                .getComponents();
        int v = components[0];
        return v;
    }
}
