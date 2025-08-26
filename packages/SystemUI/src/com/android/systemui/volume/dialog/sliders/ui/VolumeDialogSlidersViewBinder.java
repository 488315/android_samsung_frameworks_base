package com.android.systemui.volume.dialog.sliders.ui;

import android.view.LayoutInflater;
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
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderUiModel;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel;
import com.android.systemui.volume.dialog.ui.binder.ViewBinder;
import com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogViewModel;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* loaded from: classes3.dex */
public final class VolumeDialogSlidersViewBinder implements ViewBinder {
    public final VolumeDialogViewModel dialogViewModel;
    public final VolumeDialogSlidersViewModel viewModel;

    /* renamed from: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlidersViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ViewGroup $floatingSlidersContainer;
        final /* synthetic */ View $mainSliderContainer;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(View view, ViewGroup viewGroup, Continuation continuation) {
            super(2, continuation);
            this.$mainSliderContainer = view;
            this.$floatingSlidersContainer = viewGroup;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return VolumeDialogSlidersViewBinder.this.new AnonymousClass1(this.$mainSliderContainer, this.$floatingSlidersContainer, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                VolumeDialogViewModel volumeDialogViewModel = VolumeDialogSlidersViewBinder.this.dialogViewModel;
                View[] viewArr = {this.$mainSliderContainer, this.$floatingSlidersContainer};
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

    /* renamed from: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSlidersViewBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ View $background;
        final /* synthetic */ View $bottomSection;
        final /* synthetic */ ViewGroup $floatingSlidersContainer;
        final /* synthetic */ View $mainSliderContainer;
        final /* synthetic */ CoroutineScope $this_bind;
        final /* synthetic */ View $topSection;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(CoroutineScope coroutineScope, View view, View view2, View view3, View view4, ViewGroup viewGroup, Continuation continuation) {
            super(2, continuation);
            this.$this_bind = coroutineScope;
            this.$mainSliderContainer = view;
            this.$background = view2;
            this.$bottomSection = view3;
            this.$topSection = view4;
            this.$floatingSlidersContainer = viewGroup;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = VolumeDialogSlidersViewBinder.this.new AnonymousClass2(this.$this_bind, this.$mainSliderContainer, this.$background, this.$bottomSection, this.$topSection, this.$floatingSlidersContainer, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((VolumeDialogSliderUiModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            VolumeDialogSliderUiModel volumeDialogSliderUiModel = (VolumeDialogSliderUiModel) this.L$0;
            VolumeDialogSlidersViewBinder volumeDialogSlidersViewBinder = VolumeDialogSlidersViewBinder.this;
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
                LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
                int i = -childCount;
                for (int i2 = 0; i2 < i; i2++) {
                    layoutInflaterFrom.inflate(R.layout.volume_dialog_slider_floating, viewGroup, true);
                }
            }
            ViewGroup viewGroup2 = this.$floatingSlidersContainer;
            VolumeDialogSlidersViewBinder volumeDialogSlidersViewBinder2 = VolumeDialogSlidersViewBinder.this;
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

    public VolumeDialogSlidersViewBinder(VolumeDialogSlidersViewModel volumeDialogSlidersViewModel, VolumeDialogViewModel volumeDialogViewModel) {
        this.viewModel = volumeDialogSlidersViewModel;
        this.dialogViewModel = volumeDialogViewModel;
    }

    public static final void access$bindSlider(VolumeDialogSlidersViewBinder volumeDialogSlidersViewBinder, CoroutineScope coroutineScope, VolumeDialogSliderComponent volumeDialogSliderComponent, View view, final View[] viewArr) {
        volumeDialogSlidersViewBinder.getClass();
        final VolumeDialogSliderViewBinder volumeDialogSliderViewBinderSliderViewBinder = volumeDialogSliderComponent.sliderViewBinder();
        volumeDialogSliderViewBinderSliderViewBinder.getClass();
        ((ComposeView) view.requireViewById(R.id.volume_dialog_slider)).setContent(new ComposableLambdaImpl(-509173949, true, new Function2() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinder$bind$1
            /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                    } else {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinder.bind.<anonymous> (VolumeDialogSliderViewBinder.kt:66)");
                        }
                        final VolumeDialogSliderViewBinder volumeDialogSliderViewBinder = volumeDialogSliderViewBinderSliderViewBinder;
                        PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(1060546873, new Function2() { // from class: com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinder$bind$1.1
                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinder.bind.<anonymous>.<anonymous> (VolumeDialogSliderViewBinder.kt:67)");
                                        }
                                        VolumeDialogSliderViewBinder volumeDialogSliderViewBinder2 = volumeDialogSliderViewBinder;
                                        VolumeDialogSliderViewBinderKt.VolumeDialogSlider(volumeDialogSliderViewBinder2.viewModel, volumeDialogSliderViewBinder2.overscrollViewModel, volumeDialogSliderViewBinder2.hapticsViewModelFactory, null, composer2, 0);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composer), composer, 48, 1);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        }));
        VolumeDialogOverscrollViewBinder volumeDialogOverscrollViewBinderOverscrollViewBinder = volumeDialogSliderComponent.overscrollViewBinder();
        volumeDialogOverscrollViewBinderOverscrollViewBinder.getClass();
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
        CoroutineTracingKt.launchInTraced(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(volumeDialogOverscrollViewBinderOverscrollViewBinder.viewModel.overscrollEvent, new VolumeDialogOverscrollViewBinder$bind$1(springAnimation, viewArr, floatValueHolder, null)), coroutineScope);
    }

    @Override // com.android.systemui.volume.dialog.ui.binder.ViewBinder
    public final void bind(CoroutineScope coroutineScope, View view) {
        ViewGroup viewGroup = (ViewGroup) view.requireViewById(R.id.volume_dialog_floating_sliders_container);
        View viewRequireViewById = view.requireViewById(R.id.volume_dialog_main_slider_container);
        View viewRequireViewById2 = view.requireViewById(R.id.volume_dialog_background);
        View viewRequireViewById3 = view.requireViewById(R.id.volume_dialog_bottom_section_container);
        View viewRequireViewById4 = view.requireViewById(R.id.volume_dialog_top_section_container);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(viewRequireViewById, viewGroup, null), 6);
        CoroutineTracingKt.launchInTraced(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(this.viewModel.sliders, new AnonymousClass2(coroutineScope, viewRequireViewById, viewRequireViewById2, viewRequireViewById3, viewRequireViewById4, viewGroup, null)), coroutineScope);
    }
}
