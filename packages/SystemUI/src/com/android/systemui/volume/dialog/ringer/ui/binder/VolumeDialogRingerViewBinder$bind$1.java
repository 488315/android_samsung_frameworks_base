package com.android.systemui.volume.dialog.ringer.ui.binder;

import android.view.View;
import com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogRingerViewBinder$bind$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ View $ringerBackgroundView;
    int label;
    final /* synthetic */ VolumeDialogRingerViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogRingerViewBinder$bind$1(VolumeDialogRingerViewBinder volumeDialogRingerViewBinder, View view, Continuation continuation) {
        super(2, continuation);
        this.this$0 = volumeDialogRingerViewBinder;
        this.$ringerBackgroundView = view;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VolumeDialogRingerViewBinder$bind$1(this.this$0, this.$ringerBackgroundView, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogRingerViewBinder$bind$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            VolumeDialogViewModel volumeDialogViewModel = this.this$0.dialogViewModel;
            View[] viewArr = {this.$ringerBackgroundView};
            this.label = 1;
            if (volumeDialogViewModel.addTouchableBounds(viewArr, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
