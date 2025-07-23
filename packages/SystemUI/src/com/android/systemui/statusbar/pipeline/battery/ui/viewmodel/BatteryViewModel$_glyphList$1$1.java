package com.android.systemui.statusbar.pipeline.battery.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.battery.ui.model.AttributionGlyph;
import java.util.Collections;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class BatteryViewModel$_glyphList$1$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    public BatteryViewModel$_glyphList$1$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        BatteryViewModel$_glyphList$1$1 batteryViewModel$_glyphList$1$1 = new BatteryViewModel$_glyphList$1$1((Continuation) obj4);
        batteryViewModel$_glyphList$1$1.Z$0 = booleanValue;
        batteryViewModel$_glyphList$1$1.L$0 = (List) obj2;
        batteryViewModel$_glyphList$1$1.L$1 = (AttributionGlyph) obj3;
        return batteryViewModel$_glyphList$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        List list = (List) this.L$0;
        AttributionGlyph attributionGlyph = (AttributionGlyph) this.L$1;
        return (!z || attributionGlyph == null) ? attributionGlyph != null ? CollectionsKt___CollectionsKt.plus(list, attributionGlyph.inline) : list : Collections.singletonList(attributionGlyph.standalone);
    }
}
