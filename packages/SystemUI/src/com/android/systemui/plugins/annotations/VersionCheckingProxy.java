package com.android.systemui.plugins.annotations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            return null;
        }
        return ((SupportVersionChecker) cls.getDeclaredAnnotation(SupportVersionChecker.class)) == null ? (T) this.mTargetInstance : (T) Proxy.newProxyInstance(this.mTargetInstance.getClass().getClassLoader(), new Class[]{this.mClass}, this);
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
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
