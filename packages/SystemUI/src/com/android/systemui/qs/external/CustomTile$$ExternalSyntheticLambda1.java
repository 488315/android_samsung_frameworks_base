package com.android.systemui.qs.external;

import android.content.pm.PackageManager;
import android.os.RemoteException;
import com.android.systemui.QpRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.qs.tileimpl.SQSTileImpl;

/* loaded from: classes2.dex */
public final /* synthetic */ class CustomTile$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CustomTile f$0;

    public /* synthetic */ CustomTile$$ExternalSyntheticLambda1(CustomTile customTile, int i) {
        this.$r8$classId = i;
        this.f$0 = customTile;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0065  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() throws PackageManager.NameNotFoundException {
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
                    }
                } else {
                    j = 200;
                }
                ((SQSTileImpl) customTile).mHandler.postDelayed(customTile$$ExternalSyntheticLambda1, j);
                break;
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
