package com.android.server.am;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.samsung.android.knox.restriction.IRestrictionPolicy;

/* loaded from: classes.dex */
public final class StrictModeViolationDialog extends BaseErrorDialog {
  public final Handler mHandler;
  public final ProcessRecord mProc;
  public final AppErrorResult mResult;
  public final ActivityManagerService mService;

  public StrictModeViolationDialog(
      Context context,
      ActivityManagerService activityManagerService,
      AppErrorResult appErrorResult,
      ProcessRecord processRecord) {
    super(context);
    CharSequence applicationLabel;
    Handler handler =
        new Handler() { // from class: com.android.server.am.StrictModeViolationDialog.1
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            ActivityManagerGlobalLock activityManagerGlobalLock =
                StrictModeViolationDialog.this.mService.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
              try {
                if (StrictModeViolationDialog.this.mProc != null) {
                  StrictModeViolationDialog.this
                      .mProc
                      .mErrorState
                      .getDialogController()
                      .clearViolationDialogs();
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
    this.mHandler = handler;
    Resources resources = context.getResources();
    this.mService = activityManagerService;
    this.mProc = processRecord;
    this.mResult = appErrorResult;
    if (processRecord.getPkgList().size() == 1
        && (applicationLabel = context.getPackageManager().getApplicationLabel(processRecord.info))
            != null) {
      setMessage(
          resources.getString(
              17042887, applicationLabel.toString(), processRecord.info.processName));
    } else {
      setMessage(resources.getString(17042888, processRecord.processName.toString()));
    }
    setCancelable(false);
    setButton(-1, resources.getText(R.string.heavy_weight_notification), handler.obtainMessage(0));
    if (processRecord.mErrorState.getErrorReportReceiver() != null) {
      try {
        IRestrictionPolicy asInterface =
            IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
        if (asInterface != null
            && asInterface.isGoogleCrashReportAllowedAsUser(processRecord.userId)) {
          setButton(-2, resources.getText(17042399), handler.obtainMessage(1));
        }
      } catch (RemoteException unused) {
      }
    }
    getWindow().addPrivateFlags(256);
    getWindow().setTitle("Strict Mode Violation: " + processRecord.info.processName);
    Handler handler2 = this.mHandler;
    handler2.sendMessageDelayed(handler2.obtainMessage(0), 60000L);
  }

  @Override // com.android.server.am.BaseErrorDialog
  public void closeDialog() {
    this.mHandler.obtainMessage(0).sendToTarget();
  }
}
