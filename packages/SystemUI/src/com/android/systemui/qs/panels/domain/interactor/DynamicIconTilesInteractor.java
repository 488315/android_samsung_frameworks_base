package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class DynamicIconTilesInteractor extends ExclusiveActivatable {
    public final CurrentTilesInteractor currentTilesInteractor;
    public final IconTilesInteractor iconTilesInteractor;

    public interface Factory {
        DynamicIconTilesInteractor create();
    }

    /* renamed from: com.android.systemui.qs.panels.domain.interactor.DynamicIconTilesInteractor$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DynamicIconTilesInteractor.this.onActivated(this);
        }
    }

    public DynamicIconTilesInteractor(IconTilesInteractor iconTilesInteractor, CurrentTilesInteractor currentTilesInteractor) {
        this.iconTilesInteractor = iconTilesInteractor;
        this.currentTilesInteractor = currentTilesInteractor;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            StateFlow currentTiles = this.currentTilesInteractor.getCurrentTiles();
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.panels.domain.interactor.DynamicIconTilesInteractor.onActivated.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation2) {
                    DynamicIconTilesInteractor dynamicIconTilesInteractor = DynamicIconTilesInteractor.this;
                    Iterable iterable = (Iterable) dynamicIconTilesInteractor.iconTilesInteractor.largeTilesSpecs.$$delegate_0.getValue();
                    List list = (List) obj2;
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(((TileModel) it.next()).spec);
                    }
                    dynamicIconTilesInteractor.iconTilesInteractor.preferencesInteractor.setLargeTilesSpecs(CollectionsKt___CollectionsKt.intersect(iterable, CollectionsKt___CollectionsKt.toSet(arrayList)));
                    return Unit.INSTANCE;
                }
            };
            anonymousClass1.label = 1;
            if (currentTiles.collect(flowCollector, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
