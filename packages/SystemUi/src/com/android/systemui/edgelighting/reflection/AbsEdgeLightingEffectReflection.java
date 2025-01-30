package com.android.systemui.edgelighting.reflection;

import android.content.Context;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AbsEdgeLightingEffectReflection extends AbstractBaseReflection {
    public final ClassLoader mClassLoader;
    public final Object mInstance;

    public AbsEdgeLightingEffectReflection(Class<?> cls, Context context, Context context2, ClassLoader classLoader) {
        super(cls);
        this.mClassLoader = classLoader;
        this.mInstance = createInstance(new Class[]{Context.class, Context.class}, context, context2);
    }

    @Override // com.android.systemui.edgelighting.reflection.AbstractBaseReflection
    public final String getBaseClassName() {
        return "com.samsung.android.sdk.edgelighting.AbsEdgeLightingEffect";
    }
}
