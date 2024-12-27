package com.android.systemui.biometrics;

import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

final class UdfpsAnimationViewController$listenForShadeExpansion$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UdfpsAnimationViewController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UdfpsAnimationViewController$listenForShadeExpansion$2(UdfpsAnimationViewController udfpsAnimationViewController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = udfpsAnimationViewController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UdfpsAnimationViewController$listenForShadeExpansion$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UdfpsAnimationViewController$listenForShadeExpansion$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            StateFlow anyExpansion = ((ShadeInteractorImpl) this.this$0.shadeInteractor).baseShadeInteractor.getAnyExpansion();
            final UdfpsAnimationViewController udfpsAnimationViewController = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.UdfpsAnimationViewController$listenForShadeExpansion$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    float floatValue = ((Number) obj2).floatValue();
                    boolean z = floatValue > 0.0f;
                    UdfpsAnimationViewController udfpsAnimationViewController2 = UdfpsAnimationViewController.this;
                    udfpsAnimationViewController2.notificationShadeVisible = z;
                    UdfpsAnimationView view = udfpsAnimationViewController2.getView();
                    view.mNotificationShadeExpansion = floatValue;
                    view.updateAlpha();
                    udfpsAnimationViewController2.updatePauseAuth();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (anyExpansion.collect(flowCollector, this) == coroutineSingletons) {
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
}
