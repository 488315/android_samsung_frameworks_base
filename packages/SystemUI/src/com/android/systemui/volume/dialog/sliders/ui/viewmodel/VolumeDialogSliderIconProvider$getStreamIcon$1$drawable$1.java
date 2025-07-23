package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import android.graphics.drawable.Drawable;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogSliderIconProvider$getStreamIcon$1$drawable$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $iconRes;
    int label;
    final /* synthetic */ VolumeDialogSliderIconProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSliderIconProvider$getStreamIcon$1$drawable$1(VolumeDialogSliderIconProvider volumeDialogSliderIconProvider, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = volumeDialogSliderIconProvider;
        this.$iconRes = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VolumeDialogSliderIconProvider$getStreamIcon$1$drawable$1(this.this$0, this.$iconRes, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogSliderIconProvider$getStreamIcon$1$drawable$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Drawable drawable = this.this$0.context.getDrawable(this.$iconRes);
        drawable.getClass();
        return drawable;
    }
}
