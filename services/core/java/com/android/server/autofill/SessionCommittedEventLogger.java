package com.android.server.autofill;

import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SessionCommittedEventLogger {
    public Optional mEventInternal;
    public final int mSessionId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionCommittedEventInternal {
        public int mCommitReason;
        public int mComponentPackageUid;
        public boolean mLastFillResponseHasSaveInfo;
        public int mRequestCount;
        public int mSaveDataTypeCount;
        public int mSaveInfoCount;
        public int mServiceUid;
        public long mSessionDurationMillis;
    }

    public SessionCommittedEventLogger(int i) {
        this.mSessionId = i;
        SessionCommittedEventInternal sessionCommittedEventInternal =
                new SessionCommittedEventInternal();
        sessionCommittedEventInternal.mComponentPackageUid = -1;
        sessionCommittedEventInternal.mRequestCount = 0;
        sessionCommittedEventInternal.mCommitReason = 0;
        sessionCommittedEventInternal.mSessionDurationMillis = 0L;
        sessionCommittedEventInternal.mSaveInfoCount = -1;
        sessionCommittedEventInternal.mSaveDataTypeCount = -1;
        sessionCommittedEventInternal.mLastFillResponseHasSaveInfo = false;
        sessionCommittedEventInternal.mServiceUid = -1;
        this.mEventInternal = Optional.of(sessionCommittedEventInternal);
    }
}
