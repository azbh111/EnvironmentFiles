package com.github.azbh111.ideaplugin.environmentvariable.compatibility;

import com.github.azbh111.ideaplugin.environmentvariable.utils.ApplicationUtils;
import com.github.azbh111.ideaplugin.environmentvariable.utils.ReflectUtils;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;

public class IntellijCompatibility {

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
