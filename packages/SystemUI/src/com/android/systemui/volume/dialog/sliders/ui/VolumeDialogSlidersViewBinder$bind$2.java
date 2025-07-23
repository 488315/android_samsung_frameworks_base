package com.android.systemui.volume.dialog.sliders.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.volume.dialog.sliders.dagger.VolumeDialogSliderComponent;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderUiModel;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogSlidersViewBinder$bind$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ View $background;
    final /* synthetic */ View $bottomSection;
    final /* synthetic */ ViewGroup $floatingSlidersContainer;
    final /* synthetic */ View $mainSliderContainer;
    final /* synthetic */ CoroutineScope $this_bind;
    final /* synthetic */ View $topSection;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeDialogSlidersViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogSlidersViewBinder$bind$2(VolumeDialogSlidersViewBinder volumeDialogSlidersViewBinder, CoroutineScope coroutineScope, View view, View view2, View view3, View view4, ViewGroup viewGroup, Continuation continuation) {
        super(2, continuation);
        this.this$0 = volumeDialogSlidersViewBinder;
        this.$this_bind = coroutineScope;
        this.$mainSliderContainer = view;
        this.$background = view2;
        this.$bottomSection = view3;
        this.$topSection = view4;
        this.$floatingSlidersContainer = viewGroup;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogSlidersViewBinder$bind$2 volumeDialogSlidersViewBinder$bind$2 = new VolumeDialogSlidersViewBinder$bind$2(this.this$0, this.$this_bind, this.$mainSliderContainer, this.$background, this.$bottomSection, this.$topSection, this.$floatingSlidersContainer, continuation);
        volumeDialogSlidersViewBinder$bind$2.L$0 = obj;
        return volumeDialogSlidersViewBinder$bind$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogSlidersViewBinder$bind$2) create((VolumeDialogSliderUiModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        VolumeDialogSliderUiModel volumeDialogSliderUiModel = (VolumeDialogSliderUiModel) this.L$0;
        VolumeDialogSlidersViewBinder volumeDialogSlidersViewBinder = this.this$0;
        CoroutineScope coroutineScope = this.$this_bind;
        VolumeDialogSliderComponent volumeDialogSliderComponent = volumeDialogSliderUiModel.sliderComponent;
        View view = this.$mainSliderContainer;
        VolumeDialogSlidersViewBinder.access$bindSlider(volumeDialogSlidersViewBinder, coroutineScope, volumeDialogSliderComponent, view, new View[]{view, this.$background, this.$bottomSection, this.$topSection});
        List list = volumeDialogSliderUiModel.floatingSliderComponent;
        ViewGroup viewGroup = this.$floatingSlidersContainer;
        int childCount = viewGroup.getChildCount() - list.size();
        if (childCount > 0) {
            viewGroup.removeViews(0, childCount);
        } else if (childCount < 0) {
            LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
            int i = -childCount;
            for (int i2 = 0; i2 < i; i2++) {
                from.inflate(R.layout.volume_dialog_slider_floating, viewGroup, true);
            }
        }
        ViewGroup viewGroup2 = this.$floatingSlidersContainer;
        VolumeDialogSlidersViewBinder volumeDialogSlidersViewBinder2 = this.this$0;
        CoroutineScope coroutineScope2 = this.$this_bind;
        int size = list.size();
        for (int i3 = 0; i3 < size; i3++) {
            VolumeDialogSliderComponent volumeDialogSliderComponent2 = (VolumeDialogSliderComponent) list.get(i3);
            View childAt = viewGroup2.getChildAt(i3);
            childAt.getClass();
            VolumeDialogSlidersViewBinder.access$bindSlider(volumeDialogSlidersViewBinder2, coroutineScope2, volumeDialogSliderComponent2, childAt, new View[]{childAt});
        }
        return Unit.INSTANCE;
    }
}
