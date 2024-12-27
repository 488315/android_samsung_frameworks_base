package com.android.internal.os.anr;

import android.os.SystemClock;
import android.os.Trace;

import com.android.internal.util.FrameworkStatsLog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.atomic.AtomicInteger;

public class AnrLatencyTracker implements AutoCloseable {
    private static final AtomicInteger sNextAnrRecordPlacedOnQueueCookieGenerator =
            new AtomicInteger();
    private long mAMSLockLastTryAcquireStart;
    private long mAnrProcessingStartedUptime;
    private int mAnrQueueSize;
    private long mAnrRecordLastTryAcquireStart;
    private long mAnrRecordPlacedOnQueueUptime;
    private long mAnrTriggerUptime;
    private int mAnrType;
    private long mAppNotRespondingStartUptime;
    private long mCopyingFirstPidStartUptime;
    private long mCriticalEventLoglastCallUptime;
    private long mCurrentPsiStateLastCallUptime;
    private long mDumpStackTracesStartUptime;
    private long mEndUptime;
    private long mExtraPidsDumpingStartUptime;
    private long mFirstPidsDumpingStartUptime;
    private long mGlobalLockLastTryAcquireStart;
    private long mNativePidsDumpingStartUptime;
    private long mNotifyAppUnresponsiveStartUptime;
    private long mNotifyWindowUnresponsiveStartUptime;
    private long mPidLockLastTryAcquireStart;
    private long mPreDumpIfLockTooSlowStartUptime;
    private long mProcLockLastTryAcquireStart;
    private long mProcessCpuTrackerMethodsLastCallUptime;
    private volatile long mTempFileDumpingStartUptime;
    private long mUpdateCpuStatsNowLastCallUptime;
    private long mUpdateCpuStatsNowTotalLatency = 0;
    private long mCurrentPsiStateTotalLatency = 0;
    private long mProcessCpuTrackerMethodsTotalLatency = 0;
    private long mCriticalEventLogTotalLatency = 0;
    private long mGlobalLockTotalContention = 0;
    private long mPidLockTotalContention = 0;
    private long mAMSLockTotalContention = 0;
    private long mProcLockTotalContention = 0;
    private long mAnrRecordLockTotalContention = 0;
    private final AtomicInteger mDumpedProcessesCount = new AtomicInteger(0);
    private volatile int mEarlyDumpStatus = 1;
    private volatile long mTempFileDumpingDuration = 0;
    private long mCopyingFirstPidDuration = 0;
    private long mEarlyDumpRequestSubmissionUptime = 0;
    private long mEarlyDumpExecutorPidCount = 0;
    private long mFirstPidsDumpingDuration = 0;
    private long mNativePidsDumpingDuration = 0;
    private long mExtraPidsDumpingDuration = 0;
    private boolean mIsPushed = false;
    private boolean mIsSkipped = false;
    private boolean mCopyingFirstPidSucceeded = false;
    private long mPreDumpIfLockTooSlowDuration = 0;
    private long mNotifyAppUnresponsiveDuration = 0;
    private long mNotifyWindowUnresponsiveDuration = 0;
    private final int mAnrRecordPlacedOnQueueCookie =
            sNextAnrRecordPlacedOnQueueCookieGenerator.incrementAndGet();

    @Retention(RetentionPolicy.SOURCE)
    private @interface EarlyDumpStatus {
        public static final int FAILED_TO_CREATE_FILE = 3;
        public static final int SUCCEEDED = 2;
        public static final int TIMED_OUT = 4;
        public static final int UNKNOWN = 1;
    }

    public AnrLatencyTracker(int timeoutKind, long anrTriggerUptime) {
        this.mAnrTriggerUptime = anrTriggerUptime;
        this.mAnrType = timeoutKindToAnrType(timeoutKind);
    }

    public void appNotRespondingStarted() {
        this.mAppNotRespondingStartUptime = getUptimeMillis();
        Trace.traceBegin(64L, "AnrHelper#appNotResponding()");
    }

    public void appNotRespondingEnded() {
        Trace.traceEnd(64L);
    }

    public void earlyDumpRequestSubmittedWithSize(int currentProcessedPidCount) {
        this.mEarlyDumpRequestSubmissionUptime = getUptimeMillis();
        this.mEarlyDumpExecutorPidCount = currentProcessedPidCount;
    }

    public void anrRecordPlacingOnQueueWithSize(int queueSize) {
        this.mAnrRecordPlacedOnQueueUptime = getUptimeMillis();
        Trace.asyncTraceBegin(64L, "anrRecordPlacedOnQueue", this.mAnrRecordPlacedOnQueueCookie);
        this.mAnrQueueSize = queueSize;
        Trace.traceCounter(64L, "anrRecordsQueueSize", queueSize + 1);
    }

    public void anrProcessingStarted() {
        this.mAnrProcessingStartedUptime = getUptimeMillis();
        Trace.asyncTraceEnd(64L, "anrRecordPlacedOnQueue", this.mAnrRecordPlacedOnQueueCookie);
        Trace.traceBegin(64L, "anrProcessing");
    }

    public void anrProcessingEnded() {
        Trace.traceEnd(64L);
        close();
    }

    public void dumpStackTracesStarted() {
        this.mDumpStackTracesStartUptime = getUptimeMillis();
        Trace.traceBegin(64L, "dumpStackTraces()");
    }

    public void dumpStackTracesEnded() {
        Trace.traceEnd(64L);
    }

    public void updateCpuStatsNowCalled() {
        this.mUpdateCpuStatsNowLastCallUptime = getUptimeMillis();
        Trace.traceBegin(64L, "updateCpuStatsNow()");
    }

    public void updateCpuStatsNowReturned() {
        this.mUpdateCpuStatsNowTotalLatency +=
                getUptimeMillis() - this.mUpdateCpuStatsNowLastCallUptime;
        Trace.traceEnd(64L);
    }

    public void currentPsiStateCalled() {
        this.mCurrentPsiStateLastCallUptime = getUptimeMillis();
        Trace.traceBegin(64L, "currentPsiState()");
    }

    public void currentPsiStateReturned() {
        this.mCurrentPsiStateTotalLatency +=
                getUptimeMillis() - this.mCurrentPsiStateLastCallUptime;
        Trace.traceEnd(64L);
    }

    public void processCpuTrackerMethodsCalled() {
        this.mProcessCpuTrackerMethodsLastCallUptime = getUptimeMillis();
        Trace.traceBegin(64L, "processCpuTracker");
    }

    public void processCpuTrackerMethodsReturned() {
        this.mProcessCpuTrackerMethodsTotalLatency +=
                getUptimeMillis() - this.mProcessCpuTrackerMethodsLastCallUptime;
        Trace.traceEnd(64L);
    }

    public void criticalEventLogStarted() {
        this.mCriticalEventLoglastCallUptime = getUptimeMillis();
        Trace.traceBegin(64L, "criticalEventLog");
    }

    public void criticalEventLogEnded() {
        this.mCriticalEventLogTotalLatency +=
                getUptimeMillis() - this.mCriticalEventLoglastCallUptime;
        Trace.traceEnd(64L);
    }

    public void nativePidCollectionStarted() {
        Trace.traceBegin(64L, "nativePidCollection");
    }

    public void nativePidCollectionEnded() {
        Trace.traceEnd(64L);
    }

    public void dumpingPidStarted(int pid) {
        Trace.traceBegin(64L, "dumpingPid#" + pid);
    }

    public void dumpingPidEnded() {
        this.mDumpedProcessesCount.incrementAndGet();
        Trace.traceEnd(64L);
    }

    public void dumpingFirstPidsStarted() {
        this.mFirstPidsDumpingStartUptime = getUptimeMillis();
        Trace.traceBegin(64L, "dumpingFirstPids");
    }

    public void dumpingFirstPidsEnded() {
        this.mFirstPidsDumpingDuration = getUptimeMillis() - this.mFirstPidsDumpingStartUptime;
        Trace.traceEnd(64L);
    }

    public void copyingFirstPidStarted() {
        this.mCopyingFirstPidStartUptime = getUptimeMillis();
        Trace.traceBegin(64L, "copyingFirstPid");
    }

    public void copyingFirstPidEnded(boolean copySucceeded) {
        this.mCopyingFirstPidDuration = getUptimeMillis() - this.mCopyingFirstPidStartUptime;
        this.mCopyingFirstPidSucceeded = copySucceeded;
        Trace.traceEnd(64L);
    }

    public void dumpStackTracesTempFileStarted() {
        this.mTempFileDumpingStartUptime = getUptimeMillis();
        Trace.traceBegin(64L, "dumpStackTracesTempFile");
    }

    public void dumpStackTracesTempFileEnded() {
        this.mTempFileDumpingDuration = getUptimeMillis() - this.mTempFileDumpingStartUptime;
        if (this.mEarlyDumpStatus == 1) {
            this.mEarlyDumpStatus = 2;
        }
        Trace.traceEnd(64L);
    }

    public void dumpStackTracesTempFileCreationFailed() {
        this.mEarlyDumpStatus = 3;
        Trace.instant(64L, "dumpStackTracesTempFileCreationFailed");
    }

    public void dumpStackTracesTempFileTimedOut() {
        this.mEarlyDumpStatus = 4;
        Trace.instant(64L, "dumpStackTracesTempFileTimedOut");
    }

    public void dumpingNativePidsStarted() {
        this.mNativePidsDumpingStartUptime = getUptimeMillis();
        Trace.traceBegin(64L, "dumpingNativePids");
    }

    public void dumpingNativePidsEnded() {
        this.mNativePidsDumpingDuration = getUptimeMillis() - this.mNativePidsDumpingStartUptime;
        Trace.traceEnd(64L);
    }

    public void dumpingExtraPidsStarted() {
        this.mExtraPidsDumpingStartUptime = getUptimeMillis();
        Trace.traceBegin(64L, "dumpingExtraPids");
    }

    public void dumpingExtraPidsEnded() {
        this.mExtraPidsDumpingDuration = getUptimeMillis() - this.mExtraPidsDumpingStartUptime;
        Trace.traceEnd(64L);
    }

    public void waitingOnGlobalLockStarted() {
        this.mGlobalLockLastTryAcquireStart = getUptimeMillis();
        Trace.traceBegin(64L, "globalLock");
    }

    public void waitingOnGlobalLockEnded() {
        this.mGlobalLockTotalContention += getUptimeMillis() - this.mGlobalLockLastTryAcquireStart;
        Trace.traceEnd(64L);
    }

    public void waitingOnPidLockStarted() {
        this.mPidLockLastTryAcquireStart = getUptimeMillis();
        Trace.traceBegin(64L, "pidLockContention");
    }

    public void waitingOnPidLockEnded() {
        this.mPidLockTotalContention += getUptimeMillis() - this.mPidLockLastTryAcquireStart;
        Trace.traceEnd(64L);
    }

    public void waitingOnAMSLockStarted() {
        this.mAMSLockLastTryAcquireStart = getUptimeMillis();
        Trace.traceBegin(64L, "AMSLockContention");
    }

    public void waitingOnAMSLockEnded() {
        this.mAMSLockTotalContention += getUptimeMillis() - this.mAMSLockLastTryAcquireStart;
        Trace.traceEnd(64L);
    }

    public void waitingOnProcLockStarted() {
        this.mProcLockLastTryAcquireStart = getUptimeMillis();
        Trace.traceBegin(64L, "procLockContention");
    }

    public void waitingOnProcLockEnded() {
        this.mProcLockTotalContention += getUptimeMillis() - this.mProcLockLastTryAcquireStart;
        Trace.traceEnd(64L);
    }

    public void waitingOnAnrRecordLockStarted() {
        this.mAnrRecordLastTryAcquireStart = getUptimeMillis();
        Trace.traceBegin(64L, "anrRecordLockContention");
    }

    public void waitingOnAnrRecordLockEnded() {
        this.mAnrRecordLockTotalContention +=
                getUptimeMillis() - this.mAnrRecordLastTryAcquireStart;
        Trace.traceEnd(64L);
    }

    public void anrRecordsQueueSizeWhenPopped(int queueSize) {
        Trace.traceCounter(64L, "anrRecordsQueueSize", queueSize);
    }

    public void preDumpIfLockTooSlowStarted() {
        this.mPreDumpIfLockTooSlowStartUptime = getUptimeMillis();
    }

    public void preDumpIfLockTooSlowEnded() {
        this.mPreDumpIfLockTooSlowDuration +=
                getUptimeMillis() - this.mPreDumpIfLockTooSlowStartUptime;
    }

    public void anrSkippedProcessErrorStateRecordAppNotResponding() {
        anrSkipped("appNotResponding");
    }

    public void anrSkippedDumpStackTraces() {
        anrSkipped("dumpStackTraces");
    }

    public void notifyAppUnresponsiveStarted() {
        this.mNotifyAppUnresponsiveStartUptime = getUptimeMillis();
        Trace.traceBegin(64L, "notifyAppUnresponsive()");
    }

    public void notifyAppUnresponsiveEnded() {
        this.mNotifyAppUnresponsiveDuration =
                getUptimeMillis() - this.mNotifyAppUnresponsiveStartUptime;
        Trace.traceEnd(64L);
    }

    public void notifyWindowUnresponsiveStarted() {
        this.mNotifyWindowUnresponsiveStartUptime = getUptimeMillis();
        Trace.traceBegin(64L, "notifyWindowUnresponsive()");
    }

    public void notifyWindowUnresponsiveEnded() {
        this.mNotifyWindowUnresponsiveDuration =
                getUptimeMillis() - this.mNotifyWindowUnresponsiveStartUptime;
        Trace.traceEnd(64L);
    }

    public String dumpAsCommaSeparatedArrayWithHeader() {
        return "DurationsV5: "
                + this.mAnrTriggerUptime
                + ","
                + (this.mAppNotRespondingStartUptime - this.mAnrTriggerUptime)
                + ","
                + (this.mAnrRecordPlacedOnQueueUptime - this.mAppNotRespondingStartUptime)
                + ","
                + (this.mAnrProcessingStartedUptime - this.mAnrRecordPlacedOnQueueUptime)
                + ","
                + (this.mDumpStackTracesStartUptime - this.mAnrProcessingStartedUptime)
                + ","
                + this.mUpdateCpuStatsNowTotalLatency
                + ","
                + this.mCurrentPsiStateTotalLatency
                + ","
                + this.mProcessCpuTrackerMethodsTotalLatency
                + ","
                + this.mCriticalEventLogTotalLatency
                + ","
                + this.mGlobalLockTotalContention
                + ","
                + this.mPidLockTotalContention
                + ","
                + this.mAMSLockTotalContention
                + ","
                + this.mProcLockTotalContention
                + ","
                + this.mAnrRecordLockTotalContention
                + ","
                + this.mAnrQueueSize
                + ","
                + ((this.mFirstPidsDumpingStartUptime > 0
                                ? this.mFirstPidsDumpingStartUptime
                                : this.mCopyingFirstPidStartUptime)
                        - this.mDumpStackTracesStartUptime)
                + ","
                + this.mTempFileDumpingDuration
                + ","
                + (this.mTempFileDumpingStartUptime - this.mEarlyDumpRequestSubmissionUptime)
                + ","
                + this.mEarlyDumpExecutorPidCount
                + ","
                + this.mCopyingFirstPidDuration
                + ","
                + this.mEarlyDumpStatus
                + ","
                + (this.mCopyingFirstPidSucceeded ? 1 : 0)
                + ","
                + this.mPreDumpIfLockTooSlowDuration
                + ","
                + this.mNotifyAppUnresponsiveDuration
                + ","
                + this.mNotifyWindowUnresponsiveDuration
                + "\n\n";
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (!this.mIsSkipped && !this.mIsPushed) {
            this.mEndUptime = getUptimeMillis();
            pushAtom();
            this.mIsPushed = true;
        }
    }

    private static int timeoutKindToAnrType(int timeoutKind) {
        switch (timeoutKind) {
            case 1:
                return 4;
            case 2:
                return 5;
            case 3:
                return 1;
            case 4:
                return 3;
            case 5:
                return 2;
            case 6:
                return 6;
            case 7:
            default:
                return 0;
            case 8:
                return 7;
            case 9:
                return 8;
        }
    }

    public long getUptimeMillis() {
        return SystemClock.uptimeMillis();
    }

    public void pushAtom() {
        FrameworkStatsLog.write(
                516,
                this.mEndUptime - this.mAnrTriggerUptime,
                this.mFirstPidsDumpingStartUptime - this.mAnrTriggerUptime,
                this.mAppNotRespondingStartUptime - this.mAnrTriggerUptime,
                this.mAnrRecordPlacedOnQueueUptime - this.mAppNotRespondingStartUptime,
                this.mAnrProcessingStartedUptime - this.mAnrRecordPlacedOnQueueUptime,
                this.mDumpStackTracesStartUptime - this.mAnrProcessingStartedUptime,
                this.mFirstPidsDumpingDuration
                        + this.mNativePidsDumpingDuration
                        + this.mExtraPidsDumpingDuration,
                this.mUpdateCpuStatsNowTotalLatency,
                this.mCurrentPsiStateTotalLatency,
                this.mProcessCpuTrackerMethodsTotalLatency,
                this.mCriticalEventLogTotalLatency,
                this.mGlobalLockTotalContention,
                this.mPidLockTotalContention,
                this.mAMSLockTotalContention,
                this.mProcLockTotalContention,
                this.mAnrRecordLockTotalContention,
                this.mAnrQueueSize,
                this.mAnrType,
                this.mDumpedProcessesCount.get());
    }

    private void anrSkipped(String method) {
        Trace.instant(64L, "AnrSkipped@" + method);
        this.mIsSkipped = true;
    }
}
