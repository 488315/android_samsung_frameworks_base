package com.android.systemui.keyguard.data.repository;

import androidx.core.animation.Animator;
import androidx.core.animation.ValueAnimator;
import com.android.keyguard.logging.ScrimLogger;
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class LightRevealScrimRepositoryImpl$revealAmount$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightRevealScrimRepositoryImpl$revealAmount$1(LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lightRevealScrimRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LightRevealScrimRepositoryImpl$revealAmount$1 lightRevealScrimRepositoryImpl$revealAmount$1 = new LightRevealScrimRepositoryImpl$revealAmount$1(this.this$0, continuation);
        lightRevealScrimRepositoryImpl$revealAmount$1.L$0 = obj;
        return lightRevealScrimRepositoryImpl$revealAmount$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LightRevealScrimRepositoryImpl$revealAmount$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl = this.this$0;
            final Animator.AnimatorUpdateListener animatorUpdateListener = new Animator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$revealAmount$1$updateListener$1
                @Override // androidx.core.animation.Animator.AnimatorUpdateListener
                public final void onAnimationUpdate(Animator animator) {
                    Float f = (Float) ((ValueAnimator) animator).getAnimatedValue();
                    float floatValue = f.floatValue();
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(f);
                    if (floatValue <= 0.0f || floatValue >= 1.0f) {
                        ScrimLogger scrimLogger = lightRevealScrimRepositoryImpl.scrimLogger;
                        LightRevealScrimRepositoryImpl.Companion.getClass();
                        scrimLogger.d(LightRevealScrimRepositoryImpl.TAG, "revealAmount", f);
                    }
                }
            };
            this.this$0.revealAmountAnimator.addUpdateListener(animatorUpdateListener);
            final LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$revealAmount$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ValueAnimator valueAnimator = LightRevealScrimRepositoryImpl.this.revealAmountAnimator;
                    Animator.AnimatorUpdateListener animatorUpdateListener2 = animatorUpdateListener;
                    ArrayList arrayList = valueAnimator.mUpdateListeners;
                    if (arrayList != null) {
                        arrayList.remove(animatorUpdateListener2);
                        if (valueAnimator.mUpdateListeners.size() == 0) {
                            valueAnimator.mUpdateListeners = null;
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
