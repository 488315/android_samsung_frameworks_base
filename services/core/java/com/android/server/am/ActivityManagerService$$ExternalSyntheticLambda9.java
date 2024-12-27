package com.android.server.am;

import android.app.AppGlobals;
import android.hardware.display.DisplayManagerInternal;
import android.os.Build;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Slog;
import com.android.server.am.ActivityManagerService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda0;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ActivityManagerService$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ActivityManagerService$$ExternalSyntheticLambda9(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((DisplayManagerInternal) obj).onOverlayChanged();
                return;
            case 1:
                ((WindowManagerService) obj).onOverlayChanged();
                return;
            case 2:
                ActivityTaskManagerService.LocalService localService = (ActivityTaskManagerService.LocalService) ((ActivityTaskManagerInternal) obj);
                localService.getClass();
                if (Trace.isTagEnabled(32L)) {
                    Trace.traceBegin(32L, "showSystemReadyErrorDialogs");
                }
                boolean isBuildConsistent = Build.isBuildConsistent();
                WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        try {
                            if (AppGlobals.getPackageManager().hasSystemUidErrors()) {
                                Slog.e("ActivityTaskManager", "UIDs on the system are inconsistent, you need to wipe your data partition or your device will be unstable.");
                                ActivityTaskManagerService.this.mUiHandler.post(new ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda0(localService, 2));
                            }
                        } catch (RemoteException unused) {
                        }
                        if (!isBuildConsistent) {
                            Slog.e("ActivityTaskManager", "Build fingerprint is not consistent, warning user");
                            ActivityTaskManagerService.this.mUiHandler.post(new ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda0(localService, 3));
                        }
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                Trace.traceEnd(32L);
                return;
            default:
                ActivityManagerService.AnonymousClass12 anonymousClass12 = (ActivityManagerService.AnonymousClass12) obj;
                ActivityManagerProcLock activityManagerProcLock = ActivityManagerService.this.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerProcLock) {
                    try {
                        ActivityManagerService.this.mAppProfiler.requestPssAllProcsLPr(SystemClock.uptimeMillis(), false);
                    } catch (Throwable th2) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th2;
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                return;
        }
    }
}
