package com.android.systemui.wallpapers.domain.interactor;

import com.android.systemui.wallpapers.data.repository.WallpaperRepository;
import com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WallpaperInteractor {
    public final Flow wallpaperSupportsAmbientMode;

    public WallpaperInteractor(WallpaperRepository wallpaperRepository) {
        this.wallpaperSupportsAmbientMode = ((WallpaperRepositoryImpl) wallpaperRepository).wallpaperSupportsAmbientMode;
    }
}
