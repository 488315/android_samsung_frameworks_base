package com.android.server.am;

import android.R;
import android.app.ApplicationErrorReport;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.text.BidiFormatter;
import android.util.Log;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.samsung.android.feature.SemGateConfig;
import com.samsung.android.knox.restriction.IRestrictionPolicy;

/* loaded from: classes.dex */
public final class AppErrorDialog extends BaseErrorDialog implements View.OnClickListener {
  public static int ALREADY_SHOWING = -3;
  public static int BACKGROUND_USER = -2;
  public static int CANT_SHOW = -1;
  public final Handler mHandler;
  public final boolean mIsRestartable;
  public final ProcessRecord mProc;
  public final ActivityManagerGlobalLock mProcLock;
  public final AppErrorResult mResult;
  public final ActivityManagerService mService;

  public class Data {
    public ApplicationErrorReport.CrashInfo crashInfo;
    public boolean isRestartableForService;
    public ProcessRecord proc;
    public boolean repeating;
    public AppErrorResult result;
    public int taskId;
  }

  public AppErrorDialog(Context context, ActivityManagerService activityManagerService, Data data) {
    super(context);
    CharSequence applicationLabel;
    Handler handler = new Handler() { // from class: com.android.server.am.AppErrorDialog.1
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            AppErrorDialog.this.setResult(message.what);
            AppErrorDialog.this.dismiss();
          }
        };
    this.mHandler = handler;
    Resources resources = context.getResources();
    this.mService = activityManagerService;
    this.mProcLock = activityManagerService.mProcLock;
    ProcessRecord processRecord = data.proc;
    this.mProc = processRecord;
    this.mResult = data.result;
    boolean z = false;
    if ((data.taskId != -1 || data.isRestartableForService)
        && Settings.Global.getInt(context.getContentResolver(), "show_restart_in_crash_dialog", 0)
            != 0) {
      z = true;
    }
    this.mIsRestartable = z;
    BidiFormatter bidiFormatter = BidiFormatter.getInstance();
    if (processRecord.getPkgList().size() == 1
        && (applicationLabel = context.getPackageManager().getApplicationLabel(processRecord.info))
            != null) {
      setTitle(
          resources.getString(
              data.repeating
                  ? R.string.biometric_face_not_recognized
                  : R.string.biometric_error_user_canceled,
              bidiFormatter.unicodeWrap(applicationLabel.toString()),
              bidiFormatter.unicodeWrap(processRecord.info.processName)));
    } else {
      setTitle(
          resources.getString(
              data.repeating
                  ? R.string.bluetooth_a2dp_audio_route_name
                  : R.string.bluetooth_a2dp_audio_route_id,
              bidiFormatter.unicodeWrap(processRecord.processName.toString())));
    }
    setCancelable(true);
    setCancelMessage(handler.obtainMessage(7));
    WindowManager.LayoutParams attributes = getWindow().getAttributes();
    attributes.setTitle("Application Error: " + processRecord.info.processName);
    attributes.privateFlags = attributes.privateFlags | 272;
    getWindow().setAttributes(attributes);
    if (processRecord.isPersistent()) {
      getWindow().setType(2010);
    }
    handler.sendMessageDelayed(
        handler.obtainMessage(6),
        BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
    if (SemGateConfig.isGateEnabled()) {
      Slog.i(
          "AppErrorDialog",
          "Showing crash dialog for package "
              + processRecord.info.packageName
              + " u"
              + processRecord.userId);
      setOnShowListener(
          new DialogInterface
              .OnShowListener() { // from class:
                                  // com.android.server.am.AppErrorDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
              AppErrorDialog.this.lambda$new$0(dialogInterface);
            }
          });
      setOnDismissListener(
          new DialogInterface
              .OnDismissListener() { // from class:
                                     // com.android.server.am.AppErrorDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
              Log.i("GATE", "<GATE-M>APP_FC:FC dialog has been cleared</GATE-M>");
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(DialogInterface dialogInterface) {
    ProcessRecord processRecord = this.mProc;
    String str = processRecord == null ? "" : processRecord.processName;
    Button button = ((AppErrorDialog) dialogInterface).getButton(-1);
    button.getLocationOnScreen(new int[2]);
    Log.i(
        "GATE",
        "<GATE-M>APP_FC:"
            + str
            + ","
            + Math.round(r1[0] + (button.getWidth() / 2))
            + ","
            + Math.round(r1[1] + (button.getHeight() / 2))
            + "</GATE-M>");
    Log.i("GATE", "<GATE-M>APP_FC:Storing dumpstate at /data/log, dumpstate_app_error</GATE-M>");
    button.performClick();
  }

  @Override // android.app.AlertDialog, android.app.Dialog
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    FrameLayout frameLayout = (FrameLayout) findViewById(R.id.custom);
    Context context = getContext();
    LayoutInflater.from(context)
        .inflate(R.layout.app_permission_item_old, (ViewGroup) frameLayout, true);
    boolean z = this.mProc.mErrorState.getErrorReportReceiver() != null;
    TextView textView = (TextView) findViewById(R.id.aerr_wait);
    textView.setOnClickListener(this);
    textView.setVisibility(this.mIsRestartable ? 0 : 8);
    TextView textView2 = (TextView) findViewById(R.id.aerr_restart);
    textView2.setOnClickListener(this);
    textView2.setVisibility(z ? 0 : 8);
    try {
      IRestrictionPolicy asInterface =
          IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
      if (asInterface != null && !asInterface.isGoogleCrashReportAllowedAsUser(this.mProc.userId)) {
        textView2.setVisibility(8);
      }
    } catch (RemoteException unused) {
    }
    ((TextView) findViewById(R.id.aerr_mute)).setOnClickListener(this);
    TextView textView3 = (TextView) findViewById(R.id.aerr_close);
    textView3.setOnClickListener(this);
    if (Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) == 0) {
      textView3.setVisibility(8);
    }
    boolean z2 =
        (Build.IS_USER
                || Settings.Global.getInt(
                        context.getContentResolver(), "development_settings_enabled", 0)
                    == 0
                || Settings.Global.getInt(
                        context.getContentResolver(), "show_mute_in_crash_dialog", 0)
                    == 0)
            ? false
            : true;
    TextView textView4 = (TextView) findViewById(R.id.aerr_report);
    textView4.setOnClickListener(this);
    textView4.setVisibility(z2 ? 0 : 8);
    findViewById(R.id.day_names).setVisibility(0);
  }

  @Override // android.app.Dialog, android.content.DialogInterface
  public void dismiss() {
    if (!this.mResult.mHasResult) {
      setResult(1);
    }
    super.dismiss();
  }

  public final void setResult(int i) {
    ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
    ActivityManagerService.boostPriorityForProcLockedSection();
    synchronized (activityManagerGlobalLock) {
      try {
        ProcessRecord processRecord = this.mProc;
        if (processRecord != null) {
          processRecord.mErrorState.getDialogController().clearCrashDialogs(false);
        }
      } catch (Throwable th) {
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        throw th;
      }
    }
    ActivityManagerService.resetPriorityAfterProcLockedSection();
    this.mResult.set(i);
    this.mHandler.removeMessages(6);
  }

  @Override // android.view.View.OnClickListener
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.aerr_close:
        this.mHandler.obtainMessage(8).sendToTarget();
        break;
      case R.id.aerr_mute:
        this.mHandler.obtainMessage(1).sendToTarget();
        break;
      case R.id.aerr_report:
        this.mHandler.obtainMessage(5).sendToTarget();
        break;
      case R.id.aerr_restart:
        this.mHandler.obtainMessage(2).sendToTarget();
        break;
      case R.id.aerr_wait:
        this.mHandler.obtainMessage(3).sendToTarget();
        break;
    }
  }
}
