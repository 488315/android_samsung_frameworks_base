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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:114:0x04ed  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0504  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0511  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x052b  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x053c  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0551  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x055d  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0565  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x057d  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0588  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0593  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x059e  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x05a9  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x05b1  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x05bc  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x05c7  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x05e3  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x063e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:166:0x063f  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x05e9  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x05ce  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x05c3  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x05b8  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x05a5  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x059a  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x058f  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x0584  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x056c  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x0562  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0558  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x054d  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x0545  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x052e  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x0521  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x050a  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x03c3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:259:0x03a9  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x03a2  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x02ce  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x02e0  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x02f8  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0339  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x039f  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x03a6  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0403  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x040d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v49, types: [com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda4] */
    /* JADX WARN: Type inference failed for: r4v12, types: [com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda4] */
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
