package com.android.server.am;

import android.R;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public final class AppNotRespondingDialog extends BaseErrorDialog implements View.OnClickListener {
    public final Data mData;
    public final AnonymousClass1 mHandler;
    public final ProcessRecord mProc;
    public final ActivityManagerService mService;

    public final class Data {
        public final ApplicationInfo aInfo;
        public final boolean aboveSystem;
        public final boolean isContinuousAnr;
        public final ProcessRecord proc;

        public Data(
                ProcessRecord processRecord,
                ApplicationInfo applicationInfo,
                boolean z,
                boolean z2) {
            this.proc = processRecord;
            this.aInfo = applicationInfo;
            this.aboveSystem = z;
            this.isContinuousAnr = z2;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.am.AppNotRespondingDialog$1] */
    public AppNotRespondingDialog(
            ActivityManagerService activityManagerService, Context context, Data data) {
        super(context);
        int i;
        this.mHandler =
                new Handler() { // from class: com.android.server.am.AppNotRespondingDialog.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        MetricsLogger.action(
                                AppNotRespondingDialog.this.getContext(),
                                FrameworkStatsLog
                                        .APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_MEDIA_SESSION_CALLBACK,
                                message.what);
                        int i2 = message.what;
                        Intent intent = null;
                        if (i2 == 1) {
                            AppNotRespondingDialog appNotRespondingDialog =
                                    AppNotRespondingDialog.this;
                            appNotRespondingDialog.mService.killAppAtUsersRequest(
                                    appNotRespondingDialog.mProc);
                        } else if (i2 == 2 || i2 == 3) {
                            ActivityManagerService activityManagerService2 =
                                    AppNotRespondingDialog.this.mService;
                            ActivityManagerService.boostPriorityForLockedSection();
                            synchronized (activityManagerService2) {
                                try {
                                    AppNotRespondingDialog appNotRespondingDialog2 =
                                            AppNotRespondingDialog.this;
                                    ProcessRecord processRecord = appNotRespondingDialog2.mProc;
                                    ProcessErrorStateRecord processErrorStateRecord =
                                            processRecord.mErrorState;
                                    if (message.what == 3) {
                                        AppErrors appErrors =
                                                appNotRespondingDialog2.mService.mAppErrors;
                                        long currentTimeMillis = System.currentTimeMillis();
                                        appErrors.getClass();
                                        intent =
                                                AppErrors.createAppErrorIntentLOSP(
                                                        processRecord, currentTimeMillis, null);
                                    }
                                    ActivityManagerProcLock activityManagerProcLock =
                                            AppNotRespondingDialog.this.mService.mProcLock;
                                    ActivityManagerService.boostPriorityForProcLockedSection();
                                    synchronized (activityManagerProcLock) {
                                        try {
                                            processErrorStateRecord.setNotResponding(false);
                                            processErrorStateRecord.mDialogController
                                                    .clearAnrDialogs();
                                        } catch (Throwable th) {
                                            ActivityManagerService
                                                    .resetPriorityAfterProcLockedSection();
                                            throw th;
                                        }
                                    }
                                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                                    AppNotRespondingDialog.this.mService.mServices
                                            .scheduleServiceTimeoutLocked(processRecord);
                                    AppNotRespondingDialog appNotRespondingDialog3 =
                                            AppNotRespondingDialog.this;
                                    Data data2 = appNotRespondingDialog3.mData;
                                    if (data2.isContinuousAnr) {
                                        appNotRespondingDialog3.mService.mInternal
                                                .rescheduleAnrDialog(data2);
                                    }
                                } catch (Throwable th2) {
                                    ActivityManagerService.resetPriorityAfterLockedSection();
                                    throw th2;
                                }
                            }
                            ActivityManagerService.resetPriorityAfterLockedSection();
                        }
                        if (intent != null) {
                            try {
                                AppNotRespondingDialog.this.getContext().startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Slog.w(
                                        "AppNotRespondingDialog",
                                        "bug report receiver dissappeared",
                                        e);
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
                applicationInfo != null
                        ? applicationInfo.loadLabel(context.getPackageManager())
                        : null;
        if (processRecord.mPkgList.size() != 1
                || (charSequence =
                                context.getPackageManager().getApplicationLabel(processRecord.info))
                        == null) {
            if (loadLabel != null) {
                charSequence = processRecord.processName;
                i = R.string.car_loading_profile;
            } else {
                loadLabel = processRecord.processName;
                i = R.string.car_mode_disable_notification_title;
            }
        } else if (loadLabel != null) {
            i = R.string.capital_on;
        } else {
            charSequence = processRecord.processName;
            i = 17039814;
            loadLabel = charSequence;
        }
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        setTitle(
                charSequence != null
                        ? resources.getString(
                                i,
                                bidiFormatter.unicodeWrap(loadLabel.toString()),
                                bidiFormatter.unicodeWrap(charSequence.toString()))
                        : resources.getString(i, bidiFormatter.unicodeWrap(loadLabel.toString())));
        if (data.aboveSystem) {
            getWindow().setType(2010);
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.setTitle("Application Not Responding: " + processRecord.info.processName);
        attributes.privateFlags = 272;
        getWindow().setAttributes(attributes);
        if (SemGateConfig.isGateEnabled()) {
            setOnShowListener(
                    new DialogInterface.OnShowListener() { // from class:
                        // com.android.server.am.AppNotRespondingDialog$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnShowListener
                        public final void onShow(DialogInterface dialogInterface) {
                            ProcessRecord processRecord2 = AppNotRespondingDialog.this.mProc;
                            String str = processRecord2 == null ? "" : processRecord2.processName;
                            Button button =
                                    ((AppNotRespondingDialog) dialogInterface).getButton(-1);
                            button.getLocationOnScreen(new int[2]);
                            Log.i(
                                    "GATE",
                                    "<GATE-M>APP_ANR:"
                                            + str
                                            + ","
                                            + Math.round((button.getWidth() / 2) + r1[0])
                                            + ","
                                            + Math.round((button.getHeight() / 2) + r1[1])
                                            + "</GATE-M>");
                            Log.i(
                                    "GATE",
                                    "<GATE-M>APP_ANR:Storing dumpstate at /data/log/,"
                                        + " dumpstate_app_anr</GATE-M>");
                            button.performClick();
                        }
                    });
            setOnDismissListener(new AppNotRespondingDialog$$ExternalSyntheticLambda1());
        }
    }

    @Override // com.android.server.am.BaseErrorDialog
    public final void closeDialog() {
        obtainMessage(1).sendToTarget();
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int id = view.getId();
        if (id == 16908762) {
            obtainMessage(1).sendToTarget();
        } else if (id == 16908764) {
            obtainMessage(3).sendToTarget();
        } else {
            if (id != 16908766) {
                return;
            }
            obtainMessage(2).sendToTarget();
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LayoutInflater.from(getContext())
                .inflate(
                        R.layout.app_permission_item_money,
                        (ViewGroup) findViewById(R.id.custom),
                        true);
        TextView textView = (TextView) findViewById(R.id.aerr_wait);
        textView.setOnClickListener(this);
        textView.setVisibility(this.mProc.mErrorState.mErrorReportReceiver != null ? 0 : 8);
        try {
            IRestrictionPolicy asInterface =
                    IRestrictionPolicy.Stub.asInterface(
                            ServiceManager.getService("restriction_policy"));
            if (asInterface != null
                    && !asInterface.isGoogleCrashReportAllowedAsUser(this.mProc.userId)) {
                textView.setVisibility(8);
            }
        } catch (RemoteException unused) {
        }
        ((TextView) findViewById(R.id.aerr_report)).setOnClickListener(this);
        ((TextView) findViewById(R.id.alarm)).setOnClickListener(this);
        findViewById(R.id.default_activity_button).setVisibility(0);
    }
}
