package com.android.systemui.common.ui.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.res.ColorResources_androidKt;
import com.android.systemui.common.shared.model.Color;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes.dex */
public abstract class ColorKt {
    public static final long toColor(Color color, Composer composer) {
        long jColorResource;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.common.ui.compose.toColor (Color.kt:27)");
        }
        if (color instanceof Color.Attribute) {
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startReplaceGroup(-436932086);
            jColorResource = com.android.compose.theme.ColorKt.colorAttr(((Color.Attribute) color).attribute, composerImpl);
            composerImpl.end(false);
        } else if (color instanceof Color.Loaded) {
            ComposerImpl composerImpl2 = (ComposerImpl) composer;
            composerImpl2.startReplaceGroup(-436929246);
            composerImpl2.end(false);
            jColorResource = androidx.compose.ui.graphics.ColorKt.Color(((Color.Loaded) color).color);
        } else {
            if (!(color instanceof Color.Resource)) {
                ComposerImpl composerImpl3 = (ComposerImpl) composer;
                composerImpl3.startReplaceGroup(-436934540);
                composerImpl3.end(false);
                throw new NoWhenBranchMatchedException();
            }
            ComposerImpl composerImpl4 = (ComposerImpl) composer;
            composerImpl4.startReplaceGroup(-436926579);
            jColorResource = ColorResources_androidKt.colorResource(((Color.Resource) color).colorRes, composerImpl4);
            composerImpl4.end(false);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return jColorResource;
    }
}
