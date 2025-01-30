package com.android.server.am;

import android.content.Context;
import android.util.Slog;

/* loaded from: classes.dex */
public class ProcessCpusetController {
    public static final String TAG = "ProcessCpusetController";
    public static ProcessCpusetController mController;
    public final ActivityManagerService mAm;
    public final Context mContext;
    public final String mSystemPackage;

    public ProcessCpusetController(ActivityManagerService activityManagerService) {
        this.mAm = activityManagerService;
        Context context = activityManagerService.mContext;
        this.mContext = context;
        this.mSystemPackage = context.getPackageName();
    }

    public static synchronized void prepareController(ActivityManagerService activityManagerService) {
        synchronized (ProcessCpusetController.class) {
            if (mController == null && activityManagerService != null) {
                mController = new ProcessCpusetController(activityManagerService);
            }
        }
    }

    public static ProcessCpusetController getController() {
        return mController;
    }

    public static ProcessCpusetController getController(ActivityManagerService activityManagerService) {
        prepareController(activityManagerService);
        return getController();
    }

    public void resetAbnormalList() {
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                for (int size = this.mAm.mProcessList.getmLruProcesses().size() - 1; size >= 0; size--) {
                    ((ProcessRecord) this.mAm.mProcessList.getmLruProcesses().get(size)).mState.setAbnormalStatus(false);
                }
                this.mAm.updateOomAdjLocked(23);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public boolean setProcessSlowdown(int i, boolean z) {
        ProcessStateRecord processStateRecord;
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                int size = this.mAm.mProcessList.getmLruProcesses().size() - 1;
                while (true) {
                    if (size < 0) {
                        processStateRecord = null;
                        break;
                    }
                    ProcessRecord processRecord = (ProcessRecord) this.mAm.mProcessList.getmLruProcesses().get(size);
                    if (processRecord.getPid() == i) {
                        processStateRecord = processRecord.mState;
                        break;
                    }
                    size--;
                }
            } finally {
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        if (processStateRecord == null) {
            Slog.w(TAG, "Slowdown: pid " + i + " is nonexistent");
            return false;
        }
        processStateRecord.setAbnormalStatus(z);
        ActivityManagerService activityManagerService2 = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService2) {
            try {
                this.mAm.updateOomAdjLocked(23);
            } finally {
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        return true;
    }

    public boolean setFGSFilter(int i, boolean z) {
        boolean z2;
        ActivityManagerService activityManagerService = this.mAm;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                UidRecord uidRecord = this.mAm.mProcessList.mActiveUids.get(i);
                if (uidRecord == null || uidRecord.getCurProcState() != 4) {
                    z2 = false;
                } else {
                    uidRecord.setFGSFilterStatus(z);
                    this.mAm.updateOomAdjLocked(24);
                    z2 = true;
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        return z2;
    }
}
