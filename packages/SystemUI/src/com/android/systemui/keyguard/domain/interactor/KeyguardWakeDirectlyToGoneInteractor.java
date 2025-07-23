package com.android.systemui.keyguard.domain.interactor;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SystemSettings;
import com.android.systemui.util.time.SystemClock;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardWakeDirectlyToGoneInteractor {
    public final KeyguardWakeDirectlyToGoneInteractor$broadcastReceiver$1 broadcastReceiver;
    public final Flow canWakeDirectlyToGone;
    public boolean isAwake;
    public final LockPatternUtils lockPatternUtils;
    public final PowerInteractor powerInteractor;
    public final KeyguardRepository repository;
    public final SecureSettings secureSettings;
    public final SelectedUserInteractor selectedUserInteractor;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 shouldSuppressKeyguard;
    public final SystemClock systemClock;
    public final SystemSettings systemSettings;
    public int timeoutCounter;
    public final KeyguardTransitionInteractor transitionInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v2, types: [android.content.BroadcastReceiver, com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$broadcastReceiver$1] */
    public KeyguardWakeDirectlyToGoneInteractor(CoroutineScope coroutineScope, Context context, KeyguardRepository keyguardRepository, SystemClock systemClock, AlarmManager alarmManager, KeyguardTransitionInteractor keyguardTransitionInteractor, PowerInteractor powerInteractor, SecureSettings secureSettings, LockPatternUtils lockPatternUtils, SystemSettings systemSettings, SelectedUserInteractor selectedUserInteractor, final KeyguardEnabledInteractor keyguardEnabledInteractor, KeyguardServiceShowLockscreenInteractor keyguardServiceShowLockscreenInteractor, final KeyguardInteractor keyguardInteractor) {
        this.repository = keyguardRepository;
        this.systemClock = systemClock;
        this.transitionInteractor = keyguardTransitionInteractor;
        this.powerInteractor = powerInteractor;
        this.secureSettings = secureSettings;
        this.lockPatternUtils = lockPatternUtils;
        this.systemSettings = systemSettings;
        this.selectedUserInteractor = selectedUserInteractor;
        DistinctFlowImpl distinctFlowImpl = powerInteractor.isAwake;
        final SharedFlowImpl sharedFlowImpl = keyguardServiceShowLockscreenInteractor.showNowEvents;
        final ChannelLimitedFlowMerge merge = FlowKt.merge(distinctFlowImpl, new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$filter$1$2$1
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
                        com.android.systemui.keyguard.domain.interactor.ShowWhileAwakeReason r6 = (com.android.systemui.keyguard.domain.interactor.ShowWhileAwakeReason) r6
                        com.android.systemui.keyguard.domain.interactor.ShowWhileAwakeReason r2 = com.android.systemui.keyguard.domain.interactor.ShowWhileAwakeReason.KEYGUARD_TIMEOUT_WHILE_SCREEN_ON
                        if (r6 != r2) goto L44
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardWakeDirectlyToGoneInteractor$shouldSuppressKeyguard$3(null), new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ KeyguardEnabledInteractor $keyguardEnabledInteractor$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardEnabledInteractor keyguardEnabledInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$keyguardEnabledInteractor$inlined = keyguardEnabledInteractor;
                }

                /* JADX WARN: Code restructure failed: missing block: B:18:0x0065, code lost:
                
                    if (r6.emit(r8, r7) != r0) goto L23;
                 */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r7 = r8 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r7 == 0) goto L13
                        r7 = r8
                        com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$map$1$2$1 r7 = (com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r7
                        int r0 = r7.label
                        r1 = -2147483648(0xffffffff80000000, float:-0.0)
                        r2 = r0 & r1
                        if (r2 == 0) goto L13
                        int r0 = r0 - r1
                        r7.label = r0
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$map$1$2$1 r7 = new com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$map$1$2$1
                        r7.<init>(r8)
                    L18:
                        java.lang.Object r8 = r7.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r1 = r7.label
                        r2 = 0
                        r3 = 2
                        r4 = 1
                        if (r1 == 0) goto L3b
                        if (r1 == r4) goto L33
                        if (r1 != r3) goto L2b
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L68
                    L2b:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L33:
                        java.lang.Object r6 = r7.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L5d
                    L3b:
                        kotlin.ResultKt.throwOnFailure(r8)
                        kotlinx.coroutines.flow.FlowCollector r8 = r6.$this_unsafeFlow
                        r7.L$0 = r8
                        r7.label = r4
                        com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor r6 = r6.$keyguardEnabledInteractor$inlined
                        com.android.systemui.user.domain.interactor.SelectedUserInteractor r1 = r6.selectedUserInteractor
                        int r1 = r1.getSelectedUserId()
                        com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$isKeyguardSuppressed$2 r4 = new com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor$isKeyguardSuppressed$2
                        r4.<init>(r6, r1, r2)
                        kotlinx.coroutines.CoroutineDispatcher r6 = r6.backgroundDispatcher
                        java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r6, r4, r7)
                        if (r6 != r0) goto L5a
                        goto L67
                    L5a:
                        r5 = r8
                        r8 = r6
                        r6 = r5
                    L5d:
                        r7.L$0 = r2
                        r7.label = r3
                        java.lang.Object r6 = r6.emit(r8, r7)
                        if (r6 != r0) goto L68
                    L67:
                        return r0
                    L68:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, keyguardEnabledInteractor), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.shouldSuppressKeyguard = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) keyguardRepository;
        final Flow[] flowArr = {keyguardRepositoryImpl.isKeyguardEnabled, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, keyguardRepositoryImpl.biometricUnlockState, keyguardRepositoryImpl.canIgnoreAuthAndReturnToGone, keyguardTransitionInteractor.currentKeyguardState, keyguardTransitionInteractor.startedKeyguardTransitionStep};
        this.canWakeDirectlyToGone = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$combine$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                final /* synthetic */ KeyguardInteractor $keyguardInteractor$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, KeyguardInteractor keyguardInteractor) {
                    super(3, continuation);
                    this.$keyguardInteractor$inlined = keyguardInteractor;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.$keyguardInteractor$inlined);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:19:0x0071, code lost:
                
                    if (((java.lang.Boolean) r10.$keyguardInteractor$inlined.isKeyguardDismissible.getValue()).booleanValue() == false) goto L18;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:23:0x0079, code lost:
                
                    if (r1.to != r4) goto L23;
                 */
                /* JADX WARN: Removed duplicated region for block: B:26:0x0088 A[RETURN] */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r11) {
                    /*
                        r10 = this;
                        kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r1 = r10.label
                        r2 = 1
                        if (r1 == 0) goto L16
                        if (r1 != r2) goto Le
                        kotlin.ResultKt.throwOnFailure(r11)
                        goto L89
                    Le:
                        java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                        java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                        r10.<init>(r11)
                        throw r10
                    L16:
                        kotlin.ResultKt.throwOnFailure(r11)
                        java.lang.Object r11 = r10.L$0
                        kotlinx.coroutines.flow.FlowCollector r11 = (kotlinx.coroutines.flow.FlowCollector) r11
                        java.lang.Object r1 = r10.L$1
                        java.lang.Object[] r1 = (java.lang.Object[]) r1
                        r3 = 0
                        r4 = r1[r3]
                        java.lang.Boolean r4 = (java.lang.Boolean) r4
                        boolean r4 = r4.booleanValue()
                        r5 = r1[r2]
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        r6 = 2
                        r6 = r1[r6]
                        com.android.systemui.keyguard.shared.model.BiometricUnlockModel r6 = (com.android.systemui.keyguard.shared.model.BiometricUnlockModel) r6
                        r7 = 3
                        r7 = r1[r7]
                        java.lang.Boolean r7 = (java.lang.Boolean) r7
                        boolean r7 = r7.booleanValue()
                        r8 = 4
                        r8 = r1[r8]
                        com.android.systemui.keyguard.shared.model.KeyguardState r8 = (com.android.systemui.keyguard.shared.model.KeyguardState) r8
                        r9 = 5
                        r1 = r1[r9]
                        com.android.systemui.keyguard.shared.model.TransitionStep r1 = (com.android.systemui.keyguard.shared.model.TransitionStep) r1
                        if (r4 == 0) goto L7b
                        if (r5 != 0) goto L7b
                        com.android.systemui.keyguard.shared.model.BiometricUnlockMode$Companion r4 = com.android.systemui.keyguard.shared.model.BiometricUnlockMode.Companion
                        com.android.systemui.keyguard.shared.model.BiometricUnlockMode r5 = r6.mode
                        r4.getClass()
                        java.util.Set r4 = com.android.systemui.keyguard.shared.model.BiometricUnlockMode.wakeAndUnlockModes
                        boolean r4 = r4.contains(r5)
                        if (r4 != 0) goto L7b
                        if (r7 != 0) goto L7b
                        com.android.systemui.keyguard.shared.model.KeyguardState r4 = com.android.systemui.keyguard.shared.model.KeyguardState.DREAMING
                        if (r8 != r4) goto L73
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r4 = r10.$keyguardInteractor$inlined
                        kotlinx.coroutines.flow.StateFlowImpl r4 = r4.isKeyguardDismissible
                        java.lang.Object r4 = r4.getValue()
                        java.lang.Boolean r4 = (java.lang.Boolean) r4
                        boolean r4 = r4.booleanValue()
                        if (r4 != 0) goto L7b
                    L73:
                        com.android.systemui.keyguard.shared.model.KeyguardState r4 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
                        if (r8 != r4) goto L7c
                        com.android.systemui.keyguard.shared.model.KeyguardState r1 = r1.to
                        if (r1 != r4) goto L7c
                    L7b:
                        r3 = r2
                    L7c:
                        java.lang.Boolean r1 = java.lang.Boolean.valueOf(r3)
                        r10.label = r2
                        java.lang.Object r10 = r11.emit(r1, r10)
                        if (r10 != r0) goto L89
                        return r0
                    L89:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$combine$1.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, keyguardInteractor), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        });
        ?? r9 = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra(SystemUIAnalytics.QPNE_KEY_COUNT, 0);
                    KeyguardWakeDirectlyToGoneInteractor keyguardWakeDirectlyToGoneInteractor = KeyguardWakeDirectlyToGoneInteractor.this;
                    synchronized (this) {
                        if (keyguardWakeDirectlyToGoneInteractor.timeoutCounter == intExtra) {
                            KeyguardRepositoryImpl keyguardRepositoryImpl2 = (KeyguardRepositoryImpl) keyguardWakeDirectlyToGoneInteractor.repository;
                            keyguardRepositoryImpl2._canIgnoreAuthAndReturnToGone.updateState(null, Boolean.FALSE);
                        }
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
        };
        this.broadcastReceiver = r9;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardWakeDirectlyToGoneInteractor$setOrCancelAlarmFromWakefulness$1(this, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardWakeDirectlyToGoneInteractor$listenForWakeToClearCanIgnoreAuth$1(this, null), 7);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        intentFilter.setPriority(1000);
        context.registerReceiver(r9, intentFilter, "com.android.systemui.permission.SELF", null, 2);
    }
}
