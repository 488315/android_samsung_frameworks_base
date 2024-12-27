package com.android.server.am;

import android.app.AnrController;
import android.content.Context;
import com.android.server.am.AppErrorDialog;
import com.android.server.am.AppNotRespondingDialog;
import com.android.server.wm.WindowManagerInternal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ErrorDialogController {
    public AnrController mAnrController;
    public List mAnrDialogs;
    public final ProcessRecord mApp;
    public List mCrashDialogs;
    public final ActivityManagerGlobalLock mProcLock;
    public final ActivityManagerService mService;
    public List mViolationDialogs;
    public AppWaitingForDebuggerDialog mWaitDialog;

    public ErrorDialogController(ProcessRecord processRecord) {
        this.mApp = processRecord;
        ActivityManagerService activityManagerService = processRecord.mService;
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
    }

    public final void clearAnrDialogs() {
        List list = this.mAnrDialogs;
        if (list == null) {
            return;
        }
        scheduleForAllDialogs(list, new ErrorDialogController$$ExternalSyntheticLambda0(0));
        this.mAnrDialogs = null;
        this.mAnrController = null;
    }

    public final List getDisplayContexts(boolean z) {
        ArrayList arrayList = new ArrayList();
        if (!z) {
            this.mApp.mWindowProcessController.getDisplayContextsWithErrorDialogs(arrayList);
        }
        if (arrayList.isEmpty() || z) {
            ActivityManagerService activityManagerService = this.mService;
            WindowManagerInternal windowManagerInternal = activityManagerService.mWmInternal;
            arrayList.add(windowManagerInternal != null ? windowManagerInternal.getTopFocusedDisplayUiContext() : activityManagerService.mUiContext);
        }
        return arrayList;
    }

    public final void scheduleForAllDialogs(final List list, final Consumer consumer) {
        this.mService.mUiHandler.post(new Runnable() { // from class: com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ErrorDialogController errorDialogController = ErrorDialogController.this;
                List list2 = list;
                Consumer consumer2 = consumer;
                if (list2 == null) {
                    errorDialogController.getClass();
                    return;
                }
                errorDialogController.getClass();
                for (int size = list2.size() - 1; size >= 0; size--) {
                    consumer2.accept((BaseErrorDialog) list2.get(size));
                }
            }
        });
    }

    public final void showAnrDialogs(AppNotRespondingDialog.Data data) {
        List displayContexts = getDisplayContexts(this.mApp.mErrorState.isSilentAnr());
        this.mAnrDialogs = new ArrayList();
        ArrayList arrayList = (ArrayList) displayContexts;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((ArrayList) this.mAnrDialogs).add(new AppNotRespondingDialog(this.mService, (Context) arrayList.get(size), data));
        }
        scheduleForAllDialogs(this.mAnrDialogs, new ErrorDialogController$$ExternalSyntheticLambda0(1));
    }

    public final void showCrashDialogs(AppErrorDialog.Data data) {
        List displayContexts = getDisplayContexts(false);
        this.mCrashDialogs = new ArrayList();
        ArrayList arrayList = (ArrayList) displayContexts;
        int size = arrayList.size();
        while (true) {
            size--;
            ActivityManagerService activityManagerService = this.mService;
            if (size < 0) {
                activityManagerService.mUiHandler.post(new ErrorDialogController$$ExternalSyntheticLambda2(1, this));
                return;
            }
            ((ArrayList) this.mCrashDialogs).add(new AppErrorDialog((Context) arrayList.get(size), activityManagerService, data));
        }
    }

    public final void showViolationDialogs(AppErrorResult appErrorResult) {
        List displayContexts = getDisplayContexts(false);
        this.mViolationDialogs = new ArrayList();
        ArrayList arrayList = (ArrayList) displayContexts;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((ArrayList) this.mViolationDialogs).add(new StrictModeViolationDialog((Context) arrayList.get(size), this.mService, appErrorResult, this.mApp));
        }
        scheduleForAllDialogs(this.mViolationDialogs, new ErrorDialogController$$ExternalSyntheticLambda0(1));
    }
}
