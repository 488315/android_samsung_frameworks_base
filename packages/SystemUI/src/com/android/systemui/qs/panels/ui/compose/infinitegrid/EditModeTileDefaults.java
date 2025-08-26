package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.unit.Dp;
import com.android.compose.theme.AndroidColorScheme;
import com.android.compose.theme.AndroidColorSchemeKt;

/* loaded from: classes2.dex */
public final class EditModeTileDefaults {
    public static final float AvailableTilesGridMinHeight;
    public static final float CurrentTilesGridPadding;
    public static final float GridBackgroundCornerRadius;
    public static final EditModeTileDefaults INSTANCE = new EditModeTileDefaults();

    static {
        Dp.Companion companion = Dp.Companion;
        CurrentTilesGridPadding = 10;
        AvailableTilesGridMinHeight = 200;
        GridBackgroundCornerRadius = 42;
    }

    private EditModeTileDefaults() {
    }

    public static TileColors editTileColors(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(967424235);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.EditModeTileDefaults.editTileColors (EditTile.kt:1055)");
        }
        long j = ((AndroidColorScheme) composerImpl.consume(AndroidColorSchemeKt.LocalAndroidColorScheme)).surfaceEffect2;
        Color.Companion.getClass();
        long j2 = Color.Transparent;
        MaterialTheme.INSTANCE.getClass();
        TileColors tileColors = new TileColors(j, j2, MaterialTheme.getColorScheme(composerImpl).onSurface, MaterialTheme.getColorScheme(composerImpl).onSurface, MaterialTheme.getColorScheme(composerImpl).onSurface, 0.0f, 32, null);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return tileColors;
    }
}
