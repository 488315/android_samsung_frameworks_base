package com.android.systemui.qs.tiles.base.viewmodel;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSTileCoroutineScopeFactory {
    public final CoroutineScope applicationScope;

    public QSTileCoroutineScopeFactory(CoroutineScope coroutineScope) {
        this.applicationScope = coroutineScope;
    }
}
