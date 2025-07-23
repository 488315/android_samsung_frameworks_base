package com.android.systemui.qs.pipeline.data.domain.interactor;

import com.android.systemui.qs.TileSALogHelper;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileOrderLoggingInteractor {
    public final CoroutineDispatcher backgroundDispatcher;
    public final CurrentTilesInteractor qsTilesInteractor;
    public final CurrentTilesInteractor quickQsTilesInteractor;
    public final CoroutineScope scope;
    public final TileSALogHelper tileSALogHelper;

    public TileOrderLoggingInteractor(CurrentTilesInteractor currentTilesInteractor, CurrentTilesInteractor currentTilesInteractor2, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, TileSALogHelper tileSALogHelper) {
        this.qsTilesInteractor = currentTilesInteractor;
        this.quickQsTilesInteractor = currentTilesInteractor2;
        this.backgroundDispatcher = coroutineDispatcher;
        this.scope = coroutineScope;
        this.tileSALogHelper = tileSALogHelper;
    }

    public final void start() {
        BuildersKt.launch$default(this.scope, null, null, new TileOrderLoggingInteractor$start$1(this, null), 3);
    }
}
