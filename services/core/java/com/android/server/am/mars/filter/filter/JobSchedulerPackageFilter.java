package com.android.server.am.mars.filter.filter;

import android.app.job.JobInfo;
import android.content.Context;
import android.util.Slog;
import com.android.server.am.FreecessHandler;
import com.android.server.am.mars.filter.IFilter;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class JobSchedulerPackageFilter implements IFilter {
    public static String TAG = "MARs:" + JobSchedulerPackageFilter.class.getSimpleName();
    public Set mRunningJobSet;

    public abstract class JobSchedulerPackageFilterHolder {
        public static final JobSchedulerPackageFilter INSTANCE = new JobSchedulerPackageFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
    }

    public JobSchedulerPackageFilter() {
        this.mRunningJobSet = new HashSet();
    }

    public static JobSchedulerPackageFilter getInstance() {
        return JobSchedulerPackageFilterHolder.INSTANCE;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        return isRunningJobPkg(str) ? 18 : 0;
    }

    public void removeRunningJobs(JobInfo jobInfo) {
        if (jobInfo != null) {
            this.mRunningJobSet.remove(jobInfo);
        }
    }

    public void addRunningJobs(JobInfo jobInfo, int i) {
        if (jobInfo != null) {
            this.mRunningJobSet.add(jobInfo);
            FreecessHandler.getInstance().sendUnfreezeSpecialPackageMsg(jobInfo.getService().getPackageName(), i, "JOB_ADD");
        }
    }

    public boolean isRunningJobPkg(String str) {
        try {
        } catch (Exception e) {
            Slog.e(TAG, " isRunningJobPkg unknow err:" + e.getMessage());
        }
        if (this.mRunningJobSet.isEmpty()) {
            return false;
        }
        for (JobInfo jobInfo : this.mRunningJobSet) {
            if (jobInfo != null && jobInfo.getService().getPackageName().equals(str)) {
                return true;
            }
        }
        return false;
    }
}
