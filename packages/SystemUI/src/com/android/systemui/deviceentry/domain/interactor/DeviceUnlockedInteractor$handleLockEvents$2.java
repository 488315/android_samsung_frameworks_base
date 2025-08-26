package com.android.systemui.deviceentry.domain.interactor;

import android.util.Log;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import kotlin.Pair;
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

/* loaded from: classes2.dex */
final class DeviceUnlockedInteractor$handleLockEvents$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ DeviceUnlockedInteractor this$0;

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
            final Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(this.this$0.keyguardInteractor.isDreamingAny);
            return new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$4$invokeSuspend$$inlined$map$1

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
                            Object lockWithDelay = ((Boolean) obj).booleanValue() ? new DeviceUnlockedInteractor.LockWithDelay("started dreaming") : new DeviceUnlockedInteractor.CancelDelayedLock("stopped dreaming");
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(lockWithDelay, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
        final DistinctFlowImpl distinctFlowImplDistinctUntilChangedBy$FlowKt__DistinctKt = FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$1

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
                        WakefulnessModel wakefulnessModel = (WakefulnessModel) obj;
                        Pair pair = new Pair(Boolean.valueOf(wakefulnessModel.isAsleep()), wakefulnessModel.lastSleepReason);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(pair, anonymousClass1) == coroutineSingletons) {
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
        }, new DeviceUnlockedInteractor$handleLockEvents$2$$ExternalSyntheticLambda0(), FlowKt__DistinctKt.defaultAreEquivalent);
        final DeviceUnlockedInteractor deviceUnlockedInteractor = this.this$0;
        Flow flow = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$handleLockEvents$2$invokeSuspend$$inlined$map$2

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

                /* JADX WARN: Code restructure failed: missing block: B:34:0x00a9, code lost:
                
                    if (r2.emit(r5, r0) != r1) goto L36;
                 */
                /* JADX WARN: Removed duplicated region for block: B:30:0x0086  */
                /* JADX WARN: Removed duplicated region for block: B:31:0x008e  */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    WakeSleepReason wakeSleepReason;
                    FlowCollector flowCollector;
                    Object cancelDelayedLock;
                    WakeSleepReason wakeSleepReason2;
                    FlowCollector flowCollector2;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object powerButtonInstantlyLocks = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(powerButtonInstantlyLocks);
                        Pair pair = (Pair) obj;
                        boolean zBooleanValue = ((Boolean) pair.component1()).booleanValue();
                        wakeSleepReason = (WakeSleepReason) pair.component2();
                        flowCollector = this.$this_unsafeFlow;
                        if (zBooleanValue) {
                            if (wakeSleepReason == WakeSleepReason.POWER_BUTTON) {
                                AuthenticationInteractor authenticationInteractor = this.this$0.authenticationInteractor;
                                anonymousClass1.L$0 = flowCollector;
                                anonymousClass1.L$1 = wakeSleepReason;
                                anonymousClass1.label = 1;
                                powerButtonInstantlyLocks = authenticationInteractor.getPowerButtonInstantlyLocks(anonymousClass1);
                                if (powerButtonInstantlyLocks != coroutineSingletons) {
                                    wakeSleepReason2 = wakeSleepReason;
                                    flowCollector2 = flowCollector;
                                }
                                return coroutineSingletons;
                            }
                            cancelDelayedLock = wakeSleepReason != WakeSleepReason.SLEEP_BUTTON ? new DeviceUnlockedInteractor.LockImmediately("locked instantly from sleep button") : new DeviceUnlockedInteractor.LockWithDelay("entering sleep");
                            anonymousClass1.L$0 = null;
                            anonymousClass1.L$1 = null;
                            anonymousClass1.label = 2;
                        } else {
                            cancelDelayedLock = new DeviceUnlockedInteractor.CancelDelayedLock("waking up");
                            anonymousClass1.L$0 = null;
                            anonymousClass1.L$1 = null;
                            anonymousClass1.label = 2;
                        }
                    } else {
                        if (i2 != 1) {
                            if (i2 != 2) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(powerButtonInstantlyLocks);
                            return Unit.INSTANCE;
                        }
                        wakeSleepReason2 = (WakeSleepReason) anonymousClass1.L$1;
                        flowCollector2 = (FlowCollector) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(powerButtonInstantlyLocks);
                    }
                    if (((Boolean) powerButtonInstantlyLocks).booleanValue()) {
                        cancelDelayedLock = new DeviceUnlockedInteractor.LockImmediately("locked instantly from power button");
                        flowCollector = flowCollector2;
                        anonymousClass1.L$0 = null;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.label = 2;
                    } else {
                        flowCollector = flowCollector2;
                        wakeSleepReason = wakeSleepReason2;
                        if (wakeSleepReason != WakeSleepReason.SLEEP_BUTTON) {
                        }
                        anonymousClass1.L$0 = null;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.label = 2;
                    }
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = distinctFlowImplDistinctUntilChangedBy$FlowKt__DistinctKt.collect(new AnonymousClass2(flowCollector, deviceUnlockedInteractor), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        DeviceUnlockedInteractor deviceUnlockedInteractor2 = this.this$0;
        return FlowKt.merge(flow, LatestConflatedKt.flatMapLatestConflated(deviceUnlockedInteractor2.powerInteractor.isInteractive, new AnonymousClass4(deviceUnlockedInteractor2, null)));
    }
}
