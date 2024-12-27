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
public final class ChangeReporter {
    private static final Function<Integer, Set<ChangeReport>> NEW_CHANGE_REPORT_SET =
            new Function() { // from class:
                             // com.android.internal.compat.ChangeReporter$$ExternalSyntheticLambda0
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
    private final ConcurrentHashMap<Integer, Set<ChangeReport>> mReportedChanges =
            new ConcurrentHashMap<>();
    private boolean mDebugLogAll = false;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {}

    private static final class ChangeReport {
        long mChangeId;
        int mState;

        ChangeReport(long changeId, int state) {
            this.mChangeId = changeId;
            this.mState = state;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ChangeReport that = (ChangeReport) o;
            if (this.mChangeId == that.mChangeId && this.mState == that.mState) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Long.valueOf(this.mChangeId), Integer.valueOf(this.mState));
        }
    }

    public ChangeReporter(int source) {
        this.mSource = source;
    }

    public void reportChange(int uid, long changeId, int state, boolean isLoggableBySdk) {
        boolean isAlreadyReported =
                checkAndSetIsAlreadyReported(uid, new ChangeReport(changeId, state));
        if (!isAlreadyReported) {
            FrameworkStatsLog.write(228, uid, changeId, state, this.mSource);
        }
        if (shouldWriteToDebug(isAlreadyReported, state, isLoggableBySdk)) {
            debugLog(uid, changeId, state);
        }
    }

    public void reportChange(int uid, long changeId, int state) {
        reportChange(uid, changeId, state, true);
    }

    public void startDebugLogAll() {
        this.mDebugLogAll = true;
    }

    public void stopDebugLogAll() {
        this.mDebugLogAll = false;
    }

    boolean shouldWriteToStatsLog(int uid, long changeId, int state) {
        return !isAlreadyReported(uid, new ChangeReport(changeId, state));
    }

    private boolean shouldWriteToDebug(
            boolean isAlreadyReported, int state, boolean isLoggableBySdk) {
        if (this.mDebugLogAll) {
            return true;
        }
        if (isAlreadyReported) {
            return false;
        }
        boolean skipLoggingFlag = Flags.skipOldAndDisabledCompatLogging();
        if (!skipLoggingFlag || Log.isLoggable(TAG, 3)) {
            return true;
        }
        return isLoggableBySdk && state != 2;
    }

    boolean shouldWriteToDebug(int uid, long changeId, int state) {
        return shouldWriteToDebug(uid, changeId, state, true);
    }

    boolean shouldWriteToDebug(int uid, long changeId, int state, boolean isLoggableBySdk) {
        return shouldWriteToDebug(
                isAlreadyReported(uid, new ChangeReport(changeId, state)), state, isLoggableBySdk);
    }

    private boolean checkAndSetIsAlreadyReported(int uid, ChangeReport changeReport) {
        boolean isAlreadyReported = isAlreadyReported(uid, changeReport);
        if (!isAlreadyReported) {
            markAsReported(uid, changeReport);
        }
        return isAlreadyReported;
    }

    private boolean isAlreadyReported(int uid, ChangeReport report) {
        return this.mReportedChanges
                .getOrDefault(Integer.valueOf(uid), Collections.EMPTY_SET)
                .contains(report);
    }

    private void markAsReported(int uid, ChangeReport report) {
        this.mReportedChanges
                .computeIfAbsent(Integer.valueOf(uid), NEW_CHANGE_REPORT_SET)
                .add(report);
    }

    public void resetReportedChanges(int uid) {
        this.mReportedChanges.remove(Integer.valueOf(uid));
    }

    private void debugLog(int uid, long changeId, int state) {
        String message =
                TextUtils.formatSimple(
                        "Compat change id reported: %d; UID %d; state: %s",
                        Long.valueOf(changeId), Integer.valueOf(uid), stateToString(state));
        if (this.mSource == 2) {
            Slog.d(TAG, message);
        } else {
            Log.d(TAG, message);
        }
    }

    private static String stateToString(int state) {
        switch (state) {
            case 1:
                return "ENABLED";
            case 2:
                return "DISABLED";
            case 3:
                return "LOGGED";
            default:
                return "UNKNOWN";
        }
    }
}
