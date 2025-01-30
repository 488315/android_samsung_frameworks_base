package com.google.dexmaker.p043dx.rop.type;

import com.google.dexmaker.p043dx.util.FixedSizeList;
import com.google.dexmaker.p043dx.util.MutabilityException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StdTypeList extends FixedSizeList {
    static {
        new StdTypeList(0);
        Type type = Type.INT;
        make(type);
        Type type2 = Type.LONG;
        make(type2);
        Type type3 = Type.FLOAT;
        make(type3);
        Type type4 = Type.DOUBLE;
        make(type4);
        Type type5 = Type.OBJECT;
        make(type5);
        make(Type.RETURN_ADDRESS);
        make(Type.THROWABLE);
        make(type, type);
        make(type2, type2);
        make(type3, type3);
        make(type4, type4);
        make(type5, type5);
        make(type, type5);
        make(type2, type5);
        make(type3, type5);
        make(type4, type5);
        make(type2, type);
        Type type6 = Type.INT_ARRAY;
        make(type6, type);
        Type type7 = Type.LONG_ARRAY;
        make(type7, type);
        Type type8 = Type.FLOAT_ARRAY;
        make(type8, type);
        Type type9 = Type.DOUBLE_ARRAY;
        make(type9, type);
        Type type10 = Type.OBJECT_ARRAY;
        make(type10, type);
        Type type11 = Type.BOOLEAN_ARRAY;
        make(type11, type);
        Type type12 = Type.BYTE_ARRAY;
        make(type12, type);
        Type type13 = Type.CHAR_ARRAY;
        make(type13, type);
        Type type14 = Type.SHORT_ARRAY;
        make(type14, type);
        make(type, type6, type);
        make(type2, type7, type);
        make(type3, type8, type);
        make(type4, type9, type);
        make(type5, type10, type);
        make(type, type11, type);
        make(type, type12, type);
        make(type, type13, type);
        make(type, type14, type);
    }

    public StdTypeList(int i) {
        super(i);
    }

    public static void make(Type type) {
        new StdTypeList(1).set(0, type);
    }

    public final Type get(int i) {
        try {
            Object obj = this.arr[i];
            if (obj != null) {
                return (Type) obj;
            }
            throw new NullPointerException("unset: " + i);
        } catch (ArrayIndexOutOfBoundsException unused) {
            if (i < 0) {
                throw new IndexOutOfBoundsException("n < 0");
            }
            throw new IndexOutOfBoundsException("n >= size()");
        }
    }

    public final void set(int i, Type type) {
        if (!this.mutable) {
            throw new MutabilityException("immutable instance");
        }
        try {
            this.arr[i] = type;
        } catch (ArrayIndexOutOfBoundsException unused) {
            if (i >= 0) {
                throw new IndexOutOfBoundsException("n >= size()");
            }
            throw new IndexOutOfBoundsException("n < 0");
        }
    }

    public static void make(Type type, Type type2) {
        StdTypeList stdTypeList = new StdTypeList(2);
        stdTypeList.set(0, type);
        stdTypeList.set(1, type2);
    }

    public static void make(Type type, Type type2, Type type3) {
        StdTypeList stdTypeList = new StdTypeList(3);
        stdTypeList.set(0, type);
        stdTypeList.set(1, type2);
        stdTypeList.set(2, type3);
    }
}
