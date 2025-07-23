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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public static SeslSwitchColors m3326colorsoq7We08(long j, Composer composer) {
        long Color;
        long Color2;
        long Color3;
        long Color4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-613805783);
        long color = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.ThumbOnColor, composerImpl);
        long color2 = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.TrackOffColor, composerImpl);
        long color3 = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.ThumbOffColor, composerImpl);
        Color = ColorKt.Color(Color.m461getRedimpl(color), Color.m460getGreenimpl(color), Color.m458getBlueimpl(color), 0.4f, Color.m459getColorSpaceimpl(color));
        Color2 = ColorKt.Color(Color.m461getRedimpl(j), Color.m460getGreenimpl(j), Color.m458getBlueimpl(j), 0.4f, Color.m459getColorSpaceimpl(j));
        Color3 = ColorKt.Color(Color.m461getRedimpl(color3), Color.m460getGreenimpl(color3), Color.m458getBlueimpl(color3), 0.4f, Color.m459getColorSpaceimpl(color3));
        Color4 = ColorKt.Color(Color.m461getRedimpl(color2), Color.m460getGreenimpl(color2), Color.m458getBlueimpl(color2), 0.4f, Color.m459getColorSpaceimpl(color2));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitchDefaults.colors (Switch.kt:554)");
        }
        SeslSwitchColors seslSwitchColors = new SeslSwitchColors(color, color3, Color, Color3, j, color2, Color2, Color4, null);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return seslSwitchColors;
    }
}
