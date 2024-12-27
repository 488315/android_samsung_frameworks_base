package com.android.systemui.media.mediaoutput.compose.theme;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.res.ColorResources_androidKt;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class ColorKt {
    public static final long cardBackground(Composer composer) {
        long Color;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(940439563);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Color color = (Color) composerImpl.consume(CompositionExtKt.LocalBackgroundColor);
        if (color != null) {
            Color = color.value;
        } else {
            Color.Companion.getClass();
            Color = androidx.compose.ui.graphics.ColorKt.Color(Color.m391getRedimpl(r0), Color.m390getGreenimpl(r0), Color.m388getBlueimpl(r0), 0.3f, Color.m389getColorSpaceimpl(Color.Black));
        }
        composerImpl.end(false);
        return Color;
    }

    public static final long dividerColor(Composer composer) {
        long Color;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-983281161);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Color.Companion.getClass();
        Color = androidx.compose.ui.graphics.ColorKt.Color(Color.m391getRedimpl(r0), Color.m390getGreenimpl(r0), Color.m388getBlueimpl(r0), 0.2f, Color.m389getColorSpaceimpl(Color.White));
        composerImpl.end(false);
        return Color;
    }

    public static final long mediaPrimaryColor(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1785860630);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        long Color = androidx.compose.ui.graphics.ColorKt.Color(4294638335L);
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
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (z) {
            composerImpl.startReplaceGroup(-1016395520);
            Color = ColorResources_androidKt.colorResource(R.color.sesl_primary_dark_color_dark, composerImpl);
        } else {
            composerImpl.startReplaceGroup(-1016395419);
            long colorResource = ColorResources_androidKt.colorResource(R.color.sesl_white, composerImpl);
            Color = z2 ? androidx.compose.ui.graphics.ColorKt.Color(Color.m391getRedimpl(colorResource), Color.m390getGreenimpl(colorResource), Color.m388getBlueimpl(colorResource), 0.6f, Color.m389getColorSpaceimpl(colorResource)) : colorResource;
        }
        composerImpl.end(false);
        composerImpl.end(false);
        return Color;
    }
}
