package com.gl.extrade.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class ReflectionUtil {
    private ReflectionUtil() {
    }

    public static String toString(Class<?> clazz, Object target) {
        if(clazz == null || target == null){
            throw new RuntimeException();
        }
        
        Class<?> clz = clazz;
        
        StringBuilder sb = new StringBuilder();
        while(clz != null && clz != Object.class){
            
            sb.append(toString0(clz,target));
            clz = clz.getSuperclass();
        }
        return sb.toString();
    }

    // Foo[name=123,pass=123]
    public static String toString0(Class<?> clazz, Object target) {
        StringBuilder sb = new StringBuilder();
        String simpleClassName = clazz.getSimpleName();
        sb.append(simpleClassName);
        sb.append("[");
        Field[] fields = clazz.getDeclaredFields();
        String fName = null;
        String mName = null;
        Method accessMethod = null;
        Object fieldValue = null;
        String fieldString = null;
        int fieldCount = 0;
        for (Field f : fields) {
            if(Modifier.isStatic(f.getModifiers())){
                continue;
            }
            fName = f.getName();
            mName = "get" + fName.substring(0, 1).toUpperCase() + fName.substring(1);
            try {
                accessMethod = clazz.getMethod(mName, new Class<?>[] {});
                if (accessMethod != null) {
                    fieldCount++;
                    fieldValue = accessMethod.invoke(target, new Object[] {});
                }else{
                    continue;
                }

                if (fieldValue != null) {
                    fieldString = fieldValue.toString();
                } else {
                    fieldString = "null";
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (SecurityException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            
            if(fieldCount > 1){
                sb.append(",");
            }
            
            sb.append(fName);
            sb.append("=");
            sb.append(fieldString);

            fName = null;
            mName = null;
            accessMethod = null;
            fieldValue = null;
            fieldString = null;
        }

        sb.append("]");
        return sb.toString();
    }
}
