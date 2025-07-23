package com.android.systemui.volume.dialog.sliders.ui;

import android.view.View;
import android.view.ViewGroup;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.ComposeView;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.R;
import com.android.systemui.volume.dialog.sliders.dagger.VolumeDialogSliderComponent;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel;
import com.android.systemui.volume.dialog.ui.binder.ViewBinder;
import com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogSlidersViewBinder implements ViewBinder {
    public final VolumeDialogViewModel dialogViewModel;
    public final VolumeDialogSlidersViewModel viewModel;

    public VolumeDialogSlidersViewBinder(VolumeDialogSlidersViewModel volumeDialogSlidersViewModel, VolumeDialogViewModel volumeDialogViewModel) {
        this.viewModel = volumeDialogSlidersViewModel;
        this.dialogViewModel = volumeDialogViewModel;
    }

    public static final void access$bindSlider(VolumeDialogSlidersViewBinder volumeDialogSlidersViewBinder, CoroutineScope coroutineScope, VolumeDialogSliderComponent volumeDialogSliderComponent, View view, final View[] viewArr) {
        volumeDialogSlidersViewBinder.getClass();
        final VolumeDialogSliderViewBinder sliderViewBinder = volumeDialogSliderComponent.sliderViewBinder();
        sliderViewBinder.getClass();
        ((ComposeView) view.requireViewById(R.id.volume_dialog_slider)).setContent(new ComposableLambdaImpl(-509173949, true, new Function2() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinder$bind$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinder.bind.<anonymous> (VolumeDialogSliderViewBinder.kt:66)");
                }
                final VolumeDialogSliderViewBinder volumeDialogSliderViewBinder = VolumeDialogSliderViewBinder.this;
                PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(1060546873, new Function2() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinder$bind$1.1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj3, Object obj4) {
                        Composer composer2 = (Composer) obj3;
                        if ((((Number) obj4).intValue() & 3) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinder.bind.<anonymous>.<anonymous> (VolumeDialogSliderViewBinder.kt:67)");
                        }
                        VolumeDialogSliderViewBinder volumeDialogSliderViewBinder2 = VolumeDialogSliderViewBinder.this;
                        VolumeDialogSliderViewBinderKt.VolumeDialogSlider(volumeDialogSliderViewBinder2.viewModel, volumeDialogSliderViewBinder2.overscrollViewModel, volumeDialogSliderViewBinder2.hapticsViewModelFactory, null, composer2, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composer), composer, 48, 1);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }));
        VolumeDialogOverscrollViewBinder overscrollViewBinder = volumeDialogSliderComponent.overscrollViewBinder();
        overscrollViewBinder.getClass();
        FloatValueHolder floatValueHolder = new FloatValueHolder(0.0f);
        SpringAnimation springAnimation = new SpringAnimation(floatValueHolder);
        SpringForce springForce = new SpringForce(0.0f);
        springForce.setStiffness(800.0f);
        springForce.setDampingRatio(0.6f);
        springAnimation.mSpring = springForce;
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogOverscrollViewBinder$bind$animation$2
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                for (View view2 : viewArr) {
                    view2.setTranslationY(f);
                }
            }
        });
        CoroutineTracingKt.launchInTraced(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(overscrollViewBinder.viewModel.overscrollEvent, new VolumeDialogOverscrollViewBinder$bind$1(springAnimation, viewArr, floatValueHolder, null)), coroutineScope);
    }

    @Override // com.android.systemui.volume.dialog.ui.binder.ViewBinder
    public final void bind(CoroutineScope coroutineScope, View view) {
        ViewGroup viewGroup = (ViewGroup) view.requireViewById(R.id.volume_dialog_floating_sliders_container);
        View requireViewById = view.requireViewById(R.id.volume_dialog_main_slider_container);
        View requireViewById2 = view.requireViewById(R.id.volume_dialog_background);
        View requireViewById3 = view.requireViewById(R.id.volume_dialog_bottom_section_container);
        View requireViewById4 = view.requireViewById(R.id.volume_dialog_top_section_container);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new VolumeDialogSlidersViewBinder$bind$1(this, requireViewById, viewGroup, null), 6);
        CoroutineTracingKt.launchInTraced(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(this.viewModel.sliders, new VolumeDialogSlidersViewBinder$bind$2(this, coroutineScope, requireViewById, requireViewById2, requireViewById3, requireViewById4, viewGroup, null)), coroutineScope);
    }
}
