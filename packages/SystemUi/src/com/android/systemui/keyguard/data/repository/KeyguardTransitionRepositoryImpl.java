package com.android.systemui.keyguard.data.repository;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Trace;
import android.util.Log;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.UUID;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardTransitionRepositoryImpl implements KeyguardTransitionRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SharedFlowImpl _transitions;
    public ValueAnimator lastAnimator;
    public TransitionStep lastStep;
    public final Flow transitions;
    public UUID updateTransitionId;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardTransitionRepositoryImpl() {
        SharedFlowImpl MutableSharedFlow = SharedFlowKt.MutableSharedFlow(2, 10, BufferOverflow.DROP_OLDEST);
        this._transitions = MutableSharedFlow;
        this.transitions = FlowKt.distinctUntilChanged(new ReadonlySharedFlow(MutableSharedFlow, null));
        this.lastStep = new TransitionStep(null, null, 0.0f, null, null, 31, null);
        KeyguardState keyguardState = KeyguardState.OFF;
        KeyguardState keyguardState2 = KeyguardState.LOCKSCREEN;
        TransitionState transitionState = TransitionState.STARTED;
        String simpleName = Reflection.getOrCreateKotlinClass(KeyguardTransitionRepositoryImpl.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        emitTransition(new TransitionStep(keyguardState, keyguardState2, 0.0f, transitionState, simpleName), false);
        TransitionState transitionState2 = TransitionState.FINISHED;
        String simpleName2 = Reflection.getOrCreateKotlinClass(KeyguardTransitionRepositoryImpl.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName2);
        emitTransition(new TransitionStep(keyguardState, keyguardState2, 1.0f, transitionState2, simpleName2), false);
    }

    public final void emitTransition(TransitionStep transitionStep, boolean z) {
        TransitionState transitionState = TransitionState.RUNNING;
        TransitionState transitionState2 = transitionStep.transitionState;
        if (transitionState2 != transitionState) {
            String str = "Transition: " + transitionStep.from + " -> " + transitionStep.f304to + " " + (z ? "(manual)" : "");
            int hashCode = str.hashCode();
            if (transitionState2 == TransitionState.STARTED) {
                Trace.beginAsyncSection(str, hashCode);
            } else if (transitionState2 == TransitionState.FINISHED || transitionState2 == TransitionState.CANCELED) {
                Trace.endAsyncSection(str, hashCode);
            }
        }
        if (!this._transitions.tryEmit(transitionStep)) {
            Log.w("KeyguardTransitionRepository", "Failed to emit next value without suspending");
        }
        this.lastStep = transitionStep;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final UUID startTransition(final TransitionInfo transitionInfo, boolean z) {
        float f;
        ValueAnimator valueAnimator;
        final ValueAnimator valueAnimator2;
        TransitionStep transitionStep = this.lastStep;
        if (transitionStep.from == transitionInfo.from && transitionStep.f304to == transitionInfo.f303to) {
            Log.i("KeyguardTransitionRepository", "Duplicate call to start the transition, rejecting: " + transitionInfo);
            return null;
        }
        if (transitionStep.transitionState != TransitionState.FINISHED) {
            Log.i("KeyguardTransitionRepository", "Transition still active: " + transitionStep + ", canceling");
            if (!z) {
                f = this.lastStep.value;
                final float f2 = f;
                valueAnimator = this.lastAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                valueAnimator2 = transitionInfo.animator;
                this.lastAnimator = valueAnimator2;
                if (valueAnimator2 != null) {
                    emitTransition(new TransitionStep(transitionInfo, f2, TransitionState.STARTED), false);
                    UUID randomUUID = UUID.randomUUID();
                    this.updateTransitionId = randomUUID;
                    return randomUUID;
                }
                valueAnimator2.setFloatValues(f2, 1.0f);
                valueAnimator2.setDuration((long) ((1.0f - f2) * valueAnimator2.getDuration()));
                final ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$1$updateListener$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                        KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl = KeyguardTransitionRepositoryImpl.this;
                        TransitionStep transitionStep2 = new TransitionStep(transitionInfo, ((Float) valueAnimator3.getAnimatedValue()).floatValue(), TransitionState.RUNNING);
                        int i = KeyguardTransitionRepositoryImpl.$r8$clinit;
                        keyguardTransitionRepositoryImpl.emitTransition(transitionStep2, false);
                    }
                };
                valueAnimator2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$1$adapter$1
                    public final void endAnimation(float f3, TransitionState transitionState) {
                        KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl = KeyguardTransitionRepositoryImpl.this;
                        TransitionStep transitionStep2 = new TransitionStep(transitionInfo, f3, transitionState);
                        int i = KeyguardTransitionRepositoryImpl.$r8$clinit;
                        keyguardTransitionRepositoryImpl.emitTransition(transitionStep2, false);
                        valueAnimator2.removeListener(this);
                        valueAnimator2.removeUpdateListener(animatorUpdateListener);
                        KeyguardTransitionRepositoryImpl.this.lastAnimator = null;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        endAnimation(KeyguardTransitionRepositoryImpl.this.lastStep.value, TransitionState.CANCELED);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        endAnimation(1.0f, TransitionState.FINISHED);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl = KeyguardTransitionRepositoryImpl.this;
                        TransitionStep transitionStep2 = new TransitionStep(transitionInfo, f2, TransitionState.STARTED);
                        int i = KeyguardTransitionRepositoryImpl.$r8$clinit;
                        keyguardTransitionRepositoryImpl.emitTransition(transitionStep2, false);
                    }
                });
                valueAnimator2.addUpdateListener(animatorUpdateListener);
                valueAnimator2.start();
                return null;
            }
        }
        f = 0.0f;
        final float f22 = f;
        valueAnimator = this.lastAnimator;
        if (valueAnimator != null) {
        }
        valueAnimator2 = transitionInfo.animator;
        this.lastAnimator = valueAnimator2;
        if (valueAnimator2 != null) {
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository$DefaultImpls$transition$$inlined$filter$1] */
    public final C1633x38e7c866 transition(final KeyguardState keyguardState, final KeyguardState keyguardState2) {
        final Flow flow = this.transitions;
        return new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository$DefaultImpls$transition$$inlined$filter$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository$DefaultImpls$transition$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ KeyguardState $from$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardState $to$inlined;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository$DefaultImpls$transition$$inlined$filter$1$2", m277f = "KeyguardTransitionRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository$DefaultImpls$transition$$inlined$filter$1$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, KeyguardState keyguardState, KeyguardState keyguardState2) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$from$inlined = keyguardState;
                    this.$to$inlined = keyguardState2;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:21:0x0046  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    boolean z;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                TransitionStep transitionStep = (TransitionStep) obj;
                                if (transitionStep.from == this.$from$inlined) {
                                    if (transitionStep.f304to == this.$to$inlined) {
                                        z = true;
                                        if (z) {
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                                return coroutineSingletons;
                                            }
                                        }
                                    }
                                }
                                z = false;
                                if (z) {
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, keyguardState, keyguardState2), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
