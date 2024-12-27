package com.samsung.android.server.audio;

import com.samsung.android.cover.CoverManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
