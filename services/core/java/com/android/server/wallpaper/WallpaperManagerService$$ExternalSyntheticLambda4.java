package com.android.server.wallpaper;

import android.app.ILocalWallpaperColorConsumer;
import android.app.WallpaperColors;
import android.graphics.RectF;
import android.os.RemoteException;
import android.service.wallpaper.IWallpaperEngine;
import android.util.Slog;

import java.util.function.Consumer;

public final /* synthetic */ class WallpaperManagerService$$ExternalSyntheticLambda4
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ WallpaperManagerService$$ExternalSyntheticLambda4(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public /* synthetic */ WallpaperManagerService$$ExternalSyntheticLambda4(
            WallpaperManagerService.WallpaperConnection wallpaperConnection,
            WallpaperData wallpaperData) {
        this.$r8$classId = 2;
        this.f$1 = wallpaperConnection;
        this.f$0 = wallpaperData;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                WallpaperManagerService wallpaperManagerService =
                        (WallpaperManagerService) this.f$0;
                WallpaperManagerService.WallpaperConnection wallpaperConnection =
                        (WallpaperManagerService.WallpaperConnection) this.f$1;
                WallpaperManagerService.DisplayConnector displayConnector =
                        (WallpaperManagerService.DisplayConnector) obj;
                boolean z = WallpaperManagerService.SHIPPED;
                wallpaperManagerService.getClass();
                if (displayConnector.mEngine == null) {
                    displayConnector.connectLocked(
                            wallpaperConnection, wallpaperManagerService.mFallbackWallpaper);
                    break;
                }
                break;
            case 1:
                WallpaperManagerService wallpaperManagerService2 =
                        (WallpaperManagerService) this.f$0;
                WallpaperData wallpaperData = (WallpaperData) this.f$1;
                WallpaperManagerService.DisplayConnector displayConnector2 =
                        (WallpaperManagerService.DisplayConnector) obj;
                boolean z2 = WallpaperManagerService.SHIPPED;
                wallpaperManagerService2.getClass();
                try {
                    IWallpaperEngine iWallpaperEngine = displayConnector2.mEngine;
                    if (iWallpaperEngine != null) {
                        iWallpaperEngine.setWallpaperFlags(wallpaperData.mWhich);
                        wallpaperManagerService2.mWindowManagerInternal.setWallpaperShowWhenLocked(
                                displayConnector2.mToken,
                                wallpaperManagerService2.isVisibleWhichWhenKeyguardLocked(
                                        wallpaperData.mWhich));
                        break;
                    }
                } catch (RemoteException e) {
                    Slog.e("WallpaperManagerService", "Failed to update wallpaper engine flags", e);
                    return;
                }
                break;
            case 2:
                WallpaperManagerService.WallpaperConnection wallpaperConnection2 =
                        (WallpaperManagerService.WallpaperConnection) this.f$1;
                WallpaperData wallpaperData2 = (WallpaperData) this.f$0;
                boolean z3 = WallpaperManagerService.SHIPPED;
                ((WallpaperManagerService.DisplayConnector) obj)
                        .connectLocked(wallpaperConnection2, wallpaperData2);
                break;
            default:
                try {
                    ((ILocalWallpaperColorConsumer) obj)
                            .onColorsChanged((RectF) this.f$0, (WallpaperColors) this.f$1);
                    break;
                } catch (RemoteException e2) {
                    Slog.w("WallpaperManagerService", "Failed to notify local color callbacks", e2);
                }
        }
    }
}
