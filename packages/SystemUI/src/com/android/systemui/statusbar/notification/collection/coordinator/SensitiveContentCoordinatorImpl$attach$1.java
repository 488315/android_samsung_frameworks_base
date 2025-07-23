package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.scene.domain.interactor.SceneInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SensitiveContentCoordinatorImpl$attach$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SensitiveContentCoordinatorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SensitiveContentCoordinatorImpl$attach$1(SensitiveContentCoordinatorImpl sensitiveContentCoordinatorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sensitiveContentCoordinatorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SensitiveContentCoordinatorImpl$attach$1(this.this$0, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SceneInteractor sceneInteractor;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            sceneInteractor = this.this$0.sceneInteractor;
            final ReadonlyStateFlow readonlyStateFlow = sceneInteractor.transitionState;
            final SensitiveContentCoordinatorImpl sensitiveContentCoordinatorImpl = this.this$0;
            Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1$invokeSuspend$$inlined$mapNotNull$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1$invokeSuspend$$inlined$mapNotNull$1$2, reason: invalid class name */
                public final class AnonymousClass2<T> implements FlowCollector {
                    final /* synthetic */ FlowCollector $this_unsafeFlow;
                    final /* synthetic */ SensitiveContentCoordinatorImpl this$0;

                    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1$invokeSuspend$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, SensitiveContentCoordinatorImpl sensitiveContentCoordinatorImpl) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = sensitiveContentCoordinatorImpl;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                        /*
                            r5 = this;
                            boolean r0 = r7 instanceof com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1$invokeSuspend$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r7
                            com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1$invokeSuspend$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1$invokeSuspend$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1$invokeSuspend$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1$invokeSuspend$$inlined$mapNotNull$1$2$1
                            r0.<init>(r7)
                        L18:
                            java.lang.Object r7 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r7)
                            goto L67
                        L27:
                            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                            r5.<init>(r6)
                            throw r5
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r7)
                            kotlinx.coroutines.flow.FlowCollector r7 = r5.$this_unsafeFlow
                            com.android.compose.animation.scene.ObservableTransitionState r6 = (com.android.compose.animation.scene.ObservableTransitionState) r6
                            com.android.compose.animation.scene.SceneKey r2 = com.android.systemui.scene.shared.model.Scenes.Gone
                            r4 = 0
                            boolean r6 = com.android.compose.animation.scene.ObservableTransitionState.isTransitioning$default(r6, r4, r2, r3)
                            com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl r5 = r5.this$0
                            com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor r5 = com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl.access$getDeviceEntryInteractor$p(r5)
                            kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.isDeviceEntered
                            kotlinx.coroutines.flow.StateFlow r5 = r5.$$delegate_0
                            java.lang.Object r5 = r5.getValue()
                            java.lang.Boolean r5 = (java.lang.Boolean) r5
                            boolean r5 = r5.booleanValue()
                            if (r6 == 0) goto L58
                            if (r5 != 0) goto L58
                            java.lang.Boolean r4 = java.lang.Boolean.TRUE
                            goto L5c
                        L58:
                            if (r6 != 0) goto L5c
                            java.lang.Boolean r4 = java.lang.Boolean.FALSE
                        L5c:
                            if (r4 == 0) goto L67
                            r0.label = r3
                            java.lang.Object r5 = r7.emit(r4, r0)
                            if (r5 != r1) goto L67
                            return r1
                        L67:
                            kotlin.Unit r5 = kotlin.Unit.INSTANCE
                            return r5
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1$invokeSuspend$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, sensitiveContentCoordinatorImpl), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            });
            final SensitiveContentCoordinatorImpl sensitiveContentCoordinatorImpl2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit(((Boolean) obj2).booleanValue(), continuation);
                }

                public final Object emit(boolean z, Continuation continuation) {
                    SensitiveContentCoordinatorImpl.this.inTransitionFromLockedToGone = z;
                    SensitiveContentCoordinatorImpl.this.invalidateList("inTransitionFromLockedToGoneChanged");
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (distinctUntilChanged.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((SensitiveContentCoordinatorImpl$attach$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
