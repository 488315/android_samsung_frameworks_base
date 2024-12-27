package com.android.server.job.controllers;

import android.app.job.JobInfo;
import android.app.job.JobWorkItem;
import android.app.job.UserVisibleJobSummary;
import android.content.ClipData;
import android.content.Intent;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.Network;
import android.net.Uri;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Pair;
import android.util.Patterns;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;

import com.android.modules.expresslog.Counter;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.job.GrantedUriPermissions;
import com.android.server.job.JobSchedulerInternal;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda1;

import dalvik.annotation.optimization.NeverCompile;

import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class JobStatus {
    public static final ArrayMap BASIC_PII_FILTERS;
    public static final int CONSTRAINT_STORAGE_NOT_LOW = 8;
    public static MessageDigest sMessageDigest;
    public boolean appHasDozeExemption;
    public final String batteryName;
    public final int callingUid;
    public ArraySet changedAuthorities;
    public ArraySet changedUris;
    public ContentObserverController.JobInstance contentObserverJobInstance;
    public final long earliestRunTimeElapsedMillis;
    public long enqueueTime;
    public ArrayList executingWork;
    public final JobInfo job;
    public int lastEvaluatedBias;
    public final long latestRunTimeElapsedMillis;
    public final boolean mCanApplyTransportAffinities;
    public int mConstraintChangeHistoryIndex;
    public final int[] mConstraintStatusHistory;
    public final long[] mConstraintUpdatedTimesElapsed;
    public long mCumulativeExecutionTimeMs;
    public int mDynamicConstraints;
    public boolean mExpeditedQuotaApproved;
    public String[] mFilteredDebugTags;
    public String mFilteredTraceTag;
    public long mFirstForceBatchedTimeElapsed;
    public final boolean mHasExemptedMediaUrisOnly;
    public boolean mHasMediaBackupExemption;
    public int mInternalFlags;
    public boolean mIsDowngradedDueToBuggyApp;
    public final boolean mIsProxyJob;
    public boolean mIsUserBgRestricted;
    public JobSchedulerInternal mJobSchedulerInternal;
    public final long mLastFailedRunTime;
    public final long mLastSuccessfulRunTime;
    public boolean mLoggedBucketMismatch;
    public final long mLoggingJobId;
    public long mMinimumNetworkChunkBytes;
    public final String mNamespace;
    public final String mNamespaceHash;
    public int mNumAppliedFlexibleConstraints;
    public int mNumDroppedFlexibleConstraints;
    public final int mNumSystemStops;
    public long mOriginalLatestRunTimeElapsedMillis;
    public Pair mPersistedUtcTimes;
    public boolean mReadyDeadlineSatisfied;
    public boolean mReadyDynamicSatisfied;
    public boolean mReadyNotDozing;
    public boolean mReadyNotRestrictedInBg;
    public boolean mReadyWithinQuota;
    public int mReasonReadyToUnready;
    public final int mRequiredConstraintsOfInterest;
    public int mSatisfiedConstraintsOfInterest;
    public String mSystemTraceTag;
    public long mTotalNetworkDownloadBytes;
    public long mTotalNetworkUploadBytes;
    public boolean mTransportAffinitiesSatisfied;
    public UserVisibleJobSummary mUserVisibleJobSummary;
    public String mWakelockTag;
    public long madeActive;
    public long madePending;
    public Network network;
    public int nextPendingWorkId;
    public final int numFailures;
    public int overrideState;
    public ArrayList pendingWork;
    public boolean prepared;
    public final int requiredConstraints;
    public int satisfiedConstraints;
    public String serviceProcessName;
    public final String sourcePackageName;
    public final String sourceTag;
    public final int sourceUid;
    public final int sourceUserId;
    public int standbyBucket;
    public boolean startedAsExpeditedJob;
    public boolean startedAsUserInitiatedJob;
    public boolean startedWithForegroundFlag;
    public boolean startedWithImmediacyPrivilege;
    public int trackingControllers;
    public boolean uidActive;
    public Throwable unpreparedPoint;
    public GrantedUriPermissions uriPerms;
    public long whenStandbyDeferred;
    public static final boolean DEBUG = JobSchedulerService.DEBUG;
    public static final ArrayMap sNamespaceHashCache = new ArrayMap();
    public static final Uri[] MEDIA_URIS_FOR_STANDBY_EXEMPTION = {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    };

    static {
        ArrayMap arrayMap = new ArrayMap();
        BASIC_PII_FILTERS = arrayMap;
        arrayMap.put(Patterns.EMAIL_ADDRESS, "[EMAIL]");
        arrayMap.put(Patterns.PHONE, "[PHONE]");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0254 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0129  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public JobStatus(
            android.app.job.JobInfo r20,
            int r21,
            java.lang.String r22,
            int r23,
            int r24,
            java.lang.String r25,
            java.lang.String r26,
            int r27,
            int r28,
            long r29,
            long r31,
            long r33,
            long r35,
            long r37,
            int r39,
            int r40) {
        /*
            Method dump skipped, instructions count: 684
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.job.controllers.JobStatus.<init>(android.app.job.JobInfo,"
                    + " int, java.lang.String, int, int, java.lang.String, java.lang.String, int,"
                    + " int, long, long, long, long, long, int, int):void");
    }

    public JobStatus(
            JobStatus jobStatus, long j, long j2, int i, int i2, long j3, long j4, long j5) {
        this(
                jobStatus.job,
                jobStatus.callingUid,
                jobStatus.sourcePackageName,
                jobStatus.sourceUserId,
                jobStatus.standbyBucket,
                jobStatus.mNamespace,
                jobStatus.sourceTag,
                i,
                i2,
                j,
                j2,
                j3,
                j4,
                j5,
                jobStatus.mInternalFlags,
                jobStatus.mDynamicConstraints);
    }

    public static String applyBasicPiiFilters(String str) {
        for (int size = BASIC_PII_FILTERS.size() - 1; size >= 0; size--) {
            ArrayMap arrayMap = BASIC_PII_FILTERS;
            str =
                    ((Pattern) arrayMap.keyAt(size))
                            .matcher(str)
                            .replaceAll((String) arrayMap.valueAt(size));
        }
        return str;
    }

    public static String bucketName(int i) {
        switch (i) {
            case 0:
                return "ACTIVE";
            case 1:
                return "WORKING_SET";
            case 2:
                return "FREQUENT";
            case 3:
                return "RARE";
            case 4:
                return "NEVER";
            case 5:
                return "RESTRICTED";
            case 6:
                return "EXEMPTED";
            default:
                return VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown: ");
        }
    }

    public static JobStatus createFromJobInfo(
            JobInfo jobInfo, int i, String str, int i2, String str2, String str3) {
        long minLatencyMillis;
        long maxExecutionDelayMillis;
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (jobInfo.isPeriodic()) {
            long max =
                    Math.max(
                            JobInfo.getMinPeriodMillis(),
                            Math.min(31536000000L, jobInfo.getIntervalMillis()));
            maxExecutionDelayMillis = elapsedRealtime + max;
            minLatencyMillis =
                    maxExecutionDelayMillis
                            - Math.max(
                                    JobInfo.getMinFlexMillis(),
                                    Math.min(max, jobInfo.getFlexMillis()));
        } else {
            minLatencyMillis =
                    jobInfo.hasEarlyConstraint()
                            ? jobInfo.getMinLatencyMillis() + elapsedRealtime
                            : 0L;
            maxExecutionDelayMillis =
                    jobInfo.hasLateConstraint()
                            ? jobInfo.getMaxExecutionDelayMillis() + elapsedRealtime
                            : Long.MAX_VALUE;
        }
        return new JobStatus(
                jobInfo,
                i,
                str,
                i2,
                JobSchedulerService.standbyBucketForPackage(
                        i2,
                        str != null ? str : jobInfo.getService().getPackageName(),
                        elapsedRealtime),
                str2,
                str3,
                0,
                0,
                minLatencyMillis,
                maxExecutionDelayMillis,
                0L,
                0L,
                0L,
                0,
                0);
    }

    public static void dumpConstraints(int i, PrintWriter printWriter) {
        if ((i & 1) != 0) {
            printWriter.print(" CHARGING");
        }
        if ((i & 2) != 0) {
            printWriter.print(" BATTERY_NOT_LOW");
        }
        if ((i & 8) != 0) {
            printWriter.print(" STORAGE_NOT_LOW");
        }
        if ((Integer.MIN_VALUE & i) != 0) {
            printWriter.print(" TIMING_DELAY");
        }
        if ((1073741824 & i) != 0) {
            printWriter.print(" DEADLINE");
        }
        if ((i & 4) != 0) {
            printWriter.print(" IDLE");
        }
        if ((268435456 & i) != 0) {
            printWriter.print(" CONNECTIVITY");
        }
        if ((2097152 & i) != 0) {
            printWriter.print(" FLEXIBILITY");
        }
        if ((67108864 & i) != 0) {
            printWriter.print(" CONTENT_TRIGGER");
        }
        if ((33554432 & i) != 0) {
            printWriter.print(" DEVICE_NOT_DOZING");
        }
        if ((4194304 & i) != 0) {
            printWriter.print(" BACKGROUND_NOT_RESTRICTED");
        }
        if ((8388608 & i) != 0) {
            printWriter.print(" PREFETCH");
        }
        if ((16777216 & i) != 0) {
            printWriter.print(" WITHIN_QUOTA");
        }
        if ((1048576 & i) != 0) {
            printWriter.print(" UID_NOT_RESTRICTED");
        }
        if (i != 0) {
            printWriter.print(" [0x");
            printWriter.print(Integer.toHexString(i));
            printWriter.print("]");
        }
    }

    public static void dumpConstraints(ProtoOutputStream protoOutputStream, long j, int i) {
        if ((i & 1) != 0) {
            protoOutputStream.write(j, 1);
        }
        if ((i & 2) != 0) {
            protoOutputStream.write(j, 2);
        }
        if ((i & 8) != 0) {
            protoOutputStream.write(j, 3);
        }
        if ((Integer.MIN_VALUE & i) != 0) {
            protoOutputStream.write(j, 4);
        }
        if ((1073741824 & i) != 0) {
            protoOutputStream.write(j, 5);
        }
        if ((i & 4) != 0) {
            protoOutputStream.write(j, 6);
        }
        if ((268435456 & i) != 0) {
            protoOutputStream.write(j, 7);
        }
        if ((67108864 & i) != 0) {
            protoOutputStream.write(j, 8);
        }
        if ((33554432 & i) != 0) {
            protoOutputStream.write(j, 9);
        }
        if ((16777216 & i) != 0) {
            protoOutputStream.write(j, 10);
        }
        if ((i & 4194304) != 0) {
            protoOutputStream.write(j, 11);
        }
    }

    public static void dumpJobWorkItem(
            IndentingPrintWriter indentingPrintWriter, JobWorkItem jobWorkItem, int i) {
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("#");
        indentingPrintWriter.print(i);
        indentingPrintWriter.print(": #");
        indentingPrintWriter.print(jobWorkItem.getWorkId());
        indentingPrintWriter.print(" ");
        indentingPrintWriter.print(jobWorkItem.getDeliveryCount());
        indentingPrintWriter.print("x ");
        indentingPrintWriter.println(jobWorkItem.getIntent());
        if (jobWorkItem.getGrants() != null) {
            indentingPrintWriter.println("URI grants:");
            indentingPrintWriter.increaseIndent();
            ((GrantedUriPermissions) jobWorkItem.getGrants()).dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static void dumpJobWorkItem(
            ProtoOutputStream protoOutputStream, long j, JobWorkItem jobWorkItem) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, jobWorkItem.getWorkId());
        protoOutputStream.write(1120986464258L, jobWorkItem.getDeliveryCount());
        if (jobWorkItem.getIntent() != null) {
            jobWorkItem.getIntent().dumpDebug(protoOutputStream, 1146756268035L);
        }
        Object grants = jobWorkItem.getGrants();
        if (grants != null) {
            ((GrantedUriPermissions) grants).dump(protoOutputStream, 1146756268036L);
        }
        protoOutputStream.end(start);
    }

    public void addDynamicConstraints(int i) {
        if ((16777216 & i) != 0) {
            Slog.wtf("JobScheduler.JobStatus", "Tried to set quota as a dynamic constraint");
            i &= -16777217;
        }
        if (!hasConnectivityConstraint()) {
            i &= -268435457;
        }
        if (!hasContentTriggerConstraint()) {
            i &= -67108865;
        }
        int i2 = i | this.mDynamicConstraints;
        this.mDynamicConstraints = i2;
        this.mReadyDynamicSatisfied = i2 != 0 && i2 == (this.satisfiedConstraints & i2);
    }

    public final boolean canRunInDoze() {
        if (this.appHasDozeExemption
                || (this.job.getFlags() & 1) != 0
                || shouldTreatAsUserInitiatedJob()) {
            return true;
        }
        return (shouldTreatAsExpeditedJob() || this.startedAsExpeditedJob)
                && (this.mDynamicConstraints & 33554432) == 0;
    }

    public final boolean clearTrackingController(int i) {
        int i2 = this.trackingControllers;
        if ((i2 & i) == 0) {
            return false;
        }
        this.trackingControllers = (~i) & i2;
        return true;
    }

    public final String computeSystemTraceTag() {
        String str = this.mSystemTraceTag;
        if (str != null) {
            return str;
        }
        String packageName = this.job.getService().getPackageName();
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "*job*<");
        m.append(this.sourceUid);
        m.append(">");
        String str2 = this.sourcePackageName;
        m.append(str2);
        if (!str2.equals(packageName)) {
            m.append(":");
            m.append(packageName);
        }
        m.append("/");
        m.append(this.job.getService().getShortClassName());
        if (!packageName.equals(this.serviceProcessName)) {
            m.append("$");
            m.append(this.serviceProcessName);
        }
        String str3 = this.mNamespace;
        if (str3 != null && !str3.trim().isEmpty()) {
            m.append("@");
            m.append(str3);
        }
        m.append("#");
        m.append(this.job.getId());
        String sb = m.toString();
        this.mSystemTraceTag = sb;
        return sb;
    }

    @NeverCompile
    public final void dump(IndentingPrintWriter indentingPrintWriter, boolean z, long j) {
        UserHandle.formatUid(indentingPrintWriter, this.callingUid);
        indentingPrintWriter.print(" tag=");
        indentingPrintWriter.println(getWakelockTag());
        indentingPrintWriter.print("Source: uid=");
        UserHandle.formatUid(indentingPrintWriter, this.sourceUid);
        indentingPrintWriter.print(" user=");
        indentingPrintWriter.print(this.sourceUserId);
        indentingPrintWriter.print(" pkg=");
        indentingPrintWriter.println(this.sourcePackageName);
        if (z) {
            indentingPrintWriter.println("JobInfo:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print("Service: ");
            indentingPrintWriter.println(this.job.getService().flattenToShortString());
            if (this.job.isPeriodic()) {
                indentingPrintWriter.print("PERIODIC: interval=");
                TimeUtils.formatDuration(this.job.getIntervalMillis(), indentingPrintWriter);
                indentingPrintWriter.print(" flex=");
                TimeUtils.formatDuration(this.job.getFlexMillis(), indentingPrintWriter);
                indentingPrintWriter.println();
            }
            if (this.job.isPersisted()) {
                indentingPrintWriter.println("PERSISTED");
            }
            if (this.job.getBias() != 0) {
                indentingPrintWriter.print("Bias: ");
                indentingPrintWriter.println(JobInfo.getBiasString(this.job.getBias()));
            }
            indentingPrintWriter.print("Priority: ");
            indentingPrintWriter.print(JobInfo.getPriorityString(this.job.getPriority()));
            int effectivePriority = getEffectivePriority();
            if (effectivePriority != this.job.getPriority()) {
                indentingPrintWriter.print(" effective=");
                indentingPrintWriter.print(JobInfo.getPriorityString(effectivePriority));
            }
            indentingPrintWriter.println();
            if (this.job.getFlags() != 0) {
                indentingPrintWriter.print("Flags: ");
                indentingPrintWriter.println(Integer.toHexString(this.job.getFlags()));
            }
            if (this.mInternalFlags != 0) {
                indentingPrintWriter.print("Internal flags: ");
                indentingPrintWriter.print(Integer.toHexString(this.mInternalFlags));
                if ((this.mInternalFlags & 1) != 0) {
                    indentingPrintWriter.print(" HAS_FOREGROUND_EXEMPTION");
                }
                indentingPrintWriter.println();
            }
            indentingPrintWriter.print("Requires: charging=");
            indentingPrintWriter.print(this.job.isRequireCharging());
            indentingPrintWriter.print(" batteryNotLow=");
            indentingPrintWriter.print(this.job.isRequireBatteryNotLow());
            indentingPrintWriter.print(" deviceIdle=");
            indentingPrintWriter.println(this.job.isRequireDeviceIdle());
            if (this.job.getTriggerContentUris() != null) {
                indentingPrintWriter.println("Trigger content URIs:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.job.getTriggerContentUris().length; i++) {
                    JobInfo.TriggerContentUri triggerContentUri =
                            this.job.getTriggerContentUris()[i];
                    indentingPrintWriter.print(Integer.toHexString(triggerContentUri.getFlags()));
                    indentingPrintWriter.print(' ');
                    indentingPrintWriter.println(triggerContentUri.getUri());
                }
                indentingPrintWriter.decreaseIndent();
                if (this.job.getTriggerContentUpdateDelay() >= 0) {
                    indentingPrintWriter.print("Trigger update delay: ");
                    TimeUtils.formatDuration(
                            this.job.getTriggerContentUpdateDelay(), indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                if (this.job.getTriggerContentMaxDelay() >= 0) {
                    indentingPrintWriter.print("Trigger max delay: ");
                    TimeUtils.formatDuration(
                            this.job.getTriggerContentMaxDelay(), indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter
                        .print(
                                "Has media backup exemption",
                                Boolean.valueOf(this.mHasMediaBackupExemption))
                        .println();
            }
            if (this.job.getExtras() != null && !this.job.getExtras().isDefinitelyEmpty()) {
                indentingPrintWriter.print("Extras: ");
                indentingPrintWriter.println(this.job.getExtras().toShortString());
            }
            if (this.job.getTransientExtras() != null
                    && !this.job.getTransientExtras().isDefinitelyEmpty()) {
                indentingPrintWriter.print("Transient extras: ");
                indentingPrintWriter.println(this.job.getTransientExtras().toShortString());
            }
            if (this.job.getClipData() != null) {
                indentingPrintWriter.print("Clip data: ");
                StringBuilder sb = new StringBuilder(128);
                sb.append(this.job.getClipData());
                indentingPrintWriter.println(sb);
            }
            if (this.uriPerms != null) {
                indentingPrintWriter.println("Granted URI permissions:");
                this.uriPerms.dump(indentingPrintWriter);
            }
            if (this.job.getRequiredNetwork() != null) {
                indentingPrintWriter.print("Network type: ");
                indentingPrintWriter.println(this.job.getRequiredNetwork());
            }
            if (this.mTotalNetworkDownloadBytes != -1) {
                indentingPrintWriter.print("Network download bytes: ");
                indentingPrintWriter.println(this.mTotalNetworkDownloadBytes);
            }
            if (this.mTotalNetworkUploadBytes != -1) {
                indentingPrintWriter.print("Network upload bytes: ");
                indentingPrintWriter.println(this.mTotalNetworkUploadBytes);
            }
            if (this.mMinimumNetworkChunkBytes != -1) {
                indentingPrintWriter.print("Minimum network chunk bytes: ");
                indentingPrintWriter.println(this.mMinimumNetworkChunkBytes);
            }
            if (this.job.getMinLatencyMillis() != 0) {
                indentingPrintWriter.print("Minimum latency: ");
                TimeUtils.formatDuration(this.job.getMinLatencyMillis(), indentingPrintWriter);
                indentingPrintWriter.println();
            }
            if (this.job.getMaxExecutionDelayMillis() != 0) {
                indentingPrintWriter.print("Max execution delay: ");
                TimeUtils.formatDuration(
                        this.job.getMaxExecutionDelayMillis(), indentingPrintWriter);
                indentingPrintWriter.println();
            }
            indentingPrintWriter.print("Backoff: policy=");
            indentingPrintWriter.print(this.job.getBackoffPolicy());
            indentingPrintWriter.print(" initial=");
            TimeUtils.formatDuration(this.job.getInitialBackoffMillis(), indentingPrintWriter);
            indentingPrintWriter.println();
            if (this.job.hasEarlyConstraint()) {
                indentingPrintWriter.println("Has early constraint");
            }
            if (this.job.hasLateConstraint()) {
                indentingPrintWriter.println("Has late constraint");
            }
            if (this.job.getTraceTag() != null) {
                indentingPrintWriter.print("Trace tag: ");
                indentingPrintWriter.println(this.job.getTraceTag());
            }
            if (this.job.getDebugTags().size() > 0) {
                indentingPrintWriter.print("Debug tags: ");
                indentingPrintWriter.println(this.job.getDebugTags());
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.print("Required constraints:");
        int i2 = this.requiredConstraints;
        dumpConstraints(i2, indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.print("Dynamic constraints:");
        dumpConstraints(this.mDynamicConstraints, indentingPrintWriter);
        indentingPrintWriter.println();
        if (z) {
            indentingPrintWriter.print("Satisfied constraints:");
            dumpConstraints(this.satisfiedConstraints, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print("Unsatisfied constraints:");
            dumpConstraints((16777216 | i2) & (~this.satisfiedConstraints), indentingPrintWriter);
            indentingPrintWriter.println();
            if (hasFlexibilityConstraint()) {
                indentingPrintWriter.print("Num Required Flexible constraints: ");
                indentingPrintWriter.print(getNumRequiredFlexibleConstraints());
                indentingPrintWriter.println();
                indentingPrintWriter.print("Num Dropped Flexible constraints: ");
                indentingPrintWriter.print(this.mNumDroppedFlexibleConstraints);
                indentingPrintWriter.println();
            }
            indentingPrintWriter.println("Constraint history:");
            indentingPrintWriter.increaseIndent();
            for (int i3 = 0; i3 < 10; i3++) {
                int i4 = (this.mConstraintChangeHistoryIndex + i3) % 10;
                long j2 = this.mConstraintUpdatedTimesElapsed[i4];
                if (j2 != 0) {
                    TimeUtils.formatDuration(j2, j, indentingPrintWriter);
                    indentingPrintWriter.print(" =");
                    dumpConstraints(this.mConstraintStatusHistory[i4], indentingPrintWriter);
                    indentingPrintWriter.println();
                }
            }
            indentingPrintWriter.decreaseIndent();
            if (this.appHasDozeExemption) {
                indentingPrintWriter.println("Doze whitelisted: true");
            }
            if (this.uidActive) {
                indentingPrintWriter.println("Uid: active");
            }
            if (this.job.isExemptedFromAppStandby()) {
                indentingPrintWriter.println("Is exempted from app standby");
            }
        }
        if (this.trackingControllers != 0) {
            indentingPrintWriter.print("Tracking:");
            if ((this.trackingControllers & 1) != 0) {
                indentingPrintWriter.print(" BATTERY");
            }
            if ((this.trackingControllers & 2) != 0) {
                indentingPrintWriter.print(" CONNECTIVITY");
            }
            if ((this.trackingControllers & 4) != 0) {
                indentingPrintWriter.print(" CONTENT");
            }
            if ((this.trackingControllers & 8) != 0) {
                indentingPrintWriter.print(" IDLE");
            }
            if ((this.trackingControllers & 16) != 0) {
                indentingPrintWriter.print(" STORAGE");
            }
            if ((this.trackingControllers & 32) != 0) {
                indentingPrintWriter.print(" TIME");
            }
            if ((this.trackingControllers & 64) != 0) {
                indentingPrintWriter.print(" QUOTA");
            }
            if ((this.trackingControllers & 256) != 0) {
                indentingPrintWriter.print(" UID_RESTRICT");
            }
            indentingPrintWriter.println();
        }
        indentingPrintWriter.println("Implicit constraints:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("readyNotDozing: ");
        indentingPrintWriter.println(this.mReadyNotDozing);
        indentingPrintWriter.print("readyNotRestrictedInBg: ");
        indentingPrintWriter.println(this.mReadyNotRestrictedInBg);
        if (!this.job.isPeriodic() && hasConstraint(1073741824)) {
            indentingPrintWriter.print("readyDeadlineSatisfied: ");
            indentingPrintWriter.println(this.mReadyDeadlineSatisfied);
        }
        if (this.mDynamicConstraints != 0) {
            indentingPrintWriter.print("readyDynamicSatisfied: ");
            indentingPrintWriter.println(this.mReadyDynamicSatisfied);
        }
        indentingPrintWriter.print("readyComponentEnabled: ");
        indentingPrintWriter.println(this.serviceProcessName != null);
        if ((this.job.getFlags() & 16) != 0) {
            indentingPrintWriter.print("expeditedQuotaApproved: ");
            indentingPrintWriter.print(this.mExpeditedQuotaApproved);
            indentingPrintWriter.print(" (started as EJ: ");
            indentingPrintWriter.print(this.startedAsExpeditedJob);
            indentingPrintWriter.println(")");
        }
        if ((this.job.getFlags() & 32) != 0) {
            indentingPrintWriter.print("userInitiatedApproved: ");
            indentingPrintWriter.print(shouldTreatAsUserInitiatedJob());
            indentingPrintWriter.print(" (started as UIJ: ");
            indentingPrintWriter.print(this.startedAsUserInitiatedJob);
            indentingPrintWriter.println(")");
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.print("Started with foreground flag: ");
        indentingPrintWriter.println(this.startedWithForegroundFlag);
        if (this.mIsUserBgRestricted) {
            indentingPrintWriter.println("User BG restricted");
        }
        if (this.changedAuthorities != null) {
            indentingPrintWriter.println("Changed authorities:");
            indentingPrintWriter.increaseIndent();
            for (int i5 = 0; i5 < this.changedAuthorities.size(); i5++) {
                indentingPrintWriter.println((String) this.changedAuthorities.valueAt(i5));
            }
            indentingPrintWriter.decreaseIndent();
        }
        if (this.changedUris != null) {
            indentingPrintWriter.println("Changed URIs:");
            indentingPrintWriter.increaseIndent();
            for (int i6 = 0; i6 < this.changedUris.size(); i6++) {
                indentingPrintWriter.println(this.changedUris.valueAt(i6));
            }
            indentingPrintWriter.decreaseIndent();
        }
        if (this.network != null) {
            indentingPrintWriter.print("Network: ");
            indentingPrintWriter.println(this.network);
        }
        ArrayList arrayList = this.pendingWork;
        if (arrayList != null && arrayList.size() > 0) {
            indentingPrintWriter.println("Pending work:");
            for (int i7 = 0; i7 < this.pendingWork.size(); i7++) {
                dumpJobWorkItem(indentingPrintWriter, (JobWorkItem) this.pendingWork.get(i7), i7);
            }
        }
        ArrayList arrayList2 = this.executingWork;
        if (arrayList2 != null && arrayList2.size() > 0) {
            indentingPrintWriter.println("Executing work:");
            for (int i8 = 0; i8 < this.executingWork.size(); i8++) {
                dumpJobWorkItem(indentingPrintWriter, (JobWorkItem) this.executingWork.get(i8), i8);
            }
        }
        indentingPrintWriter.print("Standby bucket: ");
        indentingPrintWriter.println(bucketName(this.standbyBucket));
        indentingPrintWriter.increaseIndent();
        if (this.whenStandbyDeferred != 0) {
            indentingPrintWriter.print("Deferred since: ");
            TimeUtils.formatDuration(this.whenStandbyDeferred, j, indentingPrintWriter);
            indentingPrintWriter.println();
        }
        if (this.mFirstForceBatchedTimeElapsed != 0) {
            indentingPrintWriter.print("Time since first force batch attempt: ");
            TimeUtils.formatDuration(this.mFirstForceBatchedTimeElapsed, j, indentingPrintWriter);
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.print("Enqueue time: ");
        TimeUtils.formatDuration(this.enqueueTime, j, indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.print("Run time: earliest=");
        long j3 = this.earliestRunTimeElapsedMillis;
        if (j3 == 0) {
            indentingPrintWriter.print("none");
        } else {
            TimeUtils.formatDuration(j3 - j, indentingPrintWriter);
        }
        indentingPrintWriter.print(", latest=");
        long j4 = this.latestRunTimeElapsedMillis;
        if (j4 == Long.MAX_VALUE) {
            indentingPrintWriter.print("none");
        } else {
            TimeUtils.formatDuration(j4 - j, indentingPrintWriter);
        }
        indentingPrintWriter.print(", original latest=");
        long j5 = this.mOriginalLatestRunTimeElapsedMillis;
        if (j5 == Long.MAX_VALUE) {
            indentingPrintWriter.print("none");
        } else {
            TimeUtils.formatDuration(j5 - j, indentingPrintWriter);
        }
        indentingPrintWriter.println();
        if (this.mCumulativeExecutionTimeMs != 0) {
            indentingPrintWriter.print("Cumulative execution time=");
            TimeUtils.formatDuration(this.mCumulativeExecutionTimeMs, indentingPrintWriter);
            indentingPrintWriter.println();
        }
        int i9 = this.numFailures;
        if (i9 != 0) {
            indentingPrintWriter.print("Num failures: ");
            indentingPrintWriter.println(i9);
        }
        int i10 = this.mNumSystemStops;
        if (i10 != 0) {
            indentingPrintWriter.print("Num system stops: ");
            indentingPrintWriter.println(i10);
        }
        long j6 = this.mLastSuccessfulRunTime;
        if (j6 != 0) {
            indentingPrintWriter.print("Last successful run: ");
            indentingPrintWriter.println(DateFormat.format("yyyy-MM-dd HH:mm:ss", j6));
        }
        long j7 = this.mLastFailedRunTime;
        if (j7 != 0) {
            indentingPrintWriter.print("Last failed run: ");
            indentingPrintWriter.println(DateFormat.format("yyyy-MM-dd HH:mm:ss", j7));
        }
    }

    public final void dump(ProtoOutputStream protoOutputStream, boolean z, long j) {
        int i;
        long start = protoOutputStream.start(1146756268034L);
        long j2 = 1120986464257L;
        protoOutputStream.write(1120986464257L, this.callingUid);
        protoOutputStream.write(1138166333442L, getWakelockTag());
        protoOutputStream.write(1120986464259L, this.sourceUid);
        protoOutputStream.write(1120986464260L, this.sourceUserId);
        protoOutputStream.write(1138166333445L, this.sourcePackageName);
        if (z) {
            long start2 = protoOutputStream.start(1146756268038L);
            this.job.getService().dumpDebug(protoOutputStream, 1146756268033L);
            protoOutputStream.write(1133871366146L, this.job.isPeriodic());
            protoOutputStream.write(1112396529667L, this.job.getIntervalMillis());
            protoOutputStream.write(1112396529668L, this.job.getFlexMillis());
            protoOutputStream.write(1133871366149L, this.job.isPersisted());
            protoOutputStream.write(1172526071814L, this.job.getBias());
            protoOutputStream.write(1120986464263L, this.job.getFlags());
            protoOutputStream.write(1112396529688L, this.mInternalFlags);
            protoOutputStream.write(1133871366152L, this.job.isRequireCharging());
            protoOutputStream.write(1133871366153L, this.job.isRequireBatteryNotLow());
            protoOutputStream.write(1133871366154L, this.job.isRequireDeviceIdle());
            if (this.job.getTriggerContentUris() != null) {
                int i2 = 0;
                while (i2 < this.job.getTriggerContentUris().length) {
                    long start3 = protoOutputStream.start(2246267895819L);
                    JobInfo.TriggerContentUri triggerContentUri =
                            this.job.getTriggerContentUris()[i2];
                    protoOutputStream.write(j2, triggerContentUri.getFlags());
                    Uri uri = triggerContentUri.getUri();
                    if (uri != null) {
                        protoOutputStream.write(1138166333442L, uri.toString());
                    }
                    protoOutputStream.end(start3);
                    i2++;
                    j2 = 1120986464257L;
                }
                if (this.job.getTriggerContentUpdateDelay() >= 0) {
                    protoOutputStream.write(
                            1112396529676L, this.job.getTriggerContentUpdateDelay());
                }
                if (this.job.getTriggerContentMaxDelay() >= 0) {
                    protoOutputStream.write(1112396529677L, this.job.getTriggerContentMaxDelay());
                }
            }
            if (this.job.getExtras() != null && !this.job.getExtras().isDefinitelyEmpty()) {
                this.job.getExtras().dumpDebug(protoOutputStream, 1146756268046L);
            }
            if (this.job.getTransientExtras() != null
                    && !this.job.getTransientExtras().isDefinitelyEmpty()) {
                this.job.getTransientExtras().dumpDebug(protoOutputStream, 1146756268047L);
            }
            if (this.job.getClipData() != null) {
                this.job.getClipData().dumpDebug(protoOutputStream, 1146756268048L);
            }
            GrantedUriPermissions grantedUriPermissions = this.uriPerms;
            if (grantedUriPermissions != null) {
                grantedUriPermissions.dump(protoOutputStream, 1146756268049L);
            }
            long j3 = this.mTotalNetworkDownloadBytes;
            if (j3 != -1) {
                protoOutputStream.write(1112396529689L, j3);
            }
            long j4 = this.mTotalNetworkUploadBytes;
            if (j4 != -1) {
                protoOutputStream.write(1112396529690L, j4);
            }
            protoOutputStream.write(1112396529684L, this.job.getMinLatencyMillis());
            protoOutputStream.write(1112396529685L, this.job.getMaxExecutionDelayMillis());
            long start4 = protoOutputStream.start(1146756268054L);
            protoOutputStream.write(1159641169921L, this.job.getBackoffPolicy());
            protoOutputStream.write(1112396529666L, this.job.getInitialBackoffMillis());
            protoOutputStream.end(start4);
            protoOutputStream.write(1133871366167L, this.job.hasEarlyConstraint());
            protoOutputStream.write(1133871366168L, this.job.hasLateConstraint());
            protoOutputStream.end(start2);
        }
        int i3 = this.requiredConstraints;
        dumpConstraints(protoOutputStream, 2259152797703L, i3);
        dumpConstraints(protoOutputStream, 2259152797727L, this.mDynamicConstraints);
        if (z) {
            dumpConstraints(protoOutputStream, 2259152797704L, this.satisfiedConstraints);
            dumpConstraints(
                    protoOutputStream,
                    2259152797705L,
                    (16777216 | i3) & (~this.satisfiedConstraints));
            protoOutputStream.write(1133871366154L, this.appHasDozeExemption);
            protoOutputStream.write(1133871366170L, this.uidActive);
            protoOutputStream.write(1133871366171L, this.job.isExemptedFromAppStandby());
        }
        if ((this.trackingControllers & 1) != 0) {
            protoOutputStream.write(2259152797707L, 0);
        }
        if ((this.trackingControllers & 2) != 0) {
            protoOutputStream.write(2259152797707L, 1);
        }
        if ((this.trackingControllers & 4) != 0) {
            protoOutputStream.write(2259152797707L, 2);
        }
        if ((this.trackingControllers & 8) != 0) {
            protoOutputStream.write(2259152797707L, 3);
        }
        if ((this.trackingControllers & 16) != 0) {
            protoOutputStream.write(2259152797707L, 4);
        }
        if ((this.trackingControllers & 32) != 0) {
            protoOutputStream.write(2259152797707L, 5);
        }
        if ((this.trackingControllers & 64) != 0) {
            protoOutputStream.write(2259152797707L, 6);
        }
        if ((this.trackingControllers & 256) != 0) {
            protoOutputStream.write(2259152797707L, 7);
        }
        long start5 = protoOutputStream.start(1146756268057L);
        protoOutputStream.write(1133871366145L, this.mReadyNotDozing);
        protoOutputStream.write(1133871366146L, this.mReadyNotRestrictedInBg);
        protoOutputStream.write(1133871366147L, this.mReadyDynamicSatisfied);
        protoOutputStream.end(start5);
        if (this.changedAuthorities != null) {
            for (int i4 = 0; i4 < this.changedAuthorities.size(); i4++) {
                protoOutputStream.write(
                        2237677961228L, (String) this.changedAuthorities.valueAt(i4));
            }
        }
        if (this.changedUris != null) {
            for (int i5 = 0; i5 < this.changedUris.size(); i5++) {
                protoOutputStream.write(
                        2237677961229L, ((Uri) this.changedUris.valueAt(i5)).toString());
            }
        }
        if (this.pendingWork != null) {
            for (int i6 = 0; i6 < this.pendingWork.size(); i6++) {
                dumpJobWorkItem(
                        protoOutputStream, 2246267895823L, (JobWorkItem) this.pendingWork.get(i6));
            }
        }
        if (this.executingWork != null) {
            for (int i7 = 0; i7 < this.executingWork.size(); i7++) {
                dumpJobWorkItem(
                        protoOutputStream,
                        2246267895824L,
                        (JobWorkItem) this.executingWork.get(i7));
            }
        }
        protoOutputStream.write(1159641169937L, this.standbyBucket);
        protoOutputStream.write(1112396529682L, j - this.enqueueTime);
        long j5 = this.whenStandbyDeferred;
        protoOutputStream.write(1112396529692L, j5 == 0 ? 0L : j - j5);
        long j6 = this.mFirstForceBatchedTimeElapsed;
        protoOutputStream.write(1112396529693L, j6 == 0 ? 0L : j - j6);
        long j7 = this.earliestRunTimeElapsedMillis;
        if (j7 == 0) {
            i = 0;
            protoOutputStream.write(1176821039123L, 0);
        } else {
            i = 0;
            protoOutputStream.write(1176821039123L, j7 - j);
        }
        long j8 = this.latestRunTimeElapsedMillis;
        if (j8 == Long.MAX_VALUE) {
            protoOutputStream.write(1176821039124L, i);
        } else {
            protoOutputStream.write(1176821039124L, j8 - j);
        }
        protoOutputStream.write(1116691496990L, this.mOriginalLatestRunTimeElapsedMillis);
        protoOutputStream.write(1120986464277L, this.numFailures + this.mNumSystemStops);
        protoOutputStream.write(1112396529686L, this.mLastSuccessfulRunTime);
        protoOutputStream.write(1112396529687L, this.mLastFailedRunTime);
        protoOutputStream.end(start);
    }

    public final void enqueueWorkLocked(JobWorkItem jobWorkItem) {
        GrantedUriPermissions grantedUriPermissions;
        if (this.pendingWork == null) {
            this.pendingWork = new ArrayList();
        }
        jobWorkItem.setWorkId(this.nextPendingWorkId);
        this.nextPendingWorkId++;
        if (jobWorkItem.getIntent() != null && (jobWorkItem.getIntent().getFlags() & 3) != 0) {
            Intent intent = jobWorkItem.getIntent();
            String shortString = toShortString();
            int flags = intent.getFlags();
            if ((flags & 3) != 0) {
                Uri data = intent.getData();
                int i = this.sourceUid;
                String str = this.sourcePackageName;
                int i2 = this.sourceUserId;
                grantedUriPermissions =
                        data != null
                                ? GrantedUriPermissions.grantUri(
                                        data, i, str, i2, flags, shortString, null)
                                : null;
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    grantedUriPermissions =
                            GrantedUriPermissions.grantClip(
                                    clipData,
                                    i,
                                    str,
                                    i2,
                                    flags,
                                    shortString,
                                    grantedUriPermissions);
                }
            } else {
                grantedUriPermissions = null;
            }
            jobWorkItem.setGrants(grantedUriPermissions);
        }
        this.pendingWork.add(jobWorkItem);
        updateNetworkBytesLocked();
    }

    public final int getEffectivePriority() {
        int min =
                Math.min(
                        ((this.mInternalFlags & 2) != 0
                                        || (this.job.isUserInitiated()
                                                && (this.mInternalFlags & 4) != 0))
                                ? 400
                                : 500,
                        this.job.getPriority());
        int i = this.numFailures;
        if (i < 2 || shouldTreatAsUserInitiatedJob()) {
            return min;
        }
        if (isRequestedExpeditedJob()) {
            return 400;
        }
        int i2 = i / 2;
        if (i2 == 1) {
            return Math.min(300, min);
        }
        if (i2 != 2) {
            return 100;
        }
        return Math.min(200, min);
    }

    public final int getEffectiveStandbyBucket() {
        if (this.mJobSchedulerInternal == null) {
            this.mJobSchedulerInternal =
                    (JobSchedulerInternal) LocalServices.getService(JobSchedulerInternal.class);
        }
        JobSchedulerInternal jobSchedulerInternal = this.mJobSchedulerInternal;
        int i = this.callingUid;
        boolean isAppConsideredBuggy =
                jobSchedulerInternal.isAppConsideredBuggy(
                        UserHandle.getUserId(i),
                        this.job.getService().getPackageName(),
                        getTimeoutBlameUserId(),
                        getTimeoutBlamePackageName());
        int i2 = this.standbyBucket;
        if (i2 == 6) {
            if (isAppConsideredBuggy) {
                String packageName = this.job.getService().getPackageName();
                String str = this.sourcePackageName;
                if (!packageName.equals(str)) {
                    str = this.job.getService().getPackageName() + "/" + str;
                }
                PinnerService$$ExternalSyntheticOutline0.m(
                        "Exempted app ", str, " considered buggy", "JobScheduler.JobStatus");
            }
            return i2;
        }
        if (this.uidActive || this.job.isExemptedFromAppStandby()) {
            return 0;
        }
        if (i2 != 5 && i2 != 4 && this.mHasMediaBackupExemption) {
            i2 = Math.min(1, i2);
        }
        if (!isAppConsideredBuggy || i2 >= 1) {
            return i2;
        }
        if (!this.mIsDowngradedDueToBuggyApp) {
            if (UserHandle.isCore(i)) {
                i = this.sourceUid;
            }
            Counter.logIncrementWithUid(
                    "job_scheduler.value_job_quota_reduced_due_to_buggy_uid", i);
            this.mIsDowngradedDueToBuggyApp = true;
        }
        return 1;
    }

    public final String[] getFilteredDebugTags() {
        String[] strArr = this.mFilteredDebugTags;
        if (strArr != null) {
            return strArr;
        }
        ArraySet debugTagsArraySet = this.job.getDebugTagsArraySet();
        this.mFilteredDebugTags = new String[debugTagsArraySet.size()];
        int i = 0;
        while (true) {
            String[] strArr2 = this.mFilteredDebugTags;
            if (i >= strArr2.length) {
                return strArr2;
            }
            strArr2[i] = applyBasicPiiFilters((String) debugTagsArraySet.valueAt(i));
            i++;
        }
    }

    public final String getFilteredTraceTag() {
        String str = this.mFilteredTraceTag;
        if (str != null) {
            return str;
        }
        String traceTag = this.job.getTraceTag();
        if (traceTag == null) {
            return null;
        }
        String applyBasicPiiFilters = applyBasicPiiFilters(traceTag);
        this.mFilteredTraceTag = applyBasicPiiFilters;
        return applyBasicPiiFilters;
    }

    public final float getFractionRunTime() {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = this.earliestRunTimeElapsedMillis;
        long j2 = this.latestRunTimeElapsedMillis;
        if (j == 0 && j2 == Long.MAX_VALUE) {
            return 1.0f;
        }
        if (j == 0) {
            if (elapsedRealtime >= j2) {
                return 1.0f;
            }
            return FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
        if (j2 == Long.MAX_VALUE) {
            if (elapsedRealtime >= j) {
                return 1.0f;
            }
            return FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
        if (elapsedRealtime <= j) {
            return FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
        if (elapsedRealtime >= j2) {
            return 1.0f;
        }
        return (elapsedRealtime - j) / (j2 - j);
    }

    public final int getNumPreviousAttempts() {
        return this.numFailures + this.mNumSystemStops;
    }

    public final int getNumRequiredFlexibleConstraints() {
        return this.mNumAppliedFlexibleConstraints - this.mNumDroppedFlexibleConstraints;
    }

    public final String getTimeoutBlamePackageName() {
        return UserHandle.isCore(this.callingUid)
                ? this.sourcePackageName
                : this.job.getService().getPackageName();
    }

    public final int getTimeoutBlameUserId() {
        int i = this.callingUid;
        return UserHandle.isCore(i) ? this.sourceUserId : UserHandle.getUserId(i);
    }

    public final long getTriggerContentMaxDelay() {
        long triggerContentMaxDelay = this.job.getTriggerContentMaxDelay();
        if (triggerContentMaxDelay < 0) {
            return 120000L;
        }
        return Math.max(triggerContentMaxDelay, 1000L);
    }

    public final long getTriggerContentUpdateDelay() {
        long triggerContentUpdateDelay = this.job.getTriggerContentUpdateDelay();
        if (triggerContentUpdateDelay < 0) {
            return 10000L;
        }
        return Math.max(triggerContentUpdateDelay, 500L);
    }

    public final UserVisibleJobSummary getUserVisibleJobSummary() {
        if (this.mUserVisibleJobSummary == null) {
            this.mUserVisibleJobSummary =
                    new UserVisibleJobSummary(
                            this.callingUid,
                            this.job.getService().getPackageName(),
                            this.sourceUserId,
                            this.sourcePackageName,
                            this.mNamespace,
                            this.job.getId());
        }
        return this.mUserVisibleJobSummary;
    }

    public final String getWakelockTag() {
        if (this.mWakelockTag == null) {
            this.mWakelockTag = "*job*/" + this.batteryName;
        }
        return this.mWakelockTag;
    }

    public final int getWorkCount() {
        ArrayList arrayList = this.pendingWork;
        int size = arrayList == null ? 0 : arrayList.size();
        ArrayList arrayList2 = this.executingWork;
        return size + (arrayList2 != null ? arrayList2.size() : 0);
    }

    public final boolean hasConnectivityConstraint() {
        return (this.requiredConstraints & 268435456) != 0;
    }

    public final boolean hasConstraint(int i) {
        return ((this.requiredConstraints & i) == 0 && (this.mDynamicConstraints & i) == 0)
                ? false
                : true;
    }

    public final boolean hasContentTriggerConstraint() {
        return (this.requiredConstraints & 67108864) != 0;
    }

    public final boolean hasFlexibilityConstraint() {
        return (this.requiredConstraints & 2097152) != 0;
    }

    public final boolean isConstraintSatisfied(int i) {
        return (this.satisfiedConstraints & i) != 0;
    }

    public final boolean isConstraintsSatisfied(int i) {
        int i2 = this.overrideState;
        if (i2 == 3) {
            return true;
        }
        if (i2 == 2) {
            i |= this.requiredConstraints & (-2136997873);
        }
        int i3 = this.mRequiredConstraintsOfInterest;
        return (i & i3) == i3;
    }

    public final boolean isReady(int i) {
        if ((this.mReadyWithinQuota || this.mReadyDynamicSatisfied || shouldTreatAsExpeditedJob())
                && getEffectiveStandbyBucket() != 4
                && this.mReadyNotDozing
                && this.mReadyNotRestrictedInBg
                && this.serviceProcessName != null) {
            return this.mReadyDeadlineSatisfied || isConstraintsSatisfied(i);
        }
        return false;
    }

    public final boolean isRequestedExpeditedJob() {
        return (this.job.getFlags() & 16) != 0;
    }

    public final boolean isUserVisibleJob() {
        return shouldTreatAsUserInitiatedJob() || this.startedAsUserInitiatedJob;
    }

    public final boolean matches(int i, int i2, String str) {
        return this.job.getId() == i2
                && this.callingUid == i
                && Objects.equals(this.mNamespace, str);
    }

    public final void maybeAddForegroundExemption(
            JobSchedulerService$$ExternalSyntheticLambda1
                    jobSchedulerService$$ExternalSyntheticLambda1) {
        if (this.job.hasEarlyConstraint()
                || this.job.hasLateConstraint()
                || (this.mInternalFlags & 1) != 0
                || !jobSchedulerService$$ExternalSyntheticLambda1.test(
                        Integer.valueOf(this.sourceUid))) {
            return;
        }
        this.mInternalFlags |= 1;
    }

    public final void prepareLocked() {
        if (this.prepared) {
            Slog.wtf("JobScheduler.JobStatus", "Already prepared: " + this);
            return;
        }
        this.prepared = true;
        GrantedUriPermissions grantedUriPermissions = null;
        this.unpreparedPoint = null;
        ClipData clipData = this.job.getClipData();
        if (clipData != null) {
            int clipGrantFlags = this.job.getClipGrantFlags();
            String shortString = toShortString();
            if ((clipGrantFlags & 3) != 0) {
                grantedUriPermissions =
                        GrantedUriPermissions.grantClip(
                                clipData,
                                this.sourceUid,
                                this.sourcePackageName,
                                this.sourceUserId,
                                clipGrantFlags,
                                shortString,
                                null);
            }
            this.uriPerms = grantedUriPermissions;
        }
    }

    public final void printUniqueId(PrintWriter printWriter) {
        String str = this.mNamespace;
        if (str != null) {
            printWriter.print(str);
            printWriter.print(":");
        } else {
            printWriter.print("#");
        }
        UserHandle.formatUid(printWriter, this.callingUid);
        printWriter.print("/");
        printWriter.print(this.job.getId());
    }

    public boolean readinessStatusWithConstraint(int i, boolean z) {
        boolean z2;
        int i2 = this.mSatisfiedConstraintsOfInterest;
        if (i == 4194304) {
            z2 = this.mReadyNotRestrictedInBg;
            this.mReadyNotRestrictedInBg = z;
        } else if (i == 16777216) {
            z2 = this.mReadyWithinQuota;
            this.mReadyWithinQuota = z;
        } else if (i == 33554432) {
            z2 = this.mReadyNotDozing;
            this.mReadyNotDozing = z;
        } else if (i != 1073741824) {
            i2 = z ? i2 | i : (~i) & i2;
            int i3 = this.mDynamicConstraints;
            this.mReadyDynamicSatisfied = i3 != 0 && i3 == (i2 & i3);
            z2 = false;
        } else {
            z2 = this.mReadyDeadlineSatisfied;
            this.mReadyDeadlineSatisfied = z;
        }
        if (i != 2097152) {
            i2 |= 2097152;
        }
        boolean isReady = isReady(i2);
        if (i == 4194304) {
            this.mReadyNotRestrictedInBg = z2;
        } else if (i == 16777216) {
            this.mReadyWithinQuota = z2;
        } else if (i == 33554432) {
            this.mReadyNotDozing = z2;
        } else if (i != 1073741824) {
            int i4 = this.mDynamicConstraints;
            this.mReadyDynamicSatisfied = i4 != 0 && i4 == (this.satisfiedConstraints & i4);
        } else {
            this.mReadyDeadlineSatisfied = z2;
        }
        return isReady;
    }

    public final boolean setConstraintSatisfied(int i, long j, boolean z) {
        int i2 = 0;
        if (((this.satisfiedConstraints & i) != 0) == z) {
            return false;
        }
        if (DEBUG) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Constraint ", " is ");
            m.append(!z ? "NOT " : "");
            m.append("satisfied for ");
            m.append(toShortString());
            Slog.v("JobScheduler.JobStatus", m.toString());
        }
        boolean z2 = !z && isReady(this.mSatisfiedConstraintsOfInterest);
        int i3 = (this.satisfiedConstraints & (~i)) | (z ? i : 0);
        this.satisfiedConstraints = i3;
        this.mSatisfiedConstraintsOfInterest = (-1800404977) & i3;
        int i4 = this.mDynamicConstraints;
        this.mReadyDynamicSatisfied = i4 != 0 && i4 == (i3 & i4);
        int i5 = this.mConstraintChangeHistoryIndex;
        this.mConstraintUpdatedTimesElapsed[i5] = j;
        this.mConstraintStatusHistory[i5] = i3;
        this.mConstraintChangeHistoryIndex = (i5 + 1) % 10;
        boolean readinessStatusWithConstraint = readinessStatusWithConstraint(i, z);
        if (z2 && !readinessStatusWithConstraint) {
            int i6 = this.requiredConstraints;
            if (i == 1) {
                if ((i & i6) != 0) {
                    i2 = 6;
                    this.mReasonReadyToUnready = i2;
                }
                i2 = 12;
                this.mReasonReadyToUnready = i2;
            } else if (i != 2) {
                if (i == 4) {
                    if ((i & i6) != 0) {
                        i2 = 8;
                    }
                    i2 = 12;
                } else if (i == 8) {
                    i2 = 9;
                } else if (i != 2097152) {
                    if (i == 4194304) {
                        if (this.mIsUserBgRestricted) {
                            i2 = 11;
                        }
                        i2 = 4;
                    } else if (i == 8388608) {
                        i2 = 15;
                    } else if (i != 16777216) {
                        if (i != 33554432) {
                            if (i != 268435456) {
                                Slog.wtf(
                                        "JobScheduler.JobStatus",
                                        "Unsupported constraint (" + i + ") --stop reason mapping");
                            } else {
                                i2 = 7;
                            }
                        }
                        i2 = 4;
                    } else {
                        i2 = 10;
                    }
                }
                this.mReasonReadyToUnready = i2;
            } else {
                if ((i & i6) != 0) {
                    i2 = 5;
                    this.mReasonReadyToUnready = i2;
                }
                i2 = 12;
                this.mReasonReadyToUnready = i2;
            }
        } else if (!z2 && readinessStatusWithConstraint) {
            this.mReasonReadyToUnready = 0;
        }
        return true;
    }

    public final void setTrackingController(int i) {
        this.trackingControllers = i | this.trackingControllers;
    }

    public final boolean shouldTreatAsExpeditedJob() {
        return this.mExpeditedQuotaApproved && isRequestedExpeditedJob();
    }

    public final boolean shouldTreatAsUserInitiatedJob() {
        if (this.job.isUserInitiated()) {
            int i = this.mInternalFlags;
            if ((i & 2) == 0 && (i & 4) == 0) {
                return true;
            }
        }
        return false;
    }

    public final String toShortString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        String str = this.mNamespace;
        if (str != null) {
            sb.append(" {");
            sb.append(str);
            sb.append("}");
        }
        sb.append(" #");
        UserHandle.formatUid(sb, this.callingUid);
        sb.append("/");
        sb.append(this.job.getId());
        sb.append(' ');
        sb.append(this.batteryName);
        return sb.toString();
    }

    public final String toShortStringExceptUniqueId() {
        return Integer.toHexString(System.identityHashCode(this)) + ' ' + this.batteryName;
    }

    public final String toString() {
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "JobStatus{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        String str = this.mNamespace;
        if (str != null) {
            RCPManagerService$$ExternalSyntheticOutline0.m$1(m, " ", str, ":");
        } else {
            m.append(" #");
        }
        int i = this.callingUid;
        UserHandle.formatUid(m, i);
        m.append("/");
        m.append(this.job.getId());
        m.append(' ');
        m.append(this.batteryName);
        m.append(" u=");
        m.append(UserHandle.getUserId(i));
        m.append(" s=");
        m.append(this.sourceUid);
        long j = this.earliestRunTimeElapsedMillis;
        long j2 = this.latestRunTimeElapsedMillis;
        if (j != 0 || j2 != Long.MAX_VALUE) {
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            m.append(" TIME=");
            if (j == 0) {
                m.append("none");
            } else {
                TimeUtils.formatDuration(j - elapsedRealtime, m);
            }
            m.append(":");
            if (j2 == Long.MAX_VALUE) {
                m.append("none");
            } else {
                TimeUtils.formatDuration(j2 - elapsedRealtime, m);
            }
        }
        if (this.job.getRequiredNetwork() != null) {
            m.append(" NET");
        }
        if (this.job.isRequireCharging()) {
            m.append(" CHARGING");
        }
        if (this.job.isRequireBatteryNotLow()) {
            m.append(" BATNOTLOW");
        }
        if (this.job.isRequireStorageNotLow()) {
            m.append(" STORENOTLOW");
        }
        if (this.job.isRequireDeviceIdle()) {
            m.append(" IDLE");
        }
        if (this.job.isPeriodic()) {
            m.append(" PERIODIC");
        }
        if (this.job.isPersisted()) {
            m.append(" PERSISTED");
        }
        if ((this.satisfiedConstraints & 33554432) == 0) {
            m.append(" WAIT:DEV_NOT_DOZING");
        }
        if (this.job.getTriggerContentUris() != null) {
            m.append(" URIS=");
            m.append(Arrays.toString(this.job.getTriggerContentUris()));
        }
        int i2 = this.numFailures;
        if (i2 != 0) {
            m.append(" failures=");
            m.append(i2);
        }
        int i3 = this.mNumSystemStops;
        if (i3 != 0) {
            m.append(" system stops=");
            m.append(i3);
        }
        if (isReady(this.mSatisfiedConstraintsOfInterest)) {
            m.append(" READY");
        } else {
            m.append(" satisfied:0x");
            m.append(Integer.toHexString(this.satisfiedConstraints));
            int i4 = this.mRequiredConstraintsOfInterest | 56623104;
            m.append(" unsatisfied:0x");
            m.append(Integer.toHexString((this.satisfiedConstraints & i4) ^ i4));
        }
        m.append("}");
        return m.toString();
    }

    public final void unprepareLocked() {
        if (this.prepared) {
            this.prepared = false;
            this.unpreparedPoint = new Throwable().fillInStackTrace();
            GrantedUriPermissions grantedUriPermissions = this.uriPerms;
            if (grantedUriPermissions != null) {
                grantedUriPermissions.revoke();
                this.uriPerms = null;
                return;
            }
            return;
        }
        Slog.wtf("JobScheduler.JobStatus", "Hasn't been prepared: " + this);
        Throwable th = this.unpreparedPoint;
        if (th != null) {
            Slog.e("JobScheduler.JobStatus", "Was already unprepared at ", th);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0043 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateMediaBackupExemptionStatus() {
        /*
            r4 = this;
            com.android.server.job.JobSchedulerInternal r0 = r4.mJobSchedulerInternal
            if (r0 != 0) goto Le
            java.lang.Class<com.android.server.job.JobSchedulerInternal> r0 = com.android.server.job.JobSchedulerInternal.class
            java.lang.Object r0 = com.android.server.LocalServices.getService(r0)
            com.android.server.job.JobSchedulerInternal r0 = (com.android.server.job.JobSchedulerInternal) r0
            r4.mJobSchedulerInternal = r0
        Le:
            boolean r0 = r4.mHasExemptedMediaUrisOnly
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L3e
            android.app.job.JobInfo r0 = r4.job
            boolean r0 = r0.hasLateConstraint()
            if (r0 != 0) goto L3e
            android.app.job.JobInfo r0 = r4.job
            android.net.NetworkRequest r0 = r0.getRequiredNetwork()
            if (r0 == 0) goto L3e
            int r0 = r4.getEffectivePriority()
            r3 = 300(0x12c, float:4.2E-43)
            if (r0 < r3) goto L3e
            com.android.server.job.JobSchedulerInternal r0 = r4.mJobSchedulerInternal
            int r3 = r4.sourceUserId
            java.lang.String r0 = r0.getCloudMediaProviderPackage(r3)
            java.lang.String r3 = r4.sourcePackageName
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L3e
            r0 = r2
            goto L3f
        L3e:
            r0 = r1
        L3f:
            boolean r3 = r4.mHasMediaBackupExemption
            if (r3 != r0) goto L44
            return r1
        L44:
            r4.mHasMediaBackupExemption = r0
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.job.controllers.JobStatus.updateMediaBackupExemptionStatus():boolean");
    }

    public final void updateNetworkBytesLocked() {
        long estimatedNetworkDownloadBytes = this.job.getEstimatedNetworkDownloadBytes();
        this.mTotalNetworkDownloadBytes = estimatedNetworkDownloadBytes;
        if (estimatedNetworkDownloadBytes < 0) {
            this.mTotalNetworkDownloadBytes = -1L;
        }
        long estimatedNetworkUploadBytes = this.job.getEstimatedNetworkUploadBytes();
        this.mTotalNetworkUploadBytes = estimatedNetworkUploadBytes;
        if (estimatedNetworkUploadBytes < 0) {
            this.mTotalNetworkUploadBytes = -1L;
        }
        this.mMinimumNetworkChunkBytes = this.job.getMinimumNetworkChunkBytes();
        if (this.pendingWork != null) {
            for (int i = 0; i < this.pendingWork.size(); i++) {
                long estimatedNetworkDownloadBytes2 =
                        ((JobWorkItem) this.pendingWork.get(i)).getEstimatedNetworkDownloadBytes();
                if (estimatedNetworkDownloadBytes2 != -1 && estimatedNetworkDownloadBytes2 > 0) {
                    long j = this.mTotalNetworkDownloadBytes;
                    if (j != -1) {
                        this.mTotalNetworkDownloadBytes = j + estimatedNetworkDownloadBytes2;
                    } else {
                        this.mTotalNetworkDownloadBytes = estimatedNetworkDownloadBytes2;
                    }
                }
                long estimatedNetworkUploadBytes2 =
                        ((JobWorkItem) this.pendingWork.get(i)).getEstimatedNetworkUploadBytes();
                if (estimatedNetworkUploadBytes2 != -1 && estimatedNetworkUploadBytes2 > 0) {
                    long j2 = this.mTotalNetworkUploadBytes;
                    if (j2 != -1) {
                        this.mTotalNetworkUploadBytes = j2 + estimatedNetworkUploadBytes2;
                    } else {
                        this.mTotalNetworkUploadBytes = estimatedNetworkUploadBytes2;
                    }
                }
                long minimumNetworkChunkBytes =
                        ((JobWorkItem) this.pendingWork.get(i)).getMinimumNetworkChunkBytes();
                long j3 = this.mMinimumNetworkChunkBytes;
                if (j3 == -1) {
                    this.mMinimumNetworkChunkBytes = minimumNetworkChunkBytes;
                } else if (minimumNetworkChunkBytes != -1) {
                    this.mMinimumNetworkChunkBytes = Math.min(j3, minimumNetworkChunkBytes);
                }
            }
        }
    }

    public final void writeToShortProto(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.callingUid);
        protoOutputStream.write(1120986464258L, this.job.getId());
        protoOutputStream.write(1138166333443L, this.batteryName);
        protoOutputStream.end(start);
    }
}
