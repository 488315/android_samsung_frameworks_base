package com.android.systemui.plugins.annotations;

import android.util.Log;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class VersionCheckingProxy<T> implements InvocationHandler {
    public static final String TAG = "VersionCheckingProxy";
    private final Class<T> mClass;
    private Object mTargetInstance;
    private Supplier<Integer> mVersionSupplier;

    public VersionCheckingProxy(Class<T> cls, Object obj, Supplier<Integer> supplier) {
        this.mClass = cls;
        this.mTargetInstance = obj;
        this.mVersionSupplier = supplier;
    }

    public T get() {
        Class<T> cls;
        if (this.mTargetInstance == null || (cls = this.mClass) == null) {
            Log.e(TAG, "mTargetInstance or class is null");
            return null;
        }
        if (((SupportVersionChecker) cls.getDeclaredAnnotation(SupportVersionChecker.class)) == null) {
            Log.e(TAG, "this class not support version checking, please add @SupportVersionChecker annotation on class, class = " + this.mClass + ", target = " + this.mTargetInstance);
            return (T) this.mTargetInstance;
        }
        Log.i(TAG, "apply version checking, class = " + this.mClass + ", target = " + this.mTargetInstance);
        return (T) Proxy.newProxyInstance(this.mTargetInstance.getClass().getClassLoader(), new Class[]{this.mClass}, this);
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) {
        Supplier<Integer> supplier;
        VersionCheck versionCheck = (VersionCheck) method.getDeclaredAnnotation(VersionCheck.class);
        if (versionCheck == null || (supplier = this.mVersionSupplier) == null || supplier.get().intValue() >= versionCheck.version()) {
            return method.invoke(this.mTargetInstance, objArr);
        }
        Class<?> returnType = method.getReturnType();
        if (returnType == Boolean.TYPE) {
            return Boolean.valueOf(versionCheck.defBoolean());
        }
        if (returnType == Integer.TYPE) {
            return Integer.valueOf(versionCheck.defInt());
        }
        if (returnType == Float.TYPE) {
            return Float.valueOf(versionCheck.defFloat());
        }
        if (returnType == Long.TYPE) {
            return Long.valueOf(versionCheck.defLong());
        }
        if (returnType == String.class) {
            return versionCheck.defString();
        }
        return null;
    }
}
