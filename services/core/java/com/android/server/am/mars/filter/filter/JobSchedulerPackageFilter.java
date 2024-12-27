package com.android.server.am.mars.filter.filter;

import android.app.job.JobInfo;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.am.FreecessController$$ExternalSyntheticOutline0;
import com.android.server.am.FreecessHandler;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class JobSchedulerPackageFilter implements IFilter {
    public Set mRunningJobSet;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class JobSchedulerPackageFilterHolder {
        public static final JobSchedulerPackageFilter INSTANCE;

        static {
            JobSchedulerPackageFilter jobSchedulerPackageFilter = new JobSchedulerPackageFilter();
            jobSchedulerPackageFilter.mRunningJobSet = new HashSet();
            INSTANCE = jobSchedulerPackageFilter;
        }
    }

    public final void addRunningJobs(JobInfo jobInfo, int i) {
        if (jobInfo != null) {
            ((HashSet) this.mRunningJobSet).add(jobInfo);
            String packageName = jobInfo.getService().getPackageName();
            boolean z = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
            FreecessHandler freecessHandler = FreecessHandler.FreecessHandlerHolder.INSTANCE;
            if (freecessHandler.mMainHandler == null) {
                return;
            }
            Bundle m =
                    FreecessController$$ExternalSyntheticOutline0.m(
                            i, "packageName", packageName, "uid");
            m.putString("reason", "JOB_ADD");
            Message obtainMessage = freecessHandler.mMainHandler.obtainMessage(17);
            obtainMessage.setData(m);
            freecessHandler.mMainHandler.sendMessage(obtainMessage);
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {}

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        try {
            if (((HashSet) this.mRunningJobSet).isEmpty()) {
                return 0;
            }
            Iterator it = ((HashSet) this.mRunningJobSet).iterator();
            while (it.hasNext()) {
                JobInfo jobInfo = (JobInfo) it.next();
                if (jobInfo != null && jobInfo.getService().getPackageName().equals(str)) {
                    return 18;
                }
            }
            return 0;
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(" isRunningJobPkg unknow err:"),
                    "MARs:JobSchedulerPackageFilter");
            return 0;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {}
}
