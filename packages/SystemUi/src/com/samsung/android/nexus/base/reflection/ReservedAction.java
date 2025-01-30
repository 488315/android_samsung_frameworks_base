package com.samsung.android.nexus.base.reflection;

import android.text.TextUtils;
import com.samsung.android.nexus.base.utils.Log;
import com.samsung.android.nexus.video.VideoLayer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ReservedAction {
    public final Object[] mArgs;
    public final Object mInstance;
    public final String mMethodName;
    public final Class[] mParamTypes;

    public ReservedAction(String str, Class<?>[] clsArr, Object[] objArr) {
        this(null, str, clsArr, objArr);
    }

    public final void doAction(VideoLayer videoLayer) {
        Method method;
        Class<?> cls = videoLayer.getClass();
        Class<?>[] clsArr = this.mParamTypes;
        String str = this.mMethodName;
        try {
            if (!TextUtils.isEmpty(str)) {
                try {
                    try {
                        method = cls.getMethod(str, clsArr);
                    } catch (NoSuchMethodException e) {
                        Log.m261e("ReservedAction", cls.getName() + " - No method. " + e);
                    }
                } catch (NoSuchMethodException unused) {
                    Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
                    declaredMethod.setAccessible(true);
                    method = declaredMethod;
                }
                method.invoke(videoLayer, this.mArgs);
                return;
            }
            method.invoke(videoLayer, this.mArgs);
            return;
        } catch (IllegalAccessException | NullPointerException | InvocationTargetException e2) {
            Log.m261e("ReservedAction", "Cannot invoke method : " + str + ", " + e2);
            return;
        }
        method = null;
    }

    public ReservedAction(Object obj, String str, Class<?>[] clsArr, Object[] objArr) {
        this.mInstance = obj;
        this.mMethodName = str;
        this.mParamTypes = clsArr;
        this.mArgs = objArr;
    }
}
