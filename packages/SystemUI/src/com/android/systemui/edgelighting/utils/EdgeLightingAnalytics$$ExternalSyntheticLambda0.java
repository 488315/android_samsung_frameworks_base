package com.android.systemui.edgelighting.utils;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public final /* synthetic */ class EdgeLightingAnalytics$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Field field = (Field) obj;
        return field.getType() == String.class && (field.getName().startsWith("SID_") || field.getName().startsWith("EID_"));
    }
}
