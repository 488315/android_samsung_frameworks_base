package com.samsung.android.knox.analytics.util;

import android.os.Bundle;
import com.samsung.android.knox.analytics.database.Contract;

/* loaded from: classes6.dex */
public class DatabaseCleanResult {
    private static final String TAG = "[KnoxAnalytics] DatabaseCleanResult";
    private long mDeletedEventsCount;
    private long mDeletedSizeBytes;

    public DatabaseCleanResult(long j, long j2) {
        this.mDeletedSizeBytes = j;
        this.mDeletedEventsCount = j2;
    }

    public static DatabaseCleanResult fromBundle(Bundle bundle) {
        if (!bundle.containsKey(Contract.DatabaseClean.Extra.DELETED_SIZE_BYTES) || !bundle.containsKey(Contract.DatabaseClean.Extra.DELETED_EVENTS_COUNT)) {
            Log.e(TAG, "fromBundle(): invalid bundle.");
            return null;
        }
        return new DatabaseCleanResult(bundle.getLong(Contract.DatabaseClean.Extra.DELETED_SIZE_BYTES), bundle.getLong(Contract.DatabaseClean.Extra.DELETED_EVENTS_COUNT));
    }

    public long getDeletedSizeBytes() {
        return this.mDeletedSizeBytes;
    }

    public long getDeletedEventsCount() {
        return this.mDeletedEventsCount;
    }
}
