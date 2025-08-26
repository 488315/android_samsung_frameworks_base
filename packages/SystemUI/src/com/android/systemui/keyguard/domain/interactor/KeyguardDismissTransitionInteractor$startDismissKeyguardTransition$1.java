package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class KeyguardDismissTransitionInteractor$startDismissKeyguardTransition$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function0 $onAlreadyGone;
    final /* synthetic */ String $reason;
    int label;
    final /* synthetic */ KeyguardDismissTransitionInteractor this$0;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardState.values().length];
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardState.AOD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardState.DOZING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardDismissTransitionInteractor$startDismissKeyguardTransition$1(KeyguardDismissTransitionInteractor keyguardDismissTransitionInteractor, String str, Function0 function0, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardDismissTransitionInteractor;
        this.$reason = str;
        this.$onAlreadyGone = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardDismissTransitionInteractor$startDismissKeyguardTransition$1(this.this$0, this.$reason, this.$onAlreadyGone, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardDismissTransitionInteractor$startDismissKeyguardTransition$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        TransitionInteractor transitionInteractor;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KeyguardState keyguardState = ((KeyguardTransitionRepositoryImpl) this.this$0.repository).currentTransitionInfo.to;
            switch (WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()]) {
                case 1:
                    transitionInteractor = this.this$0.fromLockscreenTransitionInteractor;
                    break;
                case 2:
                    transitionInteractor = this.this$0.fromPrimaryBouncerTransitionInteractor;
                    break;
                case 3:
                    transitionInteractor = this.this$0.fromAlternateBouncerTransitionInteractor;
                    break;
                case 4:
                    transitionInteractor = this.this$0.fromAodTransitionInteractor;
                    break;
                case 5:
                    transitionInteractor = this.this$0.fromDozingTransitionInteractor;
                    break;
                case 6:
                    transitionInteractor = this.this$0.fromOccludedTransitionInteractor;
                    break;
                default:
                    transitionInteractor = null;
                    break;
            }
            ValueAnimator defaultAnimatorForTransitionsToState = transitionInteractor != null ? transitionInteractor.getDefaultAnimatorForTransitionsToState(KeyguardState.GONE) : null;
            KeyguardState keyguardState2 = KeyguardState.GONE;
            if (keyguardState == keyguardState2 || defaultAnimatorForTransitionsToState == null) {
                Log.i(KeyguardDismissTransitionInteractor.TAG, "Can't transition to GONE from " + keyguardState + "; ignoring startDismissKeyguardTransition.");
                Function0 function0 = this.$onAlreadyGone;
                if (function0 != null) {
                    function0.invoke();
                    Unit unit = Unit.INSTANCE;
                }
            } else {
                KeyguardTransitionRepository keyguardTransitionRepository = this.this$0.repository;
                TransitionInfo transitionInfo = new TransitionInfo(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("KeyguardDismissTransitionInteractor", !StringsKt__StringsKt.isBlank(this.$reason) ? ContentInViewNode$Request$$ExternalSyntheticOutline0.m("(", this.$reason, ")") : ""), keyguardState, keyguardState2, defaultAnimatorForTransitionsToState, TransitionModeOnCanceled.LAST_VALUE);
                this.label = 1;
                if (((KeyguardTransitionRepositoryImpl) keyguardTransitionRepository).startTransition(transitionInfo, this) == coroutineSingletons) {
                    return coroutineSingletons;
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
