package com.android.systemui.volume.panel.component.volume.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import com.android.compose.PlatformSliderColors;
import com.android.compose.grid.GridsKt;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            GridsKt.m937VerticalGridvz2T9sI(2, modifier2, 16, 24, ComposableLambdaKt.rememberComposableLambda(-826494507, new Function2() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1
                /* JADX WARN: Code restructure failed: missing block: B:18:0x0073, code lost:
                
                    if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L18;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:22:0x009d, code lost:
                
                    if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L23;
                 */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r14, java.lang.Object r15) {
                    /*
                        r13 = this;
                        androidx.compose.runtime.Composer r14 = (androidx.compose.runtime.Composer) r14
                        java.lang.Number r15 = (java.lang.Number) r15
                        int r15 = r15.intValue()
                        r15 = r15 & 3
                        r0 = 2
                        if (r15 != r0) goto L1c
                        r15 = r14
                        androidx.compose.runtime.ComposerImpl r15 = (androidx.compose.runtime.ComposerImpl) r15
                        boolean r0 = r15.getSkipping()
                        if (r0 != 0) goto L17
                        goto L1c
                    L17:
                        r15.skipToGroupEnd()
                        goto Le7
                    L1c:
                        boolean r15 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r15 == 0) goto L27
                        java.lang.String r15 = "com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSliders.<anonymous> (GridVolumeSliders.kt:40)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r15)
                    L27:
                        java.util.List r15 = r1
                        java.util.Iterator r15 = r15.iterator()
                    L2d:
                        boolean r0 = r15.hasNext()
                        if (r0 == 0) goto Lde
                        java.lang.Object r0 = r15.next()
                        com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel r0 = (com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderViewModel) r0
                        kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r0.getSlider()
                        androidx.compose.runtime.MutableState r1 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r1, r14)
                        java.lang.Object r1 = r1.getValue()
                        r2 = r1
                        com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState r2 = (com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState) r2
                        androidx.compose.ui.Modifier$Companion r1 = androidx.compose.ui.Modifier.Companion
                        r3 = 1065353216(0x3f800000, float:1.0)
                        androidx.compose.ui.Modifier r6 = androidx.compose.foundation.layout.SizeKt.fillMaxWidth(r1, r3)
                        com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$Factory r7 = r0.getSliderHapticsViewModelFactory()
                        r10 = r14
                        androidx.compose.runtime.ComposerImpl r10 = (androidx.compose.runtime.ComposerImpl) r10
                        r1 = -2047456682(0xffffffff85f64a56, float:-2.3161036E-35)
                        r10.startReplaceGroup(r1)
                        boolean r1 = r10.changedInstance(r0)
                        boolean r3 = r10.changedInstance(r2)
                        r1 = r1 | r3
                        java.lang.Object r3 = r10.rememberedValue()
                        androidx.compose.runtime.Composer$Companion r4 = androidx.compose.runtime.Composer.Companion
                        if (r1 != 0) goto L75
                        r4.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r3 != r1) goto L7d
                    L75:
                        com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1$$ExternalSyntheticLambda0 r3 = new com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1$$ExternalSyntheticLambda0
                        r3.<init>()
                        r10.updateRememberedValue(r3)
                    L7d:
                        kotlin.jvm.functions.Function1 r3 = (kotlin.jvm.functions.Function1) r3
                        r1 = 0
                        r10.end(r1)
                        r5 = -2047449390(0xffffffff85f666d2, float:-2.3171499E-35)
                        r10.startReplaceGroup(r5)
                        boolean r5 = r10.changedInstance(r0)
                        boolean r8 = r10.changedInstance(r2)
                        r5 = r5 | r8
                        java.lang.Object r8 = r10.rememberedValue()
                        if (r5 != 0) goto L9f
                        r4.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r5 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r8 != r5) goto La7
                    L9f:
                        com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1$$ExternalSyntheticLambda1 r8 = new com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1$$ExternalSyntheticLambda1
                        r8.<init>()
                        r10.updateRememberedValue(r8)
                    La7:
                        kotlin.jvm.functions.Function0 r8 = (kotlin.jvm.functions.Function0) r8
                        r10.end(r1)
                        r5 = -2047451823(0xffffffff85f65d51, float:-2.3168008E-35)
                        r10.startReplaceGroup(r5)
                        boolean r5 = r10.changedInstance(r0)
                        java.lang.Object r9 = r10.rememberedValue()
                        if (r5 != 0) goto Lc3
                        r4.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r4 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r9 != r4) goto Lcb
                    Lc3:
                        com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1$$ExternalSyntheticLambda2 r9 = new com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1$$ExternalSyntheticLambda2
                        r9.<init>()
                        r10.updateRememberedValue(r9)
                    Lcb:
                        kotlin.jvm.functions.Function0 r9 = (kotlin.jvm.functions.Function0) r9
                        r10.end(r1)
                        r11 = 24576(0x6000, float:3.4438E-41)
                        r12 = 128(0x80, float:1.8E-43)
                        com.android.compose.PlatformSliderColors r5 = r2
                        r4 = r8
                        r8 = r9
                        r9 = 0
                        com.android.systemui.volume.panel.component.volume.ui.composable.VolumeSliderKt.VolumeSlider(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
                        goto L2d
                    Lde:
                        boolean r13 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r13 == 0) goto Le7
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    Le7:
                        kotlin.Unit r13 = kotlin.Unit.INSTANCE
                        return r13
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$GridVolumeSliders$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, ((i2 >> 3) & 112) | 28038);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(list, platformSliderColors, modifier2, i) { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.GridVolumeSlidersKt$$ExternalSyntheticLambda0
                public final /* synthetic */ List f$0;
                public final /* synthetic */ PlatformSliderColors f$1;
                public final /* synthetic */ Modifier f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    GridVolumeSlidersKt.GridVolumeSliders(this.f$0, this.f$1, this.f$2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
