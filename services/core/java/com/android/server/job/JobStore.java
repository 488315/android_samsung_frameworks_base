package com.android.server.job;

import android.app.job.JobInfo;
import android.app.job.JobWorkItem;
import android.content.Context;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SystemConfigFileCommitEventLogger;

import com.android.internal.util.jobs.ArrayUtils;
import com.android.internal.util.jobs.BitUtils;
import com.android.modules.expresslog.Histogram;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.IoThread;
import com.android.server.job.controllers.JobStatus;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class JobStore {
    static final int INVALID_UID = -2;
    static final String JOB_FILE_SPLIT_PREFIX = "jobs_";
    public static JobStore sSingleton;
    public final SystemConfigFileCommitEventLogger mEventLogger;
    public final File mJobFileDirectory;
    public final JobSet mJobSet;
    public final AtomicFile mJobsFile;
    public final Object mLock;
    public final boolean mRtcGood;
    public final AnonymousClass1 mScheduledJobHighWaterMarkLoggingRunnable;
    public boolean mSplitFileMigrationNeeded;
    public boolean mWriteInProgress;
    public final AnonymousClass2 mWriteRunnable;
    public final Object mWriteScheduleLock;
    public boolean mWriteScheduled;
    public final long mXmlTimestamp;
    public static final boolean DEBUG = JobSchedulerService.DEBUG;
    public static final Pattern SPLIT_FILE_PATTERN = Pattern.compile("^jobs_\\d+.xml$");
    public static final Object sSingletonLock = new Object();
    public static final Histogram sScheduledJob30MinHighWaterMarkLogger =
            new Histogram(
                    "job_scheduler.value_hist_scheduled_job_30_min_high_water_mark",
                    new Histogram.ScaledRangeOptions(15, 1, 99.0f, 1.5f));
    public final SparseBooleanArray mPendingJobWriteUids = new SparseBooleanArray();
    public final Handler mIoHandler = IoThread.getHandler();
    public boolean mUseSplitFiles = true;
    public final JobSchedulerInternal.JobStorePersistStats mPersistInfo =
            new JobSchedulerInternal.JobStorePersistStats();
    public int mCurrentJobSetSize = 0;
    public int mScheduledJob30MinHighWaterMark = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.job.JobStore$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {}

        @Override // java.lang.Runnable
        public final void run() {
            AppSchedulingModuleThread.getHandler().removeCallbacks(this);
            synchronized (JobStore.this.mLock) {
                JobStore.sScheduledJob30MinHighWaterMarkLogger.logSample(
                        JobStore.this.mScheduledJob30MinHighWaterMark);
                JobStore jobStore = JobStore.this;
                jobStore.mScheduledJob30MinHighWaterMark = jobStore.mJobSet.size();
            }
            AppSchedulingModuleThread.getHandler().postDelayed(this, 1800000L);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.job.JobStore$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final SparseArray mJobFiles = new SparseArray();
        public final CopyConsumer mPersistedJobCopier = new CopyConsumer();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.job.JobStore$2$CopyConsumer */
        public final class CopyConsumer implements Consumer {
            public boolean mCopyAllJobs;
            public final SparseArray mJobStoreCopy = new SparseArray();

            /* renamed from: -$$Nest$mprepare, reason: not valid java name */
            public static void m622$$Nest$mprepare(CopyConsumer copyConsumer) {
                JobStore jobStore = JobStore.this;
                int i = 0;
                copyConsumer.mCopyAllJobs =
                        !jobStore.mUseSplitFiles || jobStore.mPendingJobWriteUids.get(-1);
                JobStore jobStore2 = JobStore.this;
                if (!jobStore2.mUseSplitFiles) {
                    copyConsumer.mJobStoreCopy.put(-1, new ArrayList());
                    return;
                }
                if (!jobStore2.mPendingJobWriteUids.get(-1)) {
                    while (i < JobStore.this.mPendingJobWriteUids.size()) {
                        copyConsumer.mJobStoreCopy.put(
                                JobStore.this.mPendingJobWriteUids.keyAt(i), new ArrayList());
                        i++;
                    }
                    return;
                }
                try {
                    File[] listFiles = JobStore.this.mJobFileDirectory.listFiles();
                    if (listFiles == null) {
                        Slog.wtfStack("JobStore", "Couldn't get job file list");
                        return;
                    }
                    int length = listFiles.length;
                    while (i < length) {
                        int extractUidFromJobFileName =
                                JobStore.extractUidFromJobFileName(listFiles[i]);
                        if (extractUidFromJobFileName != -2) {
                            copyConsumer.mJobStoreCopy.put(
                                    extractUidFromJobFileName, new ArrayList());
                        }
                        i++;
                    }
                } catch (SecurityException e) {
                    Slog.wtf("JobStore", "Not allowed to read job file directory", e);
                }
            }

            public CopyConsumer() {}

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                JobStatus jobStatus = (JobStatus) obj;
                int i = JobStore.this.mUseSplitFiles ? jobStatus.callingUid : -1;
                if (jobStatus.job.isPersisted()) {
                    if (this.mCopyAllJobs || JobStore.this.mPendingJobWriteUids.get(i)) {
                        List list = (List) this.mJobStoreCopy.get(i);
                        if (list == null) {
                            list = new ArrayList();
                            this.mJobStoreCopy.put(i, list);
                        }
                        List list2 = list;
                        JobStatus jobStatus2 =
                                new JobStatus(
                                        jobStatus.job,
                                        jobStatus.callingUid,
                                        jobStatus.sourcePackageName,
                                        jobStatus.sourceUserId,
                                        jobStatus.standbyBucket,
                                        jobStatus.mNamespace,
                                        jobStatus.sourceTag,
                                        jobStatus.numFailures,
                                        jobStatus.mNumSystemStops,
                                        jobStatus.earliestRunTimeElapsedMillis,
                                        jobStatus.latestRunTimeElapsedMillis,
                                        jobStatus.mLastSuccessfulRunTime,
                                        jobStatus.mLastFailedRunTime,
                                        jobStatus.mCumulativeExecutionTimeMs,
                                        jobStatus.mInternalFlags,
                                        jobStatus.mDynamicConstraints);
                        jobStatus2.mPersistedUtcTimes = jobStatus.mPersistedUtcTimes;
                        if (jobStatus.mPersistedUtcTimes != null && JobStatus.DEBUG) {
                            Slog.i(
                                    "JobScheduler.JobStatus",
                                    "Cloning job with persisted run times",
                                    new RuntimeException("here"));
                        }
                        ArrayList arrayList = jobStatus.executingWork;
                        if (arrayList != null && arrayList.size() > 0) {
                            jobStatus2.executingWork = new ArrayList(jobStatus.executingWork);
                        }
                        ArrayList arrayList2 = jobStatus.pendingWork;
                        if (arrayList2 != null && arrayList2.size() > 0) {
                            jobStatus2.pendingWork = new ArrayList(jobStatus.pendingWork);
                        }
                        list2.add(jobStatus2);
                    }
                }
            }
        }

        public AnonymousClass2() {}

        public static void addAttributesToJobTag(
                TypedXmlSerializer typedXmlSerializer, JobStatus jobStatus) {
            typedXmlSerializer.attribute(
                    (String) null, "jobid", Integer.toString(jobStatus.job.getId()));
            typedXmlSerializer.attribute(
                    (String) null, "package", jobStatus.job.getService().getPackageName());
            typedXmlSerializer.attribute(
                    (String) null, "class", jobStatus.job.getService().getClassName());
            String str = jobStatus.sourcePackageName;
            if (str != null) {
                typedXmlSerializer.attribute((String) null, "sourcePackageName", str);
            }
            String str2 = jobStatus.mNamespace;
            if (str2 != null) {
                typedXmlSerializer.attribute((String) null, "namespace", str2);
            }
            String str3 = jobStatus.sourceTag;
            if (str3 != null) {
                typedXmlSerializer.attribute((String) null, "sourceTag", str3);
            }
            typedXmlSerializer.attribute(
                    (String) null, "sourceUserId", String.valueOf(jobStatus.sourceUserId));
            typedXmlSerializer.attribute(
                    (String) null, "uid", Integer.toString(jobStatus.callingUid));
            typedXmlSerializer.attribute(
                    (String) null, "bias", String.valueOf(jobStatus.job.getBias()));
            typedXmlSerializer.attribute(
                    (String) null, "priority", String.valueOf(jobStatus.job.getPriority()));
            typedXmlSerializer.attribute(
                    (String) null, "flags", String.valueOf(jobStatus.job.getFlags()));
            int i = jobStatus.mInternalFlags;
            if (i != 0) {
                typedXmlSerializer.attribute((String) null, "internalFlags", String.valueOf(i));
            }
            typedXmlSerializer.attribute(
                    (String) null,
                    "lastSuccessfulRunTime",
                    String.valueOf(jobStatus.mLastSuccessfulRunTime));
            typedXmlSerializer.attribute(
                    (String) null,
                    "lastFailedRunTime",
                    String.valueOf(jobStatus.mLastFailedRunTime));
            typedXmlSerializer.attributeLong(
                    (String) null, "cumulativeExecutionTime", jobStatus.mCumulativeExecutionTimeMs);
        }

        public static PersistableBundle deepCopyBundle(int i, PersistableBundle persistableBundle) {
            if (i <= 0) {
                return null;
            }
            PersistableBundle persistableBundle2 = (PersistableBundle) persistableBundle.clone();
            for (String str : persistableBundle.keySet()) {
                Object obj = persistableBundle2.get(str);
                if (obj instanceof PersistableBundle) {
                    persistableBundle2.putPersistableBundle(
                            str, deepCopyBundle(i - 1, (PersistableBundle) obj));
                }
            }
            return persistableBundle2;
        }

        public static void writeConstraintsToXml(
                TypedXmlSerializer typedXmlSerializer, JobStatus jobStatus) {
            typedXmlSerializer.startTag((String) null, "constraints");
            JobInfo jobInfo = jobStatus.job;
            if (jobStatus.hasConnectivityConstraint()) {
                NetworkRequest requiredNetwork = jobStatus.job.getRequiredNetwork();
                typedXmlSerializer.attribute(
                        (String) null,
                        "net-capabilities-csv",
                        JobStore.intArrayToString(requiredNetwork.getCapabilities()));
                typedXmlSerializer.attribute(
                        (String) null,
                        "net-forbidden-capabilities-csv",
                        JobStore.intArrayToString(requiredNetwork.getForbiddenCapabilities()));
                typedXmlSerializer.attribute(
                        (String) null,
                        "net-transport-types-csv",
                        JobStore.intArrayToString(requiredNetwork.getTransportTypes()));
                if (jobInfo.getEstimatedNetworkDownloadBytes() != -1) {
                    typedXmlSerializer.attributeLong(
                            (String) null,
                            "estimated-download-bytes",
                            jobInfo.getEstimatedNetworkDownloadBytes());
                }
                if (jobInfo.getEstimatedNetworkUploadBytes() != -1) {
                    typedXmlSerializer.attributeLong(
                            (String) null,
                            "estimated-upload-bytes",
                            jobInfo.getEstimatedNetworkUploadBytes());
                }
                if (jobInfo.getMinimumNetworkChunkBytes() != -1) {
                    typedXmlSerializer.attributeLong(
                            (String) null,
                            "minimum-network-chunk-bytes",
                            jobInfo.getMinimumNetworkChunkBytes());
                }
            }
            if (jobInfo.isRequireDeviceIdle()) {
                typedXmlSerializer.attribute((String) null, "idle", Boolean.toString(true));
            }
            if (jobInfo.isRequireCharging()) {
                typedXmlSerializer.attribute((String) null, "charging", Boolean.toString(true));
            }
            if (jobInfo.isRequireBatteryNotLow()) {
                typedXmlSerializer.attribute(
                        (String) null, "battery-not-low", Boolean.toString(true));
            }
            if (jobInfo.isRequireStorageNotLow()) {
                typedXmlSerializer.attribute(
                        (String) null, "storage-not-low", Boolean.toString(true));
            }
            typedXmlSerializer.endTag((String) null, "constraints");
        }

        public static void writeDebugInfoToXml(
                TypedXmlSerializer typedXmlSerializer, JobStatus jobStatus) {
            ArraySet debugTagsArraySet = jobStatus.job.getDebugTagsArraySet();
            int size = debugTagsArraySet.size();
            String traceTag = jobStatus.job.getTraceTag();
            if (traceTag == null && size == 0) {
                return;
            }
            typedXmlSerializer.startTag((String) null, "debug-info");
            if (traceTag != null) {
                typedXmlSerializer.attribute((String) null, "trace-tag", traceTag);
            }
            for (int i = 0; i < size; i++) {
                typedXmlSerializer.startTag((String) null, "debug-tag");
                typedXmlSerializer.attribute(
                        (String) null, "tag", (String) debugTagsArraySet.valueAt(i));
                typedXmlSerializer.endTag((String) null, "debug-tag");
            }
            typedXmlSerializer.endTag((String) null, "debug-info");
        }

        public static void writeExecutionCriteriaToXml(
                XmlSerializer xmlSerializer, JobStatus jobStatus) {
            JobInfo jobInfo = jobStatus.job;
            if (jobInfo.isPeriodic()) {
                xmlSerializer.startTag(null, "periodic");
                xmlSerializer.attribute(null, "period", Long.toString(jobInfo.getIntervalMillis()));
                xmlSerializer.attribute(null, "flex", Long.toString(jobInfo.getFlexMillis()));
            } else {
                xmlSerializer.startTag(null, "one-off");
            }
            Pair pair = jobStatus.mPersistedUtcTimes;
            if (JobStore.DEBUG && pair != null) {
                Slog.i("JobStore", "storing original UTC timestamps for " + jobStatus);
            }
            long millis = JobSchedulerService.sSystemClock.millis();
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (jobStatus.hasConstraint(1073741824)) {
                xmlSerializer.attribute(
                        null,
                        "deadline",
                        Long.toString(
                                pair == null
                                        ? (jobStatus.latestRunTimeElapsedMillis - elapsedRealtime)
                                                + millis
                                        : ((Long) pair.second).longValue()));
            }
            if (jobStatus.hasConstraint(Integer.MIN_VALUE)) {
                xmlSerializer.attribute(
                        null,
                        "delay",
                        Long.toString(
                                pair == null
                                        ? (jobStatus.earliestRunTimeElapsedMillis - elapsedRealtime)
                                                + millis
                                        : ((Long) pair.first).longValue()));
            }
            if (jobStatus.job.getInitialBackoffMillis() != 30000
                    || jobStatus.job.getBackoffPolicy() != 1) {
                xmlSerializer.attribute(
                        null, "backoff-policy", Integer.toString(jobInfo.getBackoffPolicy()));
                xmlSerializer.attribute(
                        null, "initial-backoff", Long.toString(jobInfo.getInitialBackoffMillis()));
            }
            if (jobInfo.isPeriodic()) {
                xmlSerializer.endTag(null, "periodic");
            } else {
                xmlSerializer.endTag(null, "one-off");
            }
        }

        public static void writeJobWorkItemListToXml(
                TypedXmlSerializer typedXmlSerializer, List list) {
            if (list == null) {
                return;
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                JobWorkItem jobWorkItem = (JobWorkItem) list.get(i);
                if (jobWorkItem.getGrants() == null) {
                    if (jobWorkItem.getIntent() != null) {
                        Slog.wtf(
                                "JobStore",
                                "Encountered JobWorkItem with Intent in persisting list");
                    } else {
                        typedXmlSerializer.startTag((String) null, "job-work-item");
                        typedXmlSerializer.attributeInt(
                                (String) null, "delivery-count", jobWorkItem.getDeliveryCount());
                        if (jobWorkItem.getEstimatedNetworkDownloadBytes() != -1) {
                            typedXmlSerializer.attributeLong(
                                    (String) null,
                                    "estimated-download-bytes",
                                    jobWorkItem.getEstimatedNetworkDownloadBytes());
                        }
                        if (jobWorkItem.getEstimatedNetworkUploadBytes() != -1) {
                            typedXmlSerializer.attributeLong(
                                    (String) null,
                                    "estimated-upload-bytes",
                                    jobWorkItem.getEstimatedNetworkUploadBytes());
                        }
                        if (jobWorkItem.getMinimumNetworkChunkBytes() != -1) {
                            typedXmlSerializer.attributeLong(
                                    (String) null,
                                    "minimum-network-chunk-bytes",
                                    jobWorkItem.getMinimumNetworkChunkBytes());
                        }
                        PersistableBundle extras = jobWorkItem.getExtras();
                        typedXmlSerializer.startTag(null, "extras");
                        deepCopyBundle(10, extras).saveToXml(typedXmlSerializer);
                        typedXmlSerializer.endTag(null, "extras");
                        typedXmlSerializer.endTag((String) null, "job-work-item");
                    }
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:75:0x0217 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r6v1 */
        /* JADX WARN: Type inference failed for: r6v2, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r6v7 */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 789
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.job.JobStore.AnonymousClass2.run():void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class JobSet {
        final SparseArray mJobs = new SparseArray();
        final SparseArray mJobsPerSourceUid = new SparseArray();

        public final boolean add(JobStatus jobStatus) {
            int i = jobStatus.callingUid;
            ArraySet arraySet = (ArraySet) this.mJobs.get(i);
            if (arraySet == null) {
                arraySet = new ArraySet();
                this.mJobs.put(i, arraySet);
            }
            SparseArray sparseArray = this.mJobsPerSourceUid;
            int i2 = jobStatus.sourceUid;
            ArraySet arraySet2 = (ArraySet) sparseArray.get(i2);
            if (arraySet2 == null) {
                arraySet2 = new ArraySet();
                this.mJobsPerSourceUid.put(i2, arraySet2);
            }
            boolean add = arraySet.add(jobStatus);
            boolean add2 = arraySet2.add(jobStatus);
            if (add != add2) {
                Slog.wtf(
                        "JobStore",
                        "mJobs and mJobsPerSourceUid mismatch; caller= "
                                + add
                                + " source= "
                                + add2);
            }
            return add || add2;
        }

        public final void forEachJob(Predicate predicate, Consumer consumer) {
            for (int size = this.mJobs.size() - 1; size >= 0; size--) {
                ArraySet arraySet = (ArraySet) this.mJobs.valueAt(size);
                if (arraySet != null) {
                    for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                        JobStatus jobStatus = (JobStatus) arraySet.valueAt(size2);
                        if (predicate == null || predicate.test(jobStatus)) {
                            consumer.accept(jobStatus);
                        }
                    }
                }
            }
        }

        public final JobStatus get(int i, int i2, String str) {
            ArraySet arraySet = (ArraySet) this.mJobs.get(i);
            if (arraySet == null) {
                return null;
            }
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                JobStatus jobStatus = (JobStatus) arraySet.valueAt(size);
                if (jobStatus.job.getId() == i2 && Objects.equals(str, jobStatus.mNamespace)) {
                    return jobStatus;
                }
            }
            return null;
        }

        public final List getAllJobs() {
            ArrayList arrayList = new ArrayList(size());
            for (int size = this.mJobs.size() - 1; size >= 0; size--) {
                ArraySet arraySet = (ArraySet) this.mJobs.valueAt(size);
                if (arraySet != null) {
                    for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                        arrayList.add((JobStatus) arraySet.valueAt(size2));
                    }
                }
            }
            return arrayList;
        }

        public final void getJobsBySourceUid(int i, Set set) {
            ArraySet arraySet = (ArraySet) this.mJobsPerSourceUid.get(i);
            if (arraySet != null) {
                ((ArraySet) set).addAll((Collection) arraySet);
            }
        }

        public final boolean remove(JobStatus jobStatus) {
            int i = jobStatus.callingUid;
            ArraySet arraySet = (ArraySet) this.mJobs.get(i);
            SparseArray sparseArray = this.mJobsPerSourceUid;
            int i2 = jobStatus.sourceUid;
            ArraySet arraySet2 = (ArraySet) sparseArray.get(i2);
            boolean z = arraySet != null && arraySet.remove(jobStatus);
            boolean z2 = arraySet2 != null && arraySet2.remove(jobStatus);
            if (z != z2) {
                Slog.wtf("JobStore", "Job presence mismatch; caller=" + z + " source=" + z2);
            }
            if (!z && !z2) {
                return false;
            }
            if (arraySet != null && arraySet.size() == 0) {
                this.mJobs.remove(i);
            }
            if (arraySet2 != null && arraySet2.size() == 0) {
                this.mJobsPerSourceUid.remove(i2);
            }
            return true;
        }

        public final int size() {
            int i = 0;
            for (int size = this.mJobs.size() - 1; size >= 0; size--) {
                i += ((ArraySet) this.mJobs.valueAt(size)).size();
            }
            return i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ReadJobMapFromDiskRunnable implements Runnable {
        public final JobSet jobSet;
        public final CountDownLatch mCompletionLatch;
        public final boolean rtcGood;

        public ReadJobMapFromDiskRunnable(JobSet jobSet, boolean z, CountDownLatch countDownLatch) {
            this.jobSet = jobSet;
            this.rtcGood = z;
            this.mCompletionLatch = countDownLatch;
        }

        public static void buildConstraintsFromXml(
                JobInfo.Builder builder, TypedXmlPullParser typedXmlPullParser) {
            String attributeValue;
            String attributeValue2;
            String attributeValue3;
            String attributeValue4 =
                    typedXmlPullParser.getAttributeValue((String) null, "net-capabilities-csv");
            String attributeValue5 =
                    typedXmlPullParser.getAttributeValue(
                            (String) null, "net-forbidden-capabilities-csv");
            String attributeValue6 =
                    typedXmlPullParser.getAttributeValue((String) null, "net-transport-types-csv");
            if (attributeValue4 == null || attributeValue6 == null) {
                attributeValue =
                        typedXmlPullParser.getAttributeValue((String) null, "net-capabilities");
                attributeValue2 =
                        typedXmlPullParser.getAttributeValue(
                                (String) null, "net-unwanted-capabilities");
                attributeValue3 =
                        typedXmlPullParser.getAttributeValue((String) null, "net-transport-types");
            } else {
                attributeValue = null;
                attributeValue2 = null;
                attributeValue3 = null;
            }
            int i = 0;
            if (attributeValue4 != null && attributeValue6 != null) {
                NetworkRequest.Builder clearCapabilities =
                        new NetworkRequest.Builder().clearCapabilities();
                for (int i2 : JobStore.stringToIntArray(attributeValue4)) {
                    clearCapabilities.addCapability(i2);
                }
                for (int i3 : JobStore.stringToIntArray(attributeValue5)) {
                    clearCapabilities.addForbiddenCapability(i3);
                }
                int[] stringToIntArray = JobStore.stringToIntArray(attributeValue6);
                int length = stringToIntArray.length;
                while (i < length) {
                    clearCapabilities.addTransportType(stringToIntArray[i]);
                    i++;
                }
                builder.setRequiredNetwork(clearCapabilities.build())
                        .setEstimatedNetworkBytes(
                                typedXmlPullParser.getAttributeLong(
                                        (String) null, "estimated-download-bytes", -1L),
                                typedXmlPullParser.getAttributeLong(
                                        (String) null, "estimated-upload-bytes", -1L))
                        .setMinimumNetworkChunkBytes(
                                typedXmlPullParser.getAttributeLong(
                                        (String) null, "minimum-network-chunk-bytes", -1L));
            } else if (attributeValue == null || attributeValue3 == null) {
                if (typedXmlPullParser.getAttributeValue((String) null, "connectivity") != null) {
                    builder.setRequiredNetworkType(1);
                }
                if (typedXmlPullParser.getAttributeValue((String) null, "metered") != null) {
                    builder.setRequiredNetworkType(4);
                }
                if (typedXmlPullParser.getAttributeValue((String) null, "unmetered") != null) {
                    builder.setRequiredNetworkType(2);
                }
                if (typedXmlPullParser.getAttributeValue((String) null, "not-roaming") != null) {
                    builder.setRequiredNetworkType(3);
                }
            } else {
                NetworkRequest.Builder clearCapabilities2 =
                        new NetworkRequest.Builder().clearCapabilities();
                int[] unpackBits = BitUtils.unpackBits(Long.parseLong(attributeValue));
                for (int i4 : unpackBits) {
                    if (i4 <= 25) {
                        clearCapabilities2.addCapability(i4);
                    }
                }
                for (int i5 : BitUtils.unpackBits(Long.parseLong(attributeValue2))) {
                    if (i5 <= 25) {
                        clearCapabilities2.addForbiddenCapability(i5);
                    }
                }
                int[] unpackBits2 = BitUtils.unpackBits(Long.parseLong(attributeValue3));
                int length2 = unpackBits2.length;
                while (i < length2) {
                    int i6 = unpackBits2[i];
                    if (i6 <= 7) {
                        clearCapabilities2.addTransportType(i6);
                    }
                    i++;
                }
                builder.setRequiredNetwork(clearCapabilities2.build());
            }
            if (typedXmlPullParser.getAttributeValue((String) null, "idle") != null) {
                builder.setRequiresDeviceIdle(true);
            }
            if (typedXmlPullParser.getAttributeValue((String) null, "charging") != null) {
                builder.setRequiresCharging(true);
            }
            if (typedXmlPullParser.getAttributeValue((String) null, "battery-not-low") != null) {
                builder.setRequiresBatteryNotLow(true);
            }
            if (typedXmlPullParser.getAttributeValue((String) null, "storage-not-low") != null) {
                builder.setRequiresStorageNotLow(true);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:77:0x04c2  */
        /* JADX WARN: Removed duplicated region for block: B:83:0x04f1 A[LOOP:1: B:20:0x005c->B:83:0x04f1, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:84:0x04f0 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:86:0x04dd  */
        /* JADX WARN: Type inference failed for: r4v0 */
        /* JADX WARN: Type inference failed for: r4v1, types: [boolean] */
        /* JADX WARN: Type inference failed for: r4v4 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static java.util.List readJobMapImpl(
                long r38, java.io.InputStream r40, boolean r41) {
            /*
                Method dump skipped, instructions count: 1282
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.job.JobStore.ReadJobMapFromDiskRunnable.readJobMapImpl(long,"
                        + " java.io.InputStream, boolean):java.util.List");
        }

        public static JobWorkItem readJobWorkItemFromXml(TypedXmlPullParser typedXmlPullParser) {
            JobWorkItem.Builder builder = new JobWorkItem.Builder();
            builder.setDeliveryCount(
                            typedXmlPullParser.getAttributeInt((String) null, "delivery-count"))
                    .setEstimatedNetworkBytes(
                            typedXmlPullParser.getAttributeLong(
                                    (String) null, "estimated-download-bytes", -1L),
                            typedXmlPullParser.getAttributeLong(
                                    (String) null, "estimated-upload-bytes", -1L))
                    .setMinimumNetworkChunkBytes(
                            typedXmlPullParser.getAttributeLong(
                                    (String) null, "minimum-network-chunk-bytes", -1L));
            typedXmlPullParser.next();
            try {
                builder.setExtras(PersistableBundle.restoreFromXml(typedXmlPullParser));
                try {
                    return builder.build();
                } catch (Exception e) {
                    Slog.e("JobStore", "Invalid JobWorkItem", e);
                    return null;
                }
            } catch (IllegalArgumentException e2) {
                Slog.e("JobStore", "Persisted extras contained invalid data", e2);
                return null;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:68:0x018d, code lost:

           if (r14.getName().startsWith(com.android.server.job.JobStore.JOB_FILE_SPLIT_PREFIX) == false) goto L85;
        */
        /* JADX WARN: Removed duplicated region for block: B:67:0x0182 A[Catch: all -> 0x006c, TryCatch #7 {all -> 0x006c, blocks: (B:14:0x003c, B:16:0x0045, B:18:0x0054, B:22:0x01a1, B:23:0x006f, B:25:0x007b, B:61:0x0123, B:87:0x0120, B:103:0x010b, B:65:0x017b, B:67:0x0182, B:73:0x0191, B:79:0x012d, B:64:0x013e, B:77:0x0161, B:114:0x01a9, B:116:0x01b3, B:117:0x01b9), top: B:13:0x003c }] */
        /* JADX WARN: Removed duplicated region for block: B:73:0x0191 A[Catch: all -> 0x006c, TryCatch #7 {all -> 0x006c, blocks: (B:14:0x003c, B:16:0x0045, B:18:0x0054, B:22:0x01a1, B:23:0x006f, B:25:0x007b, B:61:0x0123, B:87:0x0120, B:103:0x010b, B:65:0x017b, B:67:0x0182, B:73:0x0191, B:79:0x012d, B:64:0x013e, B:77:0x0161, B:114:0x01a9, B:116:0x01b3, B:117:0x01b9), top: B:13:0x003c }] */
        /* JADX WARN: Removed duplicated region for block: B:81:0x011a A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 514
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.job.JobStore.ReadJobMapFromDiskRunnable.run():void");
        }
    }

    public JobStore(Object obj, File file) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mScheduledJobHighWaterMarkLoggingRunnable = anonymousClass1;
        this.mWriteRunnable = new AnonymousClass2();
        this.mLock = obj;
        this.mWriteScheduleLock = new Object();
        File file2 = new File(new File(file, "system"), "job");
        this.mJobFileDirectory = file2;
        file2.mkdirs();
        SystemConfigFileCommitEventLogger systemConfigFileCommitEventLogger =
                new SystemConfigFileCommitEventLogger("jobs");
        this.mEventLogger = systemConfigFileCommitEventLogger;
        AtomicFile atomicFile =
                new AtomicFile(new File(file2, "jobs.xml"), systemConfigFileCommitEventLogger);
        this.mJobsFile = atomicFile;
        this.mJobSet = new JobSet();
        long lastModifiedTime =
                atomicFile.exists() ? atomicFile.getLastModifiedTime() : file2.lastModified();
        this.mXmlTimestamp = lastModifiedTime;
        this.mRtcGood = JobSchedulerService.sSystemClock.millis() > lastModifiedTime;
        AppSchedulingModuleThread.getHandler().postDelayed(anonymousClass1, 1800000L);
    }

    public static Pair convertRtcBoundsToElapsed(Pair pair, long j) {
        long millis = JobSchedulerService.sSystemClock.millis();
        return Pair.create(
                Long.valueOf(
                        ((Long) pair.first).longValue() > 0
                                ? Math.max(((Long) pair.first).longValue() - millis, 0L) + j
                                : 0L),
                Long.valueOf(
                        ((Long) pair.second).longValue() < Long.MAX_VALUE
                                ? Math.max(((Long) pair.second).longValue() - millis, 0L) + j
                                : Long.MAX_VALUE));
    }

    public static int extractUidFromJobFileName(File file) {
        String name = file.getName();
        if (name.startsWith(JOB_FILE_SPLIT_PREFIX)) {
            try {
                int parseInt = Integer.parseInt(name.substring(5, name.length() - 4));
                if (parseInt < 0) {
                    return -2;
                }
                return parseInt;
            } catch (Exception e) {
                Slog.e("JobStore", "Unexpected file name format", e);
            }
        }
        return -2;
    }

    public static JobStore initAndGetForTesting(Context context, File file) {
        JobStore jobStore = new JobStore(new Object(), file);
        jobStore.readJobMapFromDisk(jobStore.mJobSet, jobStore.mRtcGood);
        jobStore.clearForTesting();
        return jobStore;
    }

    public static String intArrayToString(int[] iArr) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (int i : iArr) {
            stringJoiner.add(String.valueOf(i));
        }
        return stringJoiner.toString();
    }

    public static int[] stringToIntArray(String str) {
        if (TextUtils.isEmpty(str)) {
            return new int[0];
        }
        String[] split = str.split(",");
        int[] iArr = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            iArr[i] = Integer.parseInt(split[i]);
        }
        return iArr;
    }

    public void addForTesting(JobStatus jobStatus) {
        if (this.mJobSet.add(jobStatus)) {
            int i = this.mCurrentJobSetSize + 1;
            this.mCurrentJobSetSize = i;
            if (this.mScheduledJob30MinHighWaterMark < i) {
                this.mScheduledJob30MinHighWaterMark = i;
            }
        }
        if (jobStatus.job.isPersisted()) {
            this.mPendingJobWriteUids.put(jobStatus.callingUid, true);
        }
    }

    public void clear() {
        JobSet jobSet = this.mJobSet;
        jobSet.mJobs.clear();
        jobSet.mJobsPerSourceUid.clear();
        this.mPendingJobWriteUids.put(-1, true);
        this.mCurrentJobSetSize = 0;
        maybeWriteStatusToDiskAsync();
    }

    public void clearForTesting() {
        JobSet jobSet = this.mJobSet;
        jobSet.mJobs.clear();
        jobSet.mJobsPerSourceUid.clear();
        this.mPendingJobWriteUids.put(-1, true);
        this.mCurrentJobSetSize = 0;
    }

    public final boolean containsJob(JobStatus jobStatus) {
        JobSet jobSet = this.mJobSet;
        jobSet.getClass();
        ArraySet arraySet = (ArraySet) jobSet.mJobs.get(jobStatus.callingUid);
        return arraySet != null && arraySet.contains(jobStatus);
    }

    public final int countJobsForUid(int i) {
        ArraySet arraySet = (ArraySet) this.mJobSet.mJobs.get(i);
        int i2 = 0;
        if (arraySet != null) {
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                JobStatus jobStatus = (JobStatus) arraySet.valueAt(size);
                if (jobStatus.callingUid == jobStatus.sourceUid) {
                    i2++;
                }
            }
        }
        return i2;
    }

    public final void forEachJob(Consumer consumer) {
        this.mJobSet.forEachJob(null, consumer);
    }

    public final void forEachJob(Predicate predicate, Consumer consumer) {
        this.mJobSet.forEachJob(predicate, consumer);
    }

    public final void forEachJobForSourceUid(int i, Consumer consumer) {
        ArraySet arraySet = (ArraySet) this.mJobSet.mJobsPerSourceUid.get(i);
        if (arraySet != null) {
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                consumer.accept((JobStatus) arraySet.valueAt(size));
            }
        }
    }

    public final ArraySet getJobsByUid(int i) {
        JobSet jobSet = this.mJobSet;
        jobSet.getClass();
        ArraySet arraySet = new ArraySet();
        ArraySet arraySet2 = (ArraySet) jobSet.mJobs.get(i);
        if (arraySet2 != null) {
            arraySet.addAll((Collection) arraySet2);
        }
        return arraySet;
    }

    public final void maybeWriteStatusToDiskAsync() {
        synchronized (this.mWriteScheduleLock) {
            try {
                if (!this.mWriteScheduled) {
                    if (DEBUG) {
                        Slog.v("JobStore", "Scheduling persist of jobs to disk.");
                    }
                    this.mIoHandler.postDelayed(this.mWriteRunnable, 2000L);
                    this.mWriteScheduled = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void migrateJobFilesAsync() {
        synchronized (this.mLock) {
            this.mPendingJobWriteUids.put(-1, true);
        }
        synchronized (this.mWriteScheduleLock) {
            this.mSplitFileMigrationNeeded = true;
            maybeWriteStatusToDiskAsync();
        }
    }

    public void readJobMapFromDisk(JobSet jobSet, boolean z) {
        new ReadJobMapFromDiskRunnable(jobSet, z, null).run();
    }

    public void removeForTesting(JobStatus jobStatus) {
        if (this.mJobSet.remove(jobStatus)) {
            this.mCurrentJobSetSize--;
        }
        if (jobStatus.job.isPersisted()) {
            this.mPendingJobWriteUids.put(jobStatus.callingUid, true);
        }
    }

    public final void removeJobsOfUnlistedUsers(final int[] iArr) {
        JobSet jobSet = this.mJobSet;
        jobSet.getClass();
        final int i = 0;
        final int i2 = 1;
        Predicate or =
                new Predicate() { // from class:
                                  // com.android.server.job.JobStore$JobSet$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        int i3 = i;
                        int[] iArr2 = iArr;
                        JobStatus jobStatus = (JobStatus) obj;
                        switch (i3) {
                            case 0:
                                return !ArrayUtils.contains(iArr2, jobStatus.sourceUserId);
                            default:
                                return !ArrayUtils.contains(
                                        iArr2, UserHandle.getUserId(jobStatus.callingUid));
                        }
                    }
                }.or(
                        new Predicate() { // from class:
                                          // com.android.server.job.JobStore$JobSet$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                int i3 = i2;
                                int[] iArr2 = iArr;
                                JobStatus jobStatus = (JobStatus) obj;
                                switch (i3) {
                                    case 0:
                                        return !ArrayUtils.contains(iArr2, jobStatus.sourceUserId);
                                    default:
                                        return !ArrayUtils.contains(
                                                iArr2, UserHandle.getUserId(jobStatus.callingUid));
                                }
                            }
                        });
        for (int size = jobSet.mJobs.size() - 1; size >= 0; size--) {
            ArraySet arraySet = (ArraySet) jobSet.mJobs.valueAt(size);
            arraySet.removeIf(or);
            if (arraySet.size() == 0) {
                jobSet.mJobs.removeAt(size);
            }
        }
        for (int size2 = jobSet.mJobsPerSourceUid.size() - 1; size2 >= 0; size2--) {
            ArraySet arraySet2 = (ArraySet) jobSet.mJobsPerSourceUid.valueAt(size2);
            arraySet2.removeIf(or);
            if (arraySet2.size() == 0) {
                jobSet.mJobsPerSourceUid.removeAt(size2);
            }
        }
        this.mCurrentJobSetSize = jobSet.size();
    }

    public void setUseSplitFilesForTesting(boolean z) {
        boolean z2;
        synchronized (this.mLock) {
            try {
                z2 = this.mUseSplitFiles != z;
                if (z2) {
                    this.mUseSplitFiles = z;
                    this.mPendingJobWriteUids.put(-1, true);
                }
            } finally {
            }
        }
        if (z2) {
            synchronized (this.mWriteScheduleLock) {
                this.mSplitFileMigrationNeeded = true;
            }
        }
    }

    public final void touchJob(JobStatus jobStatus) {
        if (jobStatus.job.isPersisted()) {
            this.mPendingJobWriteUids.put(jobStatus.callingUid, true);
            maybeWriteStatusToDiskAsync();
        }
    }

    public boolean waitForWriteToCompleteForTesting(long j) {
        long uptimeMillis = SystemClock.uptimeMillis();
        long j2 = uptimeMillis + j;
        synchronized (this.mWriteScheduleLock) {
            while (true) {
                try {
                    if (!this.mWriteScheduled && !this.mWriteInProgress) {
                        break;
                    }
                    long uptimeMillis2 = SystemClock.uptimeMillis();
                    if (uptimeMillis2 >= j2) {
                        return false;
                    }
                    try {
                        this.mWriteScheduleLock.wait((uptimeMillis2 - uptimeMillis) + j);
                    } catch (InterruptedException unused) {
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return true;
        }
    }

    public void writeStatusToDiskForTesting() {
        synchronized (this.mWriteScheduleLock) {
            try {
                if (this.mWriteScheduled) {
                    throw new IllegalStateException("An asynchronous write is already scheduled.");
                }
                this.mWriteScheduled = true;
                this.mWriteRunnable.run();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
