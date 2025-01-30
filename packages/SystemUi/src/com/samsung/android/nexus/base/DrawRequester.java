package com.samsung.android.nexus.base;

import com.samsung.android.nexus.base.utils.Log;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DrawRequester {
    public final Method mInvalidateMethod;
    public final Object mInvalidatorInstance;

    public DrawRequester(Object obj) {
        this.mInvalidateMethod = null;
        this.mInvalidatorInstance = obj;
        try {
            try {
                this.mInvalidateMethod = obj.getClass().getMethod("invalidate", new Class[0]);
            } catch (NoSuchMethodException unused) {
                Log.m261e("DrawRequester", "There's no invalidate() method in you Engine. You should implement it.");
            }
        } catch (NoSuchMethodException unused2) {
            Method declaredMethod = obj.getClass().getDeclaredMethod("invalidate", new Class[0]);
            this.mInvalidateMethod = declaredMethod;
            declaredMethod.setAccessible(true);
        }
    }
}
