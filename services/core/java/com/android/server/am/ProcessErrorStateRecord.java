package com.android.server.am;

import android.app.ActivityManager;
import android.app.ApplicationErrorReport;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.provider.Settings;
import android.util.Slog;

import com.android.server.wm.ActivityTaskManagerService;

import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.rune.CoreRune;

public final class ProcessErrorStateRecord {
    public final boolean DEBUG_ATRACE;
    public String mAnrAnnotation;
    public AppNotRespondingDialog.Data mAnrData;
    public final ProcessRecord mApp;
    public boolean mBad;
    public Runnable mCrashHandler;
    public boolean mCrashing;
    public ActivityManager.ProcessErrorStateInfo mCrashingReport;
    public final ErrorDialogController mDialogController;
    public ComponentName mErrorReportReceiver;
    public boolean mForceCrashReport;
    public boolean mNotResponding;
    public ActivityManager.ProcessErrorStateInfo mNotRespondingReport;
    public final ActivityManagerGlobalLock mProcLock;
    public final ActivityManagerService mService;

    public ProcessErrorStateRecord(ProcessRecord processRecord) {
        this.DEBUG_ATRACE = CoreRune.IS_DEBUG_LEVEL_MID || CoreRune.IS_DEBUG_LEVEL_HIGH;
        this.mApp = processRecord;
        ActivityManagerService activityManagerService = processRecord.mService;
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
        this.mDialogController = new ErrorDialogController(processRecord);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void appNotResponding(
            java.lang.String r56,
            android.content.pm.ApplicationInfo r57,
            java.lang.String r58,
            com.android.server.wm.WindowProcessController r59,
            boolean r60,
            com.android.internal.os.TimeoutRecord r61,
            java.util.concurrent.ExecutorService r62,
            final boolean r63,
            boolean r64,
            java.util.concurrent.Future r65) {
        /*
            Method dump skipped, instructions count: 1805
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ProcessErrorStateRecord.appNotResponding(java.lang.String,"
                    + " android.content.pm.ApplicationInfo, java.lang.String,"
                    + " com.android.server.wm.WindowProcessController, boolean,"
                    + " com.android.internal.os.TimeoutRecord,"
                    + " java.util.concurrent.ExecutorService, boolean, boolean,"
                    + " java.util.concurrent.Future):void");
    }

    public boolean isMonitorCpuUsage() {
        AppProfiler appProfiler = this.mService.mAppProfiler;
        return true;
    }

    public boolean isSilentAnr() {
        ContentResolver contentResolver = this.mService.mContext.getContentResolver();
        if (Settings.Secure.getIntForUser(
                        contentResolver, "anr_show_background", 0, contentResolver.getUserId())
                != 0) {
            return false;
        }
        ProcessRecord processRecord = this.mApp;
        if (processRecord.mPid == ActivityManagerService.MY_PID
                || processRecord.isInterestingToUserLocked()) {
            return false;
        }
        if (this.mApp.info != null
                && Constants.SYSTEMUI_PACKAGE_NAME.equals(this.mApp.info.packageName)) {
            return false;
        }
        ProcessStateRecord processStateRecord = this.mApp.mState;
        return (processStateRecord.mHasTopUi || processStateRecord.mHasOverlayUi) ? false : true;
    }

    public final void setNotResponding(boolean z) {
        this.mNotResponding = z;
        this.mApp.mWindowProcessController.mNotResponding = z;
    }

    public final boolean skipAnrLocked(String str) {
        if (ActivityTaskManagerService.this.mShuttingDown) {
            Slog.i("ActivityManager", "During shutdown skipping ANR: " + this + " " + str);
            return true;
        }
        if (this.mNotResponding) {
            Slog.i("ActivityManager", "Skipping duplicate ANR: " + this + " " + str);
            return true;
        }
        if (this.mCrashing) {
            Slog.i("ActivityManager", "Crashing app skipping ANR: " + this + " " + str);
            return true;
        }
        ProcessRecord processRecord = this.mApp;
        if (processRecord.mKilledByAm) {
            Slog.i("ActivityManager", "App already killed by AM skipping ANR: " + this + " " + str);
            return true;
        }
        if (!processRecord.mKilled) {
            return false;
        }
        Slog.i("ActivityManager", "Skipping died app ANR: " + this + " " + str);
        return true;
    }

    public final void startAppProblemLSP() {
        this.mErrorReportReceiver = null;
        for (int i : this.mService.mUserController.getCurrentProfileIds()) {
            ProcessRecord processRecord = this.mApp;
            if (processRecord.userId == i) {
                this.mErrorReportReceiver =
                        ApplicationErrorReport.getErrorReportReceiver(
                                this.mService.mContext,
                                processRecord.info.packageName,
                                this.mApp.info.flags);
            }
        }
        this.mService.mBroadcastQueue.onApplicationCleanupLocked(this.mApp);
    }
}
