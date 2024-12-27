package com.android.systemui.keyguard.ui.viewmodel;

import android.R;
import com.android.settingslib.Utils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class AlternateBouncerUdfpsIconViewModel$fgIconColor$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AlternateBouncerUdfpsIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlternateBouncerUdfpsIconViewModel$fgIconColor$2(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = alternateBouncerUdfpsIconViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AlternateBouncerUdfpsIconViewModel$fgIconColor$2 alternateBouncerUdfpsIconViewModel$fgIconColor$2 = new AlternateBouncerUdfpsIconViewModel$fgIconColor$2(this.this$0, continuation);
        alternateBouncerUdfpsIconViewModel$fgIconColor$2.L$0 = obj;
        return alternateBouncerUdfpsIconViewModel$fgIconColor$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AlternateBouncerUdfpsIconViewModel$fgIconColor$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Integer num = new Integer(Utils.getColorAttrDefaultColor(this.this$0.context, R.attr.textColorPrimary, 0));
            this.label = 1;
            if (flowCollector.emit(num, this) == coroutineSingletons) {
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
