package com.github.azbh111.ideaplugin.environmentvariable.compatibility;

import com.github.azbh111.ideaplugin.environmentvariable.utils.ApplicationUtils;
import com.github.azbh111.ideaplugin.environmentvariable.utils.ReflectUtils;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.ReflectionUtil;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public class IntellijCompatibility {

    public static void processNotStarted(ProgramRunner.Callback callback) {
        try {
            ReflectUtils.Invoker<Object> processNotStarted = ReflectUtils
                .getInvoker(ProgramRunner.Callback.class, "processNotStarted");
            processNotStarted.invoke(callback);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    // 调用 internal API
    public static void setCallback(ExecutionEnvironment executionEnvironment,
        @Nullable ProgramRunner.@Nullable Callback callback) {
        try {
            ReflectUtils.Invoker<Object> setCallback =
                ReflectUtils.getInvoker(ExecutionEnvironment.class, "setCallback",
                    ProgramRunner.Callback.class);
            setCallback.invoke(executionEnvironment, callback);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    // 多版本兼容
    public static ContentFactory getContentFactoryInstance() {
        try {
            ContentFactory contentFactory;
            if (ApplicationUtils.isVersionGteThan(230)) {
                ReflectUtils.Invoker<ContentFactory> getInstance = ReflectUtils.getInvoker(ContentFactory.class, "getInstance");
                contentFactory = getInstance.invoke(null);
            } else {
                Class<?> aClass = Class.forName("com.intellij.ui.content.ContentFactory.SERVICE");
                ReflectUtils.Invoker<ContentFactory> getInstance = ReflectUtils.getInvoker(aClass, "getInstance");
                contentFactory = getInstance.invoke(null);
            }
            return contentFactory;
        } catch (NoSuchFieldException | InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
