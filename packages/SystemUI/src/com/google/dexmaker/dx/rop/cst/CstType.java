package com.google.dexmaker.dx.rop.cst;

import com.google.dexmaker.dx.rop.type.Type;
import java.util.HashMap;

/* loaded from: classes4.dex */
public final class CstType extends TypedConstant {
    public static final HashMap interns = new HashMap(100);
    public final Type type;

    static {
        intern(Type.OBJECT);
        intern(Type.BOOLEAN_CLASS);
        intern(Type.BYTE_CLASS);
        intern(Type.CHARACTER_CLASS);
        intern(Type.DOUBLE_CLASS);
        intern(Type.FLOAT_CLASS);
        intern(Type.LONG_CLASS);
        intern(Type.INTEGER_CLASS);
        intern(Type.SHORT_CLASS);
        intern(Type.VOID_CLASS);
        intern(Type.BOOLEAN_ARRAY);
        intern(Type.BYTE_ARRAY);
        intern(Type.CHAR_ARRAY);
        intern(Type.DOUBLE_ARRAY);
        intern(Type.FLOAT_ARRAY);
        intern(Type.LONG_ARRAY);
        intern(Type.INT_ARRAY);
        intern(Type.SHORT_ARRAY);
    }

    public CstType(Type type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }
        if (type == Type.KNOWN_NULL) {
            throw new UnsupportedOperationException("KNOWN_NULL is not representable");
        }
        this.type = type;
    }

    public static CstType intern(Type type) {
        CstType cstType;
        HashMap map = interns;
        synchronized (map) {
            try {
                cstType = (CstType) map.get(type);
                if (cstType == null) {
                    cstType = new CstType(type);
                    map.put(type, cstType);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return cstType;
    }

    @Override // com.google.dexmaker.dx.rop.cst.Constant
    public final int compareTo0(Constant constant) {
        return this.type.descriptor.compareTo(((CstType) constant).type.descriptor);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof CstType) && this.type == ((CstType) obj).type;
    }

    public final int hashCode() {
        return this.type.descriptor.hashCode();
    }

    @Override // com.google.dexmaker.dx.util.ToHuman
    public final String toHuman() {
        return this.type.toHuman();
    }

    public final String toString() {
        return "type{" + this.type.toHuman() + '}';
    }
}
