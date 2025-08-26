package gov.nist.javax.sip.header;

import gov.nist.core.GenericObject;
import gov.nist.core.InternalErrorHandler;
import java.lang.reflect.Field;

/* loaded from: classes4.dex */
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
        Class<?> superclass = getClass();
        Class<?> superclass2 = obj.getClass();
        while (true) {
            Field[] declaredFields = superclass.getDeclaredFields();
            if (!superclass2.equals(superclass)) {
                return false;
            }
            Field[] declaredFields2 = superclass2.getDeclaredFields();
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
                                String string = type.toString();
                                if (string.compareTo("int") == 0) {
                                    if (field.getInt(this) != field2.getInt(sIPObject)) {
                                        return false;
                                    }
                                } else if (string.compareTo("short") == 0) {
                                    if (field.getShort(this) != field2.getShort(sIPObject)) {
                                        return false;
                                    }
                                } else if (string.compareTo("char") == 0) {
                                    if (field.getChar(this) != field2.getChar(sIPObject)) {
                                        return false;
                                    }
                                } else if (string.compareTo("long") == 0) {
                                    if (field.getLong(this) != field2.getLong(sIPObject)) {
                                        return false;
                                    }
                                } else if (string.compareTo("boolean") == 0) {
                                    if (field.getBoolean(this) != field2.getBoolean(sIPObject)) {
                                        return false;
                                    }
                                } else if (string.compareTo("double") == 0) {
                                    if (field.getDouble(this) != field2.getDouble(sIPObject)) {
                                        return false;
                                    }
                                } else if (string.compareTo("float") == 0 && field.getFloat(this) != field2.getFloat(sIPObject)) {
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
            if (superclass.equals(SIPObject.class)) {
                return true;
            }
            superclass = superclass.getSuperclass();
            superclass2 = superclass2.getSuperclass();
        }
    }

    public String toString() {
        return encode();
    }
}
