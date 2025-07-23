package com.android.systemui.statusbar.notification.row;

import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AsyncRowInflater {
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher inflationCoroutineDispatcher;
    public final CoroutineDispatcher mainCoroutineDispatcher;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnInflateFinishedListener {
    }

    public AsyncRowInflater(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2) {
        this.applicationScope = coroutineScope;
        this.mainCoroutineDispatcher = coroutineDispatcher;
        this.inflationCoroutineDispatcher = coroutineDispatcher2;
    }
}
