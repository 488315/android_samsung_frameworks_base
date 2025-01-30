package com.samsung.android.server.corestate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Slog;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class CoreStatePreferenceObserver {
    public static final String TAG = "CoreStatePreferenceObserver";
    public final Context mContext;
    public SharedPreferences mPref;
    public final Map mSharedPrefKeyTypeMap = new HashMap();
    public final Map mIntegerDefaultKeyMap = new HashMap();

    public CoreStatePreferenceObserver(Context context) {
        this.mContext = context;
        registerObservingItems();
    }

    public void registerObservingItems() {
        this.mSharedPrefKeyTypeMap.put("mw_immersive_mode", Integer.TYPE);
    }

    public int populateState(Bundle bundle, int i) {
        return (populate(bundle, this.mSharedPrefKeyTypeMap) ? 1 : 0) | 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0054 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x000a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean populate(Bundle bundle, Map map) {
        int i;
        boolean z = false;
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            if (((Class) entry.getValue()) == Integer.TYPE) {
                if (map == this.mSharedPrefKeyTypeMap) {
                    SharedPreferences sharedPreference = getSharedPreference();
                    if (sharedPreference != null) {
                        i = sharedPreference.getInt(str, 0);
                        if (i == bundle.getInt(str, ((Integer) this.mIntegerDefaultKeyMap.getOrDefault(str, 0)).intValue())) {
                            bundle.putInt(str, i);
                            z = true;
                        }
                    } else {
                        Slog.w(TAG, "populate: failed to get getSharedPreference");
                    }
                }
                i = 0;
                if (i == bundle.getInt(str, ((Integer) this.mIntegerDefaultKeyMap.getOrDefault(str, 0)).intValue())) {
                }
            }
        }
        return z;
    }

    public final SharedPreferences getSharedPreference() {
        if (this.mPref == null) {
            this.mPref = this.mContext.getSharedPreferences("multiwindow.property", 0);
        }
        return this.mPref;
    }
}
