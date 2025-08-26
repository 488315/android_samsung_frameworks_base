package com.android.systemui.edgelighting.utils;

import java.lang.reflect.Field;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final /* synthetic */ class EdgeLightingAnalytics$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Field field = (Field) obj;
        boolean z = EdgeLightingAnalytics.sConfigured;
        if (field.getType() == String.class) {
            return field.getName().startsWith("SID_") || field.getName().startsWith("EID_");
        }
        return false;
    }
}
