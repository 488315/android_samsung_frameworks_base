package com.android.systemui.widget;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SystemUIWidgetRes {
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
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, "#", str);
        Integer num = (Integer) ((ArrayMap) this.mResIds).get(m);
        if (num != null) {
            return num.intValue();
        }
        int identifier = this.mContext.getResources().getIdentifier(str, str2, this.mContext.getPackageName());
        if (identifier <= 0) {
            Log.e("SystemUIWidgetRes", "Invalid " + str);
            return identifier;
        }
        ((ArrayMap) this.mResIds).put(m, Integer.valueOf(identifier));
        return identifier;
    }
}
