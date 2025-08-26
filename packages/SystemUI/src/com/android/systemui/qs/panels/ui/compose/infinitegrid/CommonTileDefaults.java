package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;

/* loaded from: classes2.dex */
public final class CommonTileDefaults {
    public static final CommonTileDefaults INSTANCE = new CommonTileDefaults();
    public static final float IconSize;
    public static final float InactiveCornerRadius;
    public static final float LargeTileIconSize;
    public static final float SideIconHeight;
    public static final float SideIconWidth;
    public static final float TileArrangementPadding;
    public static final float TileEndPadding;
    public static final float TileHeight;
    public static final float TileLabelBlurWidth;
    public static final float TileStartPadding;
    public static final float ToggleTargetSize;

    static {
        float f = 32;
        Dp.Companion companion = Dp.Companion;
        IconSize = f;
        LargeTileIconSize = 28;
        SideIconWidth = f;
        SideIconHeight = 20;
        ToggleTargetSize = 56;
        TileHeight = 72;
        TileStartPadding = 8;
        TileEndPadding = 16;
        TileArrangementPadding = 6;
        InactiveCornerRadius = 50;
        TileLabelBlurWidth = f;
    }

    private CommonTileDefaults() {
    }

    public static String longPressLabel(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1840944080);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.infinitegrid.CommonTileDefaults.longPressLabel (CommonTile.kt:350)");
        }
        String strStringResource = StringResources_androidKt.stringResource(R.string.accessibility_long_click_tile, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return strStringResource;
    }
}
