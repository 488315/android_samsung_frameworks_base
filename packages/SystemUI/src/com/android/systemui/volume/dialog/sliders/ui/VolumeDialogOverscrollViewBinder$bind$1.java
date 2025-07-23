package com.android.systemui.volume.dialog.sliders.ui;

import android.view.View;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogOverscrollViewBinder$bind$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SpringAnimation $animation;
    final /* synthetic */ FloatValueHolder $animationValueHolder;
    final /* synthetic */ View[] $viewsToAnimate;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogOverscrollViewBinder$bind$1(SpringAnimation springAnimation, View[] viewArr, FloatValueHolder floatValueHolder, Continuation continuation) {
        super(2, continuation);
        this.$animation = springAnimation;
        this.$viewsToAnimate = viewArr;
        this.$animationValueHolder = floatValueHolder;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogOverscrollViewBinder$bind$1 volumeDialogOverscrollViewBinder$bind$1 = new VolumeDialogOverscrollViewBinder$bind$1(this.$animation, this.$viewsToAnimate, this.$animationValueHolder, continuation);
        volumeDialogOverscrollViewBinder$bind$1.L$0 = obj;
        return volumeDialogOverscrollViewBinder$bind$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogOverscrollViewBinder$bind$1) create((VolumeDialogOverscrollViewModel.OverscrollEventModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        VolumeDialogOverscrollViewModel.OverscrollEventModel overscrollEventModel = (VolumeDialogOverscrollViewModel.OverscrollEventModel) this.L$0;
        if (overscrollEventModel instanceof VolumeDialogOverscrollViewModel.OverscrollEventModel.Animate) {
            this.$animation.animateToFinalPosition(((VolumeDialogOverscrollViewModel.OverscrollEventModel.Animate) overscrollEventModel).targetOffsetPx);
        } else {
            if (!(overscrollEventModel instanceof VolumeDialogOverscrollViewModel.OverscrollEventModel.Move)) {
                throw new NoWhenBranchMatchedException();
            }
            this.$animation.cancel();
            View[] viewArr = this.$viewsToAnimate;
            VolumeDialogOverscrollViewModel.OverscrollEventModel.Move move = (VolumeDialogOverscrollViewModel.OverscrollEventModel.Move) overscrollEventModel;
            float f = move.touchOffsetPx;
            for (View view : viewArr) {
                view.setTranslationY(f);
            }
            this.$animationValueHolder.mValue = move.touchOffsetPx;
        }
        return Unit.INSTANCE;
    }
}
