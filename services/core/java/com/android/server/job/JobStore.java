package com.android.server.job;

import android.app.job.JobInfo;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.net.NetworkRequest;
import android.os.Environment;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SystemConfigFileCommitEventLogger;
import android.util.Xml;
import com.android.internal.util.jobs.ArrayUtils;
import com.android.internal.util.jobs.BitUtils;
import com.android.modules.expresslog.Histogram;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.IoThread;
import com.android.server.content.SyncJobService;
import com.android.server.job.JobSchedulerInternal;
import com.android.server.job.JobStore;
import com.android.server.job.controllers.JobStatus;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes2.dex */
public final class JobStore {
    static final int INVALID_UID = -2;
    static final String JOB_FILE_SPLIT_PREFIX = "jobs_";
    public static JobStore sSingleton;
    public final Context mContext;
    public final SystemConfigFileCommitEventLogger mEventLogger;
    public final File mJobFileDirectory;
    public final JobSet mJobSet;
    public final AtomicFile mJobsFile;
    public final Object mLock;
    public boolean mRtcGood;
    public final Runnable mScheduledJobHighWaterMarkLoggingRunnable;
    public boolean mSplitFileMigrationNeeded;
    public boolean mWriteInProgress;
    public final Runnable mWriteRunnable;
    public final Object mWriteScheduleLock;
    public boolean mWriteScheduled;
    public final long mXmlTimestamp;
    public static final boolean DEBUG = JobSchedulerService.DEBUG;
    public static final Object sSingletonLock = new Object();
    public static final Histogram sScheduledJob30MinHighWaterMarkLogger = new Histogram("job_scheduler.value_hist_scheduled_job_30_min_high_water_mark", new Histogram.ScaledRangeOptions(15, 1, 99.0f, 1.5f));
    public final SparseBooleanArray mPendingJobWriteUids = new SparseBooleanArray();
    public final Handler mIoHandler = IoThread.getHandler();
    public boolean mUseSplitFiles = true;
    public JobSchedulerInternal.JobStorePersistStats mPersistInfo = new JobSchedulerInternal.JobStorePersistStats();
    public int mCurrentJobSetSize = 0;
    public int mScheduledJob30MinHighWaterMark = 0;

    public static JobStore get(JobSchedulerService jobSchedulerService) {
        JobStore jobStore;
        synchronized (sSingletonLock) {
            if (sSingleton == null) {
                sSingleton = new JobStore(jobSchedulerService.getContext(), jobSchedulerService.getLock(), Environment.getDataDirectory());
            }
            jobStore = sSingleton;
        }
        return jobStore;
    }

    public static JobStore initAndGetForTesting(Context context, File file) {
        JobStore jobStore = new JobStore(context, new Object(), file);
        jobStore.init();
        jobStore.clearForTesting();
        return jobStore;
    }

    public JobStore(Context context, Object obj, File file) {
        Runnable runnable = new Runnable() { // from class: com.android.server.job.JobStore.1
            @Override // java.lang.Runnable
            public void run() {
                AppSchedulingModuleThread.getHandler().removeCallbacks(this);
                synchronized (JobStore.this.mLock) {
                    JobStore.sScheduledJob30MinHighWaterMarkLogger.logSample(JobStore.this.mScheduledJob30MinHighWaterMark);
                    JobStore jobStore = JobStore.this;
                    jobStore.mScheduledJob30MinHighWaterMark = jobStore.mJobSet.size();
                }
                AppSchedulingModuleThread.getHandler().postDelayed(this, 1800000L);
            }
        };
        this.mScheduledJobHighWaterMarkLoggingRunnable = runnable;
        this.mWriteRunnable = new Runnable() { // from class: com.android.server.job.JobStore.2
            public final SparseArray mJobFiles = new SparseArray();
            public final CopyConsumer mPersistedJobCopier = new CopyConsumer();

            /* renamed from: com.android.server.job.JobStore$2$CopyConsumer */
            public class CopyConsumer implements Consumer {
                public boolean mCopyAllJobs;
                public final SparseArray mJobStoreCopy = new SparseArray();

                public CopyConsumer() {
                }

                public final void prepare() {
                    int i = 0;
                    this.mCopyAllJobs = !JobStore.this.mUseSplitFiles || JobStore.this.mPendingJobWriteUids.get(-1);
                    if (JobStore.this.mUseSplitFiles) {
                        if (JobStore.this.mPendingJobWriteUids.get(-1)) {
                            try {
                                File[] listFiles = JobStore.this.mJobFileDirectory.listFiles();
                                if (listFiles == null) {
                                    Slog.wtfStack("JobStore", "Couldn't get job file list");
                                    return;
                                }
                                int length = listFiles.length;
                                while (i < length) {
                                    int extractUidFromJobFileName = JobStore.extractUidFromJobFileName(listFiles[i]);
                                    if (extractUidFromJobFileName != -2) {
                                        this.mJobStoreCopy.put(extractUidFromJobFileName, new ArrayList());
                                    }
                                    i++;
                                }
                                return;
                            } catch (SecurityException e) {
                                Slog.wtf("JobStore", "Not allowed to read job file directory", e);
                                return;
                            }
                        }
                        while (i < JobStore.this.mPendingJobWriteUids.size()) {
                            this.mJobStoreCopy.put(JobStore.this.mPendingJobWriteUids.keyAt(i), new ArrayList());
                            i++;
                        }
                        return;
                    }
                    this.mJobStoreCopy.put(-1, new ArrayList());
                }

                @Override // java.util.function.Consumer
                public void accept(JobStatus jobStatus) {
                    int uid = JobStore.this.mUseSplitFiles ? jobStatus.getUid() : -1;
                    if (jobStatus.isPersisted()) {
                        if (this.mCopyAllJobs || JobStore.this.mPendingJobWriteUids.get(uid)) {
                            List list = (List) this.mJobStoreCopy.get(uid);
                            if (list == null) {
                                list = new ArrayList();
                                this.mJobStoreCopy.put(uid, list);
                            }
                            list.add(new JobStatus(jobStatus));
                        }
                    }
                }

                public final void reset() {
                    this.mJobStoreCopy.clear();
                }
            }

            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                AtomicFile atomicFile;
                long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
                synchronized (JobStore.this.mWriteScheduleLock) {
                    JobStore.this.mWriteScheduled = false;
                    if (JobStore.this.mWriteInProgress) {
                        JobStore.this.maybeWriteStatusToDiskAsync();
                        return;
                    }
                    JobStore.this.mWriteInProgress = true;
                    synchronized (JobStore.this.mLock) {
                        z = JobStore.this.mUseSplitFiles;
                        this.mPersistedJobCopier.prepare();
                        JobStore.this.mJobSet.forEachJob((Predicate) null, this.mPersistedJobCopier);
                        JobStore.this.mPendingJobWriteUids.clear();
                    }
                    JobStore.this.mPersistInfo.countAllJobsSaved = 0;
                    JobStore.this.mPersistInfo.countSystemServerJobsSaved = 0;
                    JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved = 0;
                    for (int size = this.mPersistedJobCopier.mJobStoreCopy.size() - 1; size >= 0; size--) {
                        if (z) {
                            int keyAt = this.mPersistedJobCopier.mJobStoreCopy.keyAt(size);
                            atomicFile = (AtomicFile) this.mJobFiles.get(keyAt);
                            if (atomicFile == null) {
                                atomicFile = JobStore.this.createJobFile(JobStore.JOB_FILE_SPLIT_PREFIX + keyAt);
                                this.mJobFiles.put(keyAt, atomicFile);
                            }
                        } else {
                            atomicFile = JobStore.this.mJobsFile;
                        }
                        if (JobStore.DEBUG) {
                            Slog.d("JobStore", "Writing for " + this.mPersistedJobCopier.mJobStoreCopy.keyAt(size) + " to " + atomicFile.getBaseFile().getName() + ": " + ((List) this.mPersistedJobCopier.mJobStoreCopy.valueAt(size)).size() + " jobs");
                        }
                        writeJobsMapImpl(atomicFile, (List) this.mPersistedJobCopier.mJobStoreCopy.valueAt(size));
                    }
                    if (JobStore.DEBUG) {
                        Slog.v("JobStore", "Finished writing, took " + (JobSchedulerService.sElapsedRealtimeClock.millis() - millis) + "ms");
                    }
                    this.mPersistedJobCopier.reset();
                    if (!z) {
                        this.mJobFiles.clear();
                    }
                    JobStore.this.mJobFileDirectory.setLastModified(JobSchedulerService.sSystemClock.millis());
                    synchronized (JobStore.this.mWriteScheduleLock) {
                        if (JobStore.this.mSplitFileMigrationNeeded) {
                            for (File file2 : JobStore.this.mJobFileDirectory.listFiles()) {
                                if (z) {
                                    if (!file2.getName().startsWith(JobStore.JOB_FILE_SPLIT_PREFIX)) {
                                        file2.delete();
                                    }
                                } else if (file2.getName().startsWith(JobStore.JOB_FILE_SPLIT_PREFIX)) {
                                    file2.delete();
                                }
                            }
                        }
                        JobStore.this.mWriteInProgress = false;
                        JobStore.this.mWriteScheduleLock.notifyAll();
                    }
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:36:0x00ea A[Catch: all -> 0x0111, TRY_LEAVE, TryCatch #1 {all -> 0x0111, blocks: (B:40:0x00d5, B:42:0x00db, B:34:0x00e4, B:36:0x00ea, B:51:0x00c9, B:60:0x00c6), top: B:2:0x0014 }] */
            /* JADX WARN: Removed duplicated region for block: B:42:0x00db A[Catch: all -> 0x0111, TryCatch #1 {all -> 0x0111, blocks: (B:40:0x00d5, B:42:0x00db, B:34:0x00e4, B:36:0x00ea, B:51:0x00c9, B:60:0x00c6), top: B:2:0x0014 }] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void writeJobsMapImpl(AtomicFile atomicFile, List list) {
                int i;
                int i2;
                JobSchedulerInternal.JobStorePersistStats jobStorePersistStats;
                int i3;
                JobStore.this.mEventLogger.setStartTime(SystemClock.uptimeMillis());
                int i4 = 0;
                try {
                    try {
                        FileOutputStream startWrite = atomicFile.startWrite();
                        try {
                            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                            resolveSerializer.startDocument((String) null, Boolean.TRUE);
                            resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                            resolveSerializer.startTag((String) null, "job-info");
                            resolveSerializer.attribute((String) null, "version", Integer.toString(1));
                            int i5 = 0;
                            i = 0;
                            i2 = 0;
                            while (i4 < list.size()) {
                                try {
                                    JobStatus jobStatus = (JobStatus) list.get(i4);
                                    if (JobStore.DEBUG) {
                                        Slog.d("JobStore", "Saving job " + jobStatus.getJobId());
                                    }
                                    resolveSerializer.startTag((String) null, "job");
                                    addAttributesToJobTag(resolveSerializer, jobStatus);
                                    writeConstraintsToXml(resolveSerializer, jobStatus);
                                    writeExecutionCriteriaToXml(resolveSerializer, jobStatus);
                                    writeBundleToXml(jobStatus.getJob().getExtras(), resolveSerializer);
                                    writeJobWorkItemsToXml(resolveSerializer, jobStatus);
                                    resolveSerializer.endTag((String) null, "job");
                                    i5++;
                                    if (jobStatus.getUid() == 1000) {
                                        i++;
                                        if (JobStore.isSyncJob(jobStatus)) {
                                            i2++;
                                        }
                                    }
                                    i4++;
                                } catch (Throwable th) {
                                    th = th;
                                    i4 = i5;
                                    if (startWrite != null) {
                                        try {
                                            startWrite.close();
                                        } catch (Throwable th2) {
                                            try {
                                                th.addSuppressed(th2);
                                            } catch (IOException e) {
                                                e = e;
                                                if (JobStore.DEBUG) {
                                                    Slog.v("JobStore", "Error writing out job data.", e);
                                                }
                                                jobStorePersistStats = JobStore.this.mPersistInfo;
                                                i3 = jobStorePersistStats.countAllJobsSaved + i4;
                                                jobStorePersistStats.countAllJobsSaved = i3;
                                                JobStore.this.mPersistInfo.countSystemServerJobsSaved += i;
                                                JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved += i2;
                                            } catch (XmlPullParserException e2) {
                                                e = e2;
                                                if (JobStore.DEBUG) {
                                                    Slog.d("JobStore", "Error persisting bundle.", e);
                                                }
                                                jobStorePersistStats = JobStore.this.mPersistInfo;
                                                i3 = jobStorePersistStats.countAllJobsSaved + i4;
                                                jobStorePersistStats.countAllJobsSaved = i3;
                                                JobStore.this.mPersistInfo.countSystemServerJobsSaved += i;
                                                JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved += i2;
                                            }
                                        }
                                    }
                                    throw th;
                                }
                            }
                            resolveSerializer.endTag((String) null, "job-info");
                            resolveSerializer.endDocument();
                            atomicFile.finishWrite(startWrite);
                            if (startWrite != null) {
                                try {
                                    startWrite.close();
                                } catch (IOException e3) {
                                    e = e3;
                                    i4 = i5;
                                    if (JobStore.DEBUG) {
                                    }
                                    jobStorePersistStats = JobStore.this.mPersistInfo;
                                    i3 = jobStorePersistStats.countAllJobsSaved + i4;
                                    jobStorePersistStats.countAllJobsSaved = i3;
                                    JobStore.this.mPersistInfo.countSystemServerJobsSaved += i;
                                    JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved += i2;
                                } catch (XmlPullParserException e4) {
                                    e = e4;
                                    i4 = i5;
                                    if (JobStore.DEBUG) {
                                    }
                                    jobStorePersistStats = JobStore.this.mPersistInfo;
                                    i3 = jobStorePersistStats.countAllJobsSaved + i4;
                                    jobStorePersistStats.countAllJobsSaved = i3;
                                    JobStore.this.mPersistInfo.countSystemServerJobsSaved += i;
                                    JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved += i2;
                                } catch (Throwable th3) {
                                    th = th3;
                                    i4 = i5;
                                    JobStore.this.mPersistInfo.countAllJobsSaved += i4;
                                    JobStore.this.mPersistInfo.countSystemServerJobsSaved += i;
                                    JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved += i2;
                                    throw th;
                                }
                            }
                            jobStorePersistStats = JobStore.this.mPersistInfo;
                            i3 = jobStorePersistStats.countAllJobsSaved + i5;
                        } catch (Throwable th4) {
                            th = th4;
                            i = 0;
                            i2 = 0;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                    }
                } catch (IOException e5) {
                    e = e5;
                    i = 0;
                    i2 = 0;
                } catch (XmlPullParserException e6) {
                    e = e6;
                    i = 0;
                    i2 = 0;
                } catch (Throwable th6) {
                    th = th6;
                    i = 0;
                    i2 = 0;
                }
                jobStorePersistStats.countAllJobsSaved = i3;
                JobStore.this.mPersistInfo.countSystemServerJobsSaved += i;
                JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved += i2;
            }

            public final void addAttributesToJobTag(TypedXmlSerializer typedXmlSerializer, JobStatus jobStatus) {
                typedXmlSerializer.attribute((String) null, "jobid", Integer.toString(jobStatus.getJobId()));
                typedXmlSerializer.attribute((String) null, "package", jobStatus.getServiceComponent().getPackageName());
                typedXmlSerializer.attribute((String) null, "class", jobStatus.getServiceComponent().getClassName());
                if (jobStatus.getSourcePackageName() != null) {
                    typedXmlSerializer.attribute((String) null, "sourcePackageName", jobStatus.getSourcePackageName());
                }
                if (jobStatus.getNamespace() != null) {
                    typedXmlSerializer.attribute((String) null, "namespace", jobStatus.getNamespace());
                }
                if (jobStatus.getSourceTag() != null) {
                    typedXmlSerializer.attribute((String) null, "sourceTag", jobStatus.getSourceTag());
                }
                typedXmlSerializer.attribute((String) null, "sourceUserId", String.valueOf(jobStatus.getSourceUserId()));
                typedXmlSerializer.attribute((String) null, "uid", Integer.toString(jobStatus.getUid()));
                typedXmlSerializer.attribute((String) null, "bias", String.valueOf(jobStatus.getBias()));
                typedXmlSerializer.attribute((String) null, "priority", String.valueOf(jobStatus.getJob().getPriority()));
                typedXmlSerializer.attribute((String) null, "flags", String.valueOf(jobStatus.getFlags()));
                if (jobStatus.getInternalFlags() != 0) {
                    typedXmlSerializer.attribute((String) null, "internalFlags", String.valueOf(jobStatus.getInternalFlags()));
                }
                typedXmlSerializer.attribute((String) null, "lastSuccessfulRunTime", String.valueOf(jobStatus.getLastSuccessfulRunTime()));
                typedXmlSerializer.attribute((String) null, "lastFailedRunTime", String.valueOf(jobStatus.getLastFailedRunTime()));
                typedXmlSerializer.attributeLong((String) null, "cumulativeExecutionTime", jobStatus.getCumulativeExecutionTimeMs());
            }

            public final void writeBundleToXml(PersistableBundle persistableBundle, XmlSerializer xmlSerializer) {
                xmlSerializer.startTag(null, "extras");
                deepCopyBundle(persistableBundle, 10).saveToXml(xmlSerializer);
                xmlSerializer.endTag(null, "extras");
            }

            public final PersistableBundle deepCopyBundle(PersistableBundle persistableBundle, int i) {
                if (i <= 0) {
                    return null;
                }
                PersistableBundle persistableBundle2 = (PersistableBundle) persistableBundle.clone();
                for (String str : persistableBundle.keySet()) {
                    Object obj2 = persistableBundle2.get(str);
                    if (obj2 instanceof PersistableBundle) {
                        persistableBundle2.putPersistableBundle(str, deepCopyBundle((PersistableBundle) obj2, i - 1));
                    }
                }
                return persistableBundle2;
            }

            public final void writeConstraintsToXml(TypedXmlSerializer typedXmlSerializer, JobStatus jobStatus) {
                typedXmlSerializer.startTag((String) null, "constraints");
                JobInfo job = jobStatus.getJob();
                if (jobStatus.hasConnectivityConstraint()) {
                    NetworkRequest requiredNetwork = jobStatus.getJob().getRequiredNetwork();
                    typedXmlSerializer.attribute((String) null, "net-capabilities-csv", JobStore.intArrayToString(requiredNetwork.getCapabilities()));
                    typedXmlSerializer.attribute((String) null, "net-forbidden-capabilities-csv", JobStore.intArrayToString(requiredNetwork.getForbiddenCapabilities()));
                    typedXmlSerializer.attribute((String) null, "net-transport-types-csv", JobStore.intArrayToString(requiredNetwork.getTransportTypes()));
                    if (job.getEstimatedNetworkDownloadBytes() != -1) {
                        typedXmlSerializer.attributeLong((String) null, "estimated-download-bytes", job.getEstimatedNetworkDownloadBytes());
                    }
                    if (job.getEstimatedNetworkUploadBytes() != -1) {
                        typedXmlSerializer.attributeLong((String) null, "estimated-upload-bytes", job.getEstimatedNetworkUploadBytes());
                    }
                    if (job.getMinimumNetworkChunkBytes() != -1) {
                        typedXmlSerializer.attributeLong((String) null, "minimum-network-chunk-bytes", job.getMinimumNetworkChunkBytes());
                    }
                }
                if (job.isRequireDeviceIdle()) {
                    typedXmlSerializer.attribute((String) null, "idle", Boolean.toString(true));
                }
                if (job.isRequireCharging()) {
                    typedXmlSerializer.attribute((String) null, "charging", Boolean.toString(true));
                }
                if (job.isRequireBatteryNotLow()) {
                    typedXmlSerializer.attribute((String) null, "battery-not-low", Boolean.toString(true));
                }
                if (job.isRequireStorageNotLow()) {
                    typedXmlSerializer.attribute((String) null, "storage-not-low", Boolean.toString(true));
                }
                if (job.isPreferBatteryNotLow()) {
                    typedXmlSerializer.attributeBoolean((String) null, "prefer-battery-not-low", true);
                }
                if (job.isPreferCharging()) {
                    typedXmlSerializer.attributeBoolean((String) null, "prefer-charging", true);
                }
                if (job.isPreferDeviceIdle()) {
                    typedXmlSerializer.attributeBoolean((String) null, "prefer-idle", true);
                }
                typedXmlSerializer.endTag((String) null, "constraints");
            }

            public final void writeExecutionCriteriaToXml(XmlSerializer xmlSerializer, JobStatus jobStatus) {
                long longValue;
                long longValue2;
                JobInfo job = jobStatus.getJob();
                if (jobStatus.getJob().isPeriodic()) {
                    xmlSerializer.startTag(null, "periodic");
                    xmlSerializer.attribute(null, "period", Long.toString(job.getIntervalMillis()));
                    xmlSerializer.attribute(null, "flex", Long.toString(job.getFlexMillis()));
                } else {
                    xmlSerializer.startTag(null, "one-off");
                }
                Pair persistedUtcTimes = jobStatus.getPersistedUtcTimes();
                if (JobStore.DEBUG && persistedUtcTimes != null) {
                    Slog.i("JobStore", "storing original UTC timestamps for " + jobStatus);
                }
                long millis = JobSchedulerService.sSystemClock.millis();
                long millis2 = JobSchedulerService.sElapsedRealtimeClock.millis();
                if (jobStatus.hasDeadlineConstraint()) {
                    if (persistedUtcTimes == null) {
                        longValue2 = (jobStatus.getLatestRunTimeElapsed() - millis2) + millis;
                    } else {
                        longValue2 = ((Long) persistedUtcTimes.second).longValue();
                    }
                    xmlSerializer.attribute(null, "deadline", Long.toString(longValue2));
                }
                if (jobStatus.hasTimingDelayConstraint()) {
                    if (persistedUtcTimes == null) {
                        longValue = millis + (jobStatus.getEarliestRunTime() - millis2);
                    } else {
                        longValue = ((Long) persistedUtcTimes.first).longValue();
                    }
                    xmlSerializer.attribute(null, "delay", Long.toString(longValue));
                }
                if (jobStatus.getJob().getInitialBackoffMillis() != 30000 || jobStatus.getJob().getBackoffPolicy() != 1) {
                    xmlSerializer.attribute(null, "backoff-policy", Integer.toString(job.getBackoffPolicy()));
                    xmlSerializer.attribute(null, "initial-backoff", Long.toString(job.getInitialBackoffMillis()));
                }
                if (job.isPeriodic()) {
                    xmlSerializer.endTag(null, "periodic");
                } else {
                    xmlSerializer.endTag(null, "one-off");
                }
            }

            public final void writeJobWorkItemsToXml(TypedXmlSerializer typedXmlSerializer, JobStatus jobStatus) {
                writeJobWorkItemListToXml(typedXmlSerializer, jobStatus.executingWork);
                writeJobWorkItemListToXml(typedXmlSerializer, jobStatus.pendingWork);
            }

            public final void writeJobWorkItemListToXml(TypedXmlSerializer typedXmlSerializer, List list) {
                if (list == null) {
                    return;
                }
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    JobWorkItem jobWorkItem = (JobWorkItem) list.get(i);
                    if (jobWorkItem.getGrants() == null) {
                        if (jobWorkItem.getIntent() != null) {
                            Slog.wtf("JobStore", "Encountered JobWorkItem with Intent in persisting list");
                        } else {
                            typedXmlSerializer.startTag((String) null, "job-work-item");
                            typedXmlSerializer.attributeInt((String) null, "delivery-count", jobWorkItem.getDeliveryCount());
                            if (jobWorkItem.getEstimatedNetworkDownloadBytes() != -1) {
                                typedXmlSerializer.attributeLong((String) null, "estimated-download-bytes", jobWorkItem.getEstimatedNetworkDownloadBytes());
                            }
                            if (jobWorkItem.getEstimatedNetworkUploadBytes() != -1) {
                                typedXmlSerializer.attributeLong((String) null, "estimated-upload-bytes", jobWorkItem.getEstimatedNetworkUploadBytes());
                            }
                            if (jobWorkItem.getMinimumNetworkChunkBytes() != -1) {
                                typedXmlSerializer.attributeLong((String) null, "minimum-network-chunk-bytes", jobWorkItem.getMinimumNetworkChunkBytes());
                            }
                            writeBundleToXml(jobWorkItem.getExtras(), typedXmlSerializer);
                            typedXmlSerializer.endTag((String) null, "job-work-item");
                        }
                    }
                }
            }
        };
        this.mLock = obj;
        this.mWriteScheduleLock = new Object();
        this.mContext = context;
        File file2 = new File(new File(file, "system"), "job");
        this.mJobFileDirectory = file2;
        file2.mkdirs();
        this.mEventLogger = new SystemConfigFileCommitEventLogger("jobs");
        AtomicFile createJobFile = createJobFile(new File(file2, "jobs.xml"));
        this.mJobsFile = createJobFile;
        this.mJobSet = new JobSet();
        long lastModifiedTime = createJobFile.exists() ? createJobFile.getLastModifiedTime() : file2.lastModified();
        this.mXmlTimestamp = lastModifiedTime;
        this.mRtcGood = JobSchedulerService.sSystemClock.millis() > lastModifiedTime;
        AppSchedulingModuleThread.getHandler().postDelayed(runnable, 1800000L);
    }

    public final void init() {
        readJobMapFromDisk(this.mJobSet, this.mRtcGood);
    }

    public void initAsync(CountDownLatch countDownLatch) {
        this.mIoHandler.post(new ReadJobMapFromDiskRunnable(this.mJobSet, this.mRtcGood, countDownLatch));
    }

    public final AtomicFile createJobFile(String str) {
        return createJobFile(new File(this.mJobFileDirectory, str + ".xml"));
    }

    public final AtomicFile createJobFile(File file) {
        return new AtomicFile(file, this.mEventLogger);
    }

    public boolean jobTimesInflatedValid() {
        return this.mRtcGood;
    }

    public boolean clockNowValidToInflate(long j) {
        return j >= this.mXmlTimestamp;
    }

    public void runWorkAsync(Runnable runnable) {
        this.mIoHandler.post(runnable);
    }

    public void getRtcCorrectedJobsLocked(final ArrayList arrayList, final ArrayList arrayList2) {
        final long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
        forEachJob(new Consumer() { // from class: com.android.server.job.JobStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                JobStore.lambda$getRtcCorrectedJobsLocked$0(millis, arrayList, arrayList2, (JobStatus) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$getRtcCorrectedJobsLocked$0(long j, ArrayList arrayList, ArrayList arrayList2, JobStatus jobStatus) {
        Pair persistedUtcTimes = jobStatus.getPersistedUtcTimes();
        if (persistedUtcTimes != null) {
            Pair convertRtcBoundsToElapsed = convertRtcBoundsToElapsed(persistedUtcTimes, j);
            JobStatus jobStatus2 = new JobStatus(jobStatus, ((Long) convertRtcBoundsToElapsed.first).longValue(), ((Long) convertRtcBoundsToElapsed.second).longValue(), 0, 0, jobStatus.getLastSuccessfulRunTime(), jobStatus.getLastFailedRunTime(), jobStatus.getCumulativeExecutionTimeMs());
            jobStatus2.prepareLocked();
            arrayList.add(jobStatus2);
            arrayList2.add(jobStatus);
        }
    }

    public void add(JobStatus jobStatus) {
        if (this.mJobSet.add(jobStatus)) {
            this.mCurrentJobSetSize++;
            maybeUpdateHighWaterMark();
        }
        if (jobStatus.isPersisted()) {
            this.mPendingJobWriteUids.put(jobStatus.getUid(), true);
            maybeWriteStatusToDiskAsync();
        }
        if (DEBUG) {
            Slog.d("JobStore", "Added job status to store: " + jobStatus);
        }
    }

    public void addForTesting(JobStatus jobStatus) {
        if (this.mJobSet.add(jobStatus)) {
            this.mCurrentJobSetSize++;
            maybeUpdateHighWaterMark();
        }
        if (jobStatus.isPersisted()) {
            this.mPendingJobWriteUids.put(jobStatus.getUid(), true);
        }
    }

    public boolean containsJob(JobStatus jobStatus) {
        return this.mJobSet.contains(jobStatus);
    }

    public int size() {
        return this.mJobSet.size();
    }

    public JobSchedulerInternal.JobStorePersistStats getPersistStats() {
        return this.mPersistInfo;
    }

    public int countJobsForUid(int i) {
        return this.mJobSet.countJobsForUid(i);
    }

    public boolean remove(JobStatus jobStatus, boolean z) {
        boolean remove = this.mJobSet.remove(jobStatus);
        if (!remove) {
            if (!DEBUG) {
                return false;
            }
            Slog.d("JobStore", "Couldn't remove job: didn't exist: " + jobStatus);
            return false;
        }
        this.mCurrentJobSetSize--;
        if (z && jobStatus.isPersisted()) {
            this.mPendingJobWriteUids.put(jobStatus.getUid(), true);
            maybeWriteStatusToDiskAsync();
        }
        return remove;
    }

    public void removeForTesting(JobStatus jobStatus) {
        if (this.mJobSet.remove(jobStatus)) {
            this.mCurrentJobSetSize--;
        }
        if (jobStatus.isPersisted()) {
            this.mPendingJobWriteUids.put(jobStatus.getUid(), true);
        }
    }

    public void removeJobsOfUnlistedUsers(int[] iArr) {
        this.mJobSet.removeJobsOfUnlistedUsers(iArr);
        this.mCurrentJobSetSize = this.mJobSet.size();
    }

    public void touchJob(JobStatus jobStatus) {
        if (jobStatus.isPersisted()) {
            this.mPendingJobWriteUids.put(jobStatus.getUid(), true);
            maybeWriteStatusToDiskAsync();
        }
    }

    public void clear() {
        this.mJobSet.clear();
        this.mPendingJobWriteUids.put(-1, true);
        this.mCurrentJobSetSize = 0;
        maybeWriteStatusToDiskAsync();
    }

    public void clearForTesting() {
        this.mJobSet.clear();
        this.mPendingJobWriteUids.put(-1, true);
        this.mCurrentJobSetSize = 0;
    }

    public void setUseSplitFiles(boolean z) {
        synchronized (this.mLock) {
            if (this.mUseSplitFiles != z) {
                this.mUseSplitFiles = z;
                migrateJobFilesAsync();
            }
        }
    }

    public void setUseSplitFilesForTesting(boolean z) {
        boolean z2;
        synchronized (this.mLock) {
            z2 = this.mUseSplitFiles != z;
            if (z2) {
                this.mUseSplitFiles = z;
                this.mPendingJobWriteUids.put(-1, true);
            }
        }
        if (z2) {
            synchronized (this.mWriteScheduleLock) {
                this.mSplitFileMigrationNeeded = true;
            }
        }
    }

    public ArraySet getJobsBySourceUid(int i) {
        return this.mJobSet.getJobsBySourceUid(i);
    }

    public void getJobsBySourceUid(int i, Set set) {
        this.mJobSet.getJobsBySourceUid(i, set);
    }

    public ArraySet getJobsByUid(int i) {
        return this.mJobSet.getJobsByUid(i);
    }

    public void getJobsByUid(int i, Set set) {
        this.mJobSet.getJobsByUid(i, set);
    }

    public JobStatus getJobByUidAndJobId(int i, String str, int i2) {
        return this.mJobSet.get(i, str, i2);
    }

    public void forEachJob(Consumer consumer) {
        this.mJobSet.forEachJob((Predicate) null, consumer);
    }

    public void forEachJob(Predicate predicate, Consumer consumer) {
        this.mJobSet.forEachJob(predicate, consumer);
    }

    public void forEachJob(int i, Consumer consumer) {
        this.mJobSet.forEachJob(i, consumer);
    }

    public void forEachJobForSourceUid(int i, Consumer consumer) {
        this.mJobSet.forEachJobForSourceUid(i, consumer);
    }

    public final void maybeUpdateHighWaterMark() {
        int i = this.mScheduledJob30MinHighWaterMark;
        int i2 = this.mCurrentJobSetSize;
        if (i < i2) {
            this.mScheduledJob30MinHighWaterMark = i2;
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

    public final void maybeWriteStatusToDiskAsync() {
        synchronized (this.mWriteScheduleLock) {
            if (!this.mWriteScheduled) {
                if (DEBUG) {
                    Slog.v("JobStore", "Scheduling persist of jobs to disk.");
                }
                this.mIoHandler.postDelayed(this.mWriteRunnable, 2000L);
                this.mWriteScheduled = true;
            }
        }
    }

    public void readJobMapFromDisk(JobSet jobSet, boolean z) {
        new ReadJobMapFromDiskRunnable(this, jobSet, z).run();
    }

    public void writeStatusToDiskForTesting() {
        synchronized (this.mWriteScheduleLock) {
            if (this.mWriteScheduled) {
                throw new IllegalStateException("An asynchronous write is already scheduled.");
            }
            this.mWriteScheduled = true;
            this.mWriteRunnable.run();
        }
    }

    public boolean waitForWriteToCompleteForTesting(long j) {
        long uptimeMillis = SystemClock.uptimeMillis();
        long j2 = uptimeMillis + j;
        synchronized (this.mWriteScheduleLock) {
            while (true) {
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
            }
            return true;
        }
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

    public static Pair convertRtcBoundsToElapsed(Pair pair, long j) {
        long millis = JobSchedulerService.sSystemClock.millis();
        return Pair.create(Long.valueOf(((Long) pair.first).longValue() > 0 ? Math.max(((Long) pair.first).longValue() - millis, 0L) + j : 0L), Long.valueOf(((Long) pair.second).longValue() < Long.MAX_VALUE ? j + Math.max(((Long) pair.second).longValue() - millis, 0L) : Long.MAX_VALUE));
    }

    public static boolean isSyncJob(JobStatus jobStatus) {
        return SyncJobService.class.getName().equals(jobStatus.getServiceComponent().getClassName());
    }

    public final class ReadJobMapFromDiskRunnable implements Runnable {
        public final JobSet jobSet;
        public final CountDownLatch mCompletionLatch;
        public final boolean rtcGood;

        public ReadJobMapFromDiskRunnable(JobStore jobStore, JobSet jobSet, boolean z) {
            this(jobSet, z, null);
        }

        public ReadJobMapFromDiskRunnable(JobSet jobSet, boolean z, CountDownLatch countDownLatch) {
            this.jobSet = jobSet;
            this.rtcGood = z;
            this.mCompletionLatch = countDownLatch;
        }

        /* JADX WARN: Code restructure failed: missing block: B:68:0x015d, code lost:
        
            if (r14.getName().startsWith(com.android.server.job.JobStore.JOB_FILE_SPLIT_PREFIX) != false) goto L80;
         */
        /* JADX WARN: Removed duplicated region for block: B:59:0x0144 A[Catch: all -> 0x01ee, TryCatch #4 {, blocks: (B:14:0x003f, B:16:0x0048, B:18:0x0050, B:53:0x00df, B:80:0x00dc, B:94:0x00c5, B:57:0x013b, B:59:0x0144, B:64:0x0163, B:67:0x0152, B:72:0x00ed, B:56:0x00fe, B:70:0x0121, B:105:0x016b, B:107:0x0177, B:108:0x018f), top: B:13:0x003f }] */
        /* JADX WARN: Removed duplicated region for block: B:67:0x0152 A[Catch: all -> 0x01ee, TryCatch #4 {, blocks: (B:14:0x003f, B:16:0x0048, B:18:0x0050, B:53:0x00df, B:80:0x00dc, B:94:0x00c5, B:57:0x013b, B:59:0x0144, B:64:0x0163, B:67:0x0152, B:72:0x00ed, B:56:0x00fe, B:70:0x0121, B:105:0x016b, B:107:0x0177, B:108:0x018f), top: B:13:0x003f }] */
        /* JADX WARN: Removed duplicated region for block: B:74:0x00d6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            int i;
            int i2;
            boolean z;
            File[] fileArr;
            int i3;
            int i4;
            boolean z2;
            if (!JobStore.this.mJobFileDirectory.isDirectory()) {
                Slog.wtf("JobStore", "jobs directory isn't a directory O.O");
                JobStore.this.mJobFileDirectory.mkdirs();
                return;
            }
            try {
                File[] listFiles = JobStore.this.mJobFileDirectory.listFiles();
                if (listFiles == null) {
                    Slog.wtfStack("JobStore", "Couldn't get job file list");
                    return;
                }
                long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
                synchronized (JobStore.this.mLock) {
                    int length = listFiles.length;
                    int i5 = 0;
                    i = 0;
                    int i6 = 0;
                    int i7 = 0;
                    boolean z3 = false;
                    i2 = 0;
                    while (i5 < length) {
                        File file = listFiles[i5];
                        try {
                            FileInputStream openRead = JobStore.this.createJobFile(file).openRead();
                            try {
                                List readJobMapImpl = readJobMapImpl(openRead, this.rtcGood, millis);
                                fileArr = listFiles;
                                if (readJobMapImpl != null) {
                                    int i8 = 0;
                                    while (i8 < readJobMapImpl.size()) {
                                        try {
                                            JobStatus jobStatus = (JobStatus) readJobMapImpl.get(i8);
                                            List list = readJobMapImpl;
                                            i3 = length;
                                            try {
                                                z2 = z3;
                                                try {
                                                    i4 = i5;
                                                    try {
                                                        if (this.jobSet.get(jobStatus.getUid(), jobStatus.getNamespace(), jobStatus.getJobId()) != null) {
                                                            i2++;
                                                        } else {
                                                            jobStatus.prepareLocked();
                                                            jobStatus.enqueueTime = millis;
                                                            this.jobSet.add(jobStatus);
                                                            i++;
                                                            if (jobStatus.getUid() == 1000) {
                                                                i6++;
                                                                if (JobStore.isSyncJob(jobStatus)) {
                                                                    i7++;
                                                                }
                                                            }
                                                        }
                                                        i8++;
                                                        readJobMapImpl = list;
                                                        length = i3;
                                                        z3 = z2;
                                                        i5 = i4;
                                                    } catch (Throwable th) {
                                                        th = th;
                                                        Throwable th2 = th;
                                                        if (openRead != null) {
                                                            try {
                                                                openRead.close();
                                                            } catch (Throwable th3) {
                                                                try {
                                                                    th2.addSuppressed(th3);
                                                                } catch (FileNotFoundException unused) {
                                                                    Slog.e("JobStore", "Could not find jobs file: " + file.getName());
                                                                    if (!JobStore.this.mUseSplitFiles) {
                                                                    }
                                                                    i5 = i4 + 1;
                                                                    listFiles = fileArr;
                                                                    length = i3;
                                                                } catch (Exception e) {
                                                                    e = e;
                                                                    Slog.wtf("JobStore", "Unexpected exception", e);
                                                                    if (!JobStore.this.mUseSplitFiles) {
                                                                    }
                                                                    i5 = i4 + 1;
                                                                    listFiles = fileArr;
                                                                    length = i3;
                                                                }
                                                            }
                                                        }
                                                        throw th2;
                                                    }
                                                } catch (Throwable th4) {
                                                    th = th4;
                                                    i4 = i5;
                                                }
                                            } catch (Throwable th5) {
                                                th = th5;
                                                i4 = i5;
                                                z2 = z3;
                                                Throwable th22 = th;
                                                if (openRead != null) {
                                                }
                                                throw th22;
                                            }
                                        } catch (Throwable th6) {
                                            th = th6;
                                            i3 = length;
                                            i4 = i5;
                                            z2 = z3;
                                            Throwable th222 = th;
                                            if (openRead != null) {
                                            }
                                            throw th222;
                                        }
                                    }
                                }
                                i3 = length;
                                i4 = i5;
                                z2 = z3;
                                if (openRead != null) {
                                    try {
                                        openRead.close();
                                    } catch (IOException | XmlPullParserException e2) {
                                        e = e2;
                                        Slog.wtf("JobStore", "Error in " + file.getName(), e);
                                        if (!JobStore.this.mUseSplitFiles) {
                                        }
                                        i5 = i4 + 1;
                                        listFiles = fileArr;
                                        length = i3;
                                    }
                                }
                            } catch (Throwable th7) {
                                th = th7;
                                fileArr = listFiles;
                            }
                        } catch (FileNotFoundException unused2) {
                            fileArr = listFiles;
                            i3 = length;
                            i4 = i5;
                            z2 = z3;
                        } catch (IOException | XmlPullParserException e3) {
                            e = e3;
                            fileArr = listFiles;
                            i3 = length;
                            i4 = i5;
                            z2 = z3;
                        } catch (Exception e4) {
                            e = e4;
                            fileArr = listFiles;
                            i3 = length;
                            i4 = i5;
                            z2 = z3;
                        }
                        if (!JobStore.this.mUseSplitFiles) {
                            if (!file.getName().startsWith(JobStore.JOB_FILE_SPLIT_PREFIX)) {
                                z3 = true;
                            }
                            z3 = z2;
                        }
                        i5 = i4 + 1;
                        listFiles = fileArr;
                        length = i3;
                    }
                    z = z3;
                    if (JobStore.this.mPersistInfo.countAllJobsLoaded < 0) {
                        JobStore.this.mPersistInfo.countAllJobsLoaded = i;
                        JobStore.this.mPersistInfo.countSystemServerJobsLoaded = i6;
                        JobStore.this.mPersistInfo.countSystemSyncManagerJobsLoaded = i7;
                    }
                }
                Slog.i("JobStore", "Read " + i + " jobs");
                if (z) {
                    JobStore.this.migrateJobFilesAsync();
                }
                if (i2 > 0) {
                    Slog.wtf("JobStore", "Encountered " + i2 + " duplicate persisted jobs");
                }
                JobStore.this.mCurrentJobSetSize = i;
                JobStore jobStore = JobStore.this;
                jobStore.mScheduledJob30MinHighWaterMark = jobStore.mCurrentJobSetSize;
                JobStore.this.mScheduledJobHighWaterMarkLoggingRunnable.run();
                CountDownLatch countDownLatch = this.mCompletionLatch;
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            } catch (SecurityException e5) {
                Slog.wtf("JobStore", "Not allowed to read job file directory", e5);
            }
        }

        public final List readJobMapImpl(InputStream inputStream, boolean z, long j) {
            TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(inputStream);
            int eventType = resolvePullParser.getEventType();
            while (eventType != 2 && eventType != 1) {
                eventType = resolvePullParser.next();
                Slog.d("JobStore", "Start tag: " + resolvePullParser.getName());
            }
            if (eventType == 1) {
                if (JobStore.DEBUG) {
                    Slog.d("JobStore", "No persisted jobs.");
                }
                return null;
            }
            if ("job-info".equals(resolvePullParser.getName())) {
                ArrayList arrayList = new ArrayList();
                int attributeInt = resolvePullParser.getAttributeInt((String) null, "version");
                if (attributeInt > 1 || attributeInt < 0) {
                    Slog.d("JobStore", "Invalid version number, aborting jobs file read.");
                } else {
                    int next = resolvePullParser.next();
                    do {
                        if (next == 2 && "job".equals(resolvePullParser.getName())) {
                            JobStatus restoreJobFromXml = restoreJobFromXml(z, resolvePullParser, attributeInt, j);
                            if (restoreJobFromXml != null) {
                                if (JobStore.DEBUG) {
                                    Slog.d("JobStore", "Read out " + restoreJobFromXml);
                                }
                                arrayList.add(restoreJobFromXml);
                            } else {
                                Slog.d("JobStore", "Error reading job from file.");
                            }
                        }
                        next = resolvePullParser.next();
                    } while (next != 1);
                    return arrayList;
                }
            }
            return null;
        }

        public final JobStatus restoreJobFromXml(boolean z, TypedXmlPullParser typedXmlPullParser, int i, long j) {
            int next;
            int i2;
            int next2;
            Pair pair;
            long j2;
            int next3;
            try {
                JobInfo.Builder buildBuilderFromXml = buildBuilderFromXml(typedXmlPullParser);
                boolean z2 = true;
                buildBuilderFromXml.setPersisted(true);
                int parseInt = Integer.parseInt(typedXmlPullParser.getAttributeValue((String) null, "uid"));
                if (i == 0) {
                    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "priority");
                    if (attributeValue != null) {
                        buildBuilderFromXml.setBias(Integer.parseInt(attributeValue));
                    }
                } else if (i >= 1) {
                    String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "bias");
                    if (attributeValue2 != null) {
                        buildBuilderFromXml.setBias(Integer.parseInt(attributeValue2));
                    }
                    String attributeValue3 = typedXmlPullParser.getAttributeValue((String) null, "priority");
                    if (attributeValue3 != null) {
                        buildBuilderFromXml.setPriority(Integer.parseInt(attributeValue3));
                    }
                }
                String attributeValue4 = typedXmlPullParser.getAttributeValue((String) null, "flags");
                if (attributeValue4 != null) {
                    buildBuilderFromXml.setFlags(Integer.parseInt(attributeValue4));
                }
                String attributeValue5 = typedXmlPullParser.getAttributeValue((String) null, "internalFlags");
                int parseInt2 = attributeValue5 != null ? Integer.parseInt(attributeValue5) : 0;
                String attributeValue6 = typedXmlPullParser.getAttributeValue((String) null, "sourceUserId");
                int parseInt3 = attributeValue6 == null ? -1 : Integer.parseInt(attributeValue6);
                String attributeValue7 = typedXmlPullParser.getAttributeValue((String) null, "lastSuccessfulRunTime");
                long parseLong = attributeValue7 == null ? 0L : Long.parseLong(attributeValue7);
                String attributeValue8 = typedXmlPullParser.getAttributeValue((String) null, "lastFailedRunTime");
                long parseLong2 = attributeValue8 == null ? 0L : Long.parseLong(attributeValue8);
                long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "cumulativeExecutionTime", 0L);
                String attributeValue9 = typedXmlPullParser.getAttributeValue((String) null, "sourcePackageName");
                String attributeValue10 = typedXmlPullParser.getAttributeValue((String) null, "namespace");
                String attributeValue11 = typedXmlPullParser.getAttributeValue((String) null, "sourceTag");
                do {
                    next = typedXmlPullParser.next();
                    i2 = 4;
                } while (next == 4);
                int i3 = 2;
                if (next == 2 && "constraints".equals(typedXmlPullParser.getName())) {
                    try {
                        buildConstraintsFromXml(buildBuilderFromXml, typedXmlPullParser);
                        typedXmlPullParser.next();
                        while (true) {
                            next2 = typedXmlPullParser.next();
                            if (next2 != i2) {
                                break;
                            }
                            i3 = i3;
                            z2 = z2;
                            i2 = i2;
                        }
                        if (next2 != i3) {
                            return null;
                        }
                        Pair buildRtcExecutionTimesFromXml = buildRtcExecutionTimesFromXml(typedXmlPullParser);
                        Pair convertRtcBoundsToElapsed = JobStore.convertRtcBoundsToElapsed(buildRtcExecutionTimesFromXml, j);
                        if ("periodic".equals(typedXmlPullParser.getName())) {
                            try {
                                long parseLong3 = Long.parseLong(typedXmlPullParser.getAttributeValue((String) null, "period"));
                                String attributeValue12 = typedXmlPullParser.getAttributeValue((String) null, "flex");
                                if (attributeValue12 != null) {
                                    pair = buildRtcExecutionTimesFromXml;
                                    j2 = Long.valueOf(attributeValue12).longValue();
                                } else {
                                    pair = buildRtcExecutionTimesFromXml;
                                    j2 = parseLong3;
                                }
                                buildBuilderFromXml.setPeriodic(parseLong3, j2);
                                if (((Long) convertRtcBoundsToElapsed.second).longValue() > j + parseLong3 + j2) {
                                    long j3 = j + j2 + parseLong3;
                                    long j4 = j3 - j2;
                                    Slog.w("JobStore", String.format("Periodic job for uid='%d' persisted run-time is too big [%s, %s]. Clamping to [%s,%s]", Integer.valueOf(parseInt), DateUtils.formatElapsedTime(((Long) convertRtcBoundsToElapsed.first).longValue() / 1000), DateUtils.formatElapsedTime(((Long) convertRtcBoundsToElapsed.second).longValue() / 1000), DateUtils.formatElapsedTime(j4 / 1000), DateUtils.formatElapsedTime(j3 / 1000)));
                                    convertRtcBoundsToElapsed = Pair.create(Long.valueOf(j4), Long.valueOf(j3));
                                }
                            } catch (NumberFormatException unused) {
                                Slog.d("JobStore", "Error reading periodic execution criteria, skipping.");
                                return null;
                            }
                        } else {
                            pair = buildRtcExecutionTimesFromXml;
                            if ("one-off".equals(typedXmlPullParser.getName())) {
                                try {
                                    if (((Long) convertRtcBoundsToElapsed.first).longValue() != 0) {
                                        buildBuilderFromXml.setMinimumLatency(((Long) convertRtcBoundsToElapsed.first).longValue() - j);
                                    }
                                    if (((Long) convertRtcBoundsToElapsed.second).longValue() != Long.MAX_VALUE) {
                                        buildBuilderFromXml.setOverrideDeadline(((Long) convertRtcBoundsToElapsed.second).longValue() - j);
                                    }
                                } catch (NumberFormatException unused2) {
                                    Slog.d("JobStore", "Error reading job execution criteria, skipping.");
                                    return null;
                                }
                            } else {
                                if (JobStore.DEBUG) {
                                    Slog.d("JobStore", "Invalid parameter tag, skipping - " + typedXmlPullParser.getName());
                                }
                                return null;
                            }
                        }
                        maybeBuildBackoffPolicyFromXml(buildBuilderFromXml, typedXmlPullParser);
                        typedXmlPullParser.nextTag();
                        do {
                            next3 = typedXmlPullParser.next();
                        } while (next3 == 4);
                        if (next3 != 2 || !"extras".equals(typedXmlPullParser.getName())) {
                            if (JobStore.DEBUG) {
                                Slog.d("JobStore", "Error reading extras, skipping.");
                            }
                            return null;
                        }
                        try {
                            PersistableBundle restoreFromXml = PersistableBundle.restoreFromXml(typedXmlPullParser);
                            buildBuilderFromXml.setExtras(restoreFromXml);
                            List readJobWorkItemsFromXml = (typedXmlPullParser.nextTag() == 2 && "job-work-item".equals(typedXmlPullParser.getName())) ? readJobWorkItemsFromXml(typedXmlPullParser) : null;
                            try {
                                JobInfo build = buildBuilderFromXml.build(false, false);
                                if ("android".equals(attributeValue9) && restoreFromXml != null && restoreFromXml.getBoolean("SyncManagerJob", false)) {
                                    attributeValue9 = restoreFromXml.getString("owningPackage", attributeValue9);
                                    if (JobStore.DEBUG) {
                                        Slog.i("JobStore", "Fixing up sync job source package name from 'android' to '" + attributeValue9 + "'");
                                    }
                                }
                                JobStatus jobStatus = new JobStatus(build, parseInt, attributeValue9, parseInt3, JobSchedulerService.standbyBucketForPackage(attributeValue9, parseInt3, j), attributeValue10, attributeValue11, ((Long) convertRtcBoundsToElapsed.first).longValue(), ((Long) convertRtcBoundsToElapsed.second).longValue(), parseLong, parseLong2, attributeLong, z ? null : pair, parseInt2, 0);
                                if (readJobWorkItemsFromXml != null) {
                                    for (int i4 = 0; i4 < readJobWorkItemsFromXml.size(); i4++) {
                                        jobStatus.enqueueWorkLocked((JobWorkItem) readJobWorkItemsFromXml.get(i4));
                                    }
                                }
                                return jobStatus;
                            } catch (Exception e) {
                                Slog.w("JobStore", "Unable to build job from XML, ignoring: " + buildBuilderFromXml.summarize(), e);
                                return null;
                            }
                        } catch (IllegalArgumentException e2) {
                            Slog.e("JobStore", "Persisted extras contained invalid data", e2);
                            return null;
                        }
                    } catch (IOException e3) {
                        Slog.d("JobStore", "Error I/O Exception.", e3);
                        return null;
                    } catch (NumberFormatException unused3) {
                        Slog.d("JobStore", "Error reading constraints, skipping.");
                    } catch (IllegalArgumentException e4) {
                        Slog.e("JobStore", "Constraints contained invalid data", e4);
                        return null;
                    } catch (XmlPullParserException e5) {
                        Slog.d("JobStore", "Error Parser Exception.", e5);
                        return null;
                    }
                }
                return null;
            } catch (NumberFormatException unused4) {
                Slog.e("JobStore", "Error parsing job's required fields, skipping");
                return null;
            }
        }

        public final JobInfo.Builder buildBuilderFromXml(TypedXmlPullParser typedXmlPullParser) {
            return new JobInfo.Builder(typedXmlPullParser.getAttributeInt((String) null, "jobid"), new ComponentName(typedXmlPullParser.getAttributeValue((String) null, "package"), typedXmlPullParser.getAttributeValue((String) null, "class")));
        }

        public final void buildConstraintsFromXml(JobInfo.Builder builder, TypedXmlPullParser typedXmlPullParser) {
            String attributeValue;
            String attributeValue2;
            String attributeValue3;
            String attributeValue4 = typedXmlPullParser.getAttributeValue((String) null, "net-capabilities-csv");
            String attributeValue5 = typedXmlPullParser.getAttributeValue((String) null, "net-forbidden-capabilities-csv");
            String attributeValue6 = typedXmlPullParser.getAttributeValue((String) null, "net-transport-types-csv");
            if (attributeValue4 == null || attributeValue6 == null) {
                attributeValue = typedXmlPullParser.getAttributeValue((String) null, "net-capabilities");
                attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "net-unwanted-capabilities");
                attributeValue3 = typedXmlPullParser.getAttributeValue((String) null, "net-transport-types");
            } else {
                attributeValue = null;
                attributeValue2 = null;
                attributeValue3 = null;
            }
            if (attributeValue4 != null && attributeValue6 != null) {
                NetworkRequest.Builder clearCapabilities = new NetworkRequest.Builder().clearCapabilities();
                for (int i : JobStore.stringToIntArray(attributeValue4)) {
                    clearCapabilities.addCapability(i);
                }
                for (int i2 : JobStore.stringToIntArray(attributeValue5)) {
                    clearCapabilities.addForbiddenCapability(i2);
                }
                int[] stringToIntArray = JobStore.stringToIntArray(attributeValue6);
                for (int i3 : stringToIntArray) {
                    clearCapabilities.addTransportType(i3);
                }
                builder.setRequiredNetwork(clearCapabilities.build()).setEstimatedNetworkBytes(typedXmlPullParser.getAttributeLong((String) null, "estimated-download-bytes", -1L), typedXmlPullParser.getAttributeLong((String) null, "estimated-upload-bytes", -1L)).setMinimumNetworkChunkBytes(typedXmlPullParser.getAttributeLong((String) null, "minimum-network-chunk-bytes", -1L));
            } else if (attributeValue != null && attributeValue3 != null) {
                NetworkRequest.Builder clearCapabilities2 = new NetworkRequest.Builder().clearCapabilities();
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
                for (int i6 : BitUtils.unpackBits(Long.parseLong(attributeValue3))) {
                    if (i6 <= 7) {
                        clearCapabilities2.addTransportType(i6);
                    }
                }
                builder.setRequiredNetwork(clearCapabilities2.build());
            } else {
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
            builder.setPrefersBatteryNotLow(typedXmlPullParser.getAttributeBoolean((String) null, "prefer-battery-not-low", false));
            builder.setPrefersCharging(typedXmlPullParser.getAttributeBoolean((String) null, "prefer-charging", false));
            builder.setPrefersDeviceIdle(typedXmlPullParser.getAttributeBoolean((String) null, "prefer-idle", false));
        }

        public final void maybeBuildBackoffPolicyFromXml(JobInfo.Builder builder, XmlPullParser xmlPullParser) {
            String attributeValue = xmlPullParser.getAttributeValue(null, "initial-backoff");
            if (attributeValue != null) {
                builder.setBackoffCriteria(Long.parseLong(attributeValue), Integer.parseInt(xmlPullParser.getAttributeValue(null, "backoff-policy")));
            }
        }

        public final Pair buildRtcExecutionTimesFromXml(TypedXmlPullParser typedXmlPullParser) {
            return Pair.create(Long.valueOf(typedXmlPullParser.getAttributeLong((String) null, "delay", 0L)), Long.valueOf(typedXmlPullParser.getAttributeLong((String) null, "deadline", Long.MAX_VALUE)));
        }

        public final List readJobWorkItemsFromXml(TypedXmlPullParser typedXmlPullParser) {
            ArrayList arrayList = new ArrayList();
            int eventType = typedXmlPullParser.getEventType();
            while (eventType != 1 && "job-work-item".equals(typedXmlPullParser.getName())) {
                try {
                    JobWorkItem readJobWorkItemFromXml = readJobWorkItemFromXml(typedXmlPullParser);
                    if (readJobWorkItemFromXml != null) {
                        arrayList.add(readJobWorkItemFromXml);
                    }
                } catch (Exception e) {
                    Slog.e("JobStore", "Problem with persisted JobWorkItem", e);
                }
                eventType = typedXmlPullParser.next();
            }
            return arrayList;
        }

        public final JobWorkItem readJobWorkItemFromXml(TypedXmlPullParser typedXmlPullParser) {
            JobWorkItem.Builder builder = new JobWorkItem.Builder();
            builder.setDeliveryCount(typedXmlPullParser.getAttributeInt((String) null, "delivery-count")).setEstimatedNetworkBytes(typedXmlPullParser.getAttributeLong((String) null, "estimated-download-bytes", -1L), typedXmlPullParser.getAttributeLong((String) null, "estimated-upload-bytes", -1L)).setMinimumNetworkChunkBytes(typedXmlPullParser.getAttributeLong((String) null, "minimum-network-chunk-bytes", -1L));
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
    }

    public final class JobSet {
        final SparseArray mJobs = new SparseArray();
        final SparseArray mJobsPerSourceUid = new SparseArray();

        public ArraySet getJobsByUid(int i) {
            ArraySet arraySet = new ArraySet();
            getJobsByUid(i, arraySet);
            return arraySet;
        }

        public void getJobsByUid(int i, Set set) {
            ArraySet arraySet = (ArraySet) this.mJobs.get(i);
            if (arraySet != null) {
                set.addAll(arraySet);
            }
        }

        public ArraySet getJobsBySourceUid(int i) {
            ArraySet arraySet = new ArraySet();
            getJobsBySourceUid(i, arraySet);
            return arraySet;
        }

        public void getJobsBySourceUid(int i, Set set) {
            ArraySet arraySet = (ArraySet) this.mJobsPerSourceUid.get(i);
            if (arraySet != null) {
                set.addAll(arraySet);
            }
        }

        public boolean add(JobStatus jobStatus) {
            int uid = jobStatus.getUid();
            int sourceUid = jobStatus.getSourceUid();
            ArraySet arraySet = (ArraySet) this.mJobs.get(uid);
            if (arraySet == null) {
                arraySet = new ArraySet();
                this.mJobs.put(uid, arraySet);
            }
            ArraySet arraySet2 = (ArraySet) this.mJobsPerSourceUid.get(sourceUid);
            if (arraySet2 == null) {
                arraySet2 = new ArraySet();
                this.mJobsPerSourceUid.put(sourceUid, arraySet2);
            }
            boolean add = arraySet.add(jobStatus);
            boolean add2 = arraySet2.add(jobStatus);
            if (add != add2) {
                Slog.wtf("JobStore", "mJobs and mJobsPerSourceUid mismatch; caller= " + add + " source= " + add2);
            }
            return add || add2;
        }

        public boolean remove(JobStatus jobStatus) {
            int uid = jobStatus.getUid();
            ArraySet arraySet = (ArraySet) this.mJobs.get(uid);
            int sourceUid = jobStatus.getSourceUid();
            ArraySet arraySet2 = (ArraySet) this.mJobsPerSourceUid.get(sourceUid);
            boolean z = arraySet != null && arraySet.remove(jobStatus);
            boolean z2 = arraySet2 != null && arraySet2.remove(jobStatus);
            if (z != z2) {
                Slog.wtf("JobStore", "Job presence mismatch; caller=" + z + " source=" + z2);
            }
            if (!z && !z2) {
                return false;
            }
            if (arraySet != null && arraySet.size() == 0) {
                this.mJobs.remove(uid);
            }
            if (arraySet2 != null && arraySet2.size() == 0) {
                this.mJobsPerSourceUid.remove(sourceUid);
            }
            return true;
        }

        public void removeJobsOfUnlistedUsers(final int[] iArr) {
            removeAll(new Predicate() { // from class: com.android.server.job.JobStore$JobSet$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$removeJobsOfUnlistedUsers$0;
                    lambda$removeJobsOfUnlistedUsers$0 = JobStore.JobSet.lambda$removeJobsOfUnlistedUsers$0(iArr, (JobStatus) obj);
                    return lambda$removeJobsOfUnlistedUsers$0;
                }
            }.or(new Predicate() { // from class: com.android.server.job.JobStore$JobSet$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$removeJobsOfUnlistedUsers$1;
                    lambda$removeJobsOfUnlistedUsers$1 = JobStore.JobSet.lambda$removeJobsOfUnlistedUsers$1(iArr, (JobStatus) obj);
                    return lambda$removeJobsOfUnlistedUsers$1;
                }
            }));
        }

        public static /* synthetic */ boolean lambda$removeJobsOfUnlistedUsers$0(int[] iArr, JobStatus jobStatus) {
            return !ArrayUtils.contains(iArr, jobStatus.getSourceUserId());
        }

        public static /* synthetic */ boolean lambda$removeJobsOfUnlistedUsers$1(int[] iArr, JobStatus jobStatus) {
            return !ArrayUtils.contains(iArr, jobStatus.getUserId());
        }

        public final void removeAll(Predicate predicate) {
            for (int size = this.mJobs.size() - 1; size >= 0; size--) {
                ArraySet arraySet = (ArraySet) this.mJobs.valueAt(size);
                arraySet.removeIf(predicate);
                if (arraySet.size() == 0) {
                    this.mJobs.removeAt(size);
                }
            }
            for (int size2 = this.mJobsPerSourceUid.size() - 1; size2 >= 0; size2--) {
                ArraySet arraySet2 = (ArraySet) this.mJobsPerSourceUid.valueAt(size2);
                arraySet2.removeIf(predicate);
                if (arraySet2.size() == 0) {
                    this.mJobsPerSourceUid.removeAt(size2);
                }
            }
        }

        public boolean contains(JobStatus jobStatus) {
            ArraySet arraySet = (ArraySet) this.mJobs.get(jobStatus.getUid());
            return arraySet != null && arraySet.contains(jobStatus);
        }

        public JobStatus get(int i, String str, int i2) {
            ArraySet arraySet = (ArraySet) this.mJobs.get(i);
            if (arraySet == null) {
                return null;
            }
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                JobStatus jobStatus = (JobStatus) arraySet.valueAt(size);
                if (jobStatus.getJobId() == i2 && Objects.equals(str, jobStatus.getNamespace())) {
                    return jobStatus;
                }
            }
            return null;
        }

        public List getAllJobs() {
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

        public void clear() {
            this.mJobs.clear();
            this.mJobsPerSourceUid.clear();
        }

        public int size() {
            int i = 0;
            for (int size = this.mJobs.size() - 1; size >= 0; size--) {
                i += ((ArraySet) this.mJobs.valueAt(size)).size();
            }
            return i;
        }

        public int countJobsForUid(int i) {
            ArraySet arraySet = (ArraySet) this.mJobs.get(i);
            int i2 = 0;
            if (arraySet != null) {
                for (int size = arraySet.size() - 1; size >= 0; size--) {
                    JobStatus jobStatus = (JobStatus) arraySet.valueAt(size);
                    if (jobStatus.getUid() == jobStatus.getSourceUid()) {
                        i2++;
                    }
                }
            }
            return i2;
        }

        public void forEachJob(Predicate predicate, Consumer consumer) {
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

        public void forEachJob(int i, Consumer consumer) {
            ArraySet arraySet = (ArraySet) this.mJobs.get(i);
            if (arraySet != null) {
                for (int size = arraySet.size() - 1; size >= 0; size--) {
                    consumer.accept((JobStatus) arraySet.valueAt(size));
                }
            }
        }

        public void forEachJobForSourceUid(int i, Consumer consumer) {
            ArraySet arraySet = (ArraySet) this.mJobsPerSourceUid.get(i);
            if (arraySet != null) {
                for (int size = arraySet.size() - 1; size >= 0; size--) {
                    consumer.accept((JobStatus) arraySet.valueAt(size));
                }
            }
        }
    }
}
