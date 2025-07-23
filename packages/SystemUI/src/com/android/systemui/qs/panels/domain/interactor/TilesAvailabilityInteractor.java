package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TilesAvailabilityInteractor {
    public final QSFactory qsFactoryImpl;
    public final QSPipelineFlagsRepository qsPipelineFlagsRepository;

    public TilesAvailabilityInteractor(NewTilesAvailabilityInteractor newTilesAvailabilityInteractor, QSFactory qSFactory, QSPipelineFlagsRepository qSPipelineFlagsRepository) {
        this.qsFactoryImpl = qSFactory;
        this.qsPipelineFlagsRepository = qSPipelineFlagsRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getUnavailableTiles(java.lang.Iterable r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.qs.panels.domain.interactor.TilesAvailabilityInteractor$getUnavailableTiles$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.qs.panels.domain.interactor.TilesAvailabilityInteractor$getUnavailableTiles$1 r0 = (com.android.systemui.qs.panels.domain.interactor.TilesAvailabilityInteractor$getUnavailableTiles$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.panels.domain.interactor.TilesAvailabilityInteractor$getUnavailableTiles$1 r0 = new com.android.systemui.qs.panels.domain.interactor.TilesAvailabilityInteractor$getUnavailableTiles$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r0.label
            if (r1 == 0) goto L3a
            r4 = 1
            if (r1 != r4) goto L32
            java.lang.Object r4 = r0.L$1
            r5 = r4
            java.lang.Iterable r5 = (java.lang.Iterable) r5
            java.lang.Object r4 = r0.L$0
            com.android.systemui.qs.panels.domain.interactor.TilesAvailabilityInteractor r4 = (com.android.systemui.qs.panels.domain.interactor.TilesAvailabilityInteractor) r4
            kotlin.ResultKt.throwOnFailure(r6)
            java.util.Map r6 = (java.util.Map) r6
            goto L71
        L32:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L3a:
            kotlin.ResultKt.throwOnFailure(r6)
            boolean r6 = r5 instanceof java.util.Collection
            if (r6 == 0) goto L4b
            r6 = r5
            java.util.Collection r6 = (java.util.Collection) r6
            boolean r6 = r6.isEmpty()
            if (r6 == 0) goto L4b
            goto L68
        L4b:
            java.util.Iterator r6 = r5.iterator()
        L4f:
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto L68
            java.lang.Object r0 = r6.next()
            com.android.systemui.qs.pipeline.shared.TileSpec r0 = (com.android.systemui.qs.pipeline.shared.TileSpec) r0
            boolean r0 = r0 instanceof com.android.systemui.qs.pipeline.shared.TileSpec.PlatformTileSpec
            if (r0 == 0) goto L60
            goto L4f
        L60:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "Check failed."
            r4.<init>(r5)
            throw r4
        L68:
            com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository r6 = r4.qsPipelineFlagsRepository
            r6.getClass()
            java.util.Map r6 = kotlin.collections.MapsKt__MapsKt.emptyMap()
        L71:
            java.util.Set r0 = r6.keySet()
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.List r5 = kotlin.collections.CollectionsKt___CollectionsKt.minus(r5, r0)
            java.lang.Iterable r5 = (java.lang.Iterable) r5
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.Iterator r5 = r5.iterator()
        L86:
            boolean r1 = r5.hasNext()
            if (r1 == 0) goto Lb0
            java.lang.Object r1 = r5.next()
            r2 = r1
            com.android.systemui.qs.pipeline.shared.TileSpec r2 = (com.android.systemui.qs.pipeline.shared.TileSpec) r2
            com.android.systemui.plugins.qs.QSFactory r3 = r4.qsFactoryImpl
            java.lang.String r2 = r2.getSpec()
            com.android.systemui.plugins.qs.QSTile r2 = r3.createTile(r2)
            if (r2 == 0) goto La4
            boolean r3 = r2.isAvailable()
            goto La5
        La4:
            r3 = 0
        La5:
            if (r2 == 0) goto Laa
            r2.destroy()
        Laa:
            if (r3 != 0) goto L86
            r0.add(r1)
            goto L86
        Lb0:
            java.util.LinkedHashMap r4 = new java.util.LinkedHashMap
            r4.<init>()
            java.util.Set r5 = r6.entrySet()
            java.util.Iterator r5 = r5.iterator()
        Lbd:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto Le1
            java.lang.Object r6 = r5.next()
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6
            java.lang.Object r1 = r6.getValue()
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 != 0) goto Lbd
            java.lang.Object r1 = r6.getKey()
            java.lang.Object r6 = r6.getValue()
            r4.put(r1, r6)
            goto Lbd
        Le1:
            java.util.Set r4 = r4.keySet()
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            java.util.List r4 = kotlin.collections.CollectionsKt___CollectionsKt.plus(r4, r0)
            java.util.Set r4 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.domain.interactor.TilesAvailabilityInteractor.getUnavailableTiles(java.lang.Iterable, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
