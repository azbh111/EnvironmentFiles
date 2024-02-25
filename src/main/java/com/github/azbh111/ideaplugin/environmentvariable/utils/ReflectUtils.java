package com.github.azbh111.ideaplugin.environmentvariable.utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectUtils {


    public static Object get(Object target, String fieldName) throws Throwable {
        Field field = getField(target.getClass(), fieldName);
        return field.get(target);
    }

    public static void set(Object target, String fieldName, Object value) throws Throwable {
        Field field = getField(target.getClass(), fieldName);
        field.set(target, value);
    }


    private static void removeFinalModifier(Field field) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method getDeclaredFields0 = Class.class.getDeclaredMethod("getDeclaredFields0", new Class[]{boolean.class});
        getDeclaredFields0.setAccessible(true);
        Field[] fields = (Field[]) getDeclaredFields0.invoke(Field.class, new Object[]{false});
        for (Field f : fields) {
            if (f.getName().equals("modifiers")) {
                f.setAccessible(true);
                f.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            }
        }
    }

    @NotNull
    private static Field getField(Class target, String fieldName) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Field field = target.getDeclaredField(fieldName);
        if (Modifier.isFinal(field.getModifiers())) {
            removeFinalModifier(field);
        }
        field.setAccessible(true);
        return field;
    }

}
