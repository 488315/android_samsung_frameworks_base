package com.android.systemui.qs.panels.data.repository;

import com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepository;
import com.android.systemui.settings.UserTracker;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IconAndNameCustomRepository {
    public final CoroutineContext backgroundContext;
    public final InstalledTilesComponentRepository installedTilesComponentRepository;
    public final UserTracker userTracker;

    public IconAndNameCustomRepository(InstalledTilesComponentRepository installedTilesComponentRepository, UserTracker userTracker, CoroutineContext coroutineContext) {
        this.installedTilesComponentRepository = installedTilesComponentRepository;
        this.userTracker = userTracker;
        this.backgroundContext = coroutineContext;
    }

    public final Object getCustomTileData(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundContext, new IconAndNameCustomRepository$getCustomTileData$2(this, null), continuation);
    }
}
