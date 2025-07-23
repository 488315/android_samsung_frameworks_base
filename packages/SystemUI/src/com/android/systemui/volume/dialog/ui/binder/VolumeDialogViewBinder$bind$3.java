package com.android.systemui.volume.dialog.ui.binder;

import android.app.Dialog;
import android.view.Window;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogViewBinder$bind$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ Dialog $dialog;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogViewBinder$bind$3(Dialog dialog, Continuation continuation) {
        super(2, continuation);
        this.$dialog = dialog;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogViewBinder$bind$3 volumeDialogViewBinder$bind$3 = new VolumeDialogViewBinder$bind$3(this.$dialog, continuation);
        volumeDialogViewBinder$bind$3.L$0 = obj;
        return volumeDialogViewBinder$bind$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogViewBinder$bind$3) create((String) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String str = (String) this.L$0;
        Window window = this.$dialog.getWindow();
        if (window != null) {
            window.setTitle(str);
        }
        return Unit.INSTANCE;
    }
}
