package com.android.systemui.edgelighting.utils;

import android.util.Slog;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
