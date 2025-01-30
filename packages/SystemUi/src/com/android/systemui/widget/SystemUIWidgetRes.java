package com.android.systemui.widget;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemUIWidgetRes {
    public static final SystemUIWidgetRes sInstance = new SystemUIWidgetRes();
    public Context mContext;
    public final Map mResIds = new ArrayMap();

    private SystemUIWidgetRes() {
    }

    public static SystemUIWidgetRes getInstance(Context context) {
        SystemUIWidgetRes systemUIWidgetRes = sInstance;
        if (context != null && systemUIWidgetRes.mContext == null) {
            systemUIWidgetRes.mContext = context;
        }
        return systemUIWidgetRes;
    }

    public final int getResIdByName(String str, String str2) {
        String m15m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str2, "#", str);
        ArrayMap arrayMap = (ArrayMap) this.mResIds;
        Integer num = (Integer) arrayMap.get(m15m);
        if (num != null) {
            return num.intValue();
        }
        int identifier = this.mContext.getResources().getIdentifier(str, str2, this.mContext.getPackageName());
        if (identifier <= 0) {
            Log.e("SystemUIWidgetRes", "Invalid " + str);
        } else {
            arrayMap.put(m15m, Integer.valueOf(identifier));
        }
        return identifier;
    }
}
