package com.android.server.wm;

import android.os.RemoteException;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda7
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RootWindowContainer f$0;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda7(
            RootWindowContainer rootWindowContainer, int i) {
        this.$r8$classId = i;
        this.f$0 = rootWindowContainer;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        RootWindowContainer rootWindowContainer = this.f$0;
        switch (i) {
            case 0:
                Task task = (Task) obj;
                rootWindowContainer.getClass();
                int i2 = task.mLayerRank;
                ActivityRecord activityRecord = task.topRunningActivityLocked();
                if (activityRecord == null || !activityRecord.isVisibleRequested()) {
                    task.mLayerRank = -1;
                } else {
                    int i3 = rootWindowContainer.mTmpTaskLayerRank + 1;
                    rootWindowContainer.mTmpTaskLayerRank = i3;
                    task.mLayerRank = i3;
                }
                if (task.mLayerRank != i2) {
                    task.forAllActivities(
                            new RootWindowContainer$$ExternalSyntheticLambda7(
                                    rootWindowContainer, 2));
                    break;
                }
                break;
            case 1:
                WindowState windowState = (WindowState) obj;
                rootWindowContainer.getClass();
                if (windowState.mHasSurface) {
                    try {
                        windowState.mClient.closeSystemDialogs(
                                rootWindowContainer.mCloseSystemDialogsReason);
                        break;
                    } catch (RemoteException unused) {
                        return;
                    }
                }
                break;
            default:
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                rootWindowContainer.getClass();
                if (activityRecord2.hasProcess()) {
                    rootWindowContainer.mTaskSupervisor.onProcessActivityStateChanged(
                            activityRecord2.app, true);
                    break;
                }
                break;
        }
    }
}
