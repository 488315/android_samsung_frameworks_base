package com.android.systemui.wallpapers;

import android.service.wallpaper.WallpaperService;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GradientColorWallpaper extends WallpaperService {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EmptyWallpaperEngine extends WallpaperService.Engine {
        public EmptyWallpaperEngine(GradientColorWallpaper gradientColorWallpaper) {
            super(gradientColorWallpaper);
        }
    }

    static {
        new Companion(null);
    }

    @Override // android.service.wallpaper.WallpaperService
    public final WallpaperService.Engine onCreateEngine() {
        return new EmptyWallpaperEngine(this);
    }
}
