package com.android.systemui.edgelighting.utils;

import android.util.Slog;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class EdgeLightingAnalytics$$ExternalSyntheticLambda1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Field field = (Field) obj;
        String str = null;
        try {
            str = (String) field.get(null);
        } catch (IllegalAccessException unused) {
        }
        if (str != null) {
            HashMap hashMap = (HashMap) EdgeLightingAnalytics.sIDMap;
            if (!hashMap.containsKey(str)) {
                hashMap.put(str, field.getName());
                return;
            }
            if (field.getName().startsWith("SID_")) {
                Slog.d("EdgeLightingAnalytics", "Duplicated Key!! : " + field.getName() + " with " + ((String) hashMap.get(str)));
            }
        }
    }
}
