package com.android.systemui.scene.ui.viewmodel;

import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.shade.ui.viewmodel.ShadeUserActionsKt;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class GoneUserActionsViewModel extends UserActionsViewModel {
    public final ShadeModeInteractor shadeModeInteractor;

    public interface Factory {
        GoneUserActionsViewModel create();
    }

    /* renamed from: com.android.systemui.scene.ui.viewmodel.GoneUserActionsViewModel$hydrateActions$1, reason: invalid class name */
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
            return GoneUserActionsViewModel.this.hydrateActions(null, this);
        }
    }

    public GoneUserActionsViewModel(ShadeModeInteractor shadeModeInteractor) {
        this.shadeModeInteractor = shadeModeInteractor;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.scene.ui.viewmodel.UserActionsViewModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object hydrateActions(final UserActionsViewModel$$ExternalSyntheticLambda0 userActionsViewModel$$ExternalSyntheticLambda0, Continuation continuation) {
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
            ReadonlyStateFlow readonlyStateFlow = ((ShadeModeInteractorImpl) this.shadeModeInteractor).shadeMode;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.scene.ui.viewmodel.GoneUserActionsViewModel.hydrateActions.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation2) {
                    Pair[] pairArrDualShadeActions;
                    ShadeMode shadeMode = (ShadeMode) obj2;
                    ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                    if (Intrinsics.areEqual(shadeMode, ShadeMode.Single.INSTANCE)) {
                        pairArrDualShadeActions = ShadeUserActionsKt.singleShadeActions$default(1, false);
                    } else if (Intrinsics.areEqual(shadeMode, ShadeMode.Split.INSTANCE)) {
                        pairArrDualShadeActions = ShadeUserActionsKt.splitShadeActions();
                    } else {
                        if (!Intrinsics.areEqual(shadeMode, ShadeMode.Dual.INSTANCE)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        pairArrDualShadeActions = ShadeUserActionsKt.dualShadeActions();
                    }
                    CollectionsKt__MutableCollectionsKt.addAll(listBuilderCreateListBuilder, pairArrDualShadeActions);
                    ListBuilder listBuilderBuild = listBuilderCreateListBuilder.build();
                    int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(listBuilderBuild, 10));
                    if (iMapCapacity < 16) {
                        iMapCapacity = 16;
                    }
                    LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
                    ListIterator listIterator = listBuilderBuild.listIterator(0);
                    while (true) {
                        ListBuilder.Itr itr = (ListBuilder.Itr) listIterator;
                        if (!itr.hasNext()) {
                            userActionsViewModel$$ExternalSyntheticLambda0.mo781invoke(linkedHashMap);
                            return Unit.INSTANCE;
                        }
                        Pair pair = (Pair) itr.next();
                        linkedHashMap.put(pair.getFirst(), pair.getSecond());
                    }
                }
            };
            anonymousClass1.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(flowCollector, anonymousClass1) == coroutineSingletons) {
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
