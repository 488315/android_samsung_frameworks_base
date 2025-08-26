package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
public final class TilesAvailabilityInteractor {
    public final QSFactory qsFactoryImpl;
    public final QSPipelineFlagsRepository qsPipelineFlagsRepository;

    /* renamed from: com.android.systemui.qs.panels.domain.interactor.TilesAvailabilityInteractor$getUnavailableTiles$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TilesAvailabilityInteractor.this.getUnavailableTiles(null, this);
        }
    }

    public TilesAvailabilityInteractor(NewTilesAvailabilityInteractor newTilesAvailabilityInteractor, QSFactory qSFactory, QSPipelineFlagsRepository qSPipelineFlagsRepository) {
        this.qsFactoryImpl = qSFactory;
        this.qsPipelineFlagsRepository = qSPipelineFlagsRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getUnavailableTiles(Iterable iterable, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        Map mapEmptyMap;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            if (!(iterable instanceof Collection) || !((Collection) iterable).isEmpty()) {
                Iterator it = iterable.iterator();
                while (it.hasNext()) {
                    if (!(((TileSpec) it.next()) instanceof TileSpec.PlatformTileSpec)) {
                        throw new IllegalStateException("Check failed.");
                    }
                }
            }
            this.qsPipelineFlagsRepository.getClass();
            mapEmptyMap = MapsKt__MapsKt.emptyMap();
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            iterable = (Iterable) anonymousClass1.L$1;
            this = (TilesAvailabilityInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            mapEmptyMap = (Map) obj;
        }
        List listMinus = CollectionsKt___CollectionsKt.minus(iterable, (Iterable) mapEmptyMap.keySet());
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : listMinus) {
            QSTile qSTileCreateTile = this.qsFactoryImpl.createTile(((TileSpec) obj2).getSpec());
            boolean zIsAvailable = qSTileCreateTile != null ? qSTileCreateTile.isAvailable() : false;
            if (qSTileCreateTile != null) {
                qSTileCreateTile.destroy();
            }
            if (!zIsAvailable) {
                arrayList.add(obj2);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : mapEmptyMap.entrySet()) {
            if (!((Boolean) entry.getValue()).booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return CollectionsKt___CollectionsKt.toSet(CollectionsKt___CollectionsKt.plus((Iterable) linkedHashMap.keySet(), (Collection) arrayList));
    }
}
