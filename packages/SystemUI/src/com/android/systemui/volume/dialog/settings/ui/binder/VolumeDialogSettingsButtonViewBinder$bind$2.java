package com.android.systemui.volume.dialog.settings.ui.binder;

import android.widget.ImageButton;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogSettingsButtonViewBinder$bind$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ ImageButton $button;
    /* synthetic */ boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSettingsButtonViewBinder$bind$2(ImageButton imageButton, Continuation continuation) {
        super(2, continuation);
        this.$button = imageButton;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogSettingsButtonViewBinder$bind$2 volumeDialogSettingsButtonViewBinder$bind$2 = new VolumeDialogSettingsButtonViewBinder$bind$2(this.$button, continuation);
        volumeDialogSettingsButtonViewBinder$bind$2.Z$0 = ((Boolean) obj).booleanValue();
        return volumeDialogSettingsButtonViewBinder$bind$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((VolumeDialogSettingsButtonViewBinder$bind$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$button.setVisibility(this.Z$0 ? 0 : 8);
        return Unit.INSTANCE;
    }
}
