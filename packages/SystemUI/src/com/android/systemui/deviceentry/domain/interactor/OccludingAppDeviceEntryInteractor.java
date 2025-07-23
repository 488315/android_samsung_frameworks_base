package com.android.systemui.deviceentry.domain.interactor;

import android.content.Context;
import android.content.Intent;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepository;
import com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class OccludingAppDeviceEntryInteractor {
    public final Context context;
    public final OccludingAppDeviceEntryInteractor$special$$inlined$map$3 fingerprintLockoutEvents;
    public final OccludingAppDeviceEntryInteractor$special$$inlined$map$2 fingerprintUnlockSuccessEvents;
    public final Flow keyguardOccludedByApp;
    public final ChannelFlowTransformLatest message;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ KeyguardInteractor $keyguardInteractor;
        final /* synthetic */ PowerInteractor $powerInteractor;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$1$3, reason: invalid class name */
        final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
            public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

            public AnonymousClass3() {
                super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                Boolean bool2 = (Boolean) obj2;
                bool2.booleanValue();
                return new Pair(bool, bool2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PowerInteractor powerInteractor, KeyguardInteractor keyguardInteractor, Continuation continuation) {
            super(2, continuation);
            this.$powerInteractor = powerInteractor;
            this.$keyguardInteractor = keyguardInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return OccludingAppDeviceEntryInteractor.this.new AnonymousClass1(this.$powerInteractor, this.$keyguardInteractor, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow sample = FlowKt.sample(OccludingAppDeviceEntryInteractor.this.fingerprintUnlockSuccessEvents, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.$powerInteractor.isInteractive, this.$keyguardInteractor.isDreaming, AnonymousClass3.INSTANCE));
                FlowCollector flowCollector = new FlowCollector(OccludingAppDeviceEntryInteractor.this) { // from class: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor.1.4
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        ((Boolean) pair.component1()).booleanValue();
                        ((Boolean) pair.component2()).booleanValue();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (sample.collect(flowCollector, this) == coroutineSingletons) {
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
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ ActivityStarter $activityStarter;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ActivityStarter activityStarter, Continuation continuation) {
            super(2, continuation);
            this.$activityStarter = activityStarter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return OccludingAppDeviceEntryInteractor.this.new AnonymousClass2(this.$activityStarter, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final OccludingAppDeviceEntryInteractor occludingAppDeviceEntryInteractor = OccludingAppDeviceEntryInteractor.this;
                OccludingAppDeviceEntryInteractor$special$$inlined$map$3 occludingAppDeviceEntryInteractor$special$$inlined$map$3 = occludingAppDeviceEntryInteractor.fingerprintLockoutEvents;
                final ActivityStarter activityStarter = this.$activityStarter;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        final OccludingAppDeviceEntryInteractor occludingAppDeviceEntryInteractor2 = occludingAppDeviceEntryInteractor;
                        ActivityStarter.this.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor.2.1.1
                            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                            public final boolean onDismiss() {
                                Context context = OccludingAppDeviceEntryInteractor.this.context;
                                Intent intent = new Intent("android.intent.action.MAIN");
                                intent.addCategory("android.intent.category.HOME");
                                intent.setFlags(268435456);
                                context.startActivity(intent);
                                return false;
                            }

                            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                            public final boolean willRunAnimationOnKeyguard() {
                                return false;
                            }
                        }, null, false);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (occludingAppDeviceEntryInteractor$special$$inlined$map$3.collect(flowCollector, this) == coroutineSingletons) {
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
    }

    /* JADX WARN: Type inference failed for: r11v4, types: [com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$map$2] */
    public OccludingAppDeviceEntryInteractor(BiometricMessageInteractor biometricMessageInteractor, DeviceEntryFingerprintAuthRepository deviceEntryFingerprintAuthRepository, KeyguardInteractor keyguardInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, CoroutineScope coroutineScope, Context context, ActivityStarter activityStarter, PowerInteractor powerInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, CommunalSceneInteractor communalSceneInteractor) {
        this.context = context;
        final Flow[] flowArr = {keyguardInteractor.isKeyguardOccluded, keyguardInteractor.isKeyguardShowing, primaryBouncerInteractor.isShowing, alternateBouncerInteractor.isVisible, keyguardInteractor.isDozing, communalSceneInteractor.isIdleOnCommunal};
        Flow distinctUntilChanged = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$combine$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                public AnonymousClass3(Continuation continuation) {
                    super(3, continuation);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        boolean z = false;
                        Object obj2 = objArr[0];
                        Object obj3 = objArr[1];
                        Object obj4 = objArr[2];
                        Object obj5 = objArr[3];
                        Object obj6 = objArr[4];
                        boolean booleanValue = ((Boolean) objArr[5]).booleanValue();
                        boolean booleanValue2 = ((Boolean) obj6).booleanValue();
                        boolean booleanValue3 = ((Boolean) obj5).booleanValue();
                        boolean booleanValue4 = ((Boolean) obj4).booleanValue();
                        boolean booleanValue5 = ((Boolean) obj3).booleanValue();
                        if (((Boolean) obj2).booleanValue() && booleanValue5 && !booleanValue4 && !booleanValue3 && !booleanValue2 && !booleanValue) {
                            z = true;
                        }
                        Boolean valueOf = Boolean.valueOf(z);
                        this.label = 1;
                        if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
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
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        });
        this.keyguardOccludedByApp = distinctUntilChanged;
        DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl = (DeviceEntryFingerprintAuthRepositoryImpl) deviceEntryFingerprintAuthRepository;
        Flow authenticationStatus = deviceEntryFingerprintAuthRepositoryImpl.getAuthenticationStatus();
        EmptyFlow emptyFlow = EmptyFlow.INSTANCE;
        final ChannelFlowTransformLatest transformLatest = kotlinx.coroutines.flow.FlowKt.transformLatest(distinctUntilChanged, new OccludingAppDeviceEntryInteractor$ifKeyguardOccludedByApp$$inlined$flatMapLatest$1(null, authenticationStatus, emptyFlow));
        final Flow flow = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus r6 = (com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus) r6
                        boolean r6 = r6 instanceof com.android.systemui.keyguard.shared.model.SuccessFingerprintAuthenticationStatus
                        if (r6 == 0) goto L44
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.fingerprintUnlockSuccessEvents = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus r5 = (com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus) r5
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final ChannelFlowTransformLatest transformLatest2 = kotlinx.coroutines.flow.FlowKt.transformLatest(distinctUntilChanged, new OccludingAppDeviceEntryInteractor$ifKeyguardOccludedByApp$$inlined$flatMapLatest$1(null, deviceEntryFingerprintAuthRepositoryImpl.getAuthenticationStatus(), emptyFlow));
        this.fingerprintLockoutEvents = new OccludingAppDeviceEntryInteractor$special$$inlined$map$3(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$2$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$2$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus r6 = (com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus) r6
                        boolean r2 = r6 instanceof com.android.systemui.keyguard.shared.model.ErrorFingerprintAuthenticationStatus
                        if (r2 == 0) goto L4f
                        com.android.systemui.keyguard.shared.model.ErrorFingerprintAuthenticationStatus r6 = (com.android.systemui.keyguard.shared.model.ErrorFingerprintAuthenticationStatus) r6
                        int r6 = r6.msgId
                        r2 = 7
                        if (r6 == r2) goto L44
                        r2 = 9
                        if (r6 != r2) goto L4f
                    L44:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filter$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final ChannelLimitedFlowMerge channelLimitedFlowMerge = biometricMessageInteractor.fingerprintMessage;
        this.message = kotlinx.coroutines.flow.FlowKt.transformLatest(distinctUntilChanged, new OccludingAppDeviceEntryInteractor$ifKeyguardOccludedByApp$$inlined$flatMapLatest$1(null, new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filterNot$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filterNot$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filterNot$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filterNot$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filterNot$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filterNot$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.deviceentry.shared.model.FingerprintMessage r6 = (com.android.systemui.deviceentry.shared.model.FingerprintMessage) r6
                        boolean r6 = r6 instanceof com.android.systemui.deviceentry.shared.model.FingerprintLockoutMessage
                        if (r6 != 0) goto L44
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.OccludingAppDeviceEntryInteractor$special$$inlined$filterNot$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null)));
        if (context.getResources().getBoolean(R.bool.config_goToHomeFromOccludedApps)) {
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(powerInteractor, keyguardInteractor, null), 7);
        }
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(activityStarter, null), 7);
    }
}
