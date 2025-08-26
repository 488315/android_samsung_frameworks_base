package com.android.systemui.volume.dialog.ui.viewmodel;

import android.content.res.Configuration;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class VolumeDialogViewModel$isHalfOpened$2 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeDialogViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogViewModel$isHalfOpened$2(VolumeDialogViewModel volumeDialogViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = volumeDialogViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int iIntValue = ((Number) obj).intValue();
        VolumeDialogViewModel$isHalfOpened$2 volumeDialogViewModel$isHalfOpened$2 = new VolumeDialogViewModel$isHalfOpened$2(this.this$0, (Continuation) obj3);
        volumeDialogViewModel$isHalfOpened$2.I$0 = iIntValue;
        volumeDialogViewModel$isHalfOpened$2.L$0 = (Configuration) obj2;
        return volumeDialogViewModel$isHalfOpened$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        Configuration configuration = (Configuration) this.L$0;
        this.this$0.getClass();
        int i2 = configuration.orientation;
        boolean z = false;
        boolean z2 = i2 == 2;
        boolean z3 = i == 2;
        if (z2 && z3) {
            z = true;
        }
        return Boolean.valueOf(z);
    }
}
