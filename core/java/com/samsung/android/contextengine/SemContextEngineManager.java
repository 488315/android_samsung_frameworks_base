package com.samsung.android.contextengine;

import android.content.Context;

/* loaded from: classes5.dex */
public class SemContextEngineManager {
    private static final String TAG = SemContextEngineManager.class.getSimpleName();
    private final Context mContext;
    private final ISemContextEngineManager mService;
    private final int mUserId;

    public SemContextEngineManager(Context context, ISemContextEngineManager service, int userId) {
        this.mContext = context;
        this.mService = service;
        this.mUserId = userId;
    }
}
