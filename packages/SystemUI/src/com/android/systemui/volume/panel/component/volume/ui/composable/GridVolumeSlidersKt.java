package com.android.systemui.volume.panel.component.volume.ui.composable;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.compose.PlatformSliderColors;
import com.android.compose.grid.GridsKt;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public abstract class GridVolumeSlidersKt {
    public static final void GridVolumeSliders(final List list, final PlatformSliderColors platformSliderColors, Modifier modifier, Composer composer, final int i) {
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1558765346);
        int i2 = (composerImpl.changedInstance(list) ? 4 : 2) | i | (composerImpl.changed(platformSliderColors) ? 32 : 16) | (composerImpl.changed(modifier) ? 256 : 128);
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSliders (GridVolumeSliders.kt:32)");
            }
            if (list.isEmpty()) {
                throw new IllegalArgumentException("Failed requirement.");
            }
            Dp.Companion companion = Dp.Companion;
            modifier2 = modifier;
            GridsKt.m939VerticalGridvz2T9sI(2, modifier2, 16, 24, ComposableLambdaKt.rememberComposableLambda(-826494507, new Function2() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt.GridVolumeSliders.1
                /* JADX WARN: Removed duplicated region for block: B:18:0x0075  */
                /* JADX WARN: Removed duplicated region for block: B:23:0x009f  */
                /* JADX WARN: Removed duplicated region for block: B:28:0x00c3  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSliders.<anonymous> (GridVolumeSliders.kt:40)");
                            }
                            for (final SliderViewModel sliderViewModel : list) {
                                final SliderState sliderState = (SliderState) FlowExtKt.collectAsStateWithLifecycle(sliderViewModel.getSlider(), composer2).getValue();
                                Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
                                SliderHapticsViewModel.Factory sliderHapticsViewModelFactory = sliderViewModel.getSliderHapticsViewModelFactory();
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                composerImpl3.startReplaceGroup(-2047456682);
                                boolean zChangedInstance = composerImpl3.changedInstance(sliderViewModel) | composerImpl3.changedInstance(sliderState);
                                Object objRememberedValue = composerImpl3.rememberedValue();
                                Composer.Companion companion2 = Composer.Companion;
                                if (!zChangedInstance) {
                                    companion2.getClass();
                                    if (objRememberedValue == Composer.Companion.Empty) {
                                        objRememberedValue = new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1$$ExternalSyntheticLambda0
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj3) {
                                                sliderViewModel.onValueChanged(sliderState, ((Float) obj3).floatValue());
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl3.updateRememberedValue(objRememberedValue);
                                    }
                                }
                                Function1 function1 = (Function1) objRememberedValue;
                                composerImpl3.end(false);
                                composerImpl3.startReplaceGroup(-2047449390);
                                boolean zChangedInstance2 = composerImpl3.changedInstance(sliderViewModel) | composerImpl3.changedInstance(sliderState);
                                Object objRememberedValue2 = composerImpl3.rememberedValue();
                                if (!zChangedInstance2) {
                                    companion2.getClass();
                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                        objRememberedValue2 = new Function0() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1$$ExternalSyntheticLambda1
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                sliderViewModel.toggleMuted(sliderState);
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl3.updateRememberedValue(objRememberedValue2);
                                    }
                                }
                                Function0 function0 = (Function0) objRememberedValue2;
                                composerImpl3.end(false);
                                composerImpl3.startReplaceGroup(-2047451823);
                                boolean zChangedInstance3 = composerImpl3.changedInstance(sliderViewModel);
                                Object objRememberedValue3 = composerImpl3.rememberedValue();
                                if (!zChangedInstance3) {
                                    companion2.getClass();
                                    if (objRememberedValue3 == Composer.Companion.Empty) {
                                        objRememberedValue3 = new Function0() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1$$ExternalSyntheticLambda2
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                sliderViewModel.onValueChangeFinished();
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl3.updateRememberedValue(objRememberedValue3);
                                    }
                                }
                                composerImpl3.end(false);
                                VolumeSliderKt.VolumeSlider(sliderState, function1, function0, platformSliderColors, modifierFillMaxWidth, sliderHapticsViewModelFactory, (Function0) objRememberedValue3, null, composerImpl3, 24576, 128);
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, ((i2 >> 3) & 112) | 28038);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(list, platformSliderColors, modifier2, i) { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$$ExternalSyntheticLambda0
                public final /* synthetic */ List f$0;
                public final /* synthetic */ PlatformSliderColors f$1;
                public final /* synthetic */ Modifier f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    GridVolumeSlidersKt.GridVolumeSliders(this.f$0, this.f$1, this.f$2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
