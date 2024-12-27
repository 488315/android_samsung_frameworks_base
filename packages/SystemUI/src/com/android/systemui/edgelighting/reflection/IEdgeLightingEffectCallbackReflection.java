package com.android.systemui.edgelighting.reflection;

import android.util.Slog;
import java.lang.reflect.Method;

public class IEdgeLightingEffectCallbackReflection extends AbstractProxyReflection {
    public IEdgeLightingEffectCallbackReflection(Class<?> cls, ClassLoader classLoader) {
        super(cls, classLoader);
    }

    @Override // com.android.systemui.edgelighting.reflection.AbstractProxyReflection
    public final Object invokeInternal(Object obj, Method method, Object[] objArr) {
        String name = method.getName();
        Slog.i("IEdgeLightingEffectCallbackReflection", "invokeInternal: " + name);
        if ("onStarted".equals(name)) {
            onStarted();
            return null;
        }
        if ("onStopped".equals(name)) {
            onStopped();
            return null;
        }
        if ("onClickedToast".equals(name)) {
            onClickedToast();
            return null;
        }
        if ("onSwipedToast".equals(name)) {
            onSwipedToast();
            return null;
        }
        if (!"onFlingDownedToast".equals(name)) {
            return null;
        }
        onFlingDownedToast(((Boolean) objArr[0]).booleanValue());
        return null;
    }

    public void onClickedToast() {
        Slog.i("IEdgeLightingEffectCallbackReflection", "onClickedToast");
    }

    public void onFlingDownedToast(boolean z) {
        Slog.i("IEdgeLightingEffectCallbackReflection", "onFlingDownedToast");
    }

    public void onStarted() {
        Slog.i("IEdgeLightingEffectCallbackReflection", "onStarted");
    }

    public void onStopped() {
        Slog.i("IEdgeLightingEffectCallbackReflection", "onStopped");
    }

    public void onSwipedToast() {
        Slog.i("IEdgeLightingEffectCallbackReflection", "onSwipedToast");
    }
}
