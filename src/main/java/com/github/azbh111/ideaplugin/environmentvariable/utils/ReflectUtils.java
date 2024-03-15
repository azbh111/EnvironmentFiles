package com.github.azbh111.ideaplugin.environmentvariable.utils;

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

    private static Field getField(Class target, String fieldName) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Field field = target.getDeclaredField(fieldName);
        if (Modifier.isFinal(field.getModifiers())) {
            removeFinalModifier(field);
        }
        field.setAccessible(true);
        return field;
    }

    private static Method getMethod(Class target, String methodName, Class[] parameterTypes) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method declaredMethod = target.getDeclaredMethod(methodName, parameterTypes);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }


    public static <T> Invoker<T> getInvoker(Class<?> type, String methodName, Class<?>... methodParameters) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Method method = getMethod(type, methodName, methodParameters == null ? new Class[0] : methodParameters);
        return new Invoker<>(method);
    }

    public static <T> Accessor<T> getAccessor(Class<?> type, String fieldName) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Field field = getField(type, fieldName);
        return new Accessor<>(field);
    }

    public static class Invoker<T> {
        private final Method method;

        public Invoker(Method method) {
            this.method = method;
        }

        public T invoke(Object target, Object... args) throws InvocationTargetException, IllegalAccessException {
            return (T) method.invoke(target, args);
        }

    }

    public static class Accessor<T> {
        private final Field field;

        public Accessor(Field field) {
            this.field = field;
        }

        public T get(Object target) throws IllegalAccessException {
            return (T) field.get(target);
        }

        public void set(Object target, T value) throws IllegalAccessException {
            field.set(target, value);
        }
    }
}
