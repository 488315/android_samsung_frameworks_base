package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public abstract class TransitionInteractor {
    public final KeyguardState fromState;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardOcclusionInteractor keyguardOcclusionInteractor;
    public final CoroutineDispatcher mainDispatcher;
    public final String name;
    public final PowerInteractor powerInteractor;
    public final KeyguardTransitionInteractor transitionInteractor;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ KeyguardInteractor $keyguardInteractor;
        int label;

        /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForTransitionToCamera$1$1, reason: invalid class name and collision with other inner class name */
        public final class C02251 implements FlowCollector {
            public final /* synthetic */ TransitionInteractor this$0;

            public C02251(TransitionInteractor transitionInteractor) {
                this.this$0 = transitionInteractor;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
                return emit$1(continuation);
            }

            /* JADX WARN: Code restructure failed: missing block: B:34:0x0099, code lost:
            
                if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r8.this$0, r2, null, r4, "keyguardInteractor.onCameraLaunchDetected", r6, 2) == r0) goto L35;
             */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit$1(Continuation continuation) {
                TransitionInteractor$listenForTransitionToCamera$1$1$emit$1 transitionInteractor$listenForTransitionToCamera$1$1$emit$1;
                if (continuation instanceof TransitionInteractor$listenForTransitionToCamera$1$1$emit$1) {
                    transitionInteractor$listenForTransitionToCamera$1$1$emit$1 = (TransitionInteractor$listenForTransitionToCamera$1$1$emit$1) continuation;
                    int i = transitionInteractor$listenForTransitionToCamera$1$1$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        transitionInteractor$listenForTransitionToCamera$1$1$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        transitionInteractor$listenForTransitionToCamera$1$1$emit$1 = new TransitionInteractor$listenForTransitionToCamera$1$1$emit$1(this, continuation);
                    }
                }
                TransitionInteractor$listenForTransitionToCamera$1$1$emit$1 transitionInteractor$listenForTransitionToCamera$1$1$emit$12 = transitionInteractor$listenForTransitionToCamera$1$1$emit$1;
                Object objMaybeHandleInsecurePowerGesture = transitionInteractor$listenForTransitionToCamera$1$1$emit$12.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = transitionInteractor$listenForTransitionToCamera$1$1$emit$12.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(objMaybeHandleInsecurePowerGesture);
                    if (LsRune.KEYGUARD_FBE && !((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isUserUnlocked$1()) {
                        return Unit.INSTANCE;
                    }
                    transitionInteractor$listenForTransitionToCamera$1$1$emit$12.L$0 = this;
                    transitionInteractor$listenForTransitionToCamera$1$1$emit$12.label = 1;
                    objMaybeHandleInsecurePowerGesture = this.this$0.maybeHandleInsecurePowerGesture(transitionInteractor$listenForTransitionToCamera$1$1$emit$12);
                    if (objMaybeHandleInsecurePowerGesture != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(objMaybeHandleInsecurePowerGesture);
                    return Unit.INSTANCE;
                }
                this = (C02251) transitionInteractor$listenForTransitionToCamera$1$1$emit$12.L$0;
                ResultKt.throwOnFailure(objMaybeHandleInsecurePowerGesture);
                if (((Boolean) objMaybeHandleInsecurePowerGesture).booleanValue()) {
                    return Unit.INSTANCE;
                }
                TransitionModeOnCanceled transitionModeOnCanceled = ((TransitionStep) this.this$0.transitionInteractor.transitionState.$$delegate_0.getValue()).to == KeyguardState.AOD ? TransitionModeOnCanceled.REVERSE : TransitionModeOnCanceled.RESET;
                KeyguardState keyguardState = KeyguardState.OCCLUDED;
                transitionInteractor$listenForTransitionToCamera$1$1$emit$12.L$0 = null;
                transitionInteractor$listenForTransitionToCamera$1$1$emit$12.label = 2;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardInteractor keyguardInteractor, Continuation continuation) {
            super(2, continuation);
            this.$keyguardInteractor = keyguardInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return TransitionInteractor.this.new AnonymousClass1(this.$keyguardInteractor, continuation);
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
                TransitionInteractor transitionInteractor = TransitionInteractor.this;
                KeyguardInteractor$special$$inlined$filter$1 keyguardInteractor$special$$inlined$filter$1 = this.$keyguardInteractor.onCameraLaunchDetected;
                transitionInteractor.getClass();
                TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardState$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1(keyguardInteractor$special$$inlined$filter$1, transitionInteractor);
                C02251 c02251 = new C02251(TransitionInteractor.this);
                this.label = 1;
                if (transitionInteractor$filterRelevantKeyguardState$$inlined$filter$1.collect(c02251, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$maybeHandleInsecurePowerGesture$1, reason: invalid class name and case insensitive filesystem */
    final class C09161 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C09161(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TransitionInteractor.this.maybeHandleInsecurePowerGesture(this);
        }
    }

    public /* synthetic */ TransitionInteractor(KeyguardState keyguardState, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, PowerInteractor powerInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, KeyguardInteractor keyguardInteractor, DefaultConstructorMarker defaultConstructorMarker) {
        this(keyguardState, keyguardTransitionInteractor, coroutineDispatcher, coroutineDispatcher2, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor);
    }

    public static Object startTransitionTo$default(TransitionInteractor transitionInteractor, KeyguardState keyguardState, ValueAnimator valueAnimator, TransitionModeOnCanceled transitionModeOnCanceled, String str, Continuation continuation, int i) {
        if ((i & 2) != 0) {
            valueAnimator = transitionInteractor.getDefaultAnimatorForTransitionsToState(keyguardState);
        }
        ValueAnimator valueAnimator2 = valueAnimator;
        if ((i & 4) != 0) {
            transitionModeOnCanceled = TransitionModeOnCanceled.LAST_VALUE;
        }
        TransitionModeOnCanceled transitionModeOnCanceled2 = transitionModeOnCanceled;
        if ((i & 8) != 0) {
            str = "";
        }
        transitionInteractor.getClass();
        keyguardState.checkValidState();
        KeyguardState keyguardState2 = transitionInteractor.getInternalTransitionInteractor().currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to;
        String str2 = transitionInteractor.name;
        KeyguardState keyguardState3 = transitionInteractor.fromState;
        if (keyguardState3 == keyguardState2) {
            return ((KeyguardTransitionRepositoryImpl) transitionInteractor.getTransitionRepository()).startTransition(new TransitionInfo(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, StringsKt__StringsKt.isBlank(str) ? "" : ContentInViewNode$Request$$ExternalSyntheticOutline0.m("(", str, ")")), transitionInteractor.fromState, keyguardState, valueAnimator2, transitionModeOnCanceled2), continuation);
        }
        Log.e(str2, "Ignoring startTransition: This interactor asked to transition from " + keyguardState3 + " -> " + keyguardState + ", but we last transitioned to " + transitionInteractor.getInternalTransitionInteractor().currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to + ", not " + keyguardState3 + ". This should never happen - check currentTransitionInfoInternal or use filterRelevantKeyguardState before starting transitions.");
        return null;
    }

    public abstract ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState);

    public abstract InternalKeyguardTransitionInteractor getInternalTransitionInteractor();

    public abstract KeyguardTransitionRepository getTransitionRepository();

    public final Object listenForSleepTransition(final Function1 function1, SuspendLambda suspendLambda) {
        final PowerInteractor$special$$inlined$map$2 powerInteractor$special$$inlined$map$2 = this.powerInteractor.isAsleep;
        final Flow flowSample = FlowKt.sample(new TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$filter$1$2$1, reason: invalid class name */
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
                        if (((Boolean) obj).booleanValue()) {
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
                Object objCollect = powerInteractor$special$$inlined$map$2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, this), this.transitionInteractor.startedKeyguardTransitionStep);
        Object objCollect = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Function1 $callee$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, Function1 function1) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$callee$inlined = function1;
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
                        Object objMo781invoke = this.$callee$inlined.mo781invoke((TransitionStep) obj);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(objMo781invoke, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect2 = flowSample.collect(new AnonymousClass2(flowCollector, function1), continuation);
                return objCollect2 == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect2 : Unit.INSTANCE;
            }
        }.collect(new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.TransitionInteractor.listenForSleepTransition.5
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation) {
                TransitionInteractor transitionInteractor = TransitionInteractor.this;
                Object objStartTransitionTo$default = TransitionInteractor.startTransitionTo$default(transitionInteractor, (KeyguardState) transitionInteractor.keyguardInteractor.asleepKeyguardState.$$delegate_0.getValue(), null, (TransitionModeOnCanceled) obj, "Sleep transition triggered", continuation, 2);
                return objStartTransitionTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objStartTransitionTo$default : Unit.INSTANCE;
            }
        }, suspendLambda);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }

    public final void listenForTransitionToCamera(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor) {
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(keyguardInteractor, null), 7);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0084, code lost:
    
        if (startTransitionTo$default(r8, r2, null, null, "Power button gesture while keyguard is dismissible", r6, 6) == r0) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00a9, code lost:
    
        if (startTransitionTo$default(r8, r2, null, null, "Power button gesture on dismissable keyguard", r6, 6) == r0) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object maybeHandleInsecurePowerGesture(ContinuationImpl continuationImpl) {
        C09161 c09161;
        boolean z;
        if (continuationImpl instanceof C09161) {
            c09161 = (C09161) continuationImpl;
            int i = c09161.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09161.label = i - Integer.MIN_VALUE;
            } else {
                c09161 = new C09161(continuationImpl);
            }
        }
        C09161 c091612 = c09161;
        Object obj = c091612.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c091612.label;
        if (i2 != 0) {
            if (i2 == 1) {
                ResultKt.throwOnFailure(obj);
                return Boolean.TRUE;
            }
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.TRUE;
        }
        ResultKt.throwOnFailure(obj);
        KeyguardOcclusionInteractor keyguardOcclusionInteractor = this.keyguardOcclusionInteractor;
        if (((WakefulnessModel) keyguardOcclusionInteractor.powerInteractor.detailedWakefulness.$$delegate_0.getValue()).powerButtonLaunchGestureTriggered) {
            KeyguardState.Companion companion = KeyguardState.Companion;
            KeyguardState keyguardState = keyguardOcclusionInteractor.internalTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to;
            companion.getClass();
            z = KeyguardState.Companion.deviceIsAsleepInState(keyguardState);
            if (z) {
                if (((Boolean) this.keyguardInteractor.isKeyguardDismissible.getValue()).booleanValue()) {
                    KeyguardState keyguardState2 = KeyguardState.GONE;
                    c091612.label = 1;
                } else if (((Boolean) keyguardOcclusionInteractor.occludingActivityWillDismissKeyguard.$$delegate_0.getValue()).booleanValue()) {
                    KeyguardState keyguardState3 = KeyguardState.GONE;
                    c091612.label = 2;
                }
                return coroutineSingletons;
            }
            return Boolean.FALSE;
        }
        if (z) {
        }
        return Boolean.FALSE;
    }

    public abstract void start();

    private TransitionInteractor(KeyguardState keyguardState, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, PowerInteractor powerInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, KeyguardInteractor keyguardInteractor) {
        this.fromState = keyguardState;
        this.transitionInteractor = keyguardTransitionInteractor;
        this.mainDispatcher = coroutineDispatcher;
        this.powerInteractor = powerInteractor;
        this.keyguardOcclusionInteractor = keyguardOcclusionInteractor;
        this.keyguardInteractor = keyguardInteractor;
        String simpleName = Reflection.getOrCreateKotlinClass(getClass()).getSimpleName();
        this.name = simpleName == null ? "UnknownTransitionInteractor" : simpleName;
    }
}
