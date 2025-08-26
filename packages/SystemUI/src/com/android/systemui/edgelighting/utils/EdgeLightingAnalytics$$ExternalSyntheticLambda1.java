package com.android.systemui.edgelighting.utils;

import android.util.Slog;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class EdgeLightingAnalytics$$ExternalSyntheticLambda1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Field field = (Field) obj;
        boolean z = EdgeLightingAnalytics.sConfigured;
        String str = null;
        try {
            str = (String) field.get(null);
        } catch (IllegalAccessException unused) {
        }
        if (str != null) {
            HashMap map = (HashMap) EdgeLightingAnalytics.sIDMap;
            if (!map.containsKey(str)) {
                map.put(str, field.getName());
                return;
            }
            if (field.getName().startsWith("SID_")) {
                Slog.d("EdgeLightingAnalytics", "Duplicated Key!! : " + field.getName() + " with " + ((String) map.get(str)));
            }
        }
    }
}
