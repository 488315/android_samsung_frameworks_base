package com.android.systemui.wallpapers.ui.viewmodel;

import com.android.systemui.wallpapers.domain.interactor.WallpaperInteractor;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public final class WallpaperViewModel {
    public final Flow wallpaperSupportsAmbientMode;

    public WallpaperViewModel(WallpaperInteractor wallpaperInteractor) {
        this.wallpaperSupportsAmbientMode = wallpaperInteractor.wallpaperSupportsAmbientMode;
    }
}
