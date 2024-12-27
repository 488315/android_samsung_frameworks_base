package com.android.server.pm.verify.domain;

import android.content.pm.IntentFilterVerificationInfo;
import android.util.ArrayMap;
import android.util.SparseIntArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DomainVerificationLegacySettings {
    public final Object mLock = new Object();
    public final ArrayMap mStates = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LegacyState {
        public boolean attached;
        public IntentFilterVerificationInfo mInfo;
        public SparseIntArray mUserStates;
    }

    public final LegacyState getOrCreateStateLocked(String str) {
        LegacyState legacyState = (LegacyState) this.mStates.get(str);
        if (legacyState != null) {
            return legacyState;
        }
        LegacyState legacyState2 = new LegacyState();
        this.mStates.put(str, legacyState2);
        return legacyState2;
    }

    public final int getUserState(int i, String str) {
        synchronized (this.mLock) {
            try {
                LegacyState legacyState = (LegacyState) this.mStates.get(str);
                if (legacyState == null) {
                    return 0;
                }
                return legacyState.mUserStates.get(i, 0);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final IntentFilterVerificationInfo remove(String str) {
        synchronized (this.mLock) {
            try {
                LegacyState legacyState = (LegacyState) this.mStates.get(str);
                if (legacyState == null || legacyState.attached) {
                    return null;
                }
                legacyState.attached = true;
                return legacyState.mInfo;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
