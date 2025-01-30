package com.android.server.job.controllers;

import android.app.job.JobInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.job.JobSchedulerService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class ContentObserverController extends StateController {
  public static final boolean DEBUG;
  public final Handler mHandler;
  public final SparseArray mObservers;
  public final ArraySet mTrackedTasks;

  static {
    DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.ContentObserver", 3);
  }

  public ContentObserverController(JobSchedulerService jobSchedulerService) {
    super(jobSchedulerService);
    this.mTrackedTasks = new ArraySet();
    this.mObservers = new SparseArray();
    this.mHandler = new Handler(AppSchedulingModuleThread.get().getLooper());
  }

  @Override // com.android.server.job.controllers.StateController
  public void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
    JobInstance jobInstance;
    if (jobStatus.hasContentTriggerConstraint()) {
      long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
      if (jobStatus.contentObserverJobInstance == null) {
        jobStatus.contentObserverJobInstance = new JobInstance(jobStatus);
      }
      if (DEBUG) {
        Slog.i("JobScheduler.ContentObserver", "Tracking content-trigger job " + jobStatus);
      }
      this.mTrackedTasks.add(jobStatus);
      jobStatus.setTrackingController(4);
      JobInstance jobInstance2 = jobStatus.contentObserverJobInstance;
      ArraySet arraySet = jobInstance2.mChangedAuthorities;
      boolean z = true;
      boolean z2 = arraySet != null;
      if (jobStatus.changedAuthorities != null) {
        if (arraySet == null) {
          jobInstance2.mChangedAuthorities = new ArraySet();
        }
        Iterator it = jobStatus.changedAuthorities.iterator();
        while (it.hasNext()) {
          jobStatus.contentObserverJobInstance.mChangedAuthorities.add((String) it.next());
        }
        if (jobStatus.changedUris != null) {
          JobInstance jobInstance3 = jobStatus.contentObserverJobInstance;
          if (jobInstance3.mChangedUris == null) {
            jobInstance3.mChangedUris = new ArraySet();
          }
          Iterator it2 = jobStatus.changedUris.iterator();
          while (it2.hasNext()) {
            jobStatus.contentObserverJobInstance.mChangedUris.add((Uri) it2.next());
          }
        }
      } else {
        z = z2;
      }
      jobStatus.changedAuthorities = null;
      jobStatus.changedUris = null;
      jobStatus.setContentTriggerConstraintSatisfied(millis, z);
    }
    if (jobStatus2 == null || (jobInstance = jobStatus2.contentObserverJobInstance) == null) {
      return;
    }
    jobInstance.detachLocked();
    jobStatus2.contentObserverJobInstance = null;
  }

  @Override // com.android.server.job.controllers.StateController
  public void prepareForExecutionLocked(JobStatus jobStatus) {
    JobInstance jobInstance;
    if (!jobStatus.hasContentTriggerConstraint()
        || (jobInstance = jobStatus.contentObserverJobInstance) == null) {
      return;
    }
    jobStatus.changedUris = jobInstance.mChangedUris;
    jobStatus.changedAuthorities = jobInstance.mChangedAuthorities;
    jobInstance.mChangedUris = null;
    jobInstance.mChangedAuthorities = null;
  }

  @Override // com.android.server.job.controllers.StateController
  public void unprepareFromExecutionLocked(JobStatus jobStatus) {
    JobInstance jobInstance;
    if (!jobStatus.hasContentTriggerConstraint()
        || (jobInstance = jobStatus.contentObserverJobInstance) == null) {
      return;
    }
    ArraySet arraySet = jobInstance.mChangedUris;
    if (arraySet == null) {
      jobInstance.mChangedUris = jobStatus.changedUris;
    } else {
      arraySet.addAll(jobStatus.changedUris);
    }
    JobInstance jobInstance2 = jobStatus.contentObserverJobInstance;
    ArraySet arraySet2 = jobInstance2.mChangedAuthorities;
    if (arraySet2 == null) {
      jobInstance2.mChangedAuthorities = jobStatus.changedAuthorities;
    } else {
      arraySet2.addAll(jobStatus.changedAuthorities);
    }
    jobStatus.changedUris = null;
    jobStatus.changedAuthorities = null;
  }

  @Override // com.android.server.job.controllers.StateController
  public void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
    if (jobStatus.clearTrackingController(4)) {
      this.mTrackedTasks.remove(jobStatus);
      JobInstance jobInstance = jobStatus.contentObserverJobInstance;
      if (jobInstance != null) {
        jobInstance.unscheduleLocked();
        if (jobStatus2 != null) {
          JobInstance jobInstance2 = jobStatus.contentObserverJobInstance;
          if (jobInstance2 != null && jobInstance2.mChangedAuthorities != null) {
            if (jobStatus2.contentObserverJobInstance == null) {
              jobStatus2.contentObserverJobInstance = new JobInstance(jobStatus2);
            }
            JobInstance jobInstance3 = jobStatus2.contentObserverJobInstance;
            JobInstance jobInstance4 = jobStatus.contentObserverJobInstance;
            jobInstance3.mChangedAuthorities = jobInstance4.mChangedAuthorities;
            jobInstance3.mChangedUris = jobInstance4.mChangedUris;
            jobInstance4.mChangedAuthorities = null;
            jobInstance4.mChangedUris = null;
          }
        } else {
          jobStatus.contentObserverJobInstance.detachLocked();
          jobStatus.contentObserverJobInstance = null;
        }
      }
      if (DEBUG) {
        Slog.i("JobScheduler.ContentObserver", "No longer tracking job " + jobStatus);
      }
    }
  }

  @Override // com.android.server.job.controllers.StateController
  public void rescheduleForFailureLocked(JobStatus jobStatus, JobStatus jobStatus2) {
    if (jobStatus2.hasContentTriggerConstraint() && jobStatus.hasContentTriggerConstraint()) {
      jobStatus.changedAuthorities = jobStatus2.changedAuthorities;
      jobStatus.changedUris = jobStatus2.changedUris;
    }
  }

  public final class ObserverInstance extends ContentObserver {
    public final ArraySet mJobs;
    public final JobInfo.TriggerContentUri mUri;
    public final int mUserId;

    public ObserverInstance(Handler handler, JobInfo.TriggerContentUri triggerContentUri, int i) {
      super(handler);
      this.mJobs = new ArraySet();
      this.mUri = triggerContentUri;
      this.mUserId = i;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
      if (ContentObserverController.DEBUG) {
        Slog.i(
            "JobScheduler.ContentObserver",
            "onChange(self="
                + z
                + ") for "
                + uri
                + " when mUri="
                + this.mUri
                + " mUserId="
                + this.mUserId);
      }
      synchronized (ContentObserverController.this.mLock) {
        int size = this.mJobs.size();
        for (int i = 0; i < size; i++) {
          JobInstance jobInstance = (JobInstance) this.mJobs.valueAt(i);
          if (jobInstance.mChangedUris == null) {
            jobInstance.mChangedUris = new ArraySet();
          }
          if (jobInstance.mChangedUris.size() < 50) {
            jobInstance.mChangedUris.add(uri);
          }
          if (jobInstance.mChangedAuthorities == null) {
            jobInstance.mChangedAuthorities = new ArraySet();
          }
          jobInstance.mChangedAuthorities.add(uri.getAuthority());
          jobInstance.scheduleLocked();
        }
      }
    }
  }

  public final class TriggerRunnable implements Runnable {
    public final JobInstance mInstance;

    public TriggerRunnable(JobInstance jobInstance) {
      this.mInstance = jobInstance;
    }

    @Override // java.lang.Runnable
    public void run() {
      this.mInstance.trigger();
    }
  }

  public final class JobInstance {
    public ArraySet mChangedAuthorities;
    public ArraySet mChangedUris;
    public final JobStatus mJobStatus;
    public boolean mTriggerPending;
    public final ArrayList mMyObservers = new ArrayList();
    public final Runnable mExecuteRunner = new TriggerRunnable(this);
    public final Runnable mTimeoutRunner = new TriggerRunnable(this);

    public JobInstance(JobStatus jobStatus) {
      this.mJobStatus = jobStatus;
      JobInfo.TriggerContentUri[] triggerContentUris = jobStatus.getJob().getTriggerContentUris();
      int sourceUserId = jobStatus.getSourceUserId();
      ArrayMap arrayMap = (ArrayMap) ContentObserverController.this.mObservers.get(sourceUserId);
      if (arrayMap == null) {
        arrayMap = new ArrayMap();
        ContentObserverController.this.mObservers.put(sourceUserId, arrayMap);
      }
      if (triggerContentUris != null) {
        for (JobInfo.TriggerContentUri triggerContentUri : triggerContentUris) {
          ObserverInstance observerInstance = (ObserverInstance) arrayMap.get(triggerContentUri);
          if (observerInstance == null) {
            observerInstance =
                ContentObserverController.this
                .new ObserverInstance(
                    ContentObserverController.this.mHandler,
                    triggerContentUri,
                    jobStatus.getSourceUserId());
            arrayMap.put(triggerContentUri, observerInstance);
            boolean z = (triggerContentUri.getFlags() & 1) != 0;
            if (ContentObserverController.DEBUG) {
              Slog.v(
                  "JobScheduler.ContentObserver",
                  "New observer "
                      + observerInstance
                      + " for "
                      + triggerContentUri.getUri()
                      + " andDescendants="
                      + z
                      + " sourceUserId="
                      + sourceUserId);
            }
            ContentObserverController.this
                .mContext
                .getContentResolver()
                .registerContentObserver(
                    triggerContentUri.getUri(), z, observerInstance, sourceUserId);
          } else if (ContentObserverController.DEBUG) {
            Slog.v(
                "JobScheduler.ContentObserver",
                "Reusing existing observer "
                    + observerInstance
                    + " for "
                    + triggerContentUri.getUri()
                    + " andDescendants="
                    + ((triggerContentUri.getFlags() & 1) != 0));
          }
          observerInstance.mJobs.add(this);
          this.mMyObservers.add(observerInstance);
        }
      }
    }

    public void trigger() {
      boolean z;
      synchronized (ContentObserverController.this.mLock) {
        if (this.mTriggerPending) {
          z =
              this.mJobStatus.setContentTriggerConstraintSatisfied(
                  JobSchedulerService.sElapsedRealtimeClock.millis(), true);
          unscheduleLocked();
        } else {
          z = false;
        }
      }
      if (z) {
        ArraySet arraySet = new ArraySet();
        arraySet.add(this.mJobStatus);
        ContentObserverController.this.mStateChangedListener.onControllerStateChanged(arraySet);
      }
    }

    public void scheduleLocked() {
      if (!this.mTriggerPending) {
        this.mTriggerPending = true;
        ContentObserverController.this.mHandler.postDelayed(
            this.mTimeoutRunner, this.mJobStatus.getTriggerContentMaxDelay());
      }
      ContentObserverController.this.mHandler.removeCallbacks(this.mExecuteRunner);
      if (this.mChangedUris.size() >= 40) {
        ContentObserverController.this.mHandler.post(this.mExecuteRunner);
      } else {
        ContentObserverController.this.mHandler.postDelayed(
            this.mExecuteRunner, this.mJobStatus.getTriggerContentUpdateDelay());
      }
    }

    public void unscheduleLocked() {
      if (this.mTriggerPending) {
        ContentObserverController.this.mHandler.removeCallbacks(this.mExecuteRunner);
        ContentObserverController.this.mHandler.removeCallbacks(this.mTimeoutRunner);
        this.mTriggerPending = false;
      }
    }

    public void detachLocked() {
      int size = this.mMyObservers.size();
      for (int i = 0; i < size; i++) {
        ObserverInstance observerInstance = (ObserverInstance) this.mMyObservers.get(i);
        observerInstance.mJobs.remove(this);
        if (observerInstance.mJobs.size() == 0) {
          if (ContentObserverController.DEBUG) {
            Slog.i(
                "JobScheduler.ContentObserver",
                "Unregistering observer "
                    + observerInstance
                    + " for "
                    + observerInstance.mUri.getUri());
          }
          ContentObserverController.this
              .mContext
              .getContentResolver()
              .unregisterContentObserver(observerInstance);
          ArrayMap arrayMap =
              (ArrayMap) ContentObserverController.this.mObservers.get(observerInstance.mUserId);
          if (arrayMap != null) {
            arrayMap.remove(observerInstance.mUri);
          }
        }
      }
    }
  }

  @Override // com.android.server.job.controllers.StateController
  public void dumpControllerStateLocked(
      IndentingPrintWriter indentingPrintWriter, Predicate predicate) {
    boolean z;
    int i;
    int i2;
    for (int i3 = 0; i3 < this.mTrackedTasks.size(); i3++) {
      JobStatus jobStatus = (JobStatus) this.mTrackedTasks.valueAt(i3);
      if (predicate.test(jobStatus)) {
        indentingPrintWriter.print("#");
        jobStatus.printUniqueId(indentingPrintWriter);
        indentingPrintWriter.print(" from ");
        UserHandle.formatUid(indentingPrintWriter, jobStatus.getSourceUid());
        indentingPrintWriter.println();
      }
    }
    indentingPrintWriter.println();
    int size = this.mObservers.size();
    if (size > 0) {
      indentingPrintWriter.println("Observers:");
      indentingPrintWriter.increaseIndent();
      for (int i4 = 0; i4 < size; i4++) {
        ArrayMap arrayMap = (ArrayMap) this.mObservers.get(this.mObservers.keyAt(i4));
        int size2 = arrayMap.size();
        int i5 = 0;
        while (i5 < size2) {
          ObserverInstance observerInstance = (ObserverInstance) arrayMap.valueAt(i5);
          int size3 = observerInstance.mJobs.size();
          int i6 = 0;
          while (true) {
            if (i6 >= size3) {
              z = false;
              break;
            } else {
              if (predicate.test(((JobInstance) observerInstance.mJobs.valueAt(i6)).mJobStatus)) {
                z = true;
                break;
              }
              i6++;
            }
          }
          if (z) {
            JobInfo.TriggerContentUri triggerContentUri =
                (JobInfo.TriggerContentUri) arrayMap.keyAt(i5);
            indentingPrintWriter.print(triggerContentUri.getUri());
            indentingPrintWriter.print(" 0x");
            indentingPrintWriter.print(Integer.toHexString(triggerContentUri.getFlags()));
            indentingPrintWriter.print(" (");
            indentingPrintWriter.print(System.identityHashCode(observerInstance));
            indentingPrintWriter.println("):");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Jobs:");
            indentingPrintWriter.increaseIndent();
            int i7 = 0;
            while (i7 < size3) {
              JobInstance jobInstance = (JobInstance) observerInstance.mJobs.valueAt(i7);
              indentingPrintWriter.print("#");
              jobInstance.mJobStatus.printUniqueId(indentingPrintWriter);
              indentingPrintWriter.print(" from ");
              UserHandle.formatUid(indentingPrintWriter, jobInstance.mJobStatus.getSourceUid());
              if (jobInstance.mChangedAuthorities != null) {
                indentingPrintWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                indentingPrintWriter.increaseIndent();
                if (jobInstance.mTriggerPending) {
                  indentingPrintWriter.print("Trigger pending: update=");
                  i2 = size;
                  TimeUtils.formatDuration(
                      jobInstance.mJobStatus.getTriggerContentUpdateDelay(), indentingPrintWriter);
                  indentingPrintWriter.print(", max=");
                  TimeUtils.formatDuration(
                      jobInstance.mJobStatus.getTriggerContentMaxDelay(), indentingPrintWriter);
                  indentingPrintWriter.println();
                } else {
                  i2 = size;
                }
                indentingPrintWriter.println("Changed Authorities:");
                for (int i8 = 0; i8 < jobInstance.mChangedAuthorities.size(); i8++) {
                  indentingPrintWriter.println(
                      (String) jobInstance.mChangedAuthorities.valueAt(i8));
                }
                if (jobInstance.mChangedUris != null) {
                  indentingPrintWriter.println("          Changed URIs:");
                  for (int i9 = 0; i9 < jobInstance.mChangedUris.size(); i9++) {
                    indentingPrintWriter.println(jobInstance.mChangedUris.valueAt(i9));
                  }
                }
                indentingPrintWriter.decreaseIndent();
              } else {
                i2 = size;
                indentingPrintWriter.println();
              }
              i7++;
              size = i2;
            }
            i = size;
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
          } else {
            i = size;
          }
          i5++;
          size = i;
        }
      }
      indentingPrintWriter.decreaseIndent();
    }
  }

  @Override // com.android.server.job.controllers.StateController
  public void dumpControllerStateLocked(
      ProtoOutputStream protoOutputStream, long j, Predicate predicate) {
    boolean z;
    long j2;
    long j3;
    ArrayMap arrayMap;
    int i;
    ObserverInstance observerInstance;
    int i2;
    ContentObserverController contentObserverController = this;
    Predicate predicate2 = predicate;
    long start = protoOutputStream.start(j);
    long start2 = protoOutputStream.start(1146756268036L);
    for (int i3 = 0; i3 < contentObserverController.mTrackedTasks.size(); i3++) {
      JobStatus jobStatus = (JobStatus) contentObserverController.mTrackedTasks.valueAt(i3);
      if (predicate2.test(jobStatus)) {
        long start3 = protoOutputStream.start(2246267895809L);
        jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1120986464258L, jobStatus.getSourceUid());
        protoOutputStream.end(start3);
      }
    }
    int size = contentObserverController.mObservers.size();
    int i4 = 0;
    while (i4 < size) {
      int i5 = size;
      long start4 = protoOutputStream.start(2246267895810L);
      int keyAt = contentObserverController.mObservers.keyAt(i4);
      protoOutputStream.write(1120986464257L, keyAt);
      ArrayMap arrayMap2 = (ArrayMap) contentObserverController.mObservers.get(keyAt);
      int size2 = arrayMap2.size();
      int i6 = 0;
      while (i6 < size2) {
        ObserverInstance observerInstance2 = (ObserverInstance) arrayMap2.valueAt(i6);
        int size3 = observerInstance2.mJobs.size();
        int i7 = 0;
        while (true) {
          if (i7 >= size3) {
            z = false;
            break;
          } else {
            if (predicate2.test(((JobInstance) observerInstance2.mJobs.valueAt(i7)).mJobStatus)) {
              z = true;
              break;
            }
            i7++;
          }
        }
        if (z) {
          j2 = start;
          j3 = start2;
          long start5 = protoOutputStream.start(2246267895810L);
          JobInfo.TriggerContentUri triggerContentUri =
              (JobInfo.TriggerContentUri) arrayMap2.keyAt(i6);
          Uri uri = triggerContentUri.getUri();
          if (uri != null) {
            protoOutputStream.write(1138166333441L, uri.toString());
          }
          protoOutputStream.write(1120986464258L, triggerContentUri.getFlags());
          int i8 = 0;
          while (i8 < size3) {
            long start6 = protoOutputStream.start(2246267895811L);
            JobInstance jobInstance = (JobInstance) observerInstance2.mJobs.valueAt(i8);
            ArrayMap arrayMap3 = arrayMap2;
            int i9 = size2;
            jobInstance.mJobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
            protoOutputStream.write(1120986464258L, jobInstance.mJobStatus.getSourceUid());
            if (jobInstance.mChangedAuthorities == null) {
              protoOutputStream.end(start6);
              observerInstance = observerInstance2;
              i2 = size3;
            } else {
              if (jobInstance.mTriggerPending) {
                observerInstance = observerInstance2;
                i2 = size3;
                protoOutputStream.write(
                    1112396529667L, jobInstance.mJobStatus.getTriggerContentUpdateDelay());
                protoOutputStream.write(
                    1112396529668L, jobInstance.mJobStatus.getTriggerContentMaxDelay());
              } else {
                observerInstance = observerInstance2;
                i2 = size3;
              }
              for (int i10 = 0; i10 < jobInstance.mChangedAuthorities.size(); i10++) {
                protoOutputStream.write(
                    2237677961221L, (String) jobInstance.mChangedAuthorities.valueAt(i10));
              }
              if (jobInstance.mChangedUris != null) {
                for (int i11 = 0; i11 < jobInstance.mChangedUris.size(); i11++) {
                  Uri uri2 = (Uri) jobInstance.mChangedUris.valueAt(i11);
                  if (uri2 != null) {
                    protoOutputStream.write(2237677961222L, uri2.toString());
                  }
                }
              }
              protoOutputStream.end(start6);
            }
            i8++;
            observerInstance2 = observerInstance;
            arrayMap2 = arrayMap3;
            size2 = i9;
            size3 = i2;
          }
          arrayMap = arrayMap2;
          i = size2;
          protoOutputStream.end(start5);
        } else {
          j2 = start;
          j3 = start2;
          arrayMap = arrayMap2;
          i = size2;
        }
        i6++;
        predicate2 = predicate;
        start2 = j3;
        start = j2;
        arrayMap2 = arrayMap;
        size2 = i;
      }
      protoOutputStream.end(start4);
      i4++;
      contentObserverController = this;
      size = i5;
      predicate2 = predicate;
    }
    protoOutputStream.end(start2);
    protoOutputStream.end(start);
  }
}
