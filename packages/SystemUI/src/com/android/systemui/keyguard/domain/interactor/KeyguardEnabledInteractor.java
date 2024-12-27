package com.android.systemui.keyguard.domain.interactor;

import android.util.Log;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.util.kotlin.Utils;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardEnabledInteractor {
    public final BiometricSettingsRepository biometricSettingsRepository;
    public final KeyguardRepository repository;
    public final KeyguardEnabledInteractor$special$$inlined$map$1 showKeyguardWhenReenabled;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ KeyguardTransitionInteractor $transitionInteractor;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardTransitionInteractor keyguardTransitionInteractor, Continuation continuation) {
            super(2, continuation);
            this.$transitionInteractor = keyguardTransitionInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardEnabledInteractor.this.new AnonymousClass1(this.$transitionInteractor, continuation);
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
                Utils.Companion companion = Utils.Companion;
                final ReadonlyStateFlow readonlyStateFlow = ((KeyguardRepositoryImpl) KeyguardEnabledInteractor.this.repository).isKeyguardEnabled;
                Flow sample = companion.sample(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L47
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                r6 = r5
                                java.lang.Boolean r6 = (java.lang.Boolean) r6
                                boolean r6 = r6.booleanValue()
                                r6 = r6 ^ r3
                                if (r6 == 0) goto L47
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L47
                                return r1
                            L47:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                }, ((BiometricSettingsRepositoryImpl) KeyguardEnabledInteractor.this.biometricSettingsRepository).isCurrentUserInLockdown, this.$transitionInteractor.currentTransitionInfoInternal);
                final KeyguardTransitionInteractor keyguardTransitionInteractor = this.$transitionInteractor;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Triple triple = (Triple) obj2;
                        boolean booleanValue = ((Boolean) triple.component2()).booleanValue();
                        if (((TransitionInfo) triple.component3()).to != KeyguardState.GONE && !booleanValue) {
                            KeyguardTransitionInteractor keyguardTransitionInteractor2 = KeyguardTransitionInteractor.this;
                            keyguardTransitionInteractor2.getClass();
                            Flags.sceneContainer();
                            String str = KeyguardTransitionInteractor.TAG;
                            Log.d(str, "#startDismissKeyguardTransition(reason=keyguard disabled)");
                            KeyguardState keyguardState = ((TransitionInfo) keyguardTransitionInteractor2.currentTransitionInfoInternal.$$delegate_0.getValue()).to;
                            switch (KeyguardTransitionInteractor.WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()]) {
                                case 1:
                                    FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor = (FromLockscreenTransitionInteractor) keyguardTransitionInteractor2.fromLockscreenTransitionInteractor.get();
                                    fromLockscreenTransitionInteractor.getClass();
                                    BuildersKt.launch$default(fromLockscreenTransitionInteractor.scope, EmptyCoroutineContext.INSTANCE, null, new FromLockscreenTransitionInteractor$dismissKeyguard$$inlined$launch$default$1("FromLockscreenTransitionInteractor#dismissKeyguard", null, fromLockscreenTransitionInteractor), 2);
                                    break;
                                case 2:
                                    FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor = (FromPrimaryBouncerTransitionInteractor) keyguardTransitionInteractor2.fromPrimaryBouncerTransitionInteractor.get();
                                    fromPrimaryBouncerTransitionInteractor.getClass();
                                    BuildersKt.launch$default(fromPrimaryBouncerTransitionInteractor.scope, null, null, new FromPrimaryBouncerTransitionInteractor$dismissPrimaryBouncer$1(fromPrimaryBouncerTransitionInteractor, null), 3);
                                    break;
                                case 3:
                                    FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor = (FromAlternateBouncerTransitionInteractor) keyguardTransitionInteractor2.fromAlternateBouncerTransitionInteractor.get();
                                    fromAlternateBouncerTransitionInteractor.getClass();
                                    BuildersKt.launch$default(fromAlternateBouncerTransitionInteractor.scope, null, null, new FromAlternateBouncerTransitionInteractor$dismissAlternateBouncer$1(fromAlternateBouncerTransitionInteractor, null), 3);
                                    break;
                                case 4:
                                    FromAodTransitionInteractor fromAodTransitionInteractor = (FromAodTransitionInteractor) keyguardTransitionInteractor2.fromAodTransitionInteractor.get();
                                    fromAodTransitionInteractor.getClass();
                                    BuildersKt.launch$default(fromAodTransitionInteractor.scope, EmptyCoroutineContext.INSTANCE, null, new FromAodTransitionInteractor$dismissAod$$inlined$launch$default$1("FromAodTransitionInteractor#dismissAod", null, fromAodTransitionInteractor), 2);
                                    break;
                                case 5:
                                    FromDozingTransitionInteractor fromDozingTransitionInteractor = (FromDozingTransitionInteractor) keyguardTransitionInteractor2.fromDozingTransitionInteractor.get();
                                    fromDozingTransitionInteractor.getClass();
                                    BuildersKt.launch$default(fromDozingTransitionInteractor.scope, null, null, new FromDozingTransitionInteractor$dismissFromDozing$1(fromDozingTransitionInteractor, null), 3);
                                    break;
                                case 6:
                                    Log.i(str, "Already transitioning to GONE; ignoring startDismissKeyguardTransition.");
                                    break;
                                default:
                                    Log.e(str, "We don't know how to dismiss keyguard from state " + keyguardState + ".");
                                    break;
                            }
                        }
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

    public KeyguardEnabledInteractor(CoroutineScope coroutineScope, KeyguardRepository keyguardRepository, BiometricSettingsRepository biometricSettingsRepository, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        this.repository = keyguardRepository;
        this.biometricSettingsRepository = biometricSettingsRepository;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(keyguardTransitionInteractor, null), 3);
        Utils.Companion companion = Utils.Companion;
        final ReadonlyStateFlow readonlyStateFlow = ((KeyguardRepositoryImpl) keyguardRepository).isKeyguardEnabled;
        final Flow sample = companion.sample(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        r6 = r6 ^ r3
                        if (r6 == 0) goto L47
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, keyguardTransitionInteractor.currentTransitionInfoInternal, ((BiometricSettingsRepositoryImpl) biometricSettingsRepository).isCurrentUserInLockdown);
        new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Triple r5 = (kotlin.Triple) r5
                        java.lang.Object r6 = r5.component2()
                        com.android.systemui.keyguard.shared.model.TransitionInfo r6 = (com.android.systemui.keyguard.shared.model.TransitionInfo) r6
                        java.lang.Object r5 = r5.component3()
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = r6.to
                        com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
                        if (r6 == r2) goto L4e
                        if (r5 != 0) goto L4e
                        r5 = r3
                        goto L4f
                    L4e:
                        r5 = 0
                    L4f:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5e
                        return r1
                    L5e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
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
