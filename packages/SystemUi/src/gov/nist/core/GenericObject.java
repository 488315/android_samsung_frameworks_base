package gov.nist.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class GenericObject implements Serializable, Cloneable {
    protected Match matchExpression;
    public static final Set immutableClasses = new HashSet(10);
    public static final String[] immutableClassNames = {"String", "Character", "Boolean", "Byte", "Short", "Integer", "Long", "Float", "Double"};
    protected int indentation = 0;
    protected String stringRepresentation = "";

    static {
        int i = 0;
        while (true) {
            try {
                String[] strArr = immutableClassNames;
                if (i >= strArr.length) {
                    return;
                }
                immutableClasses.add(Class.forName("java.lang." + strArr[i]));
                i++;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Internal error", e);
            }
        }
    }

    public static Object makeClone(Object obj) {
        if (obj == null) {
            throw new NullPointerException("null obj!");
        }
        Class<?> cls = obj.getClass();
        if (((HashSet) immutableClasses).contains(cls)) {
            return obj;
        }
        if (cls.isArray()) {
            Class<?> componentType = cls.getComponentType();
            if (componentType.isPrimitive()) {
                return componentType == Byte.TYPE ? ((byte[]) obj).clone() : componentType == Short.TYPE ? ((short[]) obj).clone() : componentType == Integer.TYPE ? ((int[]) obj).clone() : componentType == Long.TYPE ? ((long[]) obj).clone() : componentType == Float.TYPE ? ((float[]) obj).clone() : componentType == Double.TYPE ? ((double[]) obj).clone() : componentType == Character.TYPE ? ((char[]) obj).clone() : componentType == Boolean.TYPE ? ((boolean[]) obj).clone() : obj;
            }
            return ((Object[]) obj).clone();
        }
        if (GenericObject.class.isAssignableFrom(cls)) {
            return ((GenericObject) obj).clone();
        }
        if (GenericObjectList.class.isAssignableFrom(cls)) {
            return ((GenericObjectList) obj).clone();
        }
        if (!Cloneable.class.isAssignableFrom(cls)) {
            return obj;
        }
        try {
            return cls.getMethod("clone", null).invoke(obj, null);
        } catch (IllegalAccessException | NoSuchMethodException | SecurityException | InvocationTargetException unused) {
            return obj;
        } catch (IllegalArgumentException e) {
            InternalErrorHandler.handleException(e);
            throw null;
        }
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new RuntimeException("Internal error");
        }
    }

    public abstract String encode();

    public StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(encode());
        return stringBuffer;
    }

    public boolean equals(Object obj) {
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        Class<?> cls = getClass();
        Class<?> cls2 = obj.getClass();
        while (true) {
            Field[] declaredFields = cls.getDeclaredFields();
            Field[] declaredFields2 = cls2.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                Field field = declaredFields[i];
                Field field2 = declaredFields2[i];
                if ((field.getModifiers() & 2) != 2) {
                    Class<?> type = field.getType();
                    String name = field.getName();
                    if (name.compareTo("stringRepresentation") != 0 && name.compareTo("indentation") != 0) {
                        try {
                            if (type.isPrimitive()) {
                                String cls3 = type.toString();
                                if (cls3.compareTo("int") == 0) {
                                    if (field.getInt(this) != field2.getInt(obj)) {
                                        return false;
                                    }
                                } else if (cls3.compareTo("short") == 0) {
                                    if (field.getShort(this) != field2.getShort(obj)) {
                                        return false;
                                    }
                                } else if (cls3.compareTo("char") == 0) {
                                    if (field.getChar(this) != field2.getChar(obj)) {
                                        return false;
                                    }
                                } else if (cls3.compareTo("long") == 0) {
                                    if (field.getLong(this) != field2.getLong(obj)) {
                                        return false;
                                    }
                                } else if (cls3.compareTo("boolean") == 0) {
                                    if (field.getBoolean(this) != field2.getBoolean(obj)) {
                                        return false;
                                    }
                                } else if (cls3.compareTo("double") == 0) {
                                    if (field.getDouble(this) != field2.getDouble(obj)) {
                                        return false;
                                    }
                                } else if (cls3.compareTo("float") == 0 && field.getFloat(this) != field2.getFloat(obj)) {
                                    return false;
                                }
                            } else {
                                if (field2.get(obj) == field.get(this)) {
                                    return true;
                                }
                                if (field.get(this) == null || field2.get(obj) == null) {
                                    return false;
                                }
                                if ((field2.get(obj) == null && field.get(this) != null) || !field.get(this).equals(field2.get(obj))) {
                                    return false;
                                }
                            }
                        } catch (IllegalAccessException e) {
                            InternalErrorHandler.handleException(e);
                            throw null;
                        }
                    }
                }
            }
            if (cls.equals(GenericObject.class)) {
                return true;
            }
            cls = cls.getSuperclass();
            cls2 = cls2.getSuperclass();
        }
    }
}
