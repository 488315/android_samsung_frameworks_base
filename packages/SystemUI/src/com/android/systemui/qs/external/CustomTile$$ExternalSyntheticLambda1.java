package com.android.systemui.qs.external;

import android.os.RemoteException;
import com.android.systemui.QpRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.qs.tileimpl.SQSTileImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class CustomTile$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CustomTile f$0;

    public /* synthetic */ CustomTile$$ExternalSyntheticLambda1(CustomTile customTile, int i) {
        this.$r8$classId = i;
        this.f$0 = customTile;
    }

    @Override // java.lang.Runnable
    public final void run() {
        long j;
        int i = this.$r8$classId;
        CustomTile customTile = this.f$0;
        switch (i) {
            case 0:
                customTile.getClass();
                CustomTile$$ExternalSyntheticLambda1 customTile$$ExternalSyntheticLambda1 = new CustomTile$$ExternalSyntheticLambda1(customTile, 2);
                if (QpRune.QUICK_SUBSCREEN_PANEL) {
                    DisplayLifecycle displayLifecycle = customTile.mDisplayLifecycle;
                    if (!(displayLifecycle != null ? displayLifecycle.mIsFolderOpened : false)) {
                        j = 0;
                        ((SQSTileImpl) customTile).mHandler.postDelayed(customTile$$ExternalSyntheticLambda1, j);
                        break;
                    }
                }
                j = 200;
                ((SQSTileImpl) customTile).mHandler.postDelayed(customTile$$ExternalSyntheticLambda1, j);
            case 1:
                customTile.updateDefaultTileAndIcon();
                break;
            case 2:
                customTile.getClass();
                try {
                    customTile.mService.onUnlockComplete();
                    customTile.mServiceManager.setWaitingUnlockState(false);
                    ((SQSTileImpl) customTile).mHandler.postDelayed(customTile.mStopUnlockAndRun, 1000L);
                    break;
                } catch (RemoteException unused) {
                    return;
                }
            case 3:
                if (customTile.mTileServices.mIsBootCompleted) {
                    customTile.mInitialized = true;
                    customTile.mServiceManager.setBindRequested(true);
                    TileLifecycleManager tileLifecycleManager = customTile.mService;
                    tileLifecycleManager.onStartListening();
                    tileLifecycleManager.refreshDetailInfo();
                    break;
                }
                break;
            default:
                customTile.mInitialized = true;
                customTile.mServiceManager.setBindRequested(true);
                TileLifecycleManager tileLifecycleManager2 = customTile.mService;
                tileLifecycleManager2.onStartListening();
                tileLifecycleManager2.refreshDetailInfo();
                break;
        }
    }
}
