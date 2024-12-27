package com.android.server.wallpaper;

import android.os.Environment;
import android.util.Slog;

import java.io.File;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WallpaperManagerService$$ExternalSyntheticLambda25
        implements BiConsumer {
    public final /* synthetic */ WallpaperManagerService f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ WallpaperManagerService$$ExternalSyntheticLambda25(
            WallpaperManagerService wallpaperManagerService, int i) {
        this.f$0 = wallpaperManagerService;
        this.f$1 = i;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        WallpaperManagerService wallpaperManagerService = this.f$0;
        int i = this.f$1;
        Integer num = (Integer) obj;
        boolean z = WallpaperManagerService.SHIPPED;
        wallpaperManagerService.getClass();
        File file = new File(Environment.getUserSystemDirectory(i), (String) obj2);
        if (file.exists()) {
            Slog.w(
                    "WallpaperManagerService",
                    "User:"
                            + i
                            + ", wallpaper type = "
                            + num
                            + ", wallpaper fail detect!! reset to default wallpaper");
            WallpaperManagerService.clearWallpaperBitmaps(new WallpaperData(i, num.intValue()));
            file.delete();
        }
    }
}
