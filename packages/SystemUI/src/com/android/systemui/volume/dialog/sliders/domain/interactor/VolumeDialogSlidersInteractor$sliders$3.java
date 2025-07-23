package com.android.systemui.volume.dialog.sliders.domain.interactor;

import java.util.LinkedHashSet;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogSlidersInteractor$sliders$3 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public VolumeDialogSlidersInteractor$sliders$3(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        VolumeDialogSlidersInteractor$sliders$3 volumeDialogSlidersInteractor$sliders$3 = new VolumeDialogSlidersInteractor$sliders$3((Continuation) obj3);
        volumeDialogSlidersInteractor$sliders$3.L$0 = (LinkedHashSet) obj;
        volumeDialogSlidersInteractor$sliders$3.L$1 = (LinkedHashSet) obj2;
        return volumeDialogSlidersInteractor$sliders$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LinkedHashSet linkedHashSet = (LinkedHashSet) this.L$0;
        linkedHashSet.addAll((LinkedHashSet) this.L$1);
        return linkedHashSet;
    }
}
