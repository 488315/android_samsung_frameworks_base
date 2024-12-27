package com.samsung.android.server.contextengine;

import android.content.Context;
import android.util.Log;

import com.samsung.android.contextengine.ISemContextEngineManager;

public final class SemContextEngineServiceImpl extends ISemContextEngineManager.Stub {
    public SemContextEngineServiceImpl(Context context) {
        Log.i("ContextEngineManager", "create");
    }

    public final void setDefault() {}
}
