package com.android.systemui.wallpaper;

import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$attach$2;

/* loaded from: classes3.dex */
public final /* synthetic */ class KeyguardWallpaperController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardWallpaperController f$0;

    public /* synthetic */ KeyguardWallpaperController$$ExternalSyntheticLambda1(KeyguardWallpaperController keyguardWallpaperController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardWallpaperController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        KeyguardWallpaperController keyguardWallpaperController = this.f$0;
        switch (i) {
            case 0:
                KeyguardWallpaperController keyguardWallpaperController2 = KeyguardWallpaperController.sController;
                try {
                    Log.d("KeyguardWallpaperController", "setLockWallpaperCallback()");
                    keyguardWallpaperController.mService.setLockWallpaperCallback(keyguardWallpaperController);
                    break;
                } catch (RemoteException e) {
                    Log.e("KeyguardWallpaperController", "System dead?" + e);
                    return;
                }
            case 1:
                keyguardWallpaperController.mPluginWallpaperManager.onLockWallpaperChanged(1);
                break;
            default:
                boolean zIsNoSensorRequired = WallpaperUtils.isNoSensorRequired(keyguardWallpaperController.mContext);
                SecNotificationShadeWindowControllerHelperImpl$attach$2 secNotificationShadeWindowControllerHelperImpl$attach$2 = keyguardWallpaperController.mNoSensorConsumer;
                if (secNotificationShadeWindowControllerHelperImpl$attach$2 != null) {
                    secNotificationShadeWindowControllerHelperImpl$attach$2.accept(Boolean.valueOf(zIsNoSensorRequired));
                    break;
                }
                break;
        }
    }
}
