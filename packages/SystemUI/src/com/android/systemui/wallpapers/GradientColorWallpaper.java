package com.android.systemui.wallpapers;

import android.service.wallpaper.WallpaperService;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class GradientColorWallpaper extends WallpaperService {

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
