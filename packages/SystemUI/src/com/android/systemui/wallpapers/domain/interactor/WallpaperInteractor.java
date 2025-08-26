package com.android.systemui.wallpapers.domain.interactor;

import com.android.systemui.wallpapers.data.repository.WallpaperRepository;
import com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public final class WallpaperInteractor {
    public final Flow wallpaperSupportsAmbientMode;

    public WallpaperInteractor(WallpaperRepository wallpaperRepository) {
        this.wallpaperSupportsAmbientMode = ((WallpaperRepositoryImpl) wallpaperRepository).wallpaperSupportsAmbientMode;
    }
}
