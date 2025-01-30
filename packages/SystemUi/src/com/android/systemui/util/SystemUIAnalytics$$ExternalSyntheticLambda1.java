package com.android.systemui.util;

import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SystemUIAnalytics$$ExternalSyntheticLambda1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Field field = (Field) obj;
        String str = null;
        try {
            str = (String) field.get(null);
        } catch (IllegalAccessException unused) {
        }
        if (str != null) {
            HashMap hashMap = (HashMap) SystemUIAnalytics.sIDMap;
            if (!hashMap.containsKey(str)) {
                hashMap.put(str, field.getName());
            } else if (field.getName().startsWith("SID_")) {
                StringBuilder sb = new StringBuilder("Duplicated Key!! : ");
                sb.append(field.getName());
                sb.append(" with ");
                ExifInterface$$ExternalSyntheticOutline0.m35m(sb, (String) hashMap.get(str), "SystemUIAnalytics");
            }
        }
    }
}
