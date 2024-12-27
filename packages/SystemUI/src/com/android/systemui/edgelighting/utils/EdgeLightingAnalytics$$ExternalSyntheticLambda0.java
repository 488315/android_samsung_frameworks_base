package com.android.systemui.edgelighting.utils;

import java.lang.reflect.Field;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class EdgeLightingAnalytics$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Field field = (Field) obj;
        return field.getType() == String.class && (field.getName().startsWith("SID_") || field.getName().startsWith("EID_"));
    }
}
