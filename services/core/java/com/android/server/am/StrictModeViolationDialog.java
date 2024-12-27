package com.android.server.am;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class StrictModeViolationDialog extends BaseErrorDialog {
    public final AnonymousClass1 mHandler;
    public final ProcessRecord mProc;
    public final AppErrorResult mResult;
    public final ActivityManagerService mService;

    /* JADX WARN: Type inference failed for: r0v0, types: [android.os.Handler, com.android.server.am.StrictModeViolationDialog$1] */
    public StrictModeViolationDialog(Context context, ActivityManagerService activityManagerService, AppErrorResult appErrorResult, ProcessRecord processRecord) {
        super(context);
        CharSequence applicationLabel;
        ?? r0 = new Handler() { // from class: com.android.server.am.StrictModeViolationDialog.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                ErrorDialogController errorDialogController;
                List list;
                ActivityManagerProcLock activityManagerProcLock = StrictModeViolationDialog.this.mService.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerProcLock) {
                    try {
                        ProcessRecord processRecord2 = StrictModeViolationDialog.this.mProc;
                        if (processRecord2 != null && (list = (errorDialogController = processRecord2.mErrorState.mDialogController).mViolationDialogs) != null) {
                            errorDialogController.scheduleForAllDialogs(list, new ErrorDialogController$$ExternalSyntheticLambda0(0));
                            errorDialogController.mViolationDialogs = null;
                        }
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                StrictModeViolationDialog.this.mResult.set(message.what);
                StrictModeViolationDialog.this.dismiss();
            }
        };
        this.mHandler = r0;
        Resources resources = context.getResources();
        this.mService = activityManagerService;
        this.mProc = processRecord;
        this.mResult = appErrorResult;
        if (processRecord.mPkgList.size() != 1 || (applicationLabel = context.getPackageManager().getApplicationLabel(processRecord.info)) == null) {
            setMessage(resources.getString(17043102, processRecord.processName.toString()));
        } else {
            setMessage(resources.getString(17043101, applicationLabel.toString(), processRecord.info.processName));
        }
        setCancelable(false);
        setButton(-1, resources.getText(R.string.ime_action_send), r0.obtainMessage(0));
        if (processRecord.mErrorState.mErrorReportReceiver != null) {
            try {
                IRestrictionPolicy asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
                if (asInterface != null && asInterface.isGoogleCrashReportAllowedAsUser(processRecord.userId)) {
                    setButton(-2, resources.getText(17042557), r0.obtainMessage(1));
                }
            } catch (RemoteException unused) {
            }
        }
        getWindow().addPrivateFlags(256);
        getWindow().setTitle("Strict Mode Violation: " + processRecord.info.processName);
        AnonymousClass1 anonymousClass1 = this.mHandler;
        anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage(0), 60000L);
    }

    @Override // com.android.server.am.BaseErrorDialog
    public final void closeDialog() {
        obtainMessage(0).sendToTarget();
    }
}
