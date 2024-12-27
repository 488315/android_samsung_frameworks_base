package com.android.systemui.edgelighting.reflection;

import android.content.Context;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
