package com.samsung.sesl.compose.component;

import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.foundation.shape.CornerSizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.unit.Dp;
import com.samsung.sesl.compose.component.tokens.SeslSwitchColorSchemeKeyTokens;
import com.samsung.sesl.compose.foundation.theme.BasicColorSchemeKt;

/* loaded from: classes4.dex */
public final class SeslSwitchDefaults {
    public static final SeslSwitchDefaults INSTANCE = new SeslSwitchDefaults();
    public static final float ThumbDiameter;
    public static final float ThumbPadding;
    public static final RoundedCornerShape ThumbShape;
    public static final float ThumbWidth;
    public static final float TrackHeight;
    public static final RoundedCornerShape TrackShape;
    public static final float TrackWidth;

    static {
        float f = 20;
        Dp.Companion companion = Dp.Companion;
        TrackHeight = f;
        TrackWidth = 35;
        CornerSize CornerSize = CornerSizeKt.CornerSize(50);
        TrackShape = new RoundedCornerShape(CornerSize, CornerSize, CornerSize, CornerSize);
        ThumbWidth = 25;
        ThumbDiameter = f;
        ThumbPadding = 2;
        CornerSize CornerSize2 = CornerSizeKt.CornerSize(50);
        ThumbShape = new RoundedCornerShape(CornerSize2, CornerSize2, CornerSize2, CornerSize2);
    }

    private SeslSwitchDefaults() {
    }

    /* renamed from: colors-oq7We08, reason: not valid java name */
    public static SeslSwitchColors m3343colorsoq7We08(long j, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-613805783);
        long color = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.ThumbOnColor, composerImpl);
        long color2 = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.TrackOffColor, composerImpl);
        long color3 = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.ThumbOffColor, composerImpl);
        long jColor = ColorKt.Color(Color.m463getRedimpl(color), Color.m462getGreenimpl(color), Color.m460getBlueimpl(color), 0.4f, Color.m461getColorSpaceimpl(color));
        long jColor2 = ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.4f, Color.m461getColorSpaceimpl(j));
        long jColor3 = ColorKt.Color(Color.m463getRedimpl(color3), Color.m462getGreenimpl(color3), Color.m460getBlueimpl(color3), 0.4f, Color.m461getColorSpaceimpl(color3));
        long jColor4 = ColorKt.Color(Color.m463getRedimpl(color2), Color.m462getGreenimpl(color2), Color.m460getBlueimpl(color2), 0.4f, Color.m461getColorSpaceimpl(color2));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitchDefaults.colors (Switch.kt:553)");
        }
        SeslSwitchColors seslSwitchColors = new SeslSwitchColors(color, color3, jColor, jColor3, j, color2, jColor2, jColor4, null);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return seslSwitchColors;
    }
}
