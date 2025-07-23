package com.android.systemui.qs.panels.data.repository;

import com.android.systemui.qs.panels.shared.model.InfiniteGridLayoutType;
import com.android.systemui.qs.panels.shared.model.PaginatedGridLayoutType;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GridLayoutTypeRepository {
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 defaultLayoutType = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(PaginatedGridLayoutType.INSTANCE);
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 dualShadeLayoutType = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(InfiniteGridLayoutType.INSTANCE);
}
