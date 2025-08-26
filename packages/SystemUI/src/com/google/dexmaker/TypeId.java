package com.google.dexmaker;

import com.google.dexmaker.dx.rop.cst.CstType;
import com.google.dexmaker.dx.rop.type.Type;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class TypeId {
    public static final TypeId BOOLEAN;
    public static final TypeId BYTE;
    public static final TypeId CHAR;
    public static final TypeId DOUBLE;
    public static final TypeId FLOAT;
    public static final TypeId INT;
    public static final TypeId LONG;
    public static final Map PRIMITIVE_TO_TYPE;
    public static final TypeId SHORT;
    public final CstType constant;
    public final String name;
    public final Type ropType;

    static {
        TypeId typeId = new TypeId(Type.BOOLEAN);
        BOOLEAN = typeId;
        TypeId typeId2 = new TypeId(Type.BYTE);
        BYTE = typeId2;
        TypeId typeId3 = new TypeId(Type.CHAR);
        CHAR = typeId3;
        TypeId typeId4 = new TypeId(Type.DOUBLE);
        DOUBLE = typeId4;
        TypeId typeId5 = new TypeId(Type.FLOAT);
        FLOAT = typeId5;
        TypeId typeId6 = new TypeId(Type.INT);
        INT = typeId6;
        TypeId typeId7 = new TypeId(Type.LONG);
        LONG = typeId7;
        TypeId typeId8 = new TypeId(Type.SHORT);
        SHORT = typeId8;
        TypeId typeId9 = new TypeId(Type.VOID);
        new TypeId(Type.OBJECT);
        new TypeId(Type.STRING);
        HashMap map = new HashMap();
        PRIMITIVE_TO_TYPE = map;
        map.put(Boolean.TYPE, typeId);
        map.put(Byte.TYPE, typeId2);
        map.put(Character.TYPE, typeId3);
        map.put(Double.TYPE, typeId4);
        map.put(Float.TYPE, typeId5);
        map.put(Integer.TYPE, typeId6);
        map.put(Long.TYPE, typeId7);
        map.put(Short.TYPE, typeId8);
        map.put(Void.TYPE, typeId9);
    }

    public TypeId(String str, Type type) {
        if (str == null || type == null) {
            throw null;
        }
        this.name = str;
        this.ropType = type;
        this.constant = CstType.intern(type);
    }

    public static TypeId get(Class cls) {
        if (cls.isPrimitive()) {
            return (TypeId) ((HashMap) PRIMITIVE_TO_TYPE).get(cls);
        }
        String strReplace = cls.getName().replace('.', '/');
        if (!cls.isArray()) {
            strReplace = "L" + strReplace + ';';
        }
        try {
            return new TypeId(strReplace, strReplace.equals("V") ? Type.VOID : Type.intern(strReplace));
        } catch (NullPointerException unused) {
            throw new NullPointerException("descriptor == null");
        }
    }

    public final boolean equals(Object obj) {
        return (obj instanceof TypeId) && ((TypeId) obj).name.equals(this.name);
    }

    public final MethodId getMethod(TypeId typeId, String str, TypeId... typeIdArr) {
        return new MethodId(this, typeId, str, new TypeList(typeIdArr));
    }

    public final int hashCode() {
        return this.name.hashCode();
    }

    public final String toString() {
        return this.name;
    }

    public TypeId(Type type) {
        this(type.descriptor, type);
    }
}
