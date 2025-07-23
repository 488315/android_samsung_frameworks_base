package com.android.systemui.blur.di;

import com.android.systemui.blur.di.ScreenShotBitmapProvider;
import com.android.systemui.blur.domain.interactor.WallpaperScreenShotProvider;
import com.android.systemui.blur.domain.interactor.WindowManagerScreenShotProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScreenShotBitmapFactory {
    public final WallpaperScreenShotProvider wallpaperScreenShotProvider;
    public final WindowManagerScreenShotProvider windowManagerScreenShotProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ScreenShotBitmapProvider.Type.values().length];
            try {
                iArr[ScreenShotBitmapProvider.Type.WINDOW_MANAGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ScreenShotBitmapProvider.Type.WALLPAPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ScreenShotBitmapFactory(WindowManagerScreenShotProvider windowManagerScreenShotProvider, WallpaperScreenShotProvider wallpaperScreenShotProvider) {
        this.windowManagerScreenShotProvider = windowManagerScreenShotProvider;
        this.wallpaperScreenShotProvider = wallpaperScreenShotProvider;
    }
}
