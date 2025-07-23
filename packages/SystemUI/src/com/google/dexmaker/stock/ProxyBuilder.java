package com.google.dexmaker.stock;

import com.google.dexmaker.TypeId;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ProxyBuilder {
    public static final Map PRIMITIVE_TYPE_TO_UNBOX_METHOD;
    public final Object[] constructorArgValues;

    static {
        Collections.synchronizedMap(new HashMap());
        HashMap hashMap = new HashMap();
        hashMap.put(Boolean.TYPE, Boolean.class);
        hashMap.put(Integer.TYPE, Integer.class);
        hashMap.put(Byte.TYPE, Byte.class);
        hashMap.put(Long.TYPE, Long.class);
        hashMap.put(Short.TYPE, Short.class);
        hashMap.put(Float.TYPE, Float.class);
        hashMap.put(Double.TYPE, Double.class);
        hashMap.put(Character.TYPE, Character.class);
        PRIMITIVE_TYPE_TO_UNBOX_METHOD = new HashMap();
        for (Map.Entry entry : hashMap.entrySet()) {
            TypeId typeId = TypeId.get((Class) entry.getKey());
            TypeId typeId2 = TypeId.get((Class) entry.getValue());
            ((HashMap) PRIMITIVE_TYPE_TO_UNBOX_METHOD).put(typeId, typeId2.getMethod(typeId2, "valueOf", typeId));
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put(Boolean.TYPE, TypeId.get(Boolean.class).getMethod(TypeId.BOOLEAN, "booleanValue", new TypeId[0]));
        hashMap2.put(Integer.TYPE, TypeId.get(Integer.class).getMethod(TypeId.INT, "intValue", new TypeId[0]));
        hashMap2.put(Byte.TYPE, TypeId.get(Byte.class).getMethod(TypeId.BYTE, "byteValue", new TypeId[0]));
        hashMap2.put(Long.TYPE, TypeId.get(Long.class).getMethod(TypeId.LONG, "longValue", new TypeId[0]));
        hashMap2.put(Short.TYPE, TypeId.get(Short.class).getMethod(TypeId.SHORT, "shortValue", new TypeId[0]));
        hashMap2.put(Float.TYPE, TypeId.get(Float.class).getMethod(TypeId.FLOAT, "floatValue", new TypeId[0]));
        hashMap2.put(Double.TYPE, TypeId.get(Double.class).getMethod(TypeId.DOUBLE, "doubleValue", new TypeId[0]));
        hashMap2.put(Character.TYPE, TypeId.get(Character.class).getMethod(TypeId.CHAR, "charValue", new TypeId[0]));
    }

    private ProxyBuilder(Class<Object> cls) {
        ProxyBuilder.class.getClassLoader();
        Class[] clsArr = new Class[0];
        this.constructorArgValues = new Object[0];
        new HashSet();
    }

    public static Object callSuper(Object obj, Method method, Object... objArr) {
        try {
            return obj.getClass().getMethod("super$" + method.getName() + "$" + method.getReturnType().getName().replace('.', '_').replace('[', '_').replace(';', '_'), method.getParameterTypes()).invoke(obj, objArr);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
