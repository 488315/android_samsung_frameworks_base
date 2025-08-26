package com.android.wm.shell.dagger;

import com.android.wm.shell.desktopmode.desktopwallpaperactivity.DesktopWallpaperActivityTokenProvider;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopWallpaperActivityTokenProviderFactory implements Provider {
    public static DesktopWallpaperActivityTokenProvider provideDesktopWallpaperActivityTokenProvider() {
        return new DesktopWallpaperActivityTokenProvider();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopWallpaperActivityTokenProvider();
    }
}
