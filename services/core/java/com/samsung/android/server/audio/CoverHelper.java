package com.samsung.android.server.audio;

import com.samsung.android.cover.CoverManager;

public final class CoverHelper {
    public static CoverHelper sInstance;
    public CoverManager mCoverManager;
    public boolean mIsCoverSafetyVolume;

    public static synchronized CoverHelper getInstance() {
        CoverHelper coverHelper;
        synchronized (CoverHelper.class) {
            try {
                if (sInstance == null) {
                    sInstance = new CoverHelper();
                }
                coverHelper = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return coverHelper;
    }
}
