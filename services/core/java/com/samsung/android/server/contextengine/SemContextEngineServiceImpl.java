package com.samsung.android.server.contextengine;

import android.content.Context;
import android.util.Log;
import com.samsung.android.contextengine.ISemContextEngineManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemContextEngineServiceImpl extends ISemContextEngineManager.Stub {
    public SemContextEngineServiceImpl(Context context) {
        Log.i("ContextEngineManager", "create");
    }

    public final void setDefault() {
    }
}
