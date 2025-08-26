package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.qs.pipeline.data.repository.AutoAddRepository;
import com.android.systemui.qs.pipeline.data.repository.AutoAddSettingRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3 implements FlowCollector {
    public final /* synthetic */ List $trackIfNotAddedSpecs;
    public final /* synthetic */ int $userId;
    public final /* synthetic */ AutoAddInteractor this$0;

    public AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3(List<? extends TileSpec> list, AutoAddInteractor autoAddInteractor, int i) {
        this.$trackIfNotAddedSpecs = list;
        this.this$0 = autoAddInteractor;
        this.$userId = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object emit(List list, Continuation continuation) {
        AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1 autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1;
        Iterator it;
        int i;
        AutoAddInteractor autoAddInteractor;
        if (continuation instanceof AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1) {
            autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1 = (AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1) continuation;
            int i2 = autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1.label = i2 - Integer.MIN_VALUE;
            } else {
                autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1 = new AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1(this, continuation);
            }
        }
        Object obj = autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            List list2 = this.$trackIfNotAddedSpecs;
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : list) {
                if (list2.contains((TileSpec) obj2)) {
                    arrayList.add(obj2);
                }
            }
            it = arrayList.iterator();
            AutoAddInteractor autoAddInteractor2 = this.this$0;
            i = this.$userId;
            autoAddInteractor = autoAddInteractor2;
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1.I$0;
            it = (Iterator) autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1.L$1;
            autoAddInteractor = (AutoAddInteractor) autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        while (it.hasNext()) {
            TileSpec tileSpec = (TileSpec) it.next();
            AutoAddRepository autoAddRepository = autoAddInteractor.repository;
            autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1.L$0 = autoAddInteractor;
            autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1.L$1 = it;
            autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1.I$0 = i;
            autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1.label = 1;
            if (((AutoAddSettingRepository) autoAddRepository).markTileAdded(i, tileSpec, autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}
