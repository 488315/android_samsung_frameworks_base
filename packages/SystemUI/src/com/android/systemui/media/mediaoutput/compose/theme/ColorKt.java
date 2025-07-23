package com.android.systemui.media.mediaoutput.compose.theme;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.res.ColorResources_androidKt;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ColorKt {
    public static final long cardBackground(Composer composer) {
        long Color;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(940439563);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.cardBackground (Color.kt:9)");
        }
        Color color = (Color) composerImpl.consume(CompositionExtKt.LocalBackgroundColor);
        if (color != null) {
            Color = color.value;
        } else {
            Color.Companion.getClass();
            Color = androidx.compose.ui.graphics.ColorKt.Color(Color.m461getRedimpl(r0), Color.m460getGreenimpl(r0), Color.m458getBlueimpl(r0), 0.3f, Color.m459getColorSpaceimpl(Color.Black));
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return Color;
    }

    public static final List cardMediaControlDefaultBackground(Composer composer) {
        long Color;
        long Color2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(597154858);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.cardMediaControlDefaultBackground (Color.kt:13)");
        }
        Color.Companion.getClass();
        long j = Color.Black;
        Color = androidx.compose.ui.graphics.ColorKt.Color(Color.m461getRedimpl(j), Color.m460getGreenimpl(j), Color.m458getBlueimpl(j), 0.2f, Color.m459getColorSpaceimpl(j));
        Color m454boximpl = Color.m454boximpl(Color);
        Color2 = androidx.compose.ui.graphics.ColorKt.Color(Color.m461getRedimpl(j), Color.m460getGreenimpl(j), Color.m458getBlueimpl(j), 0.5f, Color.m459getColorSpaceimpl(j));
        List asList = Arrays.asList(m454boximpl, Color.m454boximpl(Color2));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return asList;
    }

    public static final long dividerColor(Composer composer) {
        long Color;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-983281161);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.dividerColor (Color.kt:26)");
        }
        Color.Companion.getClass();
        Color = androidx.compose.ui.graphics.ColorKt.Color(Color.m461getRedimpl(r0), Color.m460getGreenimpl(r0), Color.m458getBlueimpl(r0), 0.2f, Color.m459getColorSpaceimpl(Color.White));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return Color;
    }

    public static final long mediaPrimaryColor(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1785860630);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.mediaPrimaryColor (Color.kt:16)");
        }
        long Color = androidx.compose.ui.graphics.ColorKt.Color(4294638335L);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return Color;
    }

    public static final long primaryColor(boolean z, boolean z2, Composer composer, int i) {
        long Color;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1974132644);
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = false;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.primaryColor (Color.kt:20)");
        }
        if (z) {
            composerImpl.startReplaceGroup(-1443489236);
            Color = ColorResources_androidKt.colorResource(R.color.sesl_primary_dark_color_dark, composerImpl);
        } else {
            composerImpl.startReplaceGroup(-1443486012);
            long colorResource = ColorResources_androidKt.colorResource(R.color.sesl_white, composerImpl);
            Color = z2 ? androidx.compose.ui.graphics.ColorKt.Color(Color.m461getRedimpl(colorResource), Color.m460getGreenimpl(colorResource), Color.m458getBlueimpl(colorResource), 0.6f, Color.m459getColorSpaceimpl(colorResource)) : colorResource;
        }
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return Color;
    }
}
