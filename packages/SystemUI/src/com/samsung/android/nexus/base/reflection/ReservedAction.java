package com.samsung.android.nexus.base.reflection;

import android.text.TextUtils;
import com.samsung.android.nexus.base.utils.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class ReservedAction {
    public final Object[] mArgs;
    public final Object mInstance;
    public final String mMethodName;
    public final Class[] mParamTypes;

    public ReservedAction(String str, Class<?>[] clsArr, Object[] objArr) {
        this(null, str, clsArr, objArr);
    }

    public final void doAction(Object obj) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = obj.getClass();
        String str = this.mMethodName;
        Method method = null;
        if (!TextUtils.isEmpty(str)) {
            try {
                try {
                    method = cls.getMethod(str, this.mParamTypes);
                } catch (NoSuchMethodException e) {
                    Log.e("ReservedAction", cls.getName() + " - No method. " + e);
                }
            } catch (NoSuchMethodException unused) {
                Method declaredMethod = cls.getDeclaredMethod(str, this.mParamTypes);
                declaredMethod.setAccessible(true);
                method = declaredMethod;
            }
        }
        try {
            method.invoke(obj, this.mArgs);
        } catch (IllegalAccessException | NullPointerException | InvocationTargetException e2) {
            Log.e("ReservedAction", "Cannot invoke method : " + str + ", " + e2);
        }
    }

    public ReservedAction(Object obj, String str, Class<?>[] clsArr, Object[] objArr) {
        this.mInstance = obj;
        this.mMethodName = str;
        this.mParamTypes = clsArr;
        this.mArgs = objArr;
    }
}
