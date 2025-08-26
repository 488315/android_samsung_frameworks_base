package com.android.systemui.shade.ui.viewmodel;

import android.icu.text.DateFormat;
import java.util.Date;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class ShadeHeaderViewModel$longerDateText$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public ShadeHeaderViewModel$longerDateText$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ShadeHeaderViewModel$longerDateText$2 shadeHeaderViewModel$longerDateText$2 = new ShadeHeaderViewModel$longerDateText$2((Continuation) obj3);
        shadeHeaderViewModel$longerDateText$2.L$0 = (DateFormat) obj;
        shadeHeaderViewModel$longerDateText$2.L$1 = (Date) obj2;
        return shadeHeaderViewModel$longerDateText$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return ((DateFormat) this.L$0).format((Date) this.L$1);
    }
}
