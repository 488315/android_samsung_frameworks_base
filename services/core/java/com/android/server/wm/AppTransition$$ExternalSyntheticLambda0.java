package com.android.server.wm;

import android.os.Binder;
import android.os.RemoteException;
import android.view.AppTransitionAnimationSpec;
import android.view.IAppTransitionAnimationSpecsFuture;

import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class AppTransition$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ AppTransition f$0;
    public final /* synthetic */ IAppTransitionAnimationSpecsFuture f$1;

    public /* synthetic */ AppTransition$$ExternalSyntheticLambda0(
            AppTransition appTransition,
            IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture) {
        this.f$0 = appTransition;
        this.f$1 = iAppTransitionAnimationSpecsFuture;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AppTransitionAnimationSpec[] appTransitionAnimationSpecArr;
        AppTransition appTransition = this.f$0;
        IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture = this.f$1;
        appTransition.getClass();
        try {
            Binder.allowBlocking(iAppTransitionAnimationSpecsFuture.asBinder());
            appTransitionAnimationSpecArr = iAppTransitionAnimationSpecsFuture.get();
        } catch (RemoteException e) {
            AccountManagerService$$ExternalSyntheticOutline0.m(
                    "Failed to fetch app transition specs: ", e, "WindowManager");
            appTransitionAnimationSpecArr = null;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = appTransition.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                appTransition.mNextAppTransitionAnimationsSpecsPending = false;
                appTransition.overridePendingAppTransitionMultiThumb(
                        appTransitionAnimationSpecArr,
                        appTransition.mNextAppTransitionFutureCallback,
                        null,
                        appTransition.mNextAppTransitionScaleUp);
                appTransition.mNextAppTransitionFutureCallback = null;
                appTransition.mService.requestTraversal();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }
}
