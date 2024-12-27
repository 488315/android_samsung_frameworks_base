package com.samsung.android.server.corestate;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CoreStatePreferenceObserver {
    public final Context mContext;
    public final Map mIntegerDefaultKeyMap;
    public SharedPreferences mPref;
    public final Map mSharedPrefKeyTypeMap;

    public CoreStatePreferenceObserver(Context context) {
        HashMap hashMap = new HashMap();
        this.mSharedPrefKeyTypeMap = hashMap;
        this.mIntegerDefaultKeyMap = new HashMap();
        this.mContext = context;
        hashMap.put("mw_immersive_mode", Integer.TYPE);
    }
}
