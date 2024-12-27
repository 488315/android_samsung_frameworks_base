package com.android.systemui.volume.panel.component.anc.data.repository;

import androidx.slice.SliceViewManager;
import kotlin.coroutines.CoroutineContext;

public final class AncSliceRepositoryImpl implements AncSliceRepository {
    public final CoroutineContext mainCoroutineContext;
    public final SliceViewManager sliceViewManager;

    public AncSliceRepositoryImpl(CoroutineContext coroutineContext, SliceViewManager sliceViewManager) {
        this.mainCoroutineContext = coroutineContext;
        this.sliceViewManager = sliceViewManager;
    }
}
