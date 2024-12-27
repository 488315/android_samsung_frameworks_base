package com.android.server.wallpaper;

import android.os.Bundle;
import android.os.RemoteException;
import android.service.wallpaper.IWallpaperEngine;
import android.util.Slog;

import java.util.function.Consumer;

public final /* synthetic */ class WallpaperManagerService$$ExternalSyntheticLambda8
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Bundle f$2;

    public /* synthetic */ WallpaperManagerService$$ExternalSyntheticLambda8(
            Bundle bundle, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = i;
        this.f$1 = i2;
        this.f$2 = bundle;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                int i2 = this.f$1;
                Bundle bundle = this.f$2;
                boolean z = WallpaperManagerService.SHIPPED;
                IWallpaperEngine iWallpaperEngine =
                        ((WallpaperManagerService.DisplayConnector) obj).mEngine;
                if (iWallpaperEngine != null) {
                    try {
                        iWallpaperEngine.dispatchWallpaperCommand(
                                "android.wallpaper.wakingup", i, i2, -1, bundle);
                        break;
                    } catch (RemoteException e) {
                        Slog.w(
                                "WallpaperManagerService",
                                "Failed to dispatch COMMAND_WAKING_UP",
                                e);
                        return;
                    }
                }
                break;
            default:
                int i3 = this.f$0;
                int i4 = this.f$1;
                Bundle bundle2 = this.f$2;
                boolean z2 = WallpaperManagerService.SHIPPED;
                IWallpaperEngine iWallpaperEngine2 =
                        ((WallpaperManagerService.DisplayConnector) obj).mEngine;
                if (iWallpaperEngine2 != null) {
                    try {
                        iWallpaperEngine2.dispatchWallpaperCommand(
                                "android.wallpaper.goingtosleep", i3, i4, -1, bundle2);
                        break;
                    } catch (RemoteException e2) {
                        Slog.w(
                                "WallpaperManagerService",
                                "Failed to dispatch COMMAND_GOING_TO_SLEEP",
                                e2);
                    }
                }
                break;
        }
    }
}
