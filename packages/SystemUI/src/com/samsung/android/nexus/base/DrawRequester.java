package com.samsung.android.nexus.base;

import com.samsung.android.nexus.base.utils.Log;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class DrawRequester {
    public final Method mInvalidateMethod;
    public final Object mInvalidatorInstance;

    public DrawRequester(Object obj) throws NoSuchMethodException, SecurityException {
        this.mInvalidateMethod = null;
        this.mInvalidatorInstance = obj;
        try {
            try {
                Class[] clsArr = new Class[0];
                this.mInvalidateMethod = obj.getClass().getMethod("invalidate", null);
            } catch (NoSuchMethodException unused) {
                Log.e("DrawRequester", "There's no invalidate() method in you Engine. You should implement it.");
            }
        } catch (NoSuchMethodException unused2) {
            Class[] clsArr2 = new Class[0];
            Method declaredMethod = obj.getClass().getDeclaredMethod("invalidate", null);
            this.mInvalidateMethod = declaredMethod;
            declaredMethod.setAccessible(true);
        }
    }
}
