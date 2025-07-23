package com.android.systemui.shade.ui.viewmodel;

import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import java.util.Locale;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShadeHeaderViewModel$shorterDateFormat$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ShadeHeaderViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeHeaderViewModel$shorterDateFormat$1(ShadeHeaderViewModel shadeHeaderViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shadeHeaderViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShadeHeaderViewModel$shorterDateFormat$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeHeaderViewModel$shorterDateFormat$1) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(this.this$0.shorterPattern, Locale.getDefault());
        instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
        return instanceForSkeleton;
    }
}
