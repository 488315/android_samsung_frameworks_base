package com.android.server.am;

import android.app.AnrController;
import android.content.Context;
import android.os.Handler;
import com.android.server.am.AppErrorDialog;
import com.android.server.am.AppNotRespondingDialog;
import com.android.server.wm.WindowManagerInternal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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

    public boolean hasCrashDialogs() {
        return this.mCrashDialogs != null;
    }

    public List getCrashDialogs() {
        return this.mCrashDialogs;
    }

    public boolean hasAnrDialogs() {
        return this.mAnrDialogs != null;
    }

    public List getAnrDialogs() {
        return this.mAnrDialogs;
    }

    public boolean hasViolationDialogs() {
        return this.mViolationDialogs != null;
    }

    public boolean hasDebugWaitingDialog() {
        return this.mWaitDialog != null;
    }

    public void clearAllErrorDialogs() {
        clearCrashDialogs();
        clearAnrDialogs();
        clearViolationDialogs();
        clearWaitingDialog();
    }

    public void clearCrashDialogs() {
        clearCrashDialogs(true);
    }

    public void clearCrashDialogs(boolean z) {
        List list = this.mCrashDialogs;
        if (list == null) {
            return;
        }
        if (z) {
            scheduleForAllDialogs(list, new ErrorDialogController$$ExternalSyntheticLambda4());
        }
        this.mCrashDialogs = null;
    }

    public void clearAnrDialogs() {
        List list = this.mAnrDialogs;
        if (list == null) {
            return;
        }
        scheduleForAllDialogs(list, new ErrorDialogController$$ExternalSyntheticLambda4());
        this.mAnrDialogs = null;
        this.mAnrController = null;
    }

    public void clearViolationDialogs() {
        List list = this.mViolationDialogs;
        if (list == null) {
            return;
        }
        scheduleForAllDialogs(list, new ErrorDialogController$$ExternalSyntheticLambda4());
        this.mViolationDialogs = null;
    }

    public void clearWaitingDialog() {
        final AppWaitingForDebuggerDialog appWaitingForDebuggerDialog = this.mWaitDialog;
        if (appWaitingForDebuggerDialog == null) {
            return;
        }
        Handler handler = this.mService.mUiHandler;
        Objects.requireNonNull(appWaitingForDebuggerDialog);
        handler.post(new Runnable() { // from class: com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BaseErrorDialog.this.dismiss();
            }
        });
        this.mWaitDialog = null;
    }

    public void scheduleForAllDialogs(final List list, final Consumer consumer) {
        this.mService.mUiHandler.post(new Runnable() { // from class: com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ErrorDialogController.this.lambda$scheduleForAllDialogs$0(list, consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleForAllDialogs$0(List list, Consumer consumer) {
        if (list != null) {
            forAllDialogs(list, consumer);
        }
    }

    public void forAllDialogs(List list, Consumer consumer) {
        for (int size = list.size() - 1; size >= 0; size--) {
            consumer.accept((BaseErrorDialog) list.get(size));
        }
    }

    public void showCrashDialogs(AppErrorDialog.Data data) {
        List displayContexts = getDisplayContexts(false);
        this.mCrashDialogs = new ArrayList();
        for (int size = displayContexts.size() - 1; size >= 0; size--) {
            this.mCrashDialogs.add(new AppErrorDialog((Context) displayContexts.get(size), this.mService, data));
        }
        this.mService.mUiHandler.post(new Runnable() { // from class: com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ErrorDialogController.this.lambda$showCrashDialogs$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCrashDialogs$1() {
        List list;
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                list = this.mCrashDialogs;
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        if (list != null) {
            forAllDialogs(list, new ErrorDialogController$$ExternalSyntheticLambda2());
        }
    }

    public void showAnrDialogs(AppNotRespondingDialog.Data data) {
        List displayContexts = getDisplayContexts(this.mApp.mErrorState.isSilentAnr());
        this.mAnrDialogs = new ArrayList();
        for (int size = displayContexts.size() - 1; size >= 0; size--) {
            this.mAnrDialogs.add(new AppNotRespondingDialog(this.mService, (Context) displayContexts.get(size), data));
        }
        scheduleForAllDialogs(this.mAnrDialogs, new ErrorDialogController$$ExternalSyntheticLambda2());
    }

    public void showViolationDialogs(AppErrorResult appErrorResult) {
        List displayContexts = getDisplayContexts(false);
        this.mViolationDialogs = new ArrayList();
        for (int size = displayContexts.size() - 1; size >= 0; size--) {
            this.mViolationDialogs.add(new StrictModeViolationDialog((Context) displayContexts.get(size), this.mService, appErrorResult, this.mApp));
        }
        scheduleForAllDialogs(this.mViolationDialogs, new ErrorDialogController$$ExternalSyntheticLambda2());
    }

    public void showDebugWaitingDialogs() {
        this.mWaitDialog = new AppWaitingForDebuggerDialog(this.mService, (Context) getDisplayContexts(true).get(0), this.mApp);
        this.mService.mUiHandler.post(new Runnable() { // from class: com.android.server.am.ErrorDialogController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ErrorDialogController.this.lambda$showDebugWaitingDialogs$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDebugWaitingDialogs$2() {
        AppWaitingForDebuggerDialog appWaitingForDebuggerDialog;
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                appWaitingForDebuggerDialog = this.mWaitDialog;
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        if (appWaitingForDebuggerDialog != null) {
            appWaitingForDebuggerDialog.show();
        }
    }

    public AnrController getAnrController() {
        return this.mAnrController;
    }

    public void setAnrController(AnrController anrController) {
        this.mAnrController = anrController;
    }

    public final List getDisplayContexts(boolean z) {
        Context context;
        ArrayList arrayList = new ArrayList();
        if (!z) {
            this.mApp.getWindowProcessController().getDisplayContextsWithErrorDialogs(arrayList);
        }
        if (arrayList.isEmpty() || z) {
            ActivityManagerService activityManagerService = this.mService;
            WindowManagerInternal windowManagerInternal = activityManagerService.mWmInternal;
            if (windowManagerInternal != null) {
                context = windowManagerInternal.getTopFocusedDisplayUiContext();
            } else {
                context = activityManagerService.mUiContext;
            }
            arrayList.add(context);
        }
        return arrayList;
    }

    public ErrorDialogController(ProcessRecord processRecord) {
        this.mApp = processRecord;
        ActivityManagerService activityManagerService = processRecord.mService;
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
    }
}
