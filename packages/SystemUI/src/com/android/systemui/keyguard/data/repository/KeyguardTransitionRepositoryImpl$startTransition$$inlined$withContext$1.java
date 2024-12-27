package com.android.systemui.keyguard.data.repository;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Trace;
import android.util.Log;
import com.android.app.tracing.TraceProxy_platformKt;
import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceData;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class KeyguardTransitionRepositoryImpl$startTransition$$inlined$withContext$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ TransitionInfo $info$inlined;
    final /* synthetic */ String $spanName;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ KeyguardTransitionRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardTransitionRepositoryImpl$startTransition$$inlined$withContext$1(String str, Continuation continuation, KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl, TransitionInfo transitionInfo) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = keyguardTransitionRepositoryImpl;
        this.$info$inlined = transitionInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardTransitionRepositoryImpl$startTransition$$inlined$withContext$1 keyguardTransitionRepositoryImpl$startTransition$$inlined$withContext$1 = new KeyguardTransitionRepositoryImpl$startTransition$$inlined$withContext$1(this.$spanName, continuation, this.this$0, this.$info$inlined);
        keyguardTransitionRepositoryImpl$startTransition$$inlined$withContext$1.L$0 = obj;
        return keyguardTransitionRepositoryImpl$startTransition$$inlined$withContext$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardTransitionRepositoryImpl$startTransition$$inlined$withContext$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String str = this.$spanName;
        TraceData traceData = (TraceData) TraceContextElementKt.traceThreadLocal.get();
        boolean isEnabled = Trace.isEnabled();
        if (traceData == null && !isEnabled) {
            str = "<none>";
        }
        if (traceData != null) {
            traceData.beginSpan(str);
        }
        int nextInt = isEnabled ? ThreadLocalRandom.current().nextInt() : 0;
        if (isEnabled) {
            TraceProxy_platformKt.asyncTraceForTrackBegin(nextInt, "Coroutines", str);
        }
        try {
            UUID uuid = null;
            this.this$0._currentTransitionMutex.unlock(null);
            TransitionStep transitionStep = this.this$0.lastStep;
            KeyguardState keyguardState = transitionStep.from;
            TransitionInfo transitionInfo = this.$info$inlined;
            if (keyguardState == transitionInfo.from && transitionStep.to == transitionInfo.to) {
                Log.i("KeyguardTransitionRepository", "Duplicate call to start the transition, rejecting: " + transitionInfo);
            } else {
                float f = 0.0f;
                if (transitionStep.transitionState != TransitionState.FINISHED) {
                    Log.i("KeyguardTransitionRepository", "Transition still active: " + transitionStep + ", canceling");
                    int i = KeyguardTransitionRepositoryImpl.WhenMappings.$EnumSwitchMapping$0[this.$info$inlined.modeOnCanceled.ordinal()];
                    if (i == 1) {
                        f = this.this$0.lastStep.value;
                    } else if (i != 2) {
                        if (i != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        f = 1.0f - this.this$0.lastStep.value;
                    }
                }
                ValueAnimator valueAnimator = this.this$0.lastAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl = this.this$0;
                keyguardTransitionRepositoryImpl.lastAnimator = this.$info$inlined.animator;
                UUID uuid2 = keyguardTransitionRepositoryImpl.updateTransitionId;
                if (uuid2 != null) {
                    keyguardTransitionRepositoryImpl.updateTransition(uuid2, keyguardTransitionRepositoryImpl.lastStep.value, TransitionState.CANCELED);
                }
                TransitionInfo transitionInfo2 = this.$info$inlined;
                final ValueAnimator valueAnimator2 = transitionInfo2.animator;
                if (valueAnimator2 != null) {
                    valueAnimator2.setFloatValues(f, 1.0f);
                    valueAnimator2.setDuration((long) ((1.0f - f) * valueAnimator2.getDuration()));
                    final KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl2 = this.this$0;
                    final TransitionInfo transitionInfo3 = this.$info$inlined;
                    final ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$2$2$updateListener$1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                            KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl3 = KeyguardTransitionRepositoryImpl.this;
                            TransitionStep transitionStep2 = new TransitionStep(transitionInfo3, ((Float) valueAnimator3.getAnimatedValue()).floatValue(), TransitionState.RUNNING);
                            int i2 = KeyguardTransitionRepositoryImpl.$r8$clinit;
                            keyguardTransitionRepositoryImpl3.emitTransition(transitionStep2, false);
                        }
                    };
                    final KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl3 = this.this$0;
                    final TransitionInfo transitionInfo4 = this.$info$inlined;
                    final float f2 = f;
                    valueAnimator2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$2$2$adapter$1
                        public final void endAnimation(float f3, TransitionState transitionState) {
                            KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl4 = KeyguardTransitionRepositoryImpl.this;
                            TransitionStep transitionStep2 = new TransitionStep(transitionInfo4, f3, transitionState);
                            int i2 = KeyguardTransitionRepositoryImpl.$r8$clinit;
                            keyguardTransitionRepositoryImpl4.emitTransition(transitionStep2, false);
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
                            KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl4 = KeyguardTransitionRepositoryImpl.this;
                            TransitionStep transitionStep2 = new TransitionStep(transitionInfo4, f2, TransitionState.STARTED);
                            int i2 = KeyguardTransitionRepositoryImpl.$r8$clinit;
                            keyguardTransitionRepositoryImpl4.emitTransition(transitionStep2, false);
                        }
                    });
                    valueAnimator2.addUpdateListener(animatorUpdateListener);
                    valueAnimator2.start();
                } else {
                    this.this$0.emitTransition(new TransitionStep(transitionInfo2, f, TransitionState.STARTED), false);
                    this.this$0.updateTransitionId = UUID.randomUUID();
                    uuid = this.this$0.updateTransitionId;
                }
            }
            if (isEnabled) {
                TraceProxy_platformKt.asyncTraceForTrackEnd(nextInt, "Coroutines");
            }
            if (traceData != null) {
                traceData.endSpan();
            }
            return uuid;
        } catch (Throwable th) {
            if (isEnabled) {
                TraceProxy_platformKt.asyncTraceForTrackEnd(nextInt, "Coroutines");
            }
            if (traceData != null) {
                traceData.endSpan();
            }
            throw th;
        }
    }
}
