package com.android.systemui.dreams.ui.viewmodel;

import com.android.compose.animation.scene.Swipe;
import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$$ExternalSyntheticLambda0;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.shade.ui.viewmodel.ShadeUserActionsKt;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class DreamUserActionsViewModel extends UserActionsViewModel {
    public final DeviceUnlockedInteractor deviceUnlockedInteractor;
    public final ShadeInteractor shadeInteractor;
    public final ShadeModeInteractor shadeModeInteractor;

    public interface Factory {
        DreamUserActionsViewModel create();
    }

    /* renamed from: com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        /* renamed from: com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$2$2, reason: invalid class name and collision with other inner class name */
        final class C01972 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            /* synthetic */ boolean Z$0;
            int label;

            public C01972(Continuation continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                C01972 c01972 = new C01972((Continuation) obj3);
                c01972.Z$0 = zBooleanValue;
                c01972.L$0 = (ShadeMode) obj2;
                return c01972.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Pair[] pairArrDualShadeActions;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                boolean z = this.Z$0;
                ShadeMode shadeMode = (ShadeMode) this.L$0;
                ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                if (z) {
                    Swipe.Companion.getClass();
                    listBuilderCreateListBuilder.add(Swipe.Up.to(Scenes.Gone));
                } else {
                    Swipe.Companion.getClass();
                    listBuilderCreateListBuilder.add(Swipe.Up.to(Overlays.Bouncer));
                }
                if (Intrinsics.areEqual(shadeMode, ShadeMode.Single.INSTANCE)) {
                    pairArrDualShadeActions = ShadeUserActionsKt.singleShadeActions$default(3, false);
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
                        return linkedHashMap;
                    }
                    Pair pair = (Pair) itr.next();
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
            }
        }

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = DreamUserActionsViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (!this.Z$0) {
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(MapsKt__MapsKt.emptyMap());
            }
            final ReadonlyStateFlow readonlyStateFlow = DreamUserActionsViewModel.this.deviceUnlockedInteractor.deviceUnlockStatus;
            return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$2$invokeSuspend$$inlined$map$1

                /* renamed from: com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$2$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$2$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        int label;
                        /* synthetic */ Object result;

                        public AnonymousClass1(Continuation continuation) {
                            super(continuation);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            this.result = obj;
                            this.label |= Integer.MIN_VALUE;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
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
                        Object obj2 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i2 = anonymousClass1.label;
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj2);
                            Boolean boolValueOf = Boolean.valueOf(((DeviceUnlockStatus) obj).isUnlocked);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i2 != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, ((ShadeModeInteractorImpl) DreamUserActionsViewModel.this.shadeModeInteractor).shadeMode, new C01972(null));
        }
    }

    public DreamUserActionsViewModel(DeviceUnlockedInteractor deviceUnlockedInteractor, ShadeInteractor shadeInteractor, ShadeModeInteractor shadeModeInteractor) {
        this.deviceUnlockedInteractor = deviceUnlockedInteractor;
        this.shadeInteractor = shadeInteractor;
        this.shadeModeInteractor = shadeModeInteractor;
    }

    @Override // com.android.systemui.scene.ui.viewmodel.UserActionsViewModel
    public final Object hydrateActions(final UserActionsViewModel$$ExternalSyntheticLambda0 userActionsViewModel$$ExternalSyntheticLambda0, Continuation continuation) {
        Object objCollect = LatestConflatedKt.flatMapLatestConflated(((ShadeInteractorImpl) this.shadeInteractor).isShadeTouchable, new AnonymousClass2(null)).collect(new FlowCollector() { // from class: com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel.hydrateActions.3
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation2) {
                userActionsViewModel$$ExternalSyntheticLambda0.mo781invoke((Map) obj);
                return Unit.INSTANCE;
            }
        }, continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
