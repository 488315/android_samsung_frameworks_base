package com.android.server.wm;

import android.os.IBinder;

import com.android.server.inputmethod.InputMethodManagerInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda25 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda25(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                DisplayContent displayContent = (DisplayContent) obj;
                WindowManagerGlobalLock windowManagerGlobalLock =
                        displayContent.mWmService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (displayContent.mFixedRotationLaunchingApp != null
                                && displayContent.startAsyncRotation(false)) {
                            displayContent.getPendingTransaction().apply();
                        }
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            case 1:
                ((DisplayContent) obj).getClass();
                InputMethodManagerInternal.get().onImeParentChanged();
                return;
            case 2:
                ((DisplayContent) obj).removeImeSurfaceImmediately();
                return;
            default:
                InputMethodManagerInternal.get().reportImeControl((IBinder) obj);
                return;
        }
    }
}
