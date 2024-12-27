package com.android.systemui.widget;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.Map;

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
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, "#", str);
        Integer num = (Integer) ((ArrayMap) this.mResIds).get(m);
        if (num != null) {
            return num.intValue();
        }
        int identifier = this.mContext.getResources().getIdentifier(str, str2, this.mContext.getPackageName());
        if (identifier <= 0) {
            Log.e("SystemUIWidgetRes", "Invalid " + str);
        } else {
            ((ArrayMap) this.mResIds).put(m, Integer.valueOf(identifier));
        }
        return identifier;
    }
}
