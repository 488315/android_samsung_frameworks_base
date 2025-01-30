package com.android.systemui.util;

import java.lang.reflect.Field;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SystemUIAnalytics$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Field field = (Field) obj;
        return field.getType() == String.class && (field.getName().startsWith("SID_") || field.getName().startsWith("EID_"));
    }
}
