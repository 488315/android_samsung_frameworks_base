package android.app;

import android.app.job.IJobScheduler;
import android.app.job.IUserVisibleJobObserver;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobSnapshot;
import android.app.job.JobWorkItem;
import android.app.job.PendingJobReasonsInfo;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.RemoteException;
import android.util.ArrayMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class JobSchedulerImpl extends JobScheduler {
    IJobScheduler mBinder;
    private final Context mContext;
    private final String mNamespace;

    public JobSchedulerImpl(Context context, IJobScheduler iJobScheduler) {
        this(context, iJobScheduler, null);
    }

    private JobSchedulerImpl(Context context, IJobScheduler iJobScheduler, String str) {
        this.mContext = context;
        this.mBinder = iJobScheduler;
        this.mNamespace = str;
    }

    private JobSchedulerImpl(JobSchedulerImpl jobSchedulerImpl, String str) {
        this(jobSchedulerImpl.mContext, jobSchedulerImpl.mBinder, str);
    }

    @Override // android.app.job.JobScheduler
    public JobScheduler forNamespace(String str) {
        String strSanitizeNamespace = sanitizeNamespace(str);
        if (strSanitizeNamespace == null) {
            throw new NullPointerException("namespace cannot be null");
        }
        if (strSanitizeNamespace.isEmpty()) {
            throw new IllegalArgumentException("namespace cannot be empty");
        }
        return new JobSchedulerImpl(this, strSanitizeNamespace);
    }

    @Override // android.app.job.JobScheduler
    public String getNamespace() {
        return this.mNamespace;
    }

    @Override // android.app.job.JobScheduler
    public int schedule(JobInfo jobInfo) {
        try {
            return this.mBinder.schedule(this.mNamespace, jobInfo);
        } catch (RemoteException unused) {
            return 0;
        }
    }

    @Override // android.app.job.JobScheduler
    public int enqueue(JobInfo jobInfo, JobWorkItem jobWorkItem) {
        try {
            return this.mBinder.enqueue(this.mNamespace, jobInfo, jobWorkItem);
        } catch (RemoteException unused) {
            return 0;
        }
    }

    @Override // android.app.job.JobScheduler
    public int scheduleAsPackage(JobInfo jobInfo, String str, int i, String str2) {
        try {
            return this.mBinder.scheduleAsPackage(this.mNamespace, jobInfo, str, i, str2);
        } catch (RemoteException unused) {
            return 0;
        }
    }

    @Override // android.app.job.JobScheduler
    public void cancel(int i) {
        try {
            this.mBinder.cancel(this.mNamespace, i);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.app.job.JobScheduler
    public void cancelAll() {
        try {
            this.mBinder.cancelAllInNamespace(this.mNamespace);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.app.job.JobScheduler
    public void cancelInAllNamespaces() {
        try {
            this.mBinder.cancelAll();
        } catch (RemoteException unused) {
        }
    }

    @Override // android.app.job.JobScheduler
    public List<JobInfo> getAllPendingJobs() {
        try {
            return this.mBinder.getAllPendingJobsInNamespace(this.mNamespace).getList();
        } catch (RemoteException unused) {
            return null;
        }
    }

    @Override // android.app.job.JobScheduler
    public Map<String, List<JobInfo>> getPendingJobsInAllNamespaces() {
        try {
            Map<String, ParceledListSlice<JobInfo>> allPendingJobs = this.mBinder.getAllPendingJobs();
            ArrayMap arrayMap = new ArrayMap();
            for (String str : allPendingJobs.keySet()) {
                arrayMap.put(str, allPendingJobs.get(str).getList());
            }
            return arrayMap;
        } catch (RemoteException unused) {
            return null;
        }
    }

    @Override // android.app.job.JobScheduler
    public JobInfo getPendingJob(int i) {
        try {
            return this.mBinder.getPendingJob(this.mNamespace, i);
        } catch (RemoteException unused) {
            return null;
        }
    }

    @Override // android.app.job.JobScheduler
    public int getPendingJobReason(int i) {
        try {
            return this.mBinder.getPendingJobReason(this.mNamespace, i);
        } catch (RemoteException unused) {
            return 0;
        }
    }

    @Override // android.app.job.JobScheduler
    public int[] getPendingJobReasons(int i) {
        try {
            return this.mBinder.getPendingJobReasons(this.mNamespace, i);
        } catch (RemoteException unused) {
            return new int[]{0};
        }
    }

    @Override // android.app.job.JobScheduler
    public List<PendingJobReasonsInfo> getPendingJobReasonsHistory(int i) {
        try {
            return this.mBinder.getPendingJobReasonsHistory(this.mNamespace, i);
        } catch (RemoteException unused) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override // android.app.job.JobScheduler
    public boolean canRunUserInitiatedJobs() {
        try {
            return this.mBinder.canRunUserInitiatedJobs(this.mContext.getOpPackageName());
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Override // android.app.job.JobScheduler
    public boolean hasRunUserInitiatedJobsPermission(String str, int i) {
        try {
            return this.mBinder.hasRunUserInitiatedJobsPermission(str, i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Override // android.app.job.JobScheduler
    public List<JobInfo> getStartedJobs() {
        try {
            return this.mBinder.getStartedJobs();
        } catch (RemoteException unused) {
            return null;
        }
    }

    @Override // android.app.job.JobScheduler
    public List<JobSnapshot> getAllJobSnapshots() {
        try {
            return this.mBinder.getAllJobSnapshots().getList();
        } catch (RemoteException unused) {
            return null;
        }
    }

    @Override // android.app.job.JobScheduler
    public void registerUserVisibleJobObserver(IUserVisibleJobObserver iUserVisibleJobObserver) {
        try {
            this.mBinder.registerUserVisibleJobObserver(iUserVisibleJobObserver);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.app.job.JobScheduler
    public void unregisterUserVisibleJobObserver(IUserVisibleJobObserver iUserVisibleJobObserver) {
        try {
            this.mBinder.unregisterUserVisibleJobObserver(iUserVisibleJobObserver);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.app.job.JobScheduler
    public void notePendingUserRequestedAppStop(String str, int i, String str2) {
        try {
            this.mBinder.notePendingUserRequestedAppStop(str, i, str2);
        } catch (RemoteException unused) {
        }
    }
}
