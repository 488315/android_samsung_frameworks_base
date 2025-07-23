package com.android.systemui.qs.bar;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ColoredBGHelper$requestUpdateColoredBackground$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    private /* synthetic */ Object L$0;
    int label;

    public ColoredBGHelper$requestUpdateColoredBackground$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj2).intValue();
        ColoredBGHelper$requestUpdateColoredBackground$1 coloredBGHelper$requestUpdateColoredBackground$1 = new ColoredBGHelper$requestUpdateColoredBackground$1((Continuation) obj3);
        coloredBGHelper$requestUpdateColoredBackground$1.L$0 = (FlowCollector) obj;
        coloredBGHelper$requestUpdateColoredBackground$1.I$0 = intValue;
        return coloredBGHelper$requestUpdateColoredBackground$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("bgColor from interactor = 0x", Integer.toHexString(this.I$0), "ColoredBGHelper");
            Boolean bool = Boolean.TRUE;
            this.label = 1;
            if (flowCollector.emit(bool, this) == coroutineSingletons) {
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
