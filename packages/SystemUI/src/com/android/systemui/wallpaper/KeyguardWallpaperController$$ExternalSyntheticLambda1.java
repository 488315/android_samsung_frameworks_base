package com.android.systemui.wallpaper;

import android.os.RemoteException;
import android.util.Log;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                keyguardWallpaperController.getClass();
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
                KeyguardWallpaperController.$r8$lambda$EG0gQOZLZG2rZ3JVMqdpjjIo4Yo(keyguardWallpaperController);
                break;
        }
    }
}
