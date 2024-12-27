package com.android.systemui.volume.panel.component.anc.data.repository;

import androidx.slice.SliceViewManager;
import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class AncSliceRepositoryImpl implements AncSliceRepository {
    public final CoroutineContext mainCoroutineContext;
    public final SliceViewManager sliceViewManager;

    public AncSliceRepositoryImpl(CoroutineContext coroutineContext, SliceViewManager sliceViewManager) {
        this.mainCoroutineContext = coroutineContext;
        this.sliceViewManager = sliceViewManager;
    }
}
