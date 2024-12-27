package com.android.server.wallpaper;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Slog;

import java.util.function.Consumer;

public final /* synthetic */ class WallpaperManagerService$$ExternalSyntheticLambda9
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WallpaperManagerService$$ExternalSyntheticLambda9(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                WallpaperData wallpaperData = (WallpaperData) obj2;
                WallpaperManagerService.DisplayConnector displayConnector =
                        (WallpaperManagerService.DisplayConnector) obj;
                boolean z = WallpaperManagerService.SHIPPED;
                try {
                    if (displayConnector.mEngine != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("which", wallpaperData.mWhich);
                        displayConnector.mEngine.dispatchWallpaperCommand(
                                "android.wallpaper.reapply", 0, 0, 0, bundle);
                        break;
                    }
                } catch (RemoteException e) {
                    Slog.w(
                            "WallpaperManagerService",
                            "Error sending apply message to wallpaper",
                            e);
                    return;
                }
                break;
            case 1:
                boolean z2 = WallpaperManagerService.SHIPPED;
                ((WallpaperManagerService.DisplayConnector) obj)
                        .disconnectLocked(((WallpaperData) obj2).connection);
                break;
            default:
                WallpaperManagerService.WallpaperConnection wallpaperConnection =
                        (WallpaperManagerService.WallpaperConnection) obj2;
                WallpaperManagerService.DisplayConnector displayConnector2 =
                        (WallpaperManagerService.DisplayConnector) obj;
                boolean z3 = WallpaperManagerService.SHIPPED;
                if (displayConnector2.mEngine != null) {
                    displayConnector2.disconnectLocked(wallpaperConnection);
                    break;
                }
                break;
        }
    }
}
