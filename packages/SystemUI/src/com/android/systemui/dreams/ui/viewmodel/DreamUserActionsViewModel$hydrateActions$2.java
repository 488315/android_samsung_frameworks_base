package com.android.systemui.dreams.ui.viewmodel;

import com.android.compose.animation.scene.Swipe;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.shade.ui.viewmodel.ShadeUserActionsKt;
import java.util.LinkedHashMap;
import java.util.ListIterator;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DreamUserActionsViewModel$hydrateActions$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ DreamUserActionsViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            AnonymousClass2 anonymousClass2 = new AnonymousClass2((Continuation) obj3);
            anonymousClass2.Z$0 = booleanValue;
            anonymousClass2.L$0 = (ShadeMode) obj2;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Pair[] dualShadeActions;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            ShadeMode shadeMode = (ShadeMode) this.L$0;
            ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
            if (z) {
                Swipe.Companion.getClass();
                createListBuilder.add(Swipe.Up.to(Scenes.Gone));
            } else {
                Swipe.Companion.getClass();
                createListBuilder.add(Swipe.Up.to(Overlays.Bouncer));
            }
            if (Intrinsics.areEqual(shadeMode, ShadeMode.Single.INSTANCE)) {
                dualShadeActions = ShadeUserActionsKt.singleShadeActions$default(3, false);
            } else if (Intrinsics.areEqual(shadeMode, ShadeMode.Split.INSTANCE)) {
                dualShadeActions = ShadeUserActionsKt.splitShadeActions();
            } else {
                if (!Intrinsics.areEqual(shadeMode, ShadeMode.Dual.INSTANCE)) {
                    throw new NoWhenBranchMatchedException();
                }
                dualShadeActions = ShadeUserActionsKt.dualShadeActions();
            }
            CollectionsKt__MutableCollectionsKt.addAll(createListBuilder, dualShadeActions);
            ListBuilder build = createListBuilder.build();
            int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(build, 10));
            if (mapCapacity < 16) {
                mapCapacity = 16;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
            ListIterator listIterator = build.listIterator(0);
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DreamUserActionsViewModel$hydrateActions$2(DreamUserActionsViewModel dreamUserActionsViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dreamUserActionsViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DreamUserActionsViewModel$hydrateActions$2 dreamUserActionsViewModel$hydrateActions$2 = new DreamUserActionsViewModel$hydrateActions$2(this.this$0, continuation);
        dreamUserActionsViewModel$hydrateActions$2.Z$0 = ((Boolean) obj).booleanValue();
        return dreamUserActionsViewModel$hydrateActions$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((DreamUserActionsViewModel$hydrateActions$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        final ReadonlyStateFlow readonlyStateFlow = this.this$0.deviceUnlockedInteractor.deviceUnlockStatus;
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$2$invokeSuspend$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$2$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$2$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$2$invokeSuspend$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus r5 = (com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus) r5
                        boolean r5 = r5.isUnlocked
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.dreams.ui.viewmodel.DreamUserActionsViewModel$hydrateActions$2$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, ((ShadeModeInteractorImpl) this.this$0.shadeModeInteractor).shadeMode, new AnonymousClass2(null));
    }
}
