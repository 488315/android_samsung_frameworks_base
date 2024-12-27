package com.android.server.wm;

import android.os.IBinder;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class Session$$ExternalSyntheticLambda2 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IBinder f$0;

    public /* synthetic */ Session$$ExternalSyntheticLambda2(int i, IBinder iBinder) {
        this.$r8$classId = i;
        this.f$0 = iBinder;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i = this.$r8$classId;
        IBinder iBinder = this.f$0;
        WallpaperController wallpaperController = (WallpaperController) obj;
        switch (i) {
            case 0:
                WindowState windowState = wallpaperController.mWaitingOnWallpaper;
                if (windowState != null && windowState.mClient.asBinder() == iBinder) {
                    wallpaperController.mWaitingOnWallpaper = null;
                    wallpaperController.mService.mGlobalLock.notifyAll();
                    break;
                }
                break;
            default:
                WindowState windowState2 = wallpaperController.mWaitingOnWallpaper;
                if (windowState2 != null && windowState2.mClient.asBinder() == iBinder) {
                    wallpaperController.mWaitingOnWallpaper = null;
                    wallpaperController.mService.mGlobalLock.notifyAll();
                    break;
                }
                break;
        }
    }
}
