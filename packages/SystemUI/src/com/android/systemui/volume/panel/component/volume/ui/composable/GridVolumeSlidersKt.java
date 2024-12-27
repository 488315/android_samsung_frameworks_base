package com.android.systemui.volume.panel.component.volume.ui.composable;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.compose.PlatformSliderColors;
import com.android.compose.grid.GridsKt;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class GridVolumeSlidersKt {
    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1, kotlin.jvm.internal.Lambda] */
    public static final void GridVolumeSliders(final List list, final PlatformSliderColors platformSliderColors, Modifier modifier, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1558765346);
        if ((i2 & 4) != 0) {
            modifier = Modifier.Companion;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (!(!list.isEmpty())) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        Dp.Companion companion = Dp.Companion;
        GridsKt.m819VerticalGridvz2T9sI(2, modifier, 16, 24, ComposableLambdaKt.rememberComposableLambda(-826494507, composerImpl, new Function2() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer2 = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                for (final SliderViewModel sliderViewModel : list) {
                    final SliderState sliderState = (SliderState) FlowExtKt.collectAsStateWithLifecycle(sliderViewModel.getSlider(), composer2).getValue();
                    VolumeSliderKt.VolumeSlider(sliderState, new Function1() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj3) {
                            SliderViewModel.this.onValueChanged(sliderState, ((Number) obj3).floatValue());
                            return Unit.INSTANCE;
                        }
                    }, new Function0() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1.2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            SliderViewModel.this.onValueChangeFinished();
                            return Unit.INSTANCE;
                        }
                    }, new Function0() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1.3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            SliderViewModel.this.toggleMuted(sliderState);
                            return Unit.INSTANCE;
                        }
                    }, SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), platformSliderColors, composer2, 24576, 0);
                }
                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                return Unit.INSTANCE;
            }
        }), composerImpl, ((i >> 3) & 112) | 28038, 0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Modifier modifier2 = modifier;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    GridVolumeSlidersKt.GridVolumeSliders(list, platformSliderColors, modifier2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
