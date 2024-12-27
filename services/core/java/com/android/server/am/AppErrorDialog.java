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
import com.samsung.android.feature.SemGateConfig;
import com.samsung.android.knox.restriction.IRestrictionPolicy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppErrorDialog extends BaseErrorDialog implements View.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mHandler;
    public final boolean mIsRestartable;
    public final ProcessRecord mProc;
    public final ActivityManagerProcLock mProcLock;
    public final AppErrorResult mResult;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Data {
        public ApplicationErrorReport.CrashInfo crashInfo;
        public boolean isRestartableForService;
        public ProcessRecord proc;
        public boolean repeating;
        public AppErrorResult result;
        public int taskId;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.os.Handler, com.android.server.am.AppErrorDialog$1] */
    public AppErrorDialog(Context context, ActivityManagerService activityManagerService, Data data) {
        super(context);
        CharSequence applicationLabel;
        ?? r0 = new Handler() { // from class: com.android.server.am.AppErrorDialog.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                AppErrorDialog appErrorDialog = AppErrorDialog.this;
                appErrorDialog.setResult(i);
                appErrorDialog.dismiss();
            }
        };
        this.mHandler = r0;
        Resources resources = context.getResources();
        this.mProcLock = activityManagerService.mProcLock;
        ProcessRecord processRecord = data.proc;
        this.mProc = processRecord;
        this.mResult = data.result;
        boolean z = false;
        if ((data.taskId != -1 || data.isRestartableForService) && Settings.Global.getInt(context.getContentResolver(), "show_restart_in_crash_dialog", 0) != 0) {
            z = true;
        }
        this.mIsRestartable = z;
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        if (processRecord.mPkgList.size() != 1 || (applicationLabel = context.getPackageManager().getApplicationLabel(processRecord.info)) == null) {
            setTitle(resources.getString(data.repeating ? R.string.bugreport_status : R.string.bugreport_screenshot_success_toast, bidiFormatter.unicodeWrap(processRecord.processName.toString())));
        } else {
            setTitle(resources.getString(data.repeating ? R.string.bugreport_option_full_title : R.string.bugreport_option_full_summary, bidiFormatter.unicodeWrap(applicationLabel.toString()), bidiFormatter.unicodeWrap(processRecord.info.processName)));
        }
        setCancelable(true);
        setCancelMessage(r0.obtainMessage(7));
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.setTitle("Application Error: " + processRecord.info.processName);
        attributes.privateFlags = attributes.privateFlags | 272;
        getWindow().setAttributes(attributes);
        if (processRecord.mPersistent) {
            getWindow().setType(2010);
        }
        r0.sendMessageDelayed(r0.obtainMessage(6), 300000L);
        if (SemGateConfig.isGateEnabled()) {
            Slog.i("AppErrorDialog", "Showing crash dialog for package " + processRecord.info.packageName + " u" + processRecord.userId);
            setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.server.am.AppErrorDialog$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    ProcessRecord processRecord2 = AppErrorDialog.this.mProc;
                    String str = processRecord2 == null ? "" : processRecord2.processName;
                    Button button = ((AppErrorDialog) dialogInterface).getButton(-1);
                    button.getLocationOnScreen(new int[2]);
                    Log.i("GATE", "<GATE-M>APP_FC:" + str + "," + Math.round((button.getWidth() / 2) + r1[0]) + "," + Math.round((button.getHeight() / 2) + r1[1]) + "</GATE-M>");
                    Log.i("GATE", "<GATE-M>APP_FC:Storing dumpstate at /data/log, dumpstate_app_error</GATE-M>");
                    button.performClick();
                }
            });
            setOnDismissListener(new AppErrorDialog$$ExternalSyntheticLambda1());
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        if (!this.mResult.mHasResult) {
            setResult(1);
        }
        super.dismiss();
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (view.getId()) {
            case R.id.aerr_mute:
                obtainMessage(8).sendToTarget();
                break;
            case R.id.aerr_report:
                obtainMessage(1).sendToTarget();
                break;
            case R.id.aerr_restart:
                obtainMessage(5).sendToTarget();
                break;
            case R.id.aerr_wait:
                obtainMessage(2).sendToTarget();
                break;
            case R.id.afterDescendants:
                obtainMessage(3).sendToTarget();
                break;
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.custom);
        Context context = getContext();
        LayoutInflater.from(context).inflate(R.layout.app_perms_summary, (ViewGroup) frameLayout, true);
        boolean z = this.mProc.mErrorState.mErrorReportReceiver != null;
        TextView textView = (TextView) findViewById(R.id.afterDescendants);
        textView.setOnClickListener(this);
        textView.setVisibility(this.mIsRestartable ? 0 : 8);
        TextView textView2 = (TextView) findViewById(R.id.aerr_wait);
        textView2.setOnClickListener(this);
        textView2.setVisibility(z ? 0 : 8);
        try {
            IRestrictionPolicy asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
            if (asInterface != null && !asInterface.isGoogleCrashReportAllowedAsUser(this.mProc.userId)) {
                textView2.setVisibility(8);
            }
        } catch (RemoteException unused) {
        }
        ((TextView) findViewById(R.id.aerr_report)).setOnClickListener(this);
        TextView textView3 = (TextView) findViewById(R.id.aerr_mute);
        textView3.setOnClickListener(this);
        if (Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) == 0) {
            textView3.setVisibility(8);
        }
        boolean z2 = (Build.IS_USER || Settings.Global.getInt(context.getContentResolver(), "development_settings_enabled", 0) == 0 || Settings.Global.getInt(context.getContentResolver(), "show_mute_in_crash_dialog", 0) == 0) ? false : true;
        TextView textView4 = (TextView) findViewById(R.id.aerr_restart);
        textView4.setOnClickListener(this);
        textView4.setVisibility(z2 ? 0 : 8);
        findViewById(R.id.default_activity_button).setVisibility(0);
    }

    public final void setResult(int i) {
        ActivityManagerProcLock activityManagerProcLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerProcLock) {
            try {
                ProcessRecord processRecord = this.mProc;
                if (processRecord != null) {
                    ErrorDialogController errorDialogController = processRecord.mErrorState.mDialogController;
                    if (errorDialogController.mCrashDialogs != null) {
                        errorDialogController.mCrashDialogs = null;
                    }
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        this.mResult.set(i);
        removeMessages(6);
    }
}
