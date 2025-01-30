package com.samsung.android.server.audio;

import android.content.Context;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;

/* loaded from: classes2.dex */
public class CoverHelper {
    public static CoverHelper sInstance;
    public CoverManager mCoverManager;
    public boolean mIsCoverSafetyVolume;

    public static synchronized CoverHelper getInstance() {
        CoverHelper coverHelper;
        synchronized (CoverHelper.class) {
            if (sInstance == null) {
                sInstance = new CoverHelper();
            }
            coverHelper = sInstance;
        }
        return coverHelper;
    }

    public void init(Context context) {
        this.mCoverManager = new CoverManager(context);
    }

    public boolean isCoverOpen() {
        CoverState coverState;
        CoverManager coverManager = this.mCoverManager;
        return coverManager == null || (coverState = coverManager.getCoverState()) == null || coverState.getSwitchState();
    }

    public boolean isCoverSafetyVolume() {
        return this.mIsCoverSafetyVolume;
    }

    public void setCoverSafetyVolume(boolean z) {
        this.mIsCoverSafetyVolume = z;
    }
}
