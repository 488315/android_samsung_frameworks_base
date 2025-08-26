package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import android.util.Log;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepository;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.statusbar.policy.domain.interactor.DeviceProvisioningInteractor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class KeyguardTransitionBootInteractor {
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final DeviceProvisioningInteractor deviceProvisioningInteractor;
    public final InternalKeyguardTransitionInteractor internalTransitionInteractor;
    public final KeyguardBouncerRepository keyguardBouncerRepository;
    public final KeyguardTransitionRepository repository;
    public final CoroutineScope scope;
    public final Lazy showLockscreenOnBoot$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionBootInteractor$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            final KeyguardTransitionBootInteractor keyguardTransitionBootInteractor = this.f$0;
            final Flow flow = keyguardTransitionBootInteractor.deviceProvisioningInteractor.isDeviceProvisioned;
            return new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionBootInteractor$showLockscreenOnBoot_delegate$lambda$1$$inlined$map$1

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionBootInteractor$showLockscreenOnBoot_delegate$lambda$1$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ KeyguardTransitionBootInteractor this$0;

                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionBootInteractor$showLockscreenOnBoot_delegate$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, KeyguardTransitionBootInteractor keyguardTransitionBootInteractor) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = keyguardTransitionBootInteractor;
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:21:0x006a, code lost:
                    
                        if (r9 == r1) goto L34;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:33:0x009e, code lost:
                    
                        if (r10.emit(r8, r0) == r1) goto L34;
                     */
                    /* JADX WARN: Removed duplicated region for block: B:30:0x008e  */
                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        FlowCollector flowCollector;
                        Object objIsAuthenticationRequired;
                        Object objIsLockscreenEnabled;
                        FlowCollector flowCollector2;
                        Object obj2;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i = anonymousClass1.label;
                            if ((i & Integer.MIN_VALUE) != 0) {
                                anonymousClass1.label = i - Integer.MIN_VALUE;
                            } else {
                                anonymousClass1 = new AnonymousClass1(continuation);
                            }
                        }
                        Object obj3 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i2 = anonymousClass1.label;
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj3);
                            boolean zBooleanValue = ((Boolean) obj).booleanValue();
                            flowCollector = this.$this_unsafeFlow;
                            if (zBooleanValue) {
                                DeviceEntryInteractor deviceEntryInteractor = this.this$0.deviceEntryInteractor;
                                anonymousClass1.L$0 = flowCollector;
                                anonymousClass1.L$1 = null;
                                anonymousClass1.label = 2;
                                objIsLockscreenEnabled = ((DeviceEntryRepositoryImpl) deviceEntryInteractor.repository).isLockscreenEnabled(anonymousClass1);
                                if (objIsLockscreenEnabled != coroutineSingletons) {
                                    FlowCollector flowCollector3 = flowCollector;
                                    obj3 = objIsLockscreenEnabled;
                                    flowCollector2 = flowCollector3;
                                    Object obj4 = obj3;
                                    flowCollector = flowCollector2;
                                    obj2 = obj4;
                                    anonymousClass1.L$0 = null;
                                    anonymousClass1.L$1 = null;
                                    anonymousClass1.label = 3;
                                }
                            } else {
                                DeviceEntryInteractor deviceEntryInteractor2 = this.this$0.deviceEntryInteractor;
                                anonymousClass1.L$0 = this;
                                anonymousClass1.L$1 = flowCollector;
                                anonymousClass1.label = 1;
                                objIsAuthenticationRequired = deviceEntryInteractor2.isAuthenticationRequired(anonymousClass1);
                            }
                            return coroutineSingletons;
                        }
                        if (i2 == 1) {
                            FlowCollector flowCollector4 = (FlowCollector) anonymousClass1.L$1;
                            AnonymousClass2 anonymousClass2 = (AnonymousClass2) anonymousClass1.L$0;
                            ResultKt.throwOnFailure(obj3);
                            flowCollector = flowCollector4;
                            this = anonymousClass2;
                            objIsAuthenticationRequired = obj3;
                        } else {
                            if (i2 != 2) {
                                if (i2 != 3) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj3);
                                return Unit.INSTANCE;
                            }
                            flowCollector2 = (FlowCollector) anonymousClass1.L$0;
                            ResultKt.throwOnFailure(obj3);
                            Object obj42 = obj3;
                            flowCollector = flowCollector2;
                            obj2 = obj42;
                            anonymousClass1.L$0 = null;
                            anonymousClass1.L$1 = null;
                            anonymousClass1.label = 3;
                        }
                        if (!((Boolean) objIsAuthenticationRequired).booleanValue()) {
                            obj2 = Boolean.FALSE;
                            anonymousClass1.L$0 = null;
                            anonymousClass1.L$1 = null;
                            anonymousClass1.label = 3;
                        }
                        DeviceEntryInteractor deviceEntryInteractor3 = this.this$0.deviceEntryInteractor;
                        anonymousClass1.L$0 = flowCollector;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.label = 2;
                        objIsLockscreenEnabled = ((DeviceEntryRepositoryImpl) deviceEntryInteractor3.repository).isLockscreenEnabled(anonymousClass1);
                        if (objIsLockscreenEnabled != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object objCollect = flow.collect(new AnonymousClass2(flowCollector, keyguardTransitionBootInteractor), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
        }
    });

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionBootInteractor$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardTransitionBootInteractor.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:26:0x0083, code lost:
        
            if (r12 == r0) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00f1, code lost:
        
            if (r11 != r0) goto L42;
         */
        /* JADX WARN: Removed duplicated region for block: B:23:0x006f  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0073  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00ef  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            KeyguardTransitionRepository keyguardTransitionRepository;
            KeyguardState keyguardState;
            Object objStartTransition;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (KeyguardTransitionBootInteractor.this.internalTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().from != KeyguardState.OFF) {
                    Boxing.boxInt(Log.e("KeyguardTransitionInteractor", "showLockscreenOnBoot emitted, but we've already transitioned to a state other than OFF. We'll respect that transition, but this should not happen."));
                    return Unit.INSTANCE;
                }
                KeyguardTransitionBootInteractor keyguardTransitionBootInteractor = KeyguardTransitionBootInteractor.this;
                keyguardTransitionRepository = keyguardTransitionBootInteractor.repository;
                Flow flow = (Flow) keyguardTransitionBootInteractor.showLockscreenOnBoot$delegate.getValue();
                this.L$0 = keyguardTransitionRepository;
                this.label = 2;
                obj = FlowKt.first(flow, this);
                if (obj != coroutineSingletons) {
                    if (((Boolean) obj).booleanValue()) {
                    }
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i == 2) {
                    keyguardTransitionRepository = (KeyguardTransitionRepository) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    if (((Boolean) obj).booleanValue()) {
                        DeviceEntryInteractor deviceEntryInteractor = KeyguardTransitionBootInteractor.this.deviceEntryInteractor;
                        this.L$0 = keyguardTransitionRepository;
                        this.label = 3;
                        obj = ((DeviceEntryRepositoryImpl) deviceEntryInteractor.repository).isLockscreenEnabled(this);
                    } else {
                        keyguardState = KeyguardState.LOCKSCREEN;
                        KeyguardState keyguardState2 = keyguardState;
                        this.L$0 = null;
                        this.label = 4;
                        KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl = (KeyguardTransitionRepositoryImpl) keyguardTransitionRepository;
                        keyguardTransitionRepositoryImpl.getClass();
                        KeyguardState keyguardState3 = KeyguardState.OFF;
                        ValueAnimator valueAnimator = new ValueAnimator();
                        valueAnimator.setInterpolator(Interpolators.LINEAR);
                        valueAnimator.setDuration(933L);
                        Unit unit = Unit.INSTANCE;
                        objStartTransition = keyguardTransitionRepositoryImpl.startTransition(new TransitionInfo("KeyguardTransitionRepository(boot)", keyguardState3, keyguardState2, valueAnimator, null, 16, null), this);
                        if (objStartTransition != coroutineSingletons) {
                        }
                    }
                } else if (i == 3) {
                    keyguardTransitionRepository = (KeyguardTransitionRepository) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    keyguardState = (((Boolean) obj).booleanValue() || !(((Boolean) ((KeyguardBouncerRepositoryImpl) KeyguardTransitionBootInteractor.this.keyguardBouncerRepository).primaryBouncerShowingSoon.$$delegate_0.getValue()).booleanValue() || ((Boolean) ((KeyguardBouncerRepositoryImpl) KeyguardTransitionBootInteractor.this.keyguardBouncerRepository).primaryBouncerShow.$$delegate_0.getValue()).booleanValue())) ? KeyguardState.GONE : KeyguardState.PRIMARY_BOUNCER;
                    KeyguardState keyguardState22 = keyguardState;
                    this.L$0 = null;
                    this.label = 4;
                    KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl2 = (KeyguardTransitionRepositoryImpl) keyguardTransitionRepository;
                    keyguardTransitionRepositoryImpl2.getClass();
                    KeyguardState keyguardState32 = KeyguardState.OFF;
                    ValueAnimator valueAnimator2 = new ValueAnimator();
                    valueAnimator2.setInterpolator(Interpolators.LINEAR);
                    valueAnimator2.setDuration(933L);
                    Unit unit2 = Unit.INSTANCE;
                    objStartTransition = keyguardTransitionRepositoryImpl2.startTransition(new TransitionInfo("KeyguardTransitionRepository(boot)", keyguardState32, keyguardState22, valueAnimator2, null, 16, null), this);
                    if (objStartTransition != coroutineSingletons) {
                        objStartTransition = Unit.INSTANCE;
                    }
                } else if (i != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
    }

    public KeyguardTransitionBootInteractor(KeyguardBouncerRepository keyguardBouncerRepository, CoroutineScope coroutineScope, DeviceEntryInteractor deviceEntryInteractor, DeviceProvisioningInteractor deviceProvisioningInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor, KeyguardTransitionRepository keyguardTransitionRepository) {
        this.keyguardBouncerRepository = keyguardBouncerRepository;
        this.scope = coroutineScope;
        this.deviceEntryInteractor = deviceEntryInteractor;
        this.deviceProvisioningInteractor = deviceProvisioningInteractor;
        this.internalTransitionInteractor = internalKeyguardTransitionInteractor;
        this.repository = keyguardTransitionRepository;
    }

    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(null), 7);
    }
}
