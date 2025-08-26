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

/* loaded from: classes2.dex */
public abstract class ColorKt {
    public static final long cardBackground(Composer composer) {
        long jColor;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(940439563);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.cardBackground (Color.kt:9)");
        }
        Color color = (Color) composerImpl.consume(CompositionExtKt.LocalBackgroundColor);
        if (color != null) {
            jColor = color.value;
        } else {
            Color.Companion.getClass();
            long j = Color.Black;
            jColor = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.3f, Color.m461getColorSpaceimpl(j));
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return jColor;
    }

    public static final List cardMediaControlDefaultBackground(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(597154858);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.cardMediaControlDefaultBackground (Color.kt:13)");
        }
        Color.Companion.getClass();
        long j = Color.Black;
        List listAsList = Arrays.asList(Color.m456boximpl(androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.2f, Color.m461getColorSpaceimpl(j))), Color.m456boximpl(androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.5f, Color.m461getColorSpaceimpl(j))));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return listAsList;
    }

    public static final long dividerColor(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-983281161);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.dividerColor (Color.kt:26)");
        }
        Color.Companion.getClass();
        long j = Color.White;
        long jColor = androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.2f, Color.m461getColorSpaceimpl(j));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return jColor;
    }

    public static final long mediaPrimaryColor(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1785860630);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.mediaPrimaryColor (Color.kt:16)");
        }
        long jColor = androidx.compose.ui.graphics.ColorKt.Color(4294638335L);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return jColor;
    }

    public static final long primaryColor(boolean z, boolean z2, Composer composer, int i) {
        long jColor;
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
            jColor = ColorResources_androidKt.colorResource(R.color.sesl_primary_dark_color_dark, composerImpl);
        } else {
            composerImpl.startReplaceGroup(-1443486012);
            long jColorResource = ColorResources_androidKt.colorResource(R.color.sesl_white, composerImpl);
            jColor = z2 ? androidx.compose.ui.graphics.ColorKt.Color(Color.m463getRedimpl(jColorResource), Color.m462getGreenimpl(jColorResource), Color.m460getBlueimpl(jColorResource), 0.6f, Color.m461getColorSpaceimpl(jColorResource)) : jColorResource;
        }
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return jColor;
    }
}
