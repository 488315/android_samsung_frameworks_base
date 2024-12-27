package com.android.server.autofill;

import java.util.Optional;

public final class SessionCommittedEventLogger {
    public Optional mEventInternal;
    public final int mSessionId;

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
