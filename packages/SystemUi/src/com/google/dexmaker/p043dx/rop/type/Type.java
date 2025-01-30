package com.google.dexmaker.p043dx.rop.type;

import com.google.dexmaker.p043dx.util.ToHuman;
import com.sec.ims.settings.ImsProfile;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Type implements ToHuman, Comparable {
    public static final Type BOOLEAN;
    public static final Type BOOLEAN_ARRAY;
    public static final Type BOOLEAN_CLASS;
    public static final Type BYTE;
    public static final Type BYTE_ARRAY;
    public static final Type BYTE_CLASS;
    public static final Type CHAR;
    public static final Type CHARACTER_CLASS;
    public static final Type CHAR_ARRAY;
    public static final Type DOUBLE;
    public static final Type DOUBLE_ARRAY;
    public static final Type DOUBLE_CLASS;
    public static final Type FLOAT;
    public static final Type FLOAT_ARRAY;
    public static final Type FLOAT_CLASS;
    public static final Type INT;
    public static final Type INTEGER_CLASS;
    public static final Type INT_ARRAY;
    public static final Type KNOWN_NULL;
    public static final Type LONG;
    public static final Type LONG_ARRAY;
    public static final Type LONG_CLASS;
    public static final Type OBJECT;
    public static final Type OBJECT_ARRAY;
    public static final Type RETURN_ADDRESS;
    public static final Type SHORT;
    public static final Type SHORT_ARRAY;
    public static final Type SHORT_CLASS;
    public static final Type STRING;
    public static final Type THROWABLE;
    public static final Type VOID;
    public static final Type VOID_CLASS;
    public static final HashMap internTable = new HashMap(500);
    public Type arrayType;
    public final int basicType;
    public String className;
    public Type componentType;
    public final String descriptor;

    static {
        Type type = new Type("Z", 1);
        BOOLEAN = type;
        Type type2 = new Type(ImsProfile.TIMER_NAME_B, 2);
        BYTE = type2;
        Type type3 = new Type(ImsProfile.TIMER_NAME_C, 3);
        CHAR = type3;
        Type type4 = new Type(ImsProfile.TIMER_NAME_D, 4);
        DOUBLE = type4;
        Type type5 = new Type(ImsProfile.TIMER_NAME_F, 5);
        FLOAT = type5;
        Type type6 = new Type(ImsProfile.TIMER_NAME_I, 6);
        INT = type6;
        Type type7 = new Type(ImsProfile.TIMER_NAME_J, 7);
        LONG = type7;
        Type type8 = new Type("S", 8);
        SHORT = type8;
        VOID = new Type("V", 0);
        KNOWN_NULL = new Type("<null>", 9);
        RETURN_ADDRESS = new Type("<addr>", 10);
        putIntern(type);
        putIntern(type2);
        putIntern(type3);
        putIntern(type4);
        putIntern(type5);
        putIntern(type6);
        putIntern(type7);
        putIntern(type8);
        intern("Ljava/lang/annotation/Annotation;");
        intern("Ljava/lang/Class;");
        intern("Ljava/lang/Cloneable;");
        Type intern = intern("Ljava/lang/Object;");
        OBJECT = intern;
        intern("Ljava/io/Serializable;");
        STRING = intern("Ljava/lang/String;");
        THROWABLE = intern("Ljava/lang/Throwable;");
        BOOLEAN_CLASS = intern("Ljava/lang/Boolean;");
        BYTE_CLASS = intern("Ljava/lang/Byte;");
        CHARACTER_CLASS = intern("Ljava/lang/Character;");
        DOUBLE_CLASS = intern("Ljava/lang/Double;");
        FLOAT_CLASS = intern("Ljava/lang/Float;");
        INTEGER_CLASS = intern("Ljava/lang/Integer;");
        LONG_CLASS = intern("Ljava/lang/Long;");
        SHORT_CLASS = intern("Ljava/lang/Short;");
        VOID_CLASS = intern("Ljava/lang/Void;");
        BOOLEAN_ARRAY = type.getArrayType();
        BYTE_ARRAY = type2.getArrayType();
        CHAR_ARRAY = type3.getArrayType();
        DOUBLE_ARRAY = type4.getArrayType();
        FLOAT_ARRAY = type5.getArrayType();
        INT_ARRAY = type6.getArrayType();
        LONG_ARRAY = type7.getArrayType();
        OBJECT_ARRAY = intern.getArrayType();
        SHORT_ARRAY = type8.getArrayType();
    }

    private Type(String str, int i, int i2) {
        if (str == null) {
            throw new NullPointerException("descriptor == null");
        }
        if (i < 0 || i >= 11) {
            throw new IllegalArgumentException("bad basicType");
        }
        if (i2 < -1) {
            throw new IllegalArgumentException("newAt < -1");
        }
        this.descriptor = str;
        this.basicType = i;
        this.arrayType = null;
        this.componentType = null;
    }

    public static Type intern(String str) {
        Type type;
        HashMap hashMap = internTable;
        synchronized (hashMap) {
            type = (Type) hashMap.get(str);
        }
        if (type != null) {
            return type;
        }
        try {
            char charAt = str.charAt(0);
            if (charAt == '[') {
                return intern(str.substring(1)).getArrayType();
            }
            int length = str.length();
            if (charAt == 'L') {
                int i = length - 1;
                if (str.charAt(i) == ';') {
                    for (int i2 = 1; i2 < i; i2++) {
                        char charAt2 = str.charAt(i2);
                        if (charAt2 != '(' && charAt2 != ')' && charAt2 != '.') {
                            if (charAt2 == '/') {
                                if (i2 == 1 || i2 == i || str.charAt(i2 - 1) == '/') {
                                    throw new IllegalArgumentException("bad descriptor: ".concat(str));
                                }
                            } else if (charAt2 != ';' && charAt2 != '[') {
                            }
                        }
                        throw new IllegalArgumentException("bad descriptor: ".concat(str));
                    }
                    return putIntern(new Type(str, 9));
                }
            }
            throw new IllegalArgumentException("bad descriptor: ".concat(str));
        } catch (IndexOutOfBoundsException unused) {
            throw new IllegalArgumentException("descriptor is empty");
        } catch (NullPointerException unused2) {
            throw new NullPointerException("descriptor == null");
        }
    }

    public static Type putIntern(Type type) {
        HashMap hashMap = internTable;
        synchronized (hashMap) {
            String str = type.descriptor;
            Type type2 = (Type) hashMap.get(str);
            if (type2 != null) {
                return type2;
            }
            hashMap.put(str, type);
            return type;
        }
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return this.descriptor.compareTo(((Type) obj).descriptor);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Type) {
            return this.descriptor.equals(((Type) obj).descriptor);
        }
        return false;
    }

    public final Type getArrayType() {
        if (this.arrayType == null) {
            this.arrayType = putIntern(new Type("[" + this.descriptor, 9));
        }
        return this.arrayType;
    }

    public final int hashCode() {
        return this.descriptor.hashCode();
    }

    @Override // com.google.dexmaker.p043dx.util.ToHuman
    public final String toHuman() {
        switch (this.basicType) {
            case 0:
                return "void";
            case 1:
                return "boolean";
            case 2:
                return "byte";
            case 3:
                return "char";
            case 4:
                return "double";
            case 5:
                return "float";
            case 6:
                return "int";
            case 7:
                return "long";
            case 8:
                return "short";
            case 9:
                if (this.descriptor.charAt(0) == '[') {
                    StringBuilder sb = new StringBuilder();
                    if (this.componentType == null) {
                        if (this.descriptor.charAt(0) != '[') {
                            throw new IllegalArgumentException("not an array type: " + this.descriptor);
                        }
                        this.componentType = intern(this.descriptor.substring(1));
                    }
                    sb.append(this.componentType.toHuman());
                    sb.append("[]");
                    return sb.toString();
                }
                if (this.className == null) {
                    if (!(this.basicType == 9)) {
                        throw new IllegalArgumentException("not an object type: " + this.descriptor);
                    }
                    if (this.descriptor.charAt(0) == '[') {
                        this.className = this.descriptor;
                    } else {
                        String str = this.descriptor;
                        this.className = str.substring(1, str.length() - 1);
                    }
                }
                return this.className.replace("/", ".");
            default:
                return this.descriptor;
        }
    }

    public final String toString() {
        return this.descriptor;
    }

    private Type(String str, int i) {
        this(str, i, -1);
    }
}
