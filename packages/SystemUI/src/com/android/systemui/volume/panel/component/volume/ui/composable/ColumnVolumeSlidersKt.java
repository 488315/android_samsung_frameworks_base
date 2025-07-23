package com.android.systemui.volume.panel.component.volume.ui.composable;

import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.StringResources_androidKt;
import com.android.compose.PlatformSliderColors;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class ColumnVolumeSlidersKt {
    /* JADX WARN: Removed duplicated region for block: B:70:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x028f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x02ce  */
    /* JADX WARN: Type inference failed for: r6v29, types: [com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ColumnVolumeSliders$1$1$1$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ColumnVolumeSliders(final java.util.List r18, final boolean r19, final kotlin.jvm.functions.Function1 r20, final com.android.compose.PlatformSliderColors r21, final boolean r22, final androidx.compose.ui.Modifier r23, androidx.compose.runtime.Composer r24, final int r25) {
        /*
            Method dump skipped, instructions count: 761
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt.ColumnVolumeSliders(java.util.List, boolean, kotlin.jvm.functions.Function1, com.android.compose.PlatformSliderColors, boolean, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }

    public static final void ExpandButtonLegacy(final boolean z, boolean z2, final Function1 function1, final PlatformSliderColors platformSliderColors, Modifier modifier, Composer composer, final int i) {
        int i2;
        final String stringResource;
        boolean z3;
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1510574524);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(z2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= (i & 4096) == 0 ? composerImpl.changed(platformSliderColors) : composerImpl.changedInstance(platformSliderColors) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(modifier) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i2 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            z3 = z2;
            modifier2 = modifier;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.volume.ui.composable.ExpandButtonLegacy (ColumnVolumeSliders.kt:182)");
            }
            if (z) {
                composerImpl.startReplaceGroup(793949332);
                stringResource = StringResources_androidKt.stringResource(R.string.volume_panel_expanded_sliders, composerImpl);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(794032691);
                stringResource = StringResources_androidKt.stringResource(R.string.volume_panel_collapsed_sliders, composerImpl);
                composerImpl.end(false);
            }
            z3 = z2;
            AnimatedVisibilityKt.AnimatedVisibility(z3, modifier, EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(350, 350, null, 4), 2).plus(EnterExitTransitionKt.m5scaleInL8ZKhE$default(AnimationSpecKt.tween$default(350, 350, null, 4), 0.8f, 4)), EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(350, 500, null, 4), 2).plus(EnterExitTransitionKt.m6scaleOutL8ZKhE$default(AnimationSpecKt.tween$default(350, 500, null, 4), 0.8f, 4)), null, ComposableLambdaKt.rememberComposableLambda(-1830541852, new Function3() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ExpandButtonLegacy$1
                /* JADX WARN: Code restructure failed: missing block: B:20:0x00bf, code lost:
                
                    if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L23;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:7:0x0044, code lost:
                
                    if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r20, java.lang.Object r21, java.lang.Object r22) {
                    /*
                        r19 = this;
                        r0 = r19
                        r1 = 0
                        r2 = r20
                        androidx.compose.animation.AnimatedVisibilityScope r2 = (androidx.compose.animation.AnimatedVisibilityScope) r2
                        r2 = r21
                        androidx.compose.runtime.Composer r2 = (androidx.compose.runtime.Composer) r2
                        r3 = r22
                        java.lang.Number r3 = (java.lang.Number) r3
                        r3.intValue()
                        boolean r3 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r3 == 0) goto L1d
                        java.lang.String r3 = "com.android.systemui.volume.panel.component.volume.ui.composable.ExpandButtonLegacy.<anonymous> (ColumnVolumeSliders.kt:195)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r3)
                    L1d:
                        androidx.compose.ui.Modifier$Companion r3 = androidx.compose.ui.Modifier.Companion
                        r4 = 64
                        float r4 = (float) r4
                        androidx.compose.ui.unit.Dp$Companion r5 = androidx.compose.ui.unit.Dp.Companion
                        androidx.compose.ui.Modifier r3 = androidx.compose.foundation.layout.SizeKt.m139size3ABfNKs(r3, r4)
                        r8 = r2
                        androidx.compose.runtime.ComposerImpl r8 = (androidx.compose.runtime.ComposerImpl) r8
                        r2 = 927431229(0x37477a3d, float:1.1889785E-5)
                        r8.startReplaceGroup(r2)
                        java.lang.String r2 = r1
                        boolean r4 = r8.changed(r2)
                        java.lang.Object r5 = r8.rememberedValue()
                        androidx.compose.runtime.Composer$Companion r6 = androidx.compose.runtime.Composer.Companion
                        if (r4 != 0) goto L46
                        r6.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r4 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r5 != r4) goto L4e
                    L46:
                        com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ExpandButtonLegacy$1$$ExternalSyntheticLambda0 r5 = new com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ExpandButtonLegacy$1$$ExternalSyntheticLambda0
                        r5.<init>()
                        r8.updateRememberedValue(r5)
                    L4e:
                        kotlin.jvm.functions.Function1 r5 = (kotlin.jvm.functions.Function1) r5
                        r8.end(r1)
                        androidx.compose.ui.Modifier r9 = androidx.compose.ui.semantics.SemanticsModifierKt.semantics(r3, r1, r5)
                        androidx.compose.material3.IconButtonDefaults r2 = androidx.compose.material3.IconButtonDefaults.INSTANCE
                        com.android.compose.PlatformSliderColors r3 = r2
                        long r4 = r3.indicatorColor
                        r2.getClass()
                        long r13 = r3.iconColor
                        r2 = 12
                        r2 = r2 & 1
                        if (r2 == 0) goto L6f
                        androidx.compose.ui.graphics.Color$Companion r2 = androidx.compose.ui.graphics.Color.Companion
                        r2.getClass()
                        long r4 = androidx.compose.ui.graphics.Color.Unspecified
                    L6f:
                        r11 = r4
                        androidx.compose.ui.graphics.Color$Companion r2 = androidx.compose.ui.graphics.Color.Companion
                        r2.getClass()
                        long r15 = androidx.compose.ui.graphics.Color.Unspecified
                        r2.getClass()
                        boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r2 == 0) goto L85
                        java.lang.String r2 = "androidx.compose.material3.IconButtonDefaults.filledIconButtonColors (IconButtonDefaults.kt:323)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r2)
                    L85:
                        androidx.compose.material3.MaterialTheme r2 = androidx.compose.material3.MaterialTheme.INSTANCE
                        r2.getClass()
                        androidx.compose.material3.ColorScheme r2 = androidx.compose.material3.MaterialTheme.getColorScheme(r8)
                        androidx.compose.material3.IconButtonColors r10 = androidx.compose.material3.IconButtonDefaults.getDefaultFilledIconButtonColors$material3_release(r2)
                        r17 = r15
                        androidx.compose.material3.IconButtonColors r7 = r10.m264copyjRlVdoo(r11, r13, r15, r17)
                        boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r2 == 0) goto La1
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    La1:
                        r2 = 927435937(0x37478ca1, float:1.1894067E-5)
                        r8.startReplaceGroup(r2)
                        kotlin.jvm.functions.Function1 r2 = r3
                        boolean r3 = r8.changed(r2)
                        boolean r0 = r4
                        boolean r4 = r8.changed(r0)
                        r3 = r3 | r4
                        java.lang.Object r4 = r8.rememberedValue()
                        if (r3 != 0) goto Lc1
                        r6.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r4 != r3) goto Lc9
                    Lc1:
                        com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ExpandButtonLegacy$1$$ExternalSyntheticLambda1 r4 = new com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ExpandButtonLegacy$1$$ExternalSyntheticLambda1
                        r4.<init>()
                        r8.updateRememberedValue(r4)
                    Lc9:
                        r11 = r4
                        kotlin.jvm.functions.Function0 r11 = (kotlin.jvm.functions.Function0) r11
                        r8.end(r1)
                        com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ExpandButtonLegacy$1$3 r1 = new com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ExpandButtonLegacy$1$3
                        r1.<init>()
                        r0 = 242978822(0xe7b9006, float:3.1007483E-30)
                        androidx.compose.runtime.internal.ComposableLambdaImpl r12 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r0, r1, r8)
                        r4 = 1572864(0x180000, float:2.204052E-39)
                        r5 = 52
                        r13 = 0
                        r6 = 0
                        r10 = 0
                        androidx.compose.material3.IconButtonKt.IconButton(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
                        boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r0 == 0) goto Lee
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    Lee:
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ExpandButtonLegacy$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, ((i2 >> 3) & 14) | 196608 | ((i2 >> 9) & 112), 16);
            modifier2 = modifier;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final boolean z4 = z3;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    PlatformSliderColors platformSliderColors2 = platformSliderColors;
                    Modifier modifier3 = modifier2;
                    ColumnVolumeSlidersKt.ExpandButtonLegacy(z, z4, function1, platformSliderColors2, modifier3, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
