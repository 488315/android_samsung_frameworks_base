package com.samsung.android.knox.analytics.util;

import android.os.Bundle;
import com.samsung.android.knox.analytics.database.Contract;

/* loaded from: classes6.dex */
public class DatabaseCleanResult {
    private static final String TAG = "[KnoxAnalytics] " + DatabaseCleanResult.class.getSimpleName();
    private long mDeletedEventsCount;
    private long mDeletedSizeBytes;

    public DatabaseCleanResult(long deletedSizeBytes, long deletedEventsCount) {
        this.mDeletedSizeBytes = deletedSizeBytes;
        this.mDeletedEventsCount = deletedEventsCount;
    }

    public static DatabaseCleanResult fromBundle(Bundle in) {
        if (!in.containsKey(Contract.DatabaseClean.Extra.DELETED_SIZE_BYTES) || !in.containsKey(Contract.DatabaseClean.Extra.DELETED_EVENTS_COUNT)) {
            Log.e(TAG, "fromBundle(): invalid bundle.");
            return null;
        }
        return new DatabaseCleanResult(in.getLong(Contract.DatabaseClean.Extra.DELETED_SIZE_BYTES), in.getLong(Contract.DatabaseClean.Extra.DELETED_EVENTS_COUNT));
    }

    public long getDeletedSizeBytes() {
        return this.mDeletedSizeBytes;
    }

    public long getDeletedEventsCount() {
        return this.mDeletedEventsCount;
    }
}
