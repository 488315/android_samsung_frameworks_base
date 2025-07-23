package com.android.systemui.deviceentry.domain.interactor;

import android.util.Log;
import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__DistinctKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DeviceUnlockedInteractor$handleLockEvents$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ DeviceUnlockedInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;
        final /* synthetic */ DeviceUnlockedInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(DeviceUnlockedInteractor deviceUnlockedInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = deviceUnlockedInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.this$0, continuation);
            anonymousClass4.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass4;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass4) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (!this.Z$0) {
                return EmptyFlow.INSTANCE;
            }
            final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(this.this$0.keyguardInteractor.isDreamingAny);
            return new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$4$invokeSuspend$$inlined$map$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$4$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$4$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                            boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$4$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$4$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$4$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$4$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$4$invokeSuspend$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L56
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            java.lang.Boolean r5 = (java.lang.Boolean) r5
                            boolean r5 = r5.booleanValue()
                            if (r5 == 0) goto L43
                            com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$LockWithDelay r5 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$LockWithDelay
                            java.lang.String r6 = "started dreaming"
                            r5.<init>(r6)
                            goto L4b
                        L43:
                            com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$CancelDelayedLock r5 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$CancelDelayedLock
                            java.lang.String r6 = "stopped dreaming"
                            r5.<init>(r6)
                        L4b:
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L56
                            return r1
                        L56:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$4$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceUnlockedInteractor$handleLockEvents$2(DeviceUnlockedInteractor deviceUnlockedInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceUnlockedInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceUnlockedInteractor$handleLockEvents$2 deviceUnlockedInteractor$handleLockEvents$2 = new DeviceUnlockedInteractor$handleLockEvents$2(this.this$0, continuation);
        deviceUnlockedInteractor$handleLockEvents$2.Z$0 = ((Boolean) obj).booleanValue();
        return deviceUnlockedInteractor$handleLockEvents$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((DeviceUnlockedInteractor$handleLockEvents$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.Z$0) {
            Log.d(DeviceUnlockedInteractor.TAG, "In trusted environment, ignoring power-related lock events");
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new DeviceUnlockedInteractor.CancelDelayedLock("in trusted environment"));
        }
        Log.d(DeviceUnlockedInteractor.TAG, "Not in trusted environment, power-related lock events treated as normal");
        final ReadonlyStateFlow readonlyStateFlow = this.this$0.powerInteractor.detailedWakefulness;
        final DistinctFlowImpl distinctUntilChangedBy$FlowKt__DistinctKt = FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.power.shared.model.WakefulnessModel r5 = (com.android.systemui.power.shared.model.WakefulnessModel) r5
                        kotlin.Pair r6 = new kotlin.Pair
                        boolean r2 = r5.isAsleep()
                        java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
                        com.android.systemui.power.shared.model.WakeSleepReason r5 = r5.lastSleepReason
                        r6.<init>(r2, r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L4e
                        return r1
                    L4e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new DeviceUnlockedInteractor$handleLockEvents$2$$ExternalSyntheticLambda0(), FlowKt__DistinctKt.defaultAreEquivalent);
        final DeviceUnlockedInteractor deviceUnlockedInteractor = this.this$0;
        Flow flow = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DeviceUnlockedInteractor this$0;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$2$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
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
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, DeviceUnlockedInteractor deviceUnlockedInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = deviceUnlockedInteractor;
                }

                /* JADX WARN: Code restructure failed: missing block: B:21:0x00a9, code lost:
                
                    if (r2.emit(r5, r0) != r1) goto L36;
                 */
                /* JADX WARN: Removed duplicated region for block: B:19:0x0077  */
                /* JADX WARN: Removed duplicated region for block: B:23:0x0080  */
                /* JADX WARN: Removed duplicated region for block: B:26:0x0086  */
                /* JADX WARN: Removed duplicated region for block: B:27:0x008e  */
                /* JADX WARN: Removed duplicated region for block: B:28:0x003f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$2$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$2$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$2$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        if (r2 == 0) goto L3f
                        if (r2 == r4) goto L33
                        if (r2 != r3) goto L2b
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto Lac
                    L2b:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L33:
                        java.lang.Object r5 = r0.L$1
                        com.android.systemui.power.shared.model.WakeSleepReason r5 = (com.android.systemui.power.shared.model.WakeSleepReason) r5
                        java.lang.Object r6 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L6f
                    L3f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlin.Pair r6 = (kotlin.Pair) r6
                        java.lang.Object r7 = r6.component1()
                        java.lang.Boolean r7 = (java.lang.Boolean) r7
                        boolean r7 = r7.booleanValue()
                        java.lang.Object r6 = r6.component2()
                        com.android.systemui.power.shared.model.WakeSleepReason r6 = (com.android.systemui.power.shared.model.WakeSleepReason) r6
                        kotlinx.coroutines.flow.FlowCollector r2 = r5.$this_unsafeFlow
                        if (r7 == 0) goto L96
                        com.android.systemui.power.shared.model.WakeSleepReason r7 = com.android.systemui.power.shared.model.WakeSleepReason.POWER_BUTTON
                        if (r6 != r7) goto L82
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor r5 = r5.this$0
                        com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r5 = r5.authenticationInteractor
                        r0.L$0 = r2
                        r0.L$1 = r6
                        r0.label = r4
                        java.lang.Object r7 = r5.getPowerButtonInstantlyLocks(r0)
                        if (r7 != r1) goto L6d
                        goto Lab
                    L6d:
                        r5 = r6
                        r6 = r2
                    L6f:
                        java.lang.Boolean r7 = (java.lang.Boolean) r7
                        boolean r7 = r7.booleanValue()
                        if (r7 == 0) goto L80
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$LockImmediately r5 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$LockImmediately
                        java.lang.String r7 = "locked instantly from power button"
                        r5.<init>(r7)
                        r2 = r6
                        goto L9e
                    L80:
                        r2 = r6
                        r6 = r5
                    L82:
                        com.android.systemui.power.shared.model.WakeSleepReason r5 = com.android.systemui.power.shared.model.WakeSleepReason.SLEEP_BUTTON
                        if (r6 != r5) goto L8e
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$LockImmediately r5 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$LockImmediately
                        java.lang.String r6 = "locked instantly from sleep button"
                        r5.<init>(r6)
                        goto L9e
                    L8e:
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$LockWithDelay r5 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$LockWithDelay
                        java.lang.String r6 = "entering sleep"
                        r5.<init>(r6)
                        goto L9e
                    L96:
                        com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$CancelDelayedLock r5 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$CancelDelayedLock
                        java.lang.String r6 = "waking up"
                        r5.<init>(r6)
                    L9e:
                        r6 = 0
                        r0.L$0 = r6
                        r0.L$1 = r6
                        r0.label = r3
                        java.lang.Object r5 = r2.emit(r5, r0)
                        if (r5 != r1) goto Lac
                    Lab:
                        return r1
                    Lac:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, deviceUnlockedInteractor), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        DeviceUnlockedInteractor deviceUnlockedInteractor2 = this.this$0;
        return FlowKt.merge(flow, LatestConflatedKt.flatMapLatestConflated(deviceUnlockedInteractor2.powerInteractor.isInteractive, new AnonymousClass4(deviceUnlockedInteractor2, null)));
    }
}
