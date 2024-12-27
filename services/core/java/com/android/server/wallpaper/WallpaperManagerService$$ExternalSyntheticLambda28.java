package com.android.server.wallpaper;

import android.os.Bundle;
import android.os.RemoteException;
import android.service.wallpaper.IWallpaperEngine;
import android.util.Slog;

import java.io.File;
import java.util.function.Consumer;

public final /* synthetic */ class WallpaperManagerService$$ExternalSyntheticLambda28
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                boolean z = WallpaperManagerService.SHIPPED;
                IWallpaperEngine iWallpaperEngine =
                        ((WallpaperManagerService.DisplayConnector) obj).mEngine;
                if (iWallpaperEngine != null) {
                    try {
                        iWallpaperEngine.dispatchWallpaperCommand(
                                "android.wallpaper.keyguardgoingaway", -1, -1, -1, new Bundle());
                        break;
                    } catch (RemoteException e) {
                        Slog.w(
                                "WallpaperManagerService",
                                "Failed to notify that the keyguard is going away",
                                e);
                        return;
                    }
                }
                break;
            case 1:
                ((WallpaperManagerService.DisplayConnector) obj).mEngine = null;
                break;
            default:
                ((File) obj).delete();
                break;
        }
    }
}
