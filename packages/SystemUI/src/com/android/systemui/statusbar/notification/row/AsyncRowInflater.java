package com.android.systemui.statusbar.notification.row;

import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class AsyncRowInflater {
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher inflationCoroutineDispatcher;
    public final CoroutineDispatcher mainCoroutineDispatcher;

    public interface OnInflateFinishedListener {
    }

    public AsyncRowInflater(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2) {
        this.applicationScope = coroutineScope;
        this.mainCoroutineDispatcher = coroutineDispatcher;
        this.inflationCoroutineDispatcher = coroutineDispatcher2;
    }
}
