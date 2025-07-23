package com.android.internal.compat;

import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.internal.compat.flags.Flags;
import com.android.internal.util.FrameworkStatsLog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* loaded from: classes5.dex */
public class ChangeReporter {
    private static final Function<Integer, Set<ChangeReport>> NEW_CHANGE_REPORT_SET = new Function() { // from class: com.android.internal.compat.ChangeReporter$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            Set synchronizedSet;
            synchronizedSet = Collections.synchronizedSet(new HashSet());
            return synchronizedSet;
        }
    };
    public static final int SOURCE_APP_PROCESS = 1;
    public static final int SOURCE_SYSTEM_SERVER = 2;
    public static final int SOURCE_UNKNOWN_SOURCE = 0;
    public static final int STATE_DISABLED = 2;
    public static final int STATE_ENABLED = 1;
    public static final int STATE_LOGGED = 3;
    public static final int STATE_UNKNOWN_STATE = 0;
    private static final String TAG = "CompatChangeReporter";
    private int mSource;
    private final ConcurrentHashMap<Integer, Set<ChangeReport>> mReportedChanges = new ConcurrentHashMap<>();
    private boolean mDebugLogAll = false;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    boolean shouldWriteToStatsLog(boolean z, boolean z2) {
        return (z || z2) ? false : true;
    }

    private static final class ChangeReport {
        long mChangeId;
        int mState;

        ChangeReport(long j, int i) {
            this.mChangeId = j;
            this.mState = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                ChangeReport changeReport = (ChangeReport) obj;
                if (this.mChangeId == changeReport.mChangeId && this.mState == changeReport.mState) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Long.valueOf(this.mChangeId), Integer.valueOf(this.mState));
        }
    }

    public ChangeReporter(int i) {
        this.mSource = i;
    }

    public void reportChange(int i, long j, int i2, boolean z, boolean z2) {
        int i3;
        long j2;
        int i4;
        boolean checkAndSetIsAlreadyReported = checkAndSetIsAlreadyReported(i, new ChangeReport(j, i2));
        if (shouldWriteToStatsLog(z, checkAndSetIsAlreadyReported)) {
            i3 = i;
            j2 = j;
            i4 = i2;
            FrameworkStatsLog.write(228, i3, j2, i4, this.mSource);
        } else {
            i3 = i;
            j2 = j;
            i4 = i2;
        }
        if (shouldWriteToDebug(checkAndSetIsAlreadyReported, i4, z2)) {
            debugLog(i3, j2, i4);
        }
    }

    public void reportChange(int i, long j, int i2) {
        reportChange(i, j, i2, false, true);
    }

    public void startDebugLogAll() {
        this.mDebugLogAll = true;
    }

    public void stopDebugLogAll() {
        this.mDebugLogAll = false;
    }

    private boolean shouldWriteToDebug(boolean z, int i, boolean z2) {
        if (this.mDebugLogAll) {
            return true;
        }
        if (z) {
            return false;
        }
        if (!Flags.skipOldAndDisabledCompatLogging() || Log.isLoggable(TAG, 3)) {
            return true;
        }
        return z2 && i != 2;
    }

    boolean shouldWriteToDebug(int i, long j, int i2) {
        return shouldWriteToDebug(i, j, i2, true);
    }

    boolean shouldWriteToDebug(int i, long j, int i2, boolean z) {
        return shouldWriteToDebug(isAlreadyReported(i, new ChangeReport(j, i2)), i2, z);
    }

    private boolean checkAndSetIsAlreadyReported(int i, ChangeReport changeReport) {
        boolean isAlreadyReported = isAlreadyReported(i, changeReport);
        if (!isAlreadyReported) {
            markAsReported(i, changeReport);
        }
        return isAlreadyReported;
    }

    private boolean isAlreadyReported(int i, ChangeReport changeReport) {
        return this.mReportedChanges.getOrDefault(Integer.valueOf(i), Collections.EMPTY_SET).contains(changeReport);
    }

    boolean isAlreadyReported(int i, long j, int i2) {
        return isAlreadyReported(i, new ChangeReport(j, i2));
    }

    private void markAsReported(int i, ChangeReport changeReport) {
        this.mReportedChanges.computeIfAbsent(Integer.valueOf(i), NEW_CHANGE_REPORT_SET).add(changeReport);
    }

    public void resetReportedChanges(int i) {
        this.mReportedChanges.remove(Integer.valueOf(i));
    }

    private void debugLog(int i, long j, int i2) {
        String formatSimple = TextUtils.formatSimple("Compat change id reported: %d; UID %d; state: %s", Long.valueOf(j), Integer.valueOf(i), stateToString(i2));
        if (this.mSource == 2) {
            Slog.d(TAG, formatSimple);
        } else {
            Log.d(TAG, formatSimple);
        }
    }

    private static String stateToString(int i) {
        if (i == 1) {
            return "ENABLED";
        }
        if (i == 2) {
            return "DISABLED";
        }
        if (i == 3) {
            return "LOGGED";
        }
        return "UNKNOWN";
    }
}
