package com.samsung.sesl.compose.utils.ext;

import android.content.Context;
import androidx.compose.ui.graphics.Color;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ContextExtKt {
    public static final boolean isSystemInDarkTheme(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static final long seslColorResource(int i, Context context) {
        Color m3342getColor6MYuD4A = ColorResourceHelper.INSTANCE.m3342getColor6MYuD4A(context, i);
        if (m3342getColor6MYuD4A != null) {
            return m3342getColor6MYuD4A.value;
        }
        Color.Companion.getClass();
        return Color.Unspecified;
    }
}
