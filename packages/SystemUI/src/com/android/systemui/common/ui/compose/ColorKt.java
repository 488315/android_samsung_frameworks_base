package com.android.systemui.common.ui.compose;

import android.content.Context;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import com.android.compose.theme.AndroidColorScheme;
import com.android.systemui.common.shared.model.Color;
import kotlin.NoWhenBranchMatchedException;

public abstract class ColorKt {
    public static final long toColor(Color color, Composer composer) {
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (!(color instanceof Color.Attribute)) {
            if (color instanceof Color.Loaded) {
                return androidx.compose.ui.graphics.ColorKt.Color(((Color.Loaded) color).color);
            }
            throw new NoWhenBranchMatchedException();
        }
        int i = ((Color.Attribute) color).attribute;
        OpaqueKey opaqueKey2 = ComposerKt.invocation;
        AndroidColorScheme.Companion companion = AndroidColorScheme.Companion;
        Context context = (Context) ((ComposerImpl) composer).consume(AndroidCompositionLocals_androidKt.LocalContext);
        companion.getClass();
        return AndroidColorScheme.Companion.m820getColorWaAFU9c(i, context);
    }
}
