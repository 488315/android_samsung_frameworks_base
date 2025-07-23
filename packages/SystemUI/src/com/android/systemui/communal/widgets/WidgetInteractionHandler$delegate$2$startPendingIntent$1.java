package com.android.systemui.communal.widgets;

import com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class WidgetInteractionHandler$delegate$2$startPendingIntent$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ WidgetInteractionHandler this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WidgetInteractionHandler$delegate$2$startPendingIntent$1(WidgetInteractionHandler widgetInteractionHandler, Continuation continuation) {
        super(2, continuation);
        this.this$0 = widgetInteractionHandler;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WidgetInteractionHandler$delegate$2$startPendingIntent$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WidgetInteractionHandler$delegate$2$startPendingIntent$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            WidgetTrampolineInteractor widgetTrampolineInteractor = this.this$0.widgetTrampolineInteractor;
            this.label = 1;
            if (widgetTrampolineInteractor.waitForActivityStartAndDismissKeyguard(this) == coroutineSingletons) {
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
