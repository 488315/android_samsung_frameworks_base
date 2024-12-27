package com.android.server.wm;

import android.app.ActivityManagerInternal;
import android.content.pm.ApplicationInfo;
import android.util.ArrayMap;

import com.android.internal.os.BackgroundThread;
import com.android.server.am.AppProfiler;
import com.android.server.am.ProcessRecord;

import java.util.concurrent.Executor;

public final class VisibleActivityProcessTracker {
    public final ActivityTaskManagerService mAtms;
    public final ArrayMap mProcMap = new ArrayMap();
    public final Executor mBgExecutor = BackgroundThread.getExecutor();

    public final class CpuTimeRecord implements Runnable {
        public long mCpuTime;
        public boolean mHasStartCpuTime;
        public final WindowProcessController mProc;
        public boolean mShouldGetCpuTime;

        public CpuTimeRecord(WindowProcessController windowProcessController) {
            this.mProc = windowProcessController;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (this.mProc.mPid == 0) {
                return;
            }
            if (!this.mHasStartCpuTime) {
                this.mHasStartCpuTime = true;
                ProcessRecord processRecord = this.mProc.mListener;
                AppProfiler appProfiler = processRecord.mService.mAppProfiler;
                this.mCpuTime = appProfiler.mProcessCpuTracker.getCpuTimeForPid(processRecord.mPid);
                return;
            }
            ProcessRecord processRecord2 = this.mProc.mListener;
            AppProfiler appProfiler2 = processRecord2.mService.mAppProfiler;
            long cpuTimeForPid =
                    appProfiler2.mProcessCpuTracker.getCpuTimeForPid(processRecord2.mPid)
                            - this.mCpuTime;
            if (cpuTimeForPid > 0) {
                ActivityManagerInternal activityManagerInternal =
                        VisibleActivityProcessTracker.this.mAtms.mAmInternal;
                ApplicationInfo applicationInfo = this.mProc.mInfo;
                activityManagerInternal.updateForegroundTimeIfOnBattery(
                        applicationInfo.packageName, applicationInfo.uid, cpuTimeForPid);
            }
        }
    }

    public VisibleActivityProcessTracker(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtms = activityTaskManagerService;
    }
}
