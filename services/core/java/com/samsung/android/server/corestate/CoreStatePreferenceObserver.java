package com.samsung.android.server.corestate;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

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
