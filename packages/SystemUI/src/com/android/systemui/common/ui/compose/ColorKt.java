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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
