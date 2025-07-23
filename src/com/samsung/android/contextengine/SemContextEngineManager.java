package com.samsung.android.contextengine;

import android.content.Context;

/* loaded from: classes6.dex */
public class SemContextEngineManager {
    private static final String TAG = "SemContextEngineManager";
    private final Context mContext;
    private final ISemContextEngineManager mService;
    private final int mUserId;

    public SemContextEngineManager(Context context, ISemContextEngineManager iSemContextEngineManager, int i) {
        this.mContext = context;
        this.mService = iSemContextEngineManager;
        this.mUserId = i;
    }
}
