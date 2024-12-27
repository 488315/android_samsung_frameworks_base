package com.android.server.wallpaper;

import android.os.Bundle;
import android.os.RemoteException;
import android.service.wallpaper.IWallpaperEngine;
import android.util.Slog;

import java.io.File;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
