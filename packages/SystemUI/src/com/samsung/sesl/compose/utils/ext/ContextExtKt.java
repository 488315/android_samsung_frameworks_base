package com.samsung.sesl.compose.utils.ext;

import android.content.Context;
import androidx.compose.ui.graphics.Color;

/* loaded from: classes4.dex */
public abstract class ContextExtKt {
    public static final boolean isSystemInDarkTheme(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static final long seslColorResource(int i, Context context) {
        Color colorM3359getColor6MYuD4A = ColorResourceHelper.INSTANCE.m3359getColor6MYuD4A(context, i);
        if (colorM3359getColor6MYuD4A != null) {
            return colorM3359getColor6MYuD4A.value;
        }
        Color.Companion.getClass();
        return Color.Unspecified;
    }
}
