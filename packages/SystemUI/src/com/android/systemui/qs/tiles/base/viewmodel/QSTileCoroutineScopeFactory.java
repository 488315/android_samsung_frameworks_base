package com.android.systemui.qs.tiles.base.viewmodel;

import kotlinx.coroutines.CoroutineScope;

public final class QSTileCoroutineScopeFactory {
    public final CoroutineScope applicationScope;

    public QSTileCoroutineScopeFactory(CoroutineScope coroutineScope) {
        this.applicationScope = coroutineScope;
    }
}
