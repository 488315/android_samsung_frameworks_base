package com.android.systemui.qs.panels.data.repository;

import com.android.systemui.qs.panels.shared.model.InfiniteGridLayoutType;
import com.android.systemui.qs.panels.shared.model.PaginatedGridLayoutType;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
public final class GridLayoutTypeRepository {
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 defaultLayoutType = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(PaginatedGridLayoutType.INSTANCE);
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 dualShadeLayoutType = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(InfiniteGridLayoutType.INSTANCE);
}
