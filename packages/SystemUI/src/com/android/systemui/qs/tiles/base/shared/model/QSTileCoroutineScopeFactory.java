package com.android.systemui.qs.tiles.base.shared.model;

import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes2.dex */
public final class QSTileCoroutineScopeFactory {
    public final CoroutineDispatcher bgDispatcher;

    public QSTileCoroutineScopeFactory(CoroutineDispatcher coroutineDispatcher) {
        this.bgDispatcher = coroutineDispatcher;
    }
}
