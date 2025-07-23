package com.android.systemui.samsung.quicksetting.data.repository;

import com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSource;
import com.android.systemui.samsung.quicksetting.domain.repository.GridTileRepository;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GridTileRepositoryImpl implements GridTileRepository {
    public final PreferenceDataSource preferenceDataSource;

    public GridTileRepositoryImpl(PreferenceDataSource preferenceDataSource) {
        this.preferenceDataSource = preferenceDataSource;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadGridTiles(com.android.systemui.samsung.quicksetting.ui.panel.ScreenType r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.samsung.quicksetting.data.repository.GridTileRepositoryImpl$loadGridTiles$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.samsung.quicksetting.data.repository.GridTileRepositoryImpl$loadGridTiles$1 r0 = (com.android.systemui.samsung.quicksetting.data.repository.GridTileRepositoryImpl$loadGridTiles$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.samsung.quicksetting.data.repository.GridTileRepositoryImpl$loadGridTiles$1 r0 = new com.android.systemui.samsung.quicksetting.data.repository.GridTileRepositoryImpl$loadGridTiles$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r6)
            goto L3f
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.label = r3
            com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSource r4 = r4.preferenceDataSource
            com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl r4 = (com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl) r4
            java.lang.Object r6 = r4.loadGridTiles(r5, r0)
            if (r6 != r1) goto L3f
            return r1
        L3f:
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3 r4 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3
            r4.<init>(r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.data.repository.GridTileRepositoryImpl.loadGridTiles(com.android.systemui.samsung.quicksetting.ui.panel.ScreenType, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
