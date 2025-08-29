package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import com.android.compose.theme.AndroidColorScheme;
import com.android.compose.theme.AndroidColorSchemeKt;
import com.android.systemui.qs.panels.ui.viewmodel.TileUiState;

/* loaded from: classes2.dex */
public final class TileDefaults {
    public static final float ActiveIconCornerRadius;
    public static final float ActiveTileCornerRadius;
    public static final TileDefaults INSTANCE = new TileDefaults();

    static {
        Dp.Companion companion = Dp.Companion;
        ActiveIconCornerRadius = 16;
        ActiveTileCornerRadius = 24;
    }

    private TileDefaults() {
    }

    /* renamed from: animateShapeAsState-rAjV9yQ, reason: not valid java name */
    public static MutableState m2905animateShapeAsStaterAjV9yQ(int i, float f, String str, Composer composer, int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-2105857704);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileDefaults.animateShapeAsState (Tile.kt:492)");
        }
        if (i != 2) {
            CommonTileDefaults.INSTANCE.getClass();
            f = CommonTileDefaults.InactiveCornerRadius;
        }
        final State stateM8animateDpAsStateAjpBEmI = AnimateAsStateKt.m8animateDpAsStateAjpBEmI(f, null, str, composerImpl, 384, 10);
        composerImpl.startReplaceGroup(1894065778);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (objRememberedValue == Composer.Companion.Empty) {
            CornerSize cornerSize = new CornerSize() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileDefaults$animateShapeAsState$1$corner$1
                @Override // androidx.compose.foundation.shape.CornerSize
                /* renamed from: toPx-TmRCtEA */
                public final float mo185toPxTmRCtEA(Density density, long j) {
                    TileDefaults tileDefaults = TileDefaults.INSTANCE;
                    return density.mo58toPx0680j_4(((Dp) stateM8animateDpAsStateAjpBEmI.getValue()).value);
                }
            };
            RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.CircleShape;
            objRememberedValue = SnapshotStateKt.mutableStateOf$default(new RoundedCornerShape(cornerSize, cornerSize, cornerSize, cornerSize));
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        MutableState mutableState = (MutableState) objRememberedValue;
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mutableState;
    }

    public static MutableState animateTileShapeAsState(int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-437175889);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileDefaults.animateTileShapeAsState (Tile.kt:479)");
        }
        MutableState mutableStateM2905animateShapeAsStaterAjV9yQ = m2905animateShapeAsStaterAjV9yQ(i, ActiveTileCornerRadius, "QSTileIconCornerRadius", composerImpl, 3456);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mutableStateM2905animateShapeAsStaterAjV9yQ;
    }

    public static TileColors getColorForState(TileUiState tileUiState, boolean z, Composer composer, int i) {
        TileColors tileColors;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileDefaults.getColorForState (Tile.kt:447)");
        }
        int i2 = tileUiState.state;
        if (i2 == 1) {
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startReplaceGroup(-1554515849);
            if (!tileUiState.handlesSecondaryClick || z) {
                composerImpl.startReplaceGroup(-1554379821);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileDefaults.inactiveTileColors (Tile.kt:424)");
                }
                long j = ((AndroidColorScheme) composerImpl.consume(AndroidColorSchemeKt.LocalAndroidColorScheme)).surfaceEffect2;
                Color.Companion.getClass();
                long j2 = Color.Transparent;
                MaterialTheme.INSTANCE.getClass();
                tileColors = new TileColors(j, j2, MaterialTheme.getColorScheme(composerImpl).onSurface, MaterialTheme.getColorScheme(composerImpl).onSurface, MaterialTheme.getColorScheme(composerImpl).onSurface, 0.0f, 32, null);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-1554454903);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileDefaults.inactiveDualTargetTileColors (Tile.kt:413)");
                }
                StaticProvidableCompositionLocal staticProvidableCompositionLocal = AndroidColorSchemeKt.LocalAndroidColorScheme;
                long j3 = ((AndroidColorScheme) composerImpl.consume(staticProvidableCompositionLocal)).surfaceEffect2;
                long j4 = ((AndroidColorScheme) composerImpl.consume(staticProvidableCompositionLocal)).surfaceEffect3;
                MaterialTheme.INSTANCE.getClass();
                tileColors = new TileColors(j3, j4, MaterialTheme.getColorScheme(composerImpl).onSurface, MaterialTheme.getColorScheme(composerImpl).onSurface, MaterialTheme.getColorScheme(composerImpl).onSurface, 0.0f, 32, null);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
            }
            composerImpl.end(false);
        } else if (i2 != 2) {
            ComposerImpl composerImpl2 = (ComposerImpl) composer;
            composerImpl2.startReplaceGroup(-465780246);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileDefaults.unavailableTileColors (Tile.kt:434)");
            }
            StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = AndroidColorSchemeKt.LocalAndroidColorScheme;
            long j5 = ((AndroidColorScheme) composerImpl2.consume(staticProvidableCompositionLocal2)).surfaceEffect2;
            long j6 = ((AndroidColorScheme) composerImpl2.consume(staticProvidableCompositionLocal2)).surfaceEffect2;
            MaterialTheme.INSTANCE.getClass();
            tileColors = new TileColors(j5, j6, MaterialTheme.getColorScheme(composerImpl2).onSurface, MaterialTheme.getColorScheme(composerImpl2).onSurface, MaterialTheme.getColorScheme(composerImpl2).onSurface, 0.38f, null);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl2.end(false);
        } else {
            ComposerImpl composerImpl3 = (ComposerImpl) composer;
            composerImpl3.startReplaceGroup(-1554730152);
            if (z) {
                composerImpl3.startReplaceGroup(-1554627759);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileDefaults.activeIconTileColors (Tile.kt:390)");
                }
                MaterialTheme.INSTANCE.getClass();
                tileColors = new TileColors(MaterialTheme.getColorScheme(composerImpl3).primary, MaterialTheme.getColorScheme(composerImpl3).primary, MaterialTheme.getColorScheme(composerImpl3).onPrimary, MaterialTheme.getColorScheme(composerImpl3).onPrimary, MaterialTheme.getColorScheme(composerImpl3).onPrimary, 0.0f, 32, null);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl3.end(false);
            } else {
                composerImpl3.startReplaceGroup(-1554700981);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.TileDefaults.activeDualTargetTileColors (Tile.kt:402)");
                }
                long j7 = ((AndroidColorScheme) composerImpl3.consume(AndroidColorSchemeKt.LocalAndroidColorScheme)).surfaceEffect2;
                MaterialTheme.INSTANCE.getClass();
                tileColors = new TileColors(j7, MaterialTheme.getColorScheme(composerImpl3).primary, MaterialTheme.getColorScheme(composerImpl3).onSurface, MaterialTheme.getColorScheme(composerImpl3).onSurface, MaterialTheme.getColorScheme(composerImpl3).onPrimary, 0.0f, 32, null);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl3.end(false);
            }
            composerImpl3.end(false);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return tileColors;
    }
}
