package com.android.server.am;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppWaitingForDebuggerDialog extends BaseErrorDialog {
    public final AnonymousClass1 mHandler;
    public final ProcessRecord mProc;
    public final ActivityManagerService mService;

    public AppWaitingForDebuggerDialog(ActivityManagerService activityManagerService, Context context, ProcessRecord processRecord) {
        super(context);
        Handler handler = new Handler() { // from class: com.android.server.am.AppWaitingForDebuggerDialog.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                AppWaitingForDebuggerDialog appWaitingForDebuggerDialog = AppWaitingForDebuggerDialog.this;
                appWaitingForDebuggerDialog.mService.killAppAtUsersRequest(appWaitingForDebuggerDialog.mProc);
            }
        };
        this.mService = activityManagerService;
        this.mProc = processRecord;
        CharSequence applicationLabel = context.getPackageManager().getApplicationLabel(processRecord.info);
        setCancelable(false);
        StringBuilder sb = new StringBuilder();
        if (applicationLabel == null || applicationLabel.length() <= 0) {
            sb.append("Process ");
            sb.append(processRecord.processName);
        } else {
            sb.append("Application ");
            sb.append(applicationLabel);
            sb.append(" (process ");
            sb.append(processRecord.processName);
            sb.append(")");
        }
        sb.append(" is waiting for the debugger to attach.");
        setMessage(sb.toString());
        setButton(-1, "Force Close", handler.obtainMessage(1, processRecord));
        setTitle("Waiting For Debugger");
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.privateFlags |= 16;
        attributes.setTitle("Waiting For Debugger: " + processRecord.info.processName);
        getWindow().setAttributes(attributes);
    }

    @Override // com.android.server.am.BaseErrorDialog
    public final void closeDialog() {
    }
}
