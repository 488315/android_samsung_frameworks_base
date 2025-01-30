package com.android.server.wm;

import android.app.ActivityManagerInternal;
import android.content.pm.ApplicationInfo;
import android.util.ArrayMap;
import com.android.internal.os.BackgroundThread;
import java.io.PrintWriter;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class VisibleActivityProcessTracker {
    public final ActivityTaskManagerService mAtms;
    public final ArrayMap mProcMap = new ArrayMap();
    public final Executor mBgExecutor = BackgroundThread.getExecutor();

    public VisibleActivityProcessTracker(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtms = activityTaskManagerService;
    }

    public void onAnyActivityVisible(WindowProcessController windowProcessController) {
        CpuTimeRecord cpuTimeRecord = new CpuTimeRecord(windowProcessController);
        synchronized (this.mProcMap) {
            this.mProcMap.put(windowProcessController, cpuTimeRecord);
        }
        if (windowProcessController.hasResumedActivity()) {
            cpuTimeRecord.mShouldGetCpuTime = true;
            this.mBgExecutor.execute(cpuTimeRecord);
        }
    }

    public void onAllActivitiesInvisible(WindowProcessController windowProcessController) {
        CpuTimeRecord removeProcess = removeProcess(windowProcessController);
        if (removeProcess == null || !removeProcess.mShouldGetCpuTime) {
            return;
        }
        this.mBgExecutor.execute(removeProcess);
    }

    public void onActivityResumedWhileVisible(WindowProcessController windowProcessController) {
        CpuTimeRecord cpuTimeRecord;
        synchronized (this.mProcMap) {
            cpuTimeRecord = (CpuTimeRecord) this.mProcMap.get(windowProcessController);
        }
        if (cpuTimeRecord == null || cpuTimeRecord.mShouldGetCpuTime) {
            return;
        }
        cpuTimeRecord.mShouldGetCpuTime = true;
        this.mBgExecutor.execute(cpuTimeRecord);
    }

    public boolean hasResumedActivity(int i) {
        return match(i, new Predicate() { // from class: com.android.server.wm.VisibleActivityProcessTracker$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((WindowProcessController) obj).hasResumedActivity();
            }
        });
    }

    public boolean hasVisibleActivity(int i) {
        return match(i, null);
    }

    public final boolean match(int i, Predicate predicate) {
        synchronized (this.mProcMap) {
            for (int size = this.mProcMap.size() - 1; size >= 0; size--) {
                WindowProcessController windowProcessController = (WindowProcessController) this.mProcMap.keyAt(size);
                if (windowProcessController.mUid == i && (predicate == null || predicate.test(windowProcessController))) {
                    return true;
                }
            }
            return false;
        }
    }

    public CpuTimeRecord removeProcess(WindowProcessController windowProcessController) {
        CpuTimeRecord cpuTimeRecord;
        synchronized (this.mProcMap) {
            cpuTimeRecord = (CpuTimeRecord) this.mProcMap.remove(windowProcessController);
        }
        return cpuTimeRecord;
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str + "VisibleActivityProcess:[");
        synchronized (this.mProcMap) {
            for (int size = this.mProcMap.size() - 1; size >= 0; size += -1) {
                printWriter.print(" " + this.mProcMap.keyAt(size));
            }
        }
        printWriter.println("]");
    }

    public class CpuTimeRecord implements Runnable {
        public long mCpuTime;
        public boolean mHasStartCpuTime;
        public final WindowProcessController mProc;
        public boolean mShouldGetCpuTime;

        public CpuTimeRecord(WindowProcessController windowProcessController) {
            this.mProc = windowProcessController;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mProc.getPid() == 0) {
                return;
            }
            if (!this.mHasStartCpuTime) {
                this.mHasStartCpuTime = true;
                this.mCpuTime = this.mProc.getCpuTime();
                return;
            }
            long cpuTime = this.mProc.getCpuTime() - this.mCpuTime;
            if (cpuTime > 0) {
                ActivityManagerInternal activityManagerInternal = VisibleActivityProcessTracker.this.mAtms.mAmInternal;
                ApplicationInfo applicationInfo = this.mProc.mInfo;
                activityManagerInternal.updateForegroundTimeIfOnBattery(applicationInfo.packageName, applicationInfo.uid, cpuTime);
            }
        }
    }
}
