package com.android.systemui.keyguard.domain.interactor;

import android.util.MathUtils;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.FlowCollector;

final class KeyguardInteractor$keyguardTranslationY$1$3 extends SuspendLambda implements Function4 {
    final /* synthetic */ int $translationDistance;
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardInteractor$keyguardTranslationY$1$3(int i, Continuation continuation) {
        super(4, continuation);
        this.$translationDistance = i;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        float floatValue = ((Number) obj2).floatValue();
        float floatValue2 = ((Number) obj3).floatValue();
        KeyguardInteractor$keyguardTranslationY$1$3 keyguardInteractor$keyguardTranslationY$1$3 = new KeyguardInteractor$keyguardTranslationY$1$3(this.$translationDistance, (Continuation) obj4);
        keyguardInteractor$keyguardTranslationY$1$3.L$0 = (FlowCollector) obj;
        keyguardInteractor$keyguardTranslationY$1$3.F$0 = floatValue;
        keyguardInteractor$keyguardTranslationY$1$3.F$1 = floatValue2;
        return keyguardInteractor$keyguardTranslationY$1$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            float f = this.F$0;
            float f2 = this.F$1;
            boolean z = f == 0.0f || f == 1.0f;
            if (f2 == 1.0f || (f2 == 0.0f && z)) {
                Float f3 = new Float(0.0f);
                this.label = 1;
                if (flowCollector.emit(f3, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else if (!z) {
                Float f4 = new Float(MathUtils.lerp(this.$translationDistance, 0, ((PathInterpolator) Interpolators.FAST_OUT_LINEAR_IN).getInterpolation(f)));
                this.label = 2;
                if (flowCollector.emit(f4, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1 && i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
