package com.android.systemui.volume.panel.component.anc.data.repository;

import androidx.slice.SliceViewManager;
import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AncSliceRepositoryImpl implements AncSliceRepository {
    public final CoroutineContext mainCoroutineContext;
    public final SliceViewManager sliceViewManager;

    public AncSliceRepositoryImpl(CoroutineContext coroutineContext, SliceViewManager sliceViewManager) {
        this.mainCoroutineContext = coroutineContext;
        this.sliceViewManager = sliceViewManager;
    }
}
