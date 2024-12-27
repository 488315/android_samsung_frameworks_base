package com.android.server.wm;

import android.content.ComponentName;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;

import com.android.server.vr.VrManagerService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ActivityTaskManagerService$$ExternalSyntheticLambda5(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ComponentName componentName;
        boolean z;
        int i;
        ComponentName componentName2;
        boolean z2;
        int i2;
        boolean z3;
        switch (this.$r8$classId) {
            case 0:
                ActivityTaskManagerService activityTaskManagerService =
                        (ActivityTaskManagerService) this.f$0;
                ActivityRecord activityRecord = (ActivityRecord) this.f$1;
                VrController vrController = activityTaskManagerService.mVrController;
                VrManagerService.LocalService localService = vrController.mVrService;
                boolean z4 = true;
                if (localService == null) {
                    z2 = false;
                } else {
                    synchronized (vrController.mGlobalAmLock) {
                        componentName = activityRecord.requestedVrComponent;
                        z = componentName != null;
                        i = activityRecord.mUserId;
                        componentName2 = activityRecord.info.getComponentName();
                        WindowProcessController windowProcessController = activityRecord.app;
                        int i3 = vrController.mVrState;
                        if (z) {
                            vrController.mVrState |= 1;
                        } else {
                            vrController.mVrState &= -2;
                        }
                        z2 = i3 != vrController.mVrState;
                        if (z2) {
                            if (windowProcessController != null) {
                                int i4 = windowProcessController.mVrThreadTid;
                                if (i4 > 0) {
                                    vrController.setVrRenderThreadLocked(
                                            i4, windowProcessController.mCurSchedGroup, false);
                                }
                            } else {
                                vrController.updateVrRenderThreadLocked(0, false);
                            }
                        }
                        WindowProcessController windowProcessController2 = activityRecord.app;
                        i2 = windowProcessController2 != null ? windowProcessController2.mPid : -1;
                    }
                    VrManagerService.this.setVrMode(z, componentName, i, i2, componentName2);
                }
                if (z2) {
                    WindowManagerGlobalLock windowManagerGlobalLock =
                            activityTaskManagerService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            if (activityTaskManagerService.mVrController.mVrState == 0) {
                                z4 = false;
                            }
                            activityTaskManagerService.mWindowManager.disableNonVrUi(z4);
                            if (z4) {
                                activityTaskManagerService.mRootWindowContainer
                                        .removeRootTasksInWindowingModes(2);
                            }
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                return;
            case 1:
                ActivityTaskManagerService.LocalService localService2 =
                        (ActivityTaskManagerService.LocalService) this.f$0;
                WindowProcessController windowProcessController3 =
                        (WindowProcessController) this.f$1;
                synchronized (ActivityTaskManagerService.this.mIdsLock) {
                    Set idsClearSet = ActivityTaskManagerService.this.getIdsClearSet();
                    synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                        Integer valueOf = Integer.valueOf(windowProcessController3.mUid);
                        if (idsClearSet != null
                                && idsClearSet.contains(valueOf)
                                && windowProcessController3.hasThread()) {
                            try {
                                z3 = true;
                                windowProcessController3.mThread.clearIdsTrainingData(true);
                                idsClearSet.remove(valueOf);
                            } catch (RemoteException unused) {
                                Log.e("ActivityTaskManager", "Failed to clear Ids Training Data");
                            }
                        }
                        z3 = false;
                    }
                    if (z3) {
                        try {
                            ObjectOutputStream objectOutputStream =
                                    new ObjectOutputStream(
                                            new FileOutputStream("/data/system/idsFile.txt"));
                            try {
                                objectOutputStream.writeObject(idsClearSet);
                                objectOutputStream.close();
                            } catch (Throwable th2) {
                                try {
                                    objectOutputStream.close();
                                } catch (Throwable th3) {
                                    th2.addSuppressed(th3);
                                }
                                throw th2;
                            }
                        } catch (IOException unused2) {
                            Slog.w(
                                    "ActivityTaskManager",
                                    "Error writing IDS file during bindApplication.");
                        }
                    }
                }
                return;
            default:
                ActivityTaskManagerService.LocalService localService3 =
                        (ActivityTaskManagerService.LocalService) this.f$0;
                CharSequence charSequence = (CharSequence) this.f$1;
                localService3.getClass();
                ActivityTaskManagerService activityTaskManagerService2 =
                        ActivityTaskManagerService.this;
                new FactoryErrorDialog(activityTaskManagerService2.mUiContext, charSequence).show();
                activityTaskManagerService2.mAmInternal.ensureBootCompleted();
                return;
        }
    }
}
