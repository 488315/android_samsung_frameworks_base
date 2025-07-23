package com.android.systemui.wallpapers.ui.viewmodel;

import com.android.systemui.wallpapers.domain.interactor.WallpaperInteractor;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WallpaperViewModel {
    public final Flow wallpaperSupportsAmbientMode;

    public WallpaperViewModel(WallpaperInteractor wallpaperInteractor) {
        this.wallpaperSupportsAmbientMode = wallpaperInteractor.wallpaperSupportsAmbientMode;
    }
}
