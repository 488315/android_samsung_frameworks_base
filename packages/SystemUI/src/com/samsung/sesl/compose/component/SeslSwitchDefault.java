package com.samsung.sesl.compose.component;

import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.theme.ThemeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes4.dex */
public final class SeslSwitchDefault {
    public static final SeslSwitchDefault INSTANCE = new SeslSwitchDefault();

    private SeslSwitchDefault() {
    }

    public final void Thumb(float f, SeslSwitchColors seslSwitchColors, InteractionSource interactionSource, Modifier.Companion companion, boolean z, Composer composer, final int i) {
        int i2;
        SeslSwitchColors seslSwitchColors2;
        InteractionSource interactionSource2;
        boolean z2;
        final float f2;
        final Modifier.Companion companion2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-262487778);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(f) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(seslSwitchColors) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(interactionSource) ? 256 : 128;
        }
        int i3 = i2 | 3072;
        if ((i & 24576) == 0) {
            i3 |= composerImpl.changed(z) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i3 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            seslSwitchColors2 = seslSwitchColors;
            interactionSource2 = interactionSource;
            z2 = z;
            f2 = f;
            companion2 = companion;
        } else {
            Modifier.Companion companion3 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitchDefault.Thumb (Switch.kt:160)");
            }
            if (((Boolean) composerImpl.consume(ThemeKt.LocalOneUiOpenTheme)).booleanValue()) {
                composerImpl.startReplaceGroup(465716824);
                seslSwitchColors2 = seslSwitchColors;
                interactionSource2 = interactionSource;
                z2 = z;
                SwitchKt.SeslOpenThemeSwitchThumb(f, seslSwitchColors2, interactionSource2, companion3, z2, composerImpl, i3 & 65534);
                f2 = f;
                composerImpl.end(false);
            } else {
                seslSwitchColors2 = seslSwitchColors;
                interactionSource2 = interactionSource;
                z2 = z;
                composerImpl.startReplaceGroup(465991546);
                SwitchKt.SeslDefaultSwitchThumb(f, seslSwitchColors2, interactionSource2, companion3, z2, composerImpl, i3 & 65534);
                f2 = f;
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion2 = companion3;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final SeslSwitchColors seslSwitchColors3 = seslSwitchColors2;
            final InteractionSource interactionSource3 = interactionSource2;
            final boolean z3 = z2;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.SeslSwitchDefault$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    SeslSwitchDefault seslSwitchDefault = SeslSwitchDefault.INSTANCE;
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    this.f$0.Thumb(f2, seslSwitchColors3, interactionSource3, companion2, z3, composer2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public final void Track(float f, SeslSwitchColors seslSwitchColors, Modifier.Companion companion, boolean z, Composer composer, final int i) {
        int i2;
        float f2;
        SeslSwitchColors seslSwitchColors2;
        boolean z2;
        final Modifier.Companion companion2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(38396104);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(f) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(seslSwitchColors) ? 32 : 16;
        }
        int i3 = i2 | 384;
        if ((i & 3072) == 0) {
            i3 |= composerImpl.changed(z) ? 2048 : 1024;
        }
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            f2 = f;
            seslSwitchColors2 = seslSwitchColors;
            z2 = z;
            companion2 = companion;
        } else {
            Modifier.Companion companion3 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitchDefault.Track (Switch.kt:186)");
            }
            if (((Boolean) composerImpl.consume(ThemeKt.LocalOneUiOpenTheme)).booleanValue()) {
                composerImpl.startReplaceGroup(170038010);
                f2 = f;
                seslSwitchColors2 = seslSwitchColors;
                z2 = z;
                SwitchKt.SeslOpenThemeSwitchTrack(f2, seslSwitchColors2, companion3, z2, composerImpl, i3 & 8190);
                composerImpl.end(false);
            } else {
                f2 = f;
                seslSwitchColors2 = seslSwitchColors;
                z2 = z;
                composerImpl.startReplaceGroup(170258172);
                SwitchKt.SeslDefaultSwitchTrack(f2, seslSwitchColors2, companion3, z2, composerImpl, i3 & 8190);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion2 = companion3;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final float f3 = f2;
            final SeslSwitchColors seslSwitchColors3 = seslSwitchColors2;
            final boolean z3 = z2;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.SeslSwitchDefault$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    SeslSwitchDefault seslSwitchDefault = SeslSwitchDefault.INSTANCE;
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    this.f$0.Track(f3, seslSwitchColors3, companion2, z3, composer2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
