package com.android.systemui.edgelighting.reflection;

import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class AbsEdgeLightingEffectReflection extends AbstractBaseReflection {
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
