package com.android.server.backup.remote;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RemoteResult {
    public final int mType;
    public final long mValue;
    public static final RemoteResult FAILED_TIMED_OUT = new RemoteResult(1, 0);
    public static final RemoteResult FAILED_CANCELLED = new RemoteResult(2, 0);
    public static final RemoteResult FAILED_THREAD_INTERRUPTED = new RemoteResult(3, 0);

    public RemoteResult(int i, long j) {
        this.mType = i;
        this.mValue = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RemoteResult)) {
            return false;
        }
        RemoteResult remoteResult = (RemoteResult) obj;
        return this.mType == remoteResult.mType && this.mValue == remoteResult.mValue;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mType), Long.valueOf(this.mValue));
    }

    public final String toString() {
        String l;
        StringBuilder sb = new StringBuilder("RemoteResult{");
        int i = this.mType;
        if (i == 0) {
            l = Long.toString(this.mValue);
        } else if (i == 1) {
            l = "FAILED_TIMED_OUT";
        } else if (i == 2) {
            l = "FAILED_CANCELLED";
        } else {
            if (i != 3) {
                throw new AssertionError("Unknown type");
            }
            l = "FAILED_THREAD_INTERRUPTED";
        }
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, l, "}");
    }
}
