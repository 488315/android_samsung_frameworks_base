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
import com.android.systemui.keyguard.shared.model.BiometricUnlockMode;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SystemSettings;
import com.android.systemui.util.time.SystemClock;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;
import kotlinx.coroutines.flow.internal.CombineKt;

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
        final ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(distinctFlowImpl, new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$filter$1

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
                        if (((ShowWhileAwakeReason) obj) == ShowWhileAwakeReason.KEYGUARD_TIMEOUT_WHILE_SCREEN_ON) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = sharedFlowImpl.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardWakeDirectlyToGoneInteractor$shouldSuppressKeyguard$3(null), new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$map$1

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

                /* JADX WARN: Code restructure failed: missing block: B:21:0x0065, code lost:
                
                    if (r6.emit(r8, r7) == r0) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
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
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.label = 1;
                        KeyguardEnabledInteractor keyguardEnabledInteractor = this.$keyguardEnabledInteractor$inlined;
                        Object objWithContext = BuildersKt.withContext(keyguardEnabledInteractor.backgroundDispatcher, new KeyguardEnabledInteractor$isKeyguardSuppressed$2(keyguardEnabledInteractor, keyguardEnabledInteractor.selectedUserInteractor.getSelectedUserId(), null), anonymousClass1);
                        if (objWithContext != coroutineSingletons) {
                            obj2 = objWithContext;
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = channelLimitedFlowMergeMerge.collect(new AnonymousClass2(flowCollector, keyguardEnabledInteractor), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.shouldSuppressKeyguard = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) keyguardRepository;
        final Flow[] flowArr = {keyguardRepositoryImpl.isKeyguardEnabled, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, keyguardRepositoryImpl.biometricUnlockState, keyguardRepositoryImpl.canIgnoreAuthAndReturnToGone, keyguardTransitionInteractor.currentKeyguardState, keyguardTransitionInteractor.startedKeyguardTransitionStep};
        this.canWakeDirectlyToGone = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$combine$1

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

                /* JADX WARN: Removed duplicated region for block: B:22:0x007b  */
                /* JADX WARN: Removed duplicated region for block: B:25:0x0088 A[RETURN] */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    Boolean boolValueOf;
                    KeyguardState keyguardState;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        boolean z = false;
                        boolean zBooleanValue = ((Boolean) objArr[0]).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) objArr[1]).booleanValue();
                        BiometricUnlockModel biometricUnlockModel = (BiometricUnlockModel) objArr[2];
                        boolean zBooleanValue3 = ((Boolean) objArr[3]).booleanValue();
                        KeyguardState keyguardState2 = (KeyguardState) objArr[4];
                        TransitionStep transitionStep = (TransitionStep) objArr[5];
                        if (!zBooleanValue || zBooleanValue2) {
                            z = true;
                            boolValueOf = Boolean.valueOf(z);
                            this.label = 1;
                            if (flowCollector.emit(boolValueOf, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            BiometricUnlockMode.Companion companion = BiometricUnlockMode.Companion;
                            BiometricUnlockMode biometricUnlockMode = biometricUnlockModel.mode;
                            companion.getClass();
                            if (BiometricUnlockMode.wakeAndUnlockModes.contains(biometricUnlockMode) || zBooleanValue3 || ((keyguardState2 == KeyguardState.DREAMING && ((Boolean) this.$keyguardInteractor$inlined.isKeyguardDismissible.getValue()).booleanValue()) || (keyguardState2 == (keyguardState = KeyguardState.GONE) && transitionStep.to == keyguardState))) {
                            }
                            boolValueOf = Boolean.valueOf(z);
                            this.label = 1;
                            if (flowCollector.emit(boolValueOf, this) == coroutineSingletons) {
                            }
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
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, keyguardInteractor), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        });
        ?? r9 = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardWakeDirectlyToGoneInteractor$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra(SystemUIAnalytics.QPNE_KEY_COUNT, 0);
                    KeyguardWakeDirectlyToGoneInteractor keyguardWakeDirectlyToGoneInteractor = this.this$0;
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
