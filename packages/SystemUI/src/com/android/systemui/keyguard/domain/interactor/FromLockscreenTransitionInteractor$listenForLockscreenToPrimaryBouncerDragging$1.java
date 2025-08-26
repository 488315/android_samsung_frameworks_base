package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import android.util.MathUtils;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import java.util.UUID;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
final class FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$ObjectRef<UUID> $transitionId;
    int label;
    final /* synthetic */ FromLockscreenTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1(FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, Ref$ObjectRef<UUID> ref$ObjectRef, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromLockscreenTransitionInteractor;
        this.$transitionId = ref$ObjectRef;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1(this.this$0, this.$transitionId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = ((ShadeRepositoryImpl) fromLockscreenTransitionInteractor.shadeRepository).legacyShadeExpansion;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(fromLockscreenTransitionInteractor, this.$transitionId);
            this.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ Ref$ObjectRef $transitionId;
        public final /* synthetic */ FromLockscreenTransitionInteractor this$0;

        public AnonymousClass1(FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, Ref$ObjectRef<UUID> ref$ObjectRef) {
            this.this$0 = fromLockscreenTransitionInteractor;
            this.$transitionId = ref$ObjectRef;
        }

        /* JADX WARN: Code restructure failed: missing block: B:49:0x0123, code lost:
        
            if (((com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl) r4).startTransition(r10, r9) == r3) goto L66;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00dd  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x00e7  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x001a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(float f, Continuation continuation) {
            FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$1 fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$1;
            boolean zBooleanValue;
            Ref$ObjectRef ref$ObjectRef;
            TransitionState transitionState;
            TransitionState transitionState2;
            TransitionState transitionState3;
            T t;
            AnonymousClass1 anonymousClass1 = this;
            if (continuation instanceof FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$1) {
                fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$1 = (FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$1) continuation;
                int i = fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$1 = new FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$1(anonymousClass1, continuation);
                }
            }
            FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$1 fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12 = fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$1;
            Object obj = fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.label;
            if (i2 != 0) {
                if (i2 != 1) {
                    if (i2 == 2) {
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    if (i2 != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ref$ObjectRef = (Ref$ObjectRef) fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.L$0;
                    ResultKt.throwOnFailure(obj);
                    t = obj;
                    ref$ObjectRef.element = t;
                    return Unit.INSTANCE;
                }
                boolean z = fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.Z$0;
                transitionState2 = (TransitionState) fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.L$1;
                AnonymousClass1 anonymousClass12 = (AnonymousClass1) fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.L$0;
                ResultKt.throwOnFailure(obj);
                zBooleanValue = z;
                anonymousClass1 = anonymousClass12;
                transitionState = transitionState2;
                transitionState3 = TransitionState.CANCELED;
                if (transitionState != transitionState3 || transitionState == TransitionState.FINISHED) {
                    anonymousClass1.$transitionId.element = null;
                }
                if (transitionState == transitionState3) {
                    FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor = anonymousClass1.this$0;
                    KeyguardTransitionRepository keyguardTransitionRepository = fromLockscreenTransitionInteractor.transitionRepository;
                    String strM = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), fromLockscreenTransitionInteractor.name, " (on behalf of FromPrimaryBouncerInteractor)");
                    KeyguardState keyguardState = KeyguardState.PRIMARY_BOUNCER;
                    KeyguardState keyguardState2 = zBooleanValue ? KeyguardState.OCCLUDED : KeyguardState.LOCKSCREEN;
                    TransitionModeOnCanceled transitionModeOnCanceled = TransitionModeOnCanceled.REVERSE;
                    ValueAnimator defaultAnimatorForTransitionsToState = anonymousClass1.this$0.getDefaultAnimatorForTransitionsToState(KeyguardState.LOCKSCREEN);
                    defaultAnimatorForTransitionsToState.setDuration(100L);
                    TransitionInfo transitionInfo = new TransitionInfo(strM, keyguardState, keyguardState2, defaultAnimatorForTransitionsToState, transitionModeOnCanceled);
                    fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.L$0 = null;
                    fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.L$1 = null;
                    fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.label = 2;
                }
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor2 = anonymousClass1.this$0;
            StatusBarState statusBarState = (StatusBarState) fromLockscreenTransitionInteractor2.keyguardInteractor.statusBarState.$$delegate_0.getValue();
            KeyguardInteractor keyguardInteractor = fromLockscreenTransitionInteractor2.keyguardInteractor;
            boolean zBooleanValue2 = ((Boolean) keyguardInteractor.isKeyguardDismissible.getValue()).booleanValue();
            zBooleanValue = ((Boolean) keyguardInteractor.isKeyguardOccluded.getValue()).booleanValue();
            TransitionStep transitionStep = (TransitionStep) fromLockscreenTransitionInteractor2.transitionInteractor.startedKeyguardTransitionStep.$$delegate_0.getValue();
            Ref$ObjectRef ref$ObjectRef2 = anonymousClass1.$transitionId;
            UUID uuid = (UUID) ref$ObjectRef2.element;
            TransitionInfo transitionInfoCurrentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core = fromLockscreenTransitionInteractor2.internalTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            if (uuid == null) {
                if (transitionInfoCurrentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core.to == KeyguardState.LOCKSCREEN && f > 0.0f && f < 1.0f && ((Boolean) ((ShadeRepositoryImpl) fromLockscreenTransitionInteractor2.shadeRepository).legacyShadeTracking.$$delegate_0.getValue()).booleanValue() && !zBooleanValue2 && statusBarState == StatusBarState.KEYGUARD) {
                    KeyguardState keyguardState3 = KeyguardState.PRIMARY_BOUNCER;
                    fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.L$0 = ref$ObjectRef2;
                    fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.label = 3;
                    Object objStartTransitionTo$default = TransitionInteractor.startTransitionTo$default(anonymousClass1.this$0, keyguardState3, null, null, "#listenForLockscreenToPrimaryBouncerDragging", fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12, 4);
                    if (objStartTransitionTo$default != coroutineSingletons) {
                        ref$ObjectRef = ref$ObjectRef2;
                        t = objStartTransitionTo$default;
                        ref$ObjectRef.element = t;
                    }
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
            if (transitionStep.to == KeyguardState.PRIMARY_BOUNCER) {
                transitionState = f == 0.0f ? TransitionState.FINISHED : f == 1.0f ? TransitionState.CANCELED : TransitionState.RUNNING;
                if (transitionState != TransitionState.CANCELED) {
                    float fConstrainedMap = 1.0f - MathUtils.constrainedMap(0.0f, 1.0f, 0.88f, 1.0f, f);
                    fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.L$0 = anonymousClass1;
                    fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.L$1 = transitionState;
                    fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.Z$0 = zBooleanValue;
                    fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12.label = 1;
                    if (((KeyguardTransitionRepositoryImpl) fromLockscreenTransitionInteractor2.transitionRepository).updateTransition(uuid, fConstrainedMap, transitionState, fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$12) != coroutineSingletons) {
                        transitionState2 = transitionState;
                        transitionState = transitionState2;
                        transitionState3 = TransitionState.CANCELED;
                        if (transitionState != transitionState3) {
                        }
                        anonymousClass1.$transitionId.element = null;
                        if (transitionState == transitionState3) {
                        }
                    }
                } else {
                    transitionState3 = TransitionState.CANCELED;
                    if (transitionState != transitionState3) {
                    }
                    anonymousClass1.$transitionId.element = null;
                    if (transitionState == transitionState3) {
                    }
                }
                return coroutineSingletons;
            }
            return Unit.INSTANCE;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit(((Number) obj).floatValue(), continuation);
        }
    }
}
