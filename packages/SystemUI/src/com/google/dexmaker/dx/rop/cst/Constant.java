package com.google.dexmaker.dx.rop.cst;

import com.google.dexmaker.dx.util.ToHuman;

/* loaded from: classes4.dex */
public abstract class Constant implements ToHuman, Comparable {
    public abstract int compareTo0(Constant constant);

    @Override // java.lang.Comparable
    public final int compareTo(Constant constant) {
        Class<?> cls = getClass();
        Class<?> cls2 = constant.getClass();
        return cls != cls2 ? cls.getName().compareTo(cls2.getName()) : compareTo0(constant);
    }
}
