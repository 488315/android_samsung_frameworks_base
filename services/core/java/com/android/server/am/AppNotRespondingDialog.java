package com.android.server.am;

import android.R;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.BidiFormatter;
import android.util.Log;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.feature.SemGateConfig;
import com.samsung.android.knox.restriction.IRestrictionPolicy;

/* loaded from: classes.dex */
public final class AppNotRespondingDialog extends BaseErrorDialog implements View.OnClickListener {
  public final Data mData;
  public final Handler mHandler;
  public final ProcessRecord mProc;
  public final ActivityManagerService mService;

  public AppNotRespondingDialog(
      ActivityManagerService activityManagerService, Context context, Data data) {
    super(context);
    int i;
    String string;
    this.mHandler =
        new Handler() { // from class: com.android.server.am.AppNotRespondingDialog.1
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            MetricsLogger.action(
                AppNotRespondingDialog.this.getContext(),
                FrameworkStatsLog.f90x54490b7,
                message.what);
            int i2 = message.what;
            if (i2 == 1) {
              AppNotRespondingDialog.this.mService.killAppAtUsersRequest(
                  AppNotRespondingDialog.this.mProc);
            } else if (i2 == 2 || i2 == 3) {
              ActivityManagerService activityManagerService2 = AppNotRespondingDialog.this.mService;
              ActivityManagerService.boostPriorityForLockedSection();
              synchronized (activityManagerService2) {
                try {
                  ProcessRecord processRecord = AppNotRespondingDialog.this.mProc;
                  ProcessErrorStateRecord processErrorStateRecord = processRecord.mErrorState;
                  r2 =
                      message.what == 3
                          ? AppNotRespondingDialog.this.mService.mAppErrors
                              .createAppErrorIntentLOSP(
                                  processRecord, System.currentTimeMillis(), null)
                          : null;
                  ActivityManagerGlobalLock activityManagerGlobalLock =
                      AppNotRespondingDialog.this.mService.mProcLock;
                  ActivityManagerService.boostPriorityForProcLockedSection();
                  synchronized (activityManagerGlobalLock) {
                    try {
                      processErrorStateRecord.setNotResponding(false);
                      processErrorStateRecord.getDialogController().clearAnrDialogs();
                    } catch (Throwable th) {
                      ActivityManagerService.resetPriorityAfterProcLockedSection();
                      throw th;
                    }
                  }
                  ActivityManagerService.resetPriorityAfterProcLockedSection();
                  AppNotRespondingDialog.this.mService.mServices.scheduleServiceTimeoutLocked(
                      processRecord);
                  if (AppNotRespondingDialog.this.mData.isContinuousAnr) {
                    AppNotRespondingDialog.this.mService.mInternal.rescheduleAnrDialog(
                        AppNotRespondingDialog.this.mData);
                  }
                } catch (Throwable th2) {
                  ActivityManagerService.resetPriorityAfterLockedSection();
                  throw th2;
                }
              }
              ActivityManagerService.resetPriorityAfterLockedSection();
            }
            if (r2 != null) {
              try {
                AppNotRespondingDialog.this.getContext().startActivity(r2);
              } catch (ActivityNotFoundException e) {
                Slog.w("AppNotRespondingDialog", "bug report receiver dissappeared", e);
              }
            }
            AppNotRespondingDialog.this.dismiss();
          }
        };
    this.mService = activityManagerService;
    ProcessRecord processRecord = data.proc;
    this.mProc = processRecord;
    this.mData = data;
    Resources resources = context.getResources();
    setCancelable(false);
    ApplicationInfo applicationInfo = data.aInfo;
    CharSequence charSequence = null;
    CharSequence loadLabel =
        applicationInfo != null ? applicationInfo.loadLabel(context.getPackageManager()) : null;
    if (processRecord.getPkgList().size() != 1
        || (charSequence = context.getPackageManager().getApplicationLabel(processRecord.info))
            == null) {
      if (loadLabel != null) {
        charSequence = processRecord.processName;
        i = R.string.capability_desc_canPerformGestures;
      } else {
        loadLabel = processRecord.processName;
        i = R.string.capability_desc_canRequestTouchExploration;
      }
    } else if (loadLabel != null) {
      i = R.string.capability_desc_canControlMagnification;
    } else {
      charSequence = processRecord.processName;
      i = 17039800;
      loadLabel = charSequence;
    }
    BidiFormatter bidiFormatter = BidiFormatter.getInstance();
    if (charSequence != null) {
      string =
          resources.getString(
              i,
              bidiFormatter.unicodeWrap(loadLabel.toString()),
              bidiFormatter.unicodeWrap(charSequence.toString()));
    } else {
      string = resources.getString(i, bidiFormatter.unicodeWrap(loadLabel.toString()));
    }
    setTitle(string);
    if (data.aboveSystem) {
      getWindow().setType(2010);
    }
    WindowManager.LayoutParams attributes = getWindow().getAttributes();
    attributes.setTitle("Application Not Responding: " + processRecord.info.processName);
    attributes.privateFlags = 272;
    getWindow().setAttributes(attributes);
    if (SemGateConfig.isGateEnabled()) {
      setOnShowListener(
          new DialogInterface
              .OnShowListener() { // from class:
                                  // com.android.server.am.AppNotRespondingDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
              AppNotRespondingDialog.this.lambda$new$0(dialogInterface);
            }
          });
      setOnDismissListener(
          new DialogInterface
              .OnDismissListener() { // from class:
                                     // com.android.server.am.AppNotRespondingDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
              Log.i("GATE", "<GATE-M>APP_ANR:ANR dialog has been cleared</GATE-M>");
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(DialogInterface dialogInterface) {
    ProcessRecord processRecord = this.mProc;
    String str = processRecord == null ? "" : processRecord.processName;
    Button button = ((AppNotRespondingDialog) dialogInterface).getButton(-1);
    button.getLocationOnScreen(new int[2]);
    Log.i(
        "GATE",
        "<GATE-M>APP_ANR:"
            + str
            + ","
            + Math.round(r1[0] + (button.getWidth() / 2))
            + ","
            + Math.round(r1[1] + (button.getHeight() / 2))
            + "</GATE-M>");
    Log.i("GATE", "<GATE-M>APP_ANR:Storing dumpstate at /data/log/, dumpstate_app_anr</GATE-M>");
    button.performClick();
  }

  @Override // android.app.AlertDialog, android.app.Dialog
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    LayoutInflater.from(getContext())
        .inflate(R.layout.app_permission_item, (ViewGroup) findViewById(R.id.custom), true);
    TextView textView = (TextView) findViewById(R.id.aerr_restart);
    textView.setOnClickListener(this);
    textView.setVisibility(this.mProc.mErrorState.getErrorReportReceiver() != null ? 0 : 8);
    try {
      IRestrictionPolicy asInterface =
          IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
      if (asInterface != null && !asInterface.isGoogleCrashReportAllowedAsUser(this.mProc.userId)) {
        textView.setVisibility(8);
      }
    } catch (RemoteException unused) {
    }
    ((TextView) findViewById(R.id.aerr_mute)).setOnClickListener(this);
    ((TextView) findViewById(R.id.afterDescendants)).setOnClickListener(this);
    findViewById(R.id.day_names).setVisibility(0);
  }

  @Override // android.view.View.OnClickListener
  public void onClick(View view) {
    int id = view.getId();
    if (id == 16908761) {
      this.mHandler.obtainMessage(1).sendToTarget();
    } else if (id == 16908763) {
      this.mHandler.obtainMessage(3).sendToTarget();
    } else {
      if (id != 16908765) {
        return;
      }
      this.mHandler.obtainMessage(2).sendToTarget();
    }
  }

  @Override // com.android.server.am.BaseErrorDialog
  public void closeDialog() {
    this.mHandler.obtainMessage(1).sendToTarget();
  }

  public class Data {
    public final ApplicationInfo aInfo;
    public final boolean aboveSystem;
    public final boolean isContinuousAnr;
    public final ProcessRecord proc;

    public Data(
        ProcessRecord processRecord, ApplicationInfo applicationInfo, boolean z, boolean z2) {
      this.proc = processRecord;
      this.aInfo = applicationInfo;
      this.aboveSystem = z;
      this.isContinuousAnr = z2;
    }
  }
}
