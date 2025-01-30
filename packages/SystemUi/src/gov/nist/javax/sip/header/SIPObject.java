package gov.nist.javax.sip.header;

import gov.nist.core.GenericObject;
import gov.nist.core.InternalErrorHandler;
import java.lang.reflect.Field;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class SIPObject extends GenericObject {
    @Override // gov.nist.core.GenericObject
    public StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(encode());
        return stringBuffer;
    }

    @Override // gov.nist.core.GenericObject
    public boolean equals(Object obj) {
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        SIPObject sIPObject = (SIPObject) obj;
        Class<?> cls = getClass();
        Class<?> cls2 = obj.getClass();
        while (true) {
            Field[] declaredFields = cls.getDeclaredFields();
            if (!cls2.equals(cls)) {
                return false;
            }
            Field[] declaredFields2 = cls2.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                Field field = declaredFields[i];
                Field field2 = declaredFields2[i];
                int modifiers = field.getModifiers();
                if ((modifiers & 2) != 2) {
                    Class<?> type = field.getType();
                    String name = field.getName();
                    if (name.compareTo("stringRepresentation") != 0 && name.compareTo("indentation") != 0) {
                        try {
                            if (type.isPrimitive()) {
                                String cls3 = type.toString();
                                if (cls3.compareTo("int") == 0) {
                                    if (field.getInt(this) != field2.getInt(sIPObject)) {
                                        return false;
                                    }
                                } else if (cls3.compareTo("short") == 0) {
                                    if (field.getShort(this) != field2.getShort(sIPObject)) {
                                        return false;
                                    }
                                } else if (cls3.compareTo("char") == 0) {
                                    if (field.getChar(this) != field2.getChar(sIPObject)) {
                                        return false;
                                    }
                                } else if (cls3.compareTo("long") == 0) {
                                    if (field.getLong(this) != field2.getLong(sIPObject)) {
                                        return false;
                                    }
                                } else if (cls3.compareTo("boolean") == 0) {
                                    if (field.getBoolean(this) != field2.getBoolean(sIPObject)) {
                                        return false;
                                    }
                                } else if (cls3.compareTo("double") == 0) {
                                    if (field.getDouble(this) != field2.getDouble(sIPObject)) {
                                        return false;
                                    }
                                } else if (cls3.compareTo("float") == 0 && field.getFloat(this) != field2.getFloat(sIPObject)) {
                                    return false;
                                }
                            } else if (field2.get(sIPObject) != field.get(this)) {
                                if (field.get(this) == null && field2.get(sIPObject) != null) {
                                    return false;
                                }
                                if ((field2.get(sIPObject) == null && field.get(this) != null) || !field.get(this).equals(field2.get(sIPObject))) {
                                    return false;
                                }
                            }
                        } catch (IllegalAccessException e) {
                            System.out.println("accessed field ".concat(name));
                            System.out.println("modifier  " + modifiers);
                            System.out.println("modifier.private  2");
                            InternalErrorHandler.handleException(e);
                            throw null;
                        }
                    }
                }
            }
            if (cls.equals(SIPObject.class)) {
                return true;
            }
            cls = cls.getSuperclass();
            cls2 = cls2.getSuperclass();
        }
    }

    public String toString() {
        return encode();
    }
}
