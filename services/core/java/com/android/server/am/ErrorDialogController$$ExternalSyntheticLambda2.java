package com.android.server.am;

import java.util.ArrayList;
import java.util.List;

public final /* synthetic */ class ErrorDialogController$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ErrorDialogController$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AppWaitingForDebuggerDialog appWaitingForDebuggerDialog;
        List list;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ErrorDialogController errorDialogController = (ErrorDialogController) obj;
                ActivityManagerGlobalLock activityManagerGlobalLock =
                        errorDialogController.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        appWaitingForDebuggerDialog = errorDialogController.mWaitDialog;
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                if (appWaitingForDebuggerDialog != null) {
                    appWaitingForDebuggerDialog.show();
                    return;
                }
                return;
            case 1:
                ErrorDialogController errorDialogController2 = (ErrorDialogController) obj;
                ActivityManagerGlobalLock activityManagerGlobalLock2 =
                        errorDialogController2.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock2) {
                    try {
                        list = errorDialogController2.mCrashDialogs;
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                if (list != null) {
                    ArrayList arrayList = (ArrayList) list;
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        ((BaseErrorDialog) arrayList.get(size)).show();
                    }
                    return;
                }
                return;
            default:
                ((BaseErrorDialog) obj).dismiss();
                return;
        }
    }
}
