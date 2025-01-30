package com.android.systemui.edgelighting.reflection;

import android.util.Slog;
import com.google.dexmaker.stock.ProxyBuilder;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AbstractProxyReflection {
    public final Class mBaseClass;
    public final String mClassName;
    public final Object mProxyInstance;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InvocationHooker implements InvocationHandler {
        public InvocationHooker() {
        }

        @Override // java.lang.reflect.InvocationHandler
        public final Object invoke(Object obj, Method method, Object[] objArr) {
            if (!"hashCode".equals(method.getName())) {
                return AbstractProxyReflection.this.invokeInternal(obj, method, objArr);
            }
            AbstractProxyReflection abstractProxyReflection = AbstractProxyReflection.this;
            Slog.i("AbstractProxyReflection", "Create reflection hash code : " + abstractProxyReflection.mClassName);
            String[] strArr = new String[0];
            ThreadLocal threadLocal = HashCodeBuilder.REGISTRY;
            final Object[] objArr2 = new Object[0];
            Supplier supplier = new Supplier() { // from class: org.apache.commons.lang3.Validate$$ExternalSyntheticLambda0
                public final /* synthetic */ String f$0 = "The object to build a hash code for must not be null";

                @Override // java.util.function.Supplier
                public final Object get() {
                    return String.format(this.f$0, objArr2);
                }
            };
            Object obj2 = abstractProxyReflection.mProxyInstance;
            Objects.requireNonNull(obj2, (Supplier<String>) supplier);
            HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37);
            Class<?> cls = obj2.getClass();
            HashCodeBuilder.reflectionAppend(obj2, cls, hashCodeBuilder, strArr);
            while (cls.getSuperclass() != null) {
                cls = cls.getSuperclass();
                HashCodeBuilder.reflectionAppend(obj2, cls, hashCodeBuilder, strArr);
            }
            return Integer.valueOf(hashCodeBuilder.iTotal);
        }
    }

    public AbstractProxyReflection(String str) {
        this(str, new Class[0], new Object[0]);
    }

    public Object invokeInternal(Object obj, Method method, Object[] objArr) {
        try {
            return ProxyBuilder.callSuper(obj, method, objArr);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public AbstractProxyReflection(String str, Class<?>[] clsArr, Object[] objArr) {
        this.mClassName = null;
        this.mBaseClass = null;
        this.mProxyInstance = null;
        this.mClassName = str;
        try {
            this.mBaseClass = Class.forName(str);
        } catch (ClassNotFoundException e) {
            System.err.println("AbstractProxyReflection Unable to instantiate class " + e);
        }
        Class cls = this.mBaseClass;
        if (cls == null) {
            Slog.i("AbstractProxyReflection", "There's no " + this.mClassName);
            return;
        }
        try {
            this.mProxyInstance = Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{this.mBaseClass}, new InvocationHooker());
            Slog.i("AbstractProxyReflection", "Create proxy instance for interface : " + this.mClassName);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public AbstractProxyReflection(Class<?> cls, ClassLoader classLoader) {
        this.mClassName = null;
        this.mBaseClass = null;
        this.mProxyInstance = null;
        this.mBaseClass = cls;
        try {
            this.mProxyInstance = Proxy.newProxyInstance(classLoader, new Class[]{cls}, new InvocationHooker());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
