package com.google.dexmaker.p043dx.rop.cst;

import com.google.dexmaker.p043dx.util.ToHuman;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class Constant implements ToHuman, Comparable {
    public abstract int compareTo0(Constant constant);

    public abstract void typeName();

    @Override // java.lang.Comparable
    public final int compareTo(Constant constant) {
        Class<?> cls = getClass();
        Class<?> cls2 = constant.getClass();
        return cls != cls2 ? cls.getName().compareTo(cls2.getName()) : compareTo0(constant);
    }
}
